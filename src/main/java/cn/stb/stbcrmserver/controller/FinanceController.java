package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.base.Right;
import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.service.GoodsService;
import cn.stb.stbcrmserver.service.OrderService;
import cn.stb.stbcrmserver.service.StaffService;
import cn.stb.stbcrmserver.vo.FinanceOrderItem;
import cn.stb.stbcrmserver.vo.FinanceVo;
import cn.stb.stbcrmserver.vo.StaffFinanceReq;
import cn.stb.stbcrmserver.vo.StaffFinanceVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.stb.stbcrmserver.base.RightType.CRM_财务管理;

@RestController
@RequestMapping("/FinanceController")
public class FinanceController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StaffService staffService;

    @RequestMapping("/staffFinance")
    @Right(CRM_财务管理)
    public RespResult staffFinance(@RequestBody StaffFinanceReq req){
        Staff staff = staffService.findStaffByNameOrCode(req.getSearchValue());
        if (staff == null) {
            return RespResult.fail("未找到该员工，请重新搜索！");
        }
        DateTime startDate = DateTime.parse(req.getStartDate()).withTimeAtStartOfDay();
        DateTime endDate = DateTime.parse(req.getEndDate()).plusDays(1).withTimeAtStartOfDay();
        List<Order> orderList = orderService.queryOrders4Finance(staff.getStaffId(), startDate, endDate);

        if (orderList.isEmpty()) {
            return RespResult.fail("该员工暂无订单");
        }

        List<String> orderIds = orderList.stream().map(Order::getOrderId).collect(Collectors.toList());
        List<OrderGoods> orderGoods = orderService.queryOrderGoodsByOrderIds(orderIds);
        List<String> goodsIds = orderGoods.stream().map(OrderGoods::getGoodsId).collect(Collectors.toList());
        List<Goods> goodsList = goodsService.queryGoodsByIds(goodsIds);
        Map<String, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getGoodsId, x -> x));

        Map<String, List<OrderGoods>> orderGoodsMap = orderGoods.stream().collect(Collectors.groupingBy(OrderGoods::getOrderId));
        Map<String, Integer> goodsFeeMap = new HashMap<>();
        // 订单成本
        orderGoodsMap.forEach((k, v) -> {
            int sum = 0;
            for (OrderGoods og : v) {
                sum += og.getAmount() * goodsMap.get(og.getGoodsId()).getGoodsPrice();
            }
            goodsFeeMap.put(k, sum);
        });

        List<FinanceOrderItem> orderItems = orderList.stream().map(y -> FinanceOrderItem.builder()
                .orderId(y.getOrderId())
                .orderCode(y.getOrderCode())
                .totalFee(y.getTotalFee())
                .goodsFee(goodsFeeMap.get(y.getOrderId()))
                .build()).collect(Collectors.toList());

        // 所有订单的金额总和
        int gross = orderList.stream().filter(x -> x.getTotalFee() >= goodsFeeMap.get(x.getOrderId())).mapToInt(Order::getTotalFee).sum();

        // 所有订单的总成本
        int cost = orderItems.stream().filter(x -> x.getTotalFee() >= x.getGoodsFee()).mapToInt(x -> x.getGoodsFee()).sum();

        FinanceVo financeVo = FinanceVo.builder()
                .staffCode(staff.getStaffCode())
                .staffName(staff.getStaffName())
                .gross(gross)
                .cost(cost)
                .orderItems(orderItems)
                .build();
        return RespResult.ok("success", financeVo);
    }


}

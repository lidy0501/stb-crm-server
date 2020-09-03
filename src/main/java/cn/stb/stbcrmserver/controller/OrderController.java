package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.Page;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.service.OrderService;
import cn.stb.stbcrmserver.service.StaffService;
import cn.stb.stbcrmserver.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/OrderController")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private StaffService staffService;

    /**
     * 显示所有订单(员工只能看到自己的,老板能看到所有的)
     * @return
     */
    @RequestMapping("/queryAllOrder")
    @LoginIgnore
    public ListVo<OrderListVo> queryAllOrder(@RequestBody ListReq req) {
        int startIndex = req.getStartIndex();
        Page page = new Page(startIndex, 10);
        List<Order> orderList = orderService.queryAllOrder(req.getSearchValue());
        page.setTotalRows(orderList.size());
        orderList = orderList.stream().skip(startIndex).limit(10).collect(Collectors.toList());
        // 跟单人信息
        List<Staff> staffList = staffService.queryAllStaffIgnoreState();
        Map<String, Staff> staffMap = staffList.stream().collect(Collectors.toMap(Staff::getStaffId, x -> x));
        // 查询商品信息
        List<String> orderIds = orderList.stream().map(Order::getOrderId).collect(Collectors.toList());
        List<OrderGoodsItem> goodsItemList = orderService.queryOrderGoodsInfoByOrderIds(orderIds);
        Map<String, List<OrderGoodsItem>> goodsItemListMap = goodsItemList.stream().collect(Collectors.groupingBy(OrderGoodsItem::getOrderId));
        List<OrderListVo> orders = orderList.stream()
                .map(x -> OrderListVo.convert(x, staffMap.get(x.getOperatorId()).getStaffName(), goodsItemListMap.get(x.getOrderId())))
                .collect(Collectors.toList());
        ListVo<OrderListVo> data = new ListVo(orders, page);
        return data;
    }

    /**
     * 添加一个订单
     * @param req
     * @return
     */
    @RequestMapping("/addOrder")
    @LoginIgnore
    public RespResult addOrder(@RequestBody AddOrderReq req){
        return orderService.addOrder(req);
    }

    @RequestMapping("/modifyOrderStateAndDeleteById")
    @LoginIgnore
    public RespResult modifyOrderStateAndDeleteById(@RequestBody Order order){
        return orderService.modifyOrderStateAndDeleteById(order);
    }

    /**
     * 通过订单Id删除订单
     * @param orderId
     * @return
     */
    @RequestMapping("/deleteOrder/{orderId}")
    @LoginIgnore
    public RespResult deleteOrder(@PathVariable String orderId){
        return orderService.deleteOrder(orderId);
    }

    /**
     * 通过客户姓名模糊查询到客户ID,客户姓名,客户所属公司
     * @param userName
     * @return
     */
    @RequestMapping("/selectAllUserVoLikeUserName/{userName}")
    @LoginIgnore
    public List<UserVo> selectAllUserVoLikeUserName(@PathVariable String userName){
        return orderService.selectAllUserVoLikeUserName(userName);
    }

    /**
     * 通过订单Id查询当前订单的详细信息
     * @param orderId
     * @return
     */
    @RequestMapping("/selectOrderByOrderId/{orderId}")
    @LoginIgnore
    public Order selectOrderByOrderId(@PathVariable String orderId){
        return orderService.selectOrderByOrderId(orderId);
    }

    /**
     * 修改订单
     * @param order
     * @return
     */
    @RequestMapping("/modifyOrderByUserIdAndOperatorId")
    @LoginIgnore
    public RespResult modifyOrderByUserIdAndOperatorId(@RequestBody Order order){
        return orderService.modifyOrderByUserIdAndOperatorId(order);
    }

    /**
     * 修改订单状态
     */
    @RequestMapping("/changeOrderState/{orderId}/{orderState}")
    @LoginIgnore
    public RespResult changeOrderState(@PathVariable String orderId, @PathVariable String orderState) {
        return orderService.changeOrderState(orderId, orderState);
    }

    @RequestMapping("/queryAllSelectGoodsVo")
    @LoginIgnore
    public List<SelectGoodsVo> queryAllSelectGoodsVo() {
        return orderService.queryAllSelectGoodsVo();
    }
}

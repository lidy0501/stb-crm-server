package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.GoodsDao;
import cn.stb.stbcrmserver.dao.OrderDao;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;

    public List<Order> queryAllOrder(String searchValue) {
        Staff staff = AcContext.getStaff();
        Map map = new HashMap();
        map.put("operatorId", staff.getStaffId());
        map.put("staffType", staff.getStaffType());
        map.put("searchValue", searchValue);
        return orderDao.queryAllOrder(map);
    }

    /**
     * 新增订单
     * @param req
     * @return
     */
    @Transactional
    public RespResult addOrder(AddOrderReq req) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 校验订单编号是否会重复
        Order orderByCode = orderDao.findOrderByCode(req.getOrderCode());
        if (orderByCode != null) {
            return RespResult.fail("订单编号已经存在，请重新输入");
        }
        String operatorId = AcContext.getStaffId();
        Order order = Order.convert(req);
        // 落订单主表 CRM_ORDER
        orderDao.addOrder(order);
        // 落订单商品关系表CRM_ORDER_GOODS
        List<OrderGoods> orderGoodsList = req.getGoodsList().stream()
                .map(x -> OrderGoods.convert(order.getOrderId(), x)).collect(Collectors.toList());
        orderDao.addOrderGoods(orderGoodsList);
        return RespResult.ok("保存成功");
    }

    public RespResult modifyOrderStateAndDeleteById(Order order) {
        String operatorId = AcContext.getStaffId();
        order.setOperatorId(operatorId);
        int effectNum = orderDao.modifyOrderStateAndDeleteById(order);
        if (effectNum > 0 ) return RespResult.ok("修改信息成功!");
        return RespResult.fail("修改信息失败!");
    }

    public RespResult deleteOrder(String orderId) {
        String operatorId = AcContext.getStaffId();
        Map map = new HashMap();
        map.put("operatorId",operatorId);
        map.put("orderId",orderId);
        int effectNum = orderDao.deleteOrder(map);
        if(effectNum > 0 ) return RespResult.ok("删除订单成功!");
        return RespResult.fail("删除失败!");
    }

    public List<UserVo> selectAllUserVoLikeUserName(String userName) {
        return orderDao.selectAllUserVoLikeUserName(userName);
    }

    public OrderDetailsVo selectOrderByOrderId(String orderId) {
        Order order = orderDao.selectOrderByOrderId(orderId);
        List<OrderGoodsItem> goodsItemList = this.queryOrderGoodsInfoByOrderIds(Collections.singletonList(orderId));
        return OrderDetailsVo.convert(order, goodsItemList);
    }

    public RespResult modifyOrderByUserIdAndOperatorId(Order order) {
        Staff staff = AcContext.getStaff();
        if ((staff.getStaffId() == order.getOperatorId()) || ("0".equals(staff.getStaffType()))){
            int effectNum = orderDao.modifyOrderByUserIdAndOperatorId(order);
            if(effectNum>0) return RespResult.ok("修改订单信息成功!");
            return RespResult.fail("修改订单信息失败!");
        }
        return RespResult.fail("修改失败!");
    }

    /**
     * 修改订单状态
     * @param orderId
     * @param orderState
     * @return
     */
    public RespResult changeOrderState(String orderId, String orderState) {
        Map map = new LinkedHashMap();
        map.put("orderId", orderId);
        map.put("orderState", orderState);
        orderDao.changeOrderState(map);
        return RespResult.ok("操作成功");
    }

    /**
     * 根据orderId 查询商品信息
     * @return
     */
    public List<OrderGoodsItem> queryOrderGoodsInfoByOrderIds(List<String> orderIds) {
        return orderDao.queryOrderGoodsInfoByOrderIds(orderIds);
    }


    public List<SelectGoodsVo> queryAllSelectGoodsVo() {
        return goodsDao.queryAllSelectGoodsVo();
    }

    public RespResult saveEditOrder(Order order) {
        // 编辑的时候要变更operatorId !!!!
        int effectNum = orderDao.saveEditOrder(order);
        if (effectNum > 0) {
            return RespResult.ok("保存成功");
        }
        return RespResult.fail("保存失败");
    }

    public List<Order> queryAllDoneOrderByStaffId(StaffFinanceReq Req) {
        Map<String,String> map = new HashMap();
        map.put("startDate",Req.getStartDate());
        map.put("endDate",Req.getEndDate());
        map.put("staffId",Req.getStaffId());
        List<Order> orderList = orderDao.queryAllDoneOrderByStaffId(map);
        return orderList;
    }
}

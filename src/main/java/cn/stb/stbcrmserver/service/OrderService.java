package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.ContractDao;
import cn.stb.stbcrmserver.dao.GoodsDao;
import cn.stb.stbcrmserver.dao.OrderDao;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.Contract;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.vo.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private StaffDao staffDao;

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

    @Transactional
    public RespResult deleteOrder(String orderId) {
        // 合同中引用的订单不能删除（合同中引用的是订单的code）
        Order order = orderDao.selectOrderByOrderId(orderId);
        Contract contract = contractDao.findContractByOrderCode(order.getOrderCode());
        if (contract != null) {
            return RespResult.fail("该订单已被绑定在合同中，不能删除");
        }
        String operatorId = AcContext.getStaffId();
        Map map = new HashMap();
        map.put("operatorId",operatorId);
        map.put("orderId",orderId);
        // 删除订单
        int effectNum1 = orderDao.deleteOrder(map);
        // 删除对应的商品 CRM_ORDER_GOODS
        int effectNum2 = orderDao.deleteOrderGoodsByOrderId(orderId);
        if(effectNum1 > 0 && effectNum2 > 0 ) return RespResult.ok("删除订单成功!");
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

    public List<StaffFinanceVo> queryAllDoneOrderByStaffId(StaffFinanceReq Req) {
        Map<String,String> map = new HashMap();
        map.put("startDate",Req.getStartDate());
        map.put("endDate",Req.getEndDate());
        map.put("searchValue",Req.getSearchValue());
        //获取所有已经完成的订单
        List<Order> orderList = orderDao.queryAllDoneOrderByStaffId(map);






        //获取当前订单所有操作员ID
        List<String> operatorId = orderList.stream().map(Order::getOperatorId).collect(Collectors.toList());
        //获取当前订单所有订单Id
        List<String> orderId = orderList.stream().map(Order::getOrderId).collect(Collectors.toList());
        //通过操作员Id查询所以员工
        List<Staff> staffList = staffDao.queryStaffsByIds(operatorId);
        Map<String, Staff> staffMap = staffList.stream().collect(Collectors.toMap(Staff::getStaffId,x -> x));
        //通过订单ID查询所有商品信息
        List<OrderGoodsItem> orderGoodsItemList = orderDao.queryOrderGoodsInfoByOrderIds(orderId);
        Map<String,OrderGoodsItem> orderGoodsItemMap = orderGoodsItemList.stream().collect(Collectors.toMap(OrderGoodsItem::getOrderId,x -> x));
        //
        List<StaffFinanceVo> data = orderList.stream().map(order ->
                StaffFinanceVo.convert(staffMap.get(order.getOperatorId()), order , 0,orderGoodsItemMap.get(order.getOrderId()))).collect(Collectors.toList());
        return data;
    }

    /**
     * 根据条件查询订单集合
     */
    public List<Order> queryOrders4Finance(String staffId, DateTime startDate, DateTime endDate) {
        Map<String,Object> map = new HashMap();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("operatorId", staffId);
        //获取所有已经完成的订单
        List<Order> orderList = orderDao.queryAllDoneOrderByStaffId(map);
        return orderList;
    }

    /**
     * 根据orderIds 查询所有的OrderGoodsl
     * @param orderIds
     * @return
     */
    public List<OrderGoods> queryOrderGoodsByOrderIds(List<String> orderIds) {
        return orderDao.queryOrderGoodsByOrderIds(orderIds);
    }
}

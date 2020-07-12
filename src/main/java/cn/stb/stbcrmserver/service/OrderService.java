package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.OrderDao;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public List<Order> queryAllOrder(String searchValue) {
        Staff staff = AcContext.getStaff();
        Map map = new HashMap();
        map.put("operatorId", staff.getStaffId());
        map.put("staffType", staff.getStaffType());
        map.put("searchValue", searchValue);
        return orderDao.queryAllOrder(map);
    }

    public RespResult addOrder(Order order) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String operatorId = AcContext.getStaffId();
        if((!StringUtils.isEmpty(order.getProductName()))){
            order.setOrderId(UUIDUtil.getNumId());
            order.setOperatorId(operatorId);
            order.setOrderState("0");
            int effectNum = orderDao.addOrder(order);
            if (effectNum > 0) {
                return RespResult.ok("添加订单成功!");
            } else {
                return RespResult.fail("添加订单失败!");
            }
        } else {
            return RespResult.fail("产品名称不能为空!");
        }
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

    public Order selectOrderByOrderId(String orderId) {
        return orderDao.selectOrderByOrderId(orderId);
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
}

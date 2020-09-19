package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.vo.OrderGoodsItem;
import cn.stb.stbcrmserver.vo.OrderListVo;
import cn.stb.stbcrmserver.vo.StaffFinanceVo;
import cn.stb.stbcrmserver.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {
    List<Order> queryAllOrder(Map map);

    int addOrder(Order order);

    int modifyOrderStateAndDeleteById(Order order);

    int deleteOrder(Map map);

    List<UserVo> selectAllUserVoLikeUserName(String userName);

    Order selectOrderByOrderId(String orderId);

    int modifyOrderByUserIdAndOperatorId(Order order);

    int changeOrderState(Map map);

    int updateOrder(Order order);

    Order findOrderByCode(String orderCode);

    int addOrderGoods(List<OrderGoods> orderGoodsList);

    List<OrderGoodsItem> queryOrderGoodsInfoByOrderIds(List<String> orderIds);

    int saveEditOrder(Order order);

    List<Order> queryAllDoneOrderByStaffId(Map map);

    List<Order> queryOrdersByLikeCode(Map map);

    int deleteOrderGoodsByOrderId(String orderId);
}

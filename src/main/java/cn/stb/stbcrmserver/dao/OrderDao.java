package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Order;
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
}

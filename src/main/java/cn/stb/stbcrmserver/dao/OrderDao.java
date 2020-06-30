package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {
    List<Order> queryAllOrder(Map map);

    int addOrder(Order order);

    int modifyOrderStateAndDeleteById(Order order);

    int deleteOrder(Map map);
}

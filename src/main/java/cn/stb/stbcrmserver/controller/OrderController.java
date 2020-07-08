package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.User;
import cn.stb.stbcrmserver.service.OrderService;
import cn.stb.stbcrmserver.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/OrderController")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 显示所有订单(员工只能看到自己的,老板能看到所有的)
     * @return
     */
    @RequestMapping("/queryAllOrder")
    @LoginIgnore
    public List<Order> queryAllOrder(){
        return orderService.queryAllOrder();
    }

    /**
     * 添加一个订单
     * @param order
     * @return
     */
    @RequestMapping("/addOrder")
    @LoginIgnore
    public RespResult addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
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
}

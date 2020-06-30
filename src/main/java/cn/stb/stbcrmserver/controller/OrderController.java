package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/OrderController")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/queryAllOrder")
    public List<Order> queryAllOrder(){
        return orderService.queryAllOrder();
    }

    @RequestMapping("/addOrder")
    public RespResult addOrder(Order order){
        return orderService.addOrder(order);
    }

    @RequestMapping("/modifyOrderStateAndDeleteById")
    @LoginIgnore
    public RespResult modifyOrderStateAndDeleteById(Order order){
        return orderService.modifyOrderStateAndDeleteById(order);
    }
    @RequestMapping("/deleteOrder/{orderId}")
    public RespResult deleteOrder(@PathVariable String orderId){
        return orderService.deleteOrder(orderId);
    }
}

package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.Right;
import cn.stb.stbcrmserver.service.OrderService;
import cn.stb.stbcrmserver.vo.StaffFinanceReq;
import cn.stb.stbcrmserver.vo.StaffFinanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.stb.stbcrmserver.base.RightType.CRM_财务管理;

@RestController
@RequestMapping("/FinanceController")
public class FinanceController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/staffFinance")
    @Right(CRM_财务管理)
    public List<StaffFinanceVo> staffFinance(@RequestBody StaffFinanceReq Req){
        return orderService.queryAllDoneOrderByStaffId(Req);
    }
}

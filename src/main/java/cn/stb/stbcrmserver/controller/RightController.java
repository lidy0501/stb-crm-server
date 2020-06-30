package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.service.RightService;
import cn.stb.stbcrmserver.vo.RightVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/RightController")
public class RightController {
    @Autowired
    private RightService rightService;

    @RequestMapping("/getAll")
    public List<RightVo> getAll(){
        return rightService.getAll();
    }
}

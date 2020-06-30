package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/GoodsController")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 显示所有商品
     * @return
     */
    @RequestMapping("/queryAllGoods")
    public List<Goods> queryAllGoods(){
        return goodsService.queryAllGoods();
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    public RespResult addGoods(Goods goods){
        return goodsService.addGoods(goods);
    }

    /**
     * 删除商品
     * @param goods
     * @return
     */
    @RequestMapping("/deleteGoodsById/{goodsId}")
    public RespResult deleteGoodsById(@PathVariable String goodsId){
        return goodsService.deleteGoodsById(goodsId);
    }
}
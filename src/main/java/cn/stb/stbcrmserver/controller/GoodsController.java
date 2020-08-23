package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Sku;
import cn.stb.stbcrmserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    @LoginIgnore
    public List<Goods> queryAllGoods(){
        return goodsService.queryAllGoods();
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    @LoginIgnore
    public RespResult addGoods(@RequestBody Goods goods){
        return goodsService.addGoods(goods);
    }

    /**
     * 删除商品
     * @param goods
     * @return
     */
    @RequestMapping("/deleteGoodsById/{goodsId}")
    @LoginIgnore
    public RespResult deleteGoodsById(@PathVariable String goodsId){
        return goodsService.deleteGoodsById(goodsId);
    }

    /**
     * 添加商品SKU
     * @param sku
     * @return
     */
    @RequestMapping("/addSku")
    @LoginIgnore
    public RespResult addSku(@RequestBody Sku sku){
        return goodsService.addSku(sku);
    }

    @RequestMapping("/deleteSkuById/{skuId}")
    public RespResult deleteSkuById(@PathVariable String skuId){
        return goodsService.deleteSkuById(skuId);
    }
}

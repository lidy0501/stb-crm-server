package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.base.Right;
import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Sku;
import cn.stb.stbcrmserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.stb.stbcrmserver.base.RightType.CRM_商品管理;

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
    @Right(CRM_商品管理)
    public List<Goods> queryAllGoods(){
        return goodsService.queryAllGoods();
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    @Right(CRM_商品管理)
    public RespResult addGoods(@RequestBody Goods goods){
        return goodsService.addGoods(goods);
    }

    /**
     * 删除商品
     */
    @RequestMapping("/deleteGoodsById/{goodsId}")
    @Right(CRM_商品管理)
    public RespResult deleteGoodsById(@PathVariable String goodsId){
        return goodsService.deleteGoodsById(goodsId);
    }

    /**
     * 添加商品SKU
     * @param skuList
     * @return
     */
    @RequestMapping("/addSku")
    @Right(CRM_商品管理)
    public RespResult addSku(@RequestBody List<Sku> skuList){
        return goodsService.addSku(skuList);
    }

    @RequestMapping("/deleteSkuById/{skuId}")
    @Right(CRM_商品管理)
    public RespResult deleteSkuById(@PathVariable String skuId){
        return goodsService.deleteSkuById(skuId);
    }

    @RequestMapping("/querySkuList")
    @Right(CRM_商品管理)
    public List<Sku> querySkuList() {
        return goodsService.querySkuList();
    }

}

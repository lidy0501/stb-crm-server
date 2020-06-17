package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String GoodsId;//商品ID
    private String GoodsName;//商品名称
    private String GoodsPrice;//商品价格
    private String GoodsSpe;//商品规格
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注


}

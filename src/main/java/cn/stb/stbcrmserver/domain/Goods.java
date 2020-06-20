package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 商品表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String goodsId;//商品ID
    private String goodsName;//商品名称
    private String goodsPrice;//商品价格
    private String goodsSpe;//商品规格
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注


}

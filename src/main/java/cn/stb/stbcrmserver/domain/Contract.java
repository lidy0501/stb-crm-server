package cn.stb.stbcrmserver.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * 合同表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    private String contractId;//合同ID
    private String contractCode;//合同编号
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注
}

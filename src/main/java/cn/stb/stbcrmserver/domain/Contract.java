package cn.stb.stbcrmserver.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 合同表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    private String ContractId;//合同ID
    private String ContractCode;//合同编号
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注
}

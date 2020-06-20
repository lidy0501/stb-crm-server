package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 员工角色表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffRole {
    private String staffId;//员工ID
    private String roleId;//角色ID
    private String roleState;//角色状态：0无效、1有效
    private String operatorId;//操作人
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注
}

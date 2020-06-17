package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 员工角色表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffRole {
    private String StaffId;//员工ID
    private String RoleId;//角色ID
    private String RoleState;//角色状态：0无效、1有效
    private String OperatorId;//操作人
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注
}

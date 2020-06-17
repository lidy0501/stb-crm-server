package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色信息表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String RoleId;//角色ID
    private String RoleName;//角色名称
    private String RoleDesc;//角色描述
    private String RoleState;//角色状态：1有效、0无效
    private String OperatorId;//操作人
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注
}

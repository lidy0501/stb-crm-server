package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 角色信息表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String roleId;//角色ID
    private String roleName;//角色名称
    private String roleDesc;//角色描述
    private String roleState;//角色状态：1有效、0无效
    private String operatorId;//操作人
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注
}

package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 数据库表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private String StaffId;//员工ID
    private String StaffName;//员工姓名
    private String StaffCode;//员工工号,用于账号登录
    private String PassWord;//员工密码
    private String StaffPhone;//员工电话
    private String StaffType;//员工类型:0系统管理员，1普通员工
    private String StaffState;//员工状态：1正常、9离职
    private String StaffEmail;//邮箱
    private String OperatorId;//操作人
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注

}

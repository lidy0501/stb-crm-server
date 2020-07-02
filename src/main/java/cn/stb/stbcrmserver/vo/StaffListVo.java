package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffListVo {
    private String staffId;//员工ID
    private String staffName;//员工姓名
    private String staffCode;//员工工号,用于账号登录
    private String password;//员工密码
    private String staffPhone;//员工电话
    private String staffType;//员工类型:0系统管理员，1普通员工
    private String staffState;//员工状态：1正常、9离职
    private String staffEmail;//邮箱
    private String operatorId;//操作人
    private String remark;//备注
    private String rightNames;
}

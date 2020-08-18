package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 新增员工的请求bean
 */
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AddStaffReq {
    private String staffId;//员工ID
    private String staffName;//员工姓名
    private String staffCode;//员工工号,用于账号登录
    private String passWord;//员工密码
    private String staffPhone;//员工电话
    private String staffType;//员工类型:0系统管理员，1普通员工
    private String staffEmail;//邮箱
    private String remark;//备注
    private List<RightVo> rightVoList;

    public static AddStaffReq convert(Staff staff, List<RightVo> rightVoList) {
        return AddStaffReq.builder()
                .staffId(staff.getStaffId())
                .staffName(staff.getStaffName())
                .staffCode(staff.getStaffCode())
                .passWord(staff.getPassword())
                .staffPhone(staff.getStaffPhone())
                .staffType(staff.getStaffType())
                .staffEmail(staff.getStaffEmail())
                .remark(staff.getRemark())
                .rightVoList(rightVoList)
                .build();
    }

}

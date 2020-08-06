package cn.stb.stbcrmserver.domain;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.AddStaffReq;
import cn.stb.stbcrmserver.vo.RightVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.List;


/**
 * 数据库表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private String staffId;//员工ID
    private String staffName;//员工姓名
    private String staffCode;//员工工号,用于账号登录
    private String password;//员工密码
    private String staffPhone;//员工电话
    private String staffType;//员工类型:0系统管理员，1普通员工
    private String staffState;//员工状态：1正常、9离职
    private String staffEmail;//邮箱
    private String operatorId;//操作人
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注

    public static Staff convert(AddStaffReq req) {
        return Staff.builder()
                .staffId(UUIDUtil.getNumId())
                .staffName(req.getStaffName())
                .staffCode(req.getStaffCode())
                .password(req.getPassWord())
                .staffPhone(req.getStaffPhone())
                .staffType(getStaffType(req.getRightVoList()))
                .staffState("1")
                .staffEmail(req.getStaffEmail())
                .operatorId(AcContext.getStaffId())
                .remark(req.getRemark())
                .build();
    }

    /** 校验员工状态是否正常 */
    public boolean stateIsOk() {
        return "1".equals(staffState);
    }

    /** 判断新增的是不是分销员 staffType = 2 是普通员工 ，1 是分销员 */
    public static String getStaffType(List<RightVo> rightVos) {
        for (RightVo rightVo : rightVos) {
            if ("员工管理".equals(rightVo.getRightName())) return "1";
        }
        return "2";
    }

}

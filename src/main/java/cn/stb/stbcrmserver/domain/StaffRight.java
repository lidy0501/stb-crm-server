package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 角色权限表的bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffRight {
    private String staffId;//员工ID
    private String rightId;//权限ID
    private String rightState;//权限状态：1有效、0无效
    private String operatorId;//操作人
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注

}

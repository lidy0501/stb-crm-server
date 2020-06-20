package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 权限表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Right {
    private String rightId;//权限ID
    private String rightCode;//权限编码
    private String rightName;//权限名称
    private String rightState;//权限状态,1有效,0无效
    private String rightDesc;//权限说明
    private String rightUrl;//URL
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注

}

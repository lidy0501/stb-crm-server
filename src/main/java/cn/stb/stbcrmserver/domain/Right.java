package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Right {
    private String RightId;//权限ID
    private String RightCode;//权限编码
    private String RightName;//权限名称
    private String RightState;//权限状态,1有效,0无效
    private String RightDesc;//权限说明
    private String RightUrl;//URL
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注

}

package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 客户信息表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String UserId;//客户ID
    private String UserName;//客户姓名
    private String UserPhone;//客户电话
    private String UserEmail;//客户邮箱
    private String Company;//客户公司名称
    private String UserType;//客户类型：0公共区域、1私有区域
    private String UserState;//客户状态：0无效 1有效
    private String StaffId;//创建人
    private Date CreateTime;//创建时间
    private Date UpdateTime;//更新时间
    private String Remark;//备注
}

package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import cn.afterturn.easypoi.excel.annotation.Excel;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserListVo {
    private String userId;//客户ID
    @Excel(name = "编码", width = 20)
    private String userCode;//客户编码
    @Excel(name = "姓名", width = 30)
    private String userName;//客户姓名
    @Excel(name = "手机号", width = 30)
    private String userPhone;//客户电话
    @Excel(name = "邮箱", width = 20)
    private String userEmail;//客户邮箱
    @Excel(name = "公司", width = 40)
    private String company;//客户公司名称
    @Excel(name = "职位", width = 20)
    private String post;// 公司职位
    @Excel(name = "跟进人", width = 20)
    private String operatorName;//跟进人
    @Excel(name = "跟今天数", width = 20)
    private int followDays;//跟进天数
    private String region; // 客户所在地区

    public static UserListVo convert(User user, String operatorName) {
        return UserListVo.builder()
                .userId(user.getUserId())
                .userCode(user.getUserCode())
                .userName(user.getUserName())
                .userPhone(user.getUserPhone())
                .post(user.getPost())
                .company(user.getCompany())
                .userEmail(user.getUserEmail())
                .operatorName(operatorName)
                .followDays(getFollowDays(user.getFollowTime()))
                .region(user.getRegion())
                .build();
    }

    public static int getFollowDays(String startTime) {
        DateTime start = DateTime.parse(startTime);
        DateTime currentTime = DateTime.now();
        return Days.daysBetween(start, currentTime).getDays();
    }




}

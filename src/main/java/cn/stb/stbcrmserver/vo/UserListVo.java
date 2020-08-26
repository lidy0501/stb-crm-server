package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserListVo {
    private String userId;//客户ID
    private String userCode;//客户编码
    private String userName;//客户姓名
    private String userPhone;//客户电话
    private String post;// 公司职位
    private String company;//客户公司名称
    private String userEmail;//客户邮箱
    private String operatorName;//跟进人
    private int followDays;//跟进天数

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
                .build();
    }

    public static int getFollowDays(String startTime) {
        DateTime start = DateTime.parse(startTime);
        DateTime currentTime = DateTime.now();
        return Days.daysBetween(start, currentTime).getDays();
    }




}

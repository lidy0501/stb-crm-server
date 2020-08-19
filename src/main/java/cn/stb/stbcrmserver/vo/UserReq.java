package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    private String userId;//客户Id
    private String StaffId;//员工Id
}

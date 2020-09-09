package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffFinanceReq {
    private String startDate;//开始时间
    private String endDate;//结束时间
    private String staffId;//员工Id

}

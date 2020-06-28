package cn.stb.stbcrmserver.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
public enum RightType {
    CRM_员工管理("RI000"),
    CRM_客户管理("RI001"),
    CRM_订单管理("RI002");

    @Getter
    private String code;
}

package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Menu {
    private String menuId;
    private String menuName;
    private String menuState;
    private String menuDesc;
    private String menuUrl;
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注
}

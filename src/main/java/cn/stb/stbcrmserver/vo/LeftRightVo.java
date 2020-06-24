package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class LeftRightVo {
    private String rightId;//权限ID
    private String rightName;//权限名称
    private List<Menu> menus;
}

package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.base.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ListVo<T> {
    private List<T> list;
    private Page page;
}

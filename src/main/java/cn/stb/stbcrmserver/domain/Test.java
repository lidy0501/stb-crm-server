package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库表的bean
 */
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Test {
	private String id;
	private String name;
	private String testPhone;
}

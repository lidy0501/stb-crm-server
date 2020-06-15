package cn.stb.stbcrmserver.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespResult {
  public static final int FAIL_CODE = -1;
  public static final int SUCCESS_CODE = 0;
  public static final int CONFIRM_CODE = -2;

  public static final String NETWORK_ERROR_MSG = "网络开小差了，请稍后再试";

  /**
   * 返回编码,0为成功，-1为失败，不同正数为其它成功情况，不同负数为其它成功信息
   */
  private int code;

  /**
   * 主要为失败时，携带的失败信息.
   *不建议用这个来携带成功时的其它信息.
   */
  private String message;

  /**
   * 携带的数据
   */
  private Object data;
  private Object more;

  /**
   * 失败的返回对象
   * @param message 失败消息
   * @return 返回对象
   */
  public static RespResult fail(String message) {
    return RespResult.builder().code(FAIL_CODE).message(message).build();
  }

  /**
   * 失败的返回对象
   * @param message 失败消息
   * @param data 数据
   * @return 返回对象
   */
  public static RespResult fail(String message, Object data) {
    return RespResult.builder().code(FAIL_CODE).message(message).data(data).build();
  }

  /**
   * 其他失败的返回对象
   * @param code 其他失败的响应码
   * @param message 失败消息
   * @return
   */
  public static RespResult fail(int code, String message) {
    return RespResult.builder().code(code).message(message).build();
  }

  /**
   * 确认失败，刷新页面
   * @param message 失败信息
   * @return
   */
  public static RespResult reload(String message) {
    return RespResult.builder().code(CONFIRM_CODE).message(message).build();
  }

  /**
   * 成功的返回对象
   * @param message 成功信息
   * @return
   */
  public static RespResult ok(String message) {
    return RespResult.builder().code(SUCCESS_CODE).message(message).build();
  }

  /**
   * 成功的返回对象
   * @param message
   * @param data
   * @return
   */
  public static RespResult ok(String message, Object data) {
    return RespResult.builder().code(SUCCESS_CODE).message(message).data(data).build();
  }

  /**
   * 成功的返回对对象
   * @param message
   * @param data
   * @param more
   * @return
   */
  public static RespResult ok(String message, Object data, Object more) {
    return RespResult.builder().code(SUCCESS_CODE).message(message).data(data).more(more).build();
  }

  /**
   * 成功的返回对象
   * @param code 成功码
   * @param message 成功信息
   * @param data 数据
   * @return
   */
  public static RespResult okWithCode(int code, String message, Object data) {
    return RespResult.builder().code(code).message(message).data(data).build();
  }

  /**
   * 失败的返回对象
   * @param code
   * @param message
   * @param data
   * @return
   */
  public static RespResult failWithCode(int code, String message, Object data) {
    return RespResult.builder().code(code).message(message).data(data).build();
  }

  /**
   * 返回是否是成功。
   *
   * @return true 成功
   */
  public boolean isNotOk() {
    return code < SUCCESS_CODE;
  }

  public boolean ok() {
    return code == SUCCESS_CODE;
  }

  public boolean fail() {
    return code == FAIL_CODE;
  }

}

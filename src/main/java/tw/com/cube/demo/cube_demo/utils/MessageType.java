package tw.com.cube.demo.cube_demo.utils;

import lombok.Getter;
import lombok.Setter;

public enum MessageType {
  MSG_0000("0000", "成功"),
  MSG_E001("E001", "日期區間不符"),
  MSG_E002("E002", "起迄日期及幣別不得為空"),
  MSG_E090("E090", "查無資料"),
  MSG_E999("E999", "其他錯誤，請洽管理員");
  @Getter @Setter private String code;
  @Getter @Setter private String message;

  MessageType(String code, String message) {
    this.code = code;
    this.message = message;
  }
}

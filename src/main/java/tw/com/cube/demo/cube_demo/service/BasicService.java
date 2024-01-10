package tw.com.cube.demo.cube_demo.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import tw.com.cube.demo.cube_demo.dao.vo.ReturnVo;

public class BasicService {

  /**
   * Map<String, Object> to Json String
   *
   * @param result
   * @return String
   */
  public String apiResponse(Map<String, Object> result) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String json = objectMapper.writeValueAsString(result);
      return json;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * ReturnVo to Json String
   *
   * @param result
   * @return String
   */
  public String apiResponse(ReturnVo result) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      String json = objectMapper.writeValueAsString(result);
      return json;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}

package tw.com.cube.demo.cube_demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public abstract class AbstractBasicService {

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
}

package tw.com.cube.demo.cube_demo.dao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Error;

@Setter
@Getter
public class ReturnVo implements Serializable {

  @JsonProperty("result")
  private Map<String, Object> result;

  @JsonProperty("error")
  private Error error;
}

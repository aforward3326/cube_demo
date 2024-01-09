package tw.com.cube.demo.cube_demo.dao.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Fail;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Success;

@Setter
@Getter
public class ReturnVo implements Serializable {

  @JsonProperty("Success")
  private Success success;

  @JsonProperty("Fail")
  private Fail fail;
}

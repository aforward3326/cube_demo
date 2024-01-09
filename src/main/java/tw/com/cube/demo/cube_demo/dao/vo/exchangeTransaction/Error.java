package tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error implements Serializable {
  private String code;
  private String message;
}

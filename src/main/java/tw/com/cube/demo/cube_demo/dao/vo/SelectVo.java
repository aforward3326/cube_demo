package tw.com.cube.demo.cube_demo.dao.vo;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectVo implements Serializable {
  private String label;

  private String value;
}

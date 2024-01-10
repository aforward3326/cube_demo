package tw.com.cube.demo.cube_demo.task.batch.utils;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FieldUtil implements Serializable {

  private static final long serialVersionUID = 5492161003078954759L;

  private String field1;
  private String field2;
  private String field3;
  private String field4;
  private String field5;
}

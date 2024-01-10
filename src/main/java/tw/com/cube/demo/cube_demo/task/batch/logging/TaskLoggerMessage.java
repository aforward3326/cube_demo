package tw.com.cube.demo.cube_demo.task.batch.logging;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@SuppressWarnings("serial")
public class TaskLoggerMessage implements Serializable {

  @Getter private final TaskLoggerMessageLevel messageLevel;
  private final String message;
  private final Throwable e;

  public String getMessage() {
    return Objects.nonNull(e) ? message + "\n" + e.getMessage() : message;
  }
}

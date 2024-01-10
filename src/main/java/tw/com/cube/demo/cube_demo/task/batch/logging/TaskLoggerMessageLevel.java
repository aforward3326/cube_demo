package tw.com.cube.demo.cube_demo.task.batch.logging;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskLoggerMessageLevel {
  INFO("INFO", "INFO: ", 0),
  WARN("WARN", "WARN: ", 1),
  ERROR("ERROR", "ERROR: ", 2);

  private final String label;
  private final String prefix;
  private final int level;
}

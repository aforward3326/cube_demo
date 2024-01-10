package tw.com.cube.demo.cube_demo.task.batch.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tw.com.cube.demo.cube_demo.task.batch.utils.FieldUtil;

@Slf4j
@SuppressWarnings("serial")
public class TaskLogger implements Serializable {

  private List<TaskLoggerMessage> messages = new ArrayList<>();

  @Getter @Setter private boolean isCsvSupport = false;

  @Getter @Setter private Map<String, Object> parameters;

  @Getter @Setter private List<FieldUtil> fields;

  public synchronized void info(String message) {
    messages.add(new TaskLoggerMessage(TaskLoggerMessageLevel.INFO, message, null));
  }

  public synchronized void warn(String message) {
    messages.add(new TaskLoggerMessage(TaskLoggerMessageLevel.WARN, message, null));
  }

  public void error(String message) {
    error(message, null);
  }

  public synchronized void error(String message, Throwable e) {
    messages.add(new TaskLoggerMessage(TaskLoggerMessageLevel.ERROR, message, e));
    log.error(message, e);
  }

  public synchronized void clear() {
    this.messages.clear();
  }

  public synchronized boolean isEmpty() {
    return messages.isEmpty();
  }

  public synchronized boolean isNotEmpty() {
    return !isEmpty();
  }

  private synchronized List<TaskLoggerMessage> getLogMessageByLevel(TaskLoggerMessageLevel level) {
    return messages.stream()
        .filter(logMsg -> logMsg.getMessageLevel().getLevel() >= level.getLevel())
        .collect(Collectors.toList());
  }

  public synchronized String getMessage(TaskLoggerMessageLevel level) {
    StringBuilder sb = new StringBuilder();
    getLogMessageByLevel(level)
        .forEach(
            logMsg -> {
              sb.append(logMsg.getMessageLevel().getPrefix())
                  .append(logMsg.getMessage())
                  .append("\n");
            });
    return sb.toString();
  }

  public synchronized List<String> getMessageList(TaskLoggerMessageLevel level) {
    List<String> result = new ArrayList<>();
    getLogMessageByLevel(level)
        .forEach(
            logMsg -> {
              String msg =
                  new StringBuilder()
                      .append(logMsg.getMessageLevel().getPrefix())
                      .append(logMsg.getMessage())
                      .toString();
              result.add(msg);
            });
    return result;
  }

  public synchronized void removeLastMessage() {
    if (messages.size() > 0) {
      messages.remove(messages.size() - 1);
    }
  }
}

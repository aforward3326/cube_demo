package tw.com.cube.demo.cube_demo.task.batch.vo;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.batch.core.JobParameters;
import tw.com.cube.demo.cube_demo.task.batch.enums.TaskType;

@Getter
@Setter
@RequiredArgsConstructor
public class TaskDetail {

  /** 等待順序生成器 */
  @Getter(AccessLevel.PRIVATE)
  private static final AtomicInteger NEXT_WAITING_NO = new AtomicInteger(0);

  /** 等待順序 */
  @Setter(AccessLevel.PRIVATE)
  private Integer waitingNo = NEXT_WAITING_NO.getAndIncrement();

  /** 排程時間 */
  @NonNull private Date ScheduledTime;

  /** 任務類型 */
  @NonNull private TaskType taskType;

  /** 任務建立者 */
  @NonNull private String userId;

  /** 執行所需要的參數 */
  @NonNull private JobParameters jobParams;

  /** 檔案(名稱) */
  private String fileName;

  private Long jobInstance;

  public TaskDetail(
      Date scheduledTime,
      @NonNull TaskType taskType,
      @NonNull String userId,
      @NonNull JobParameters jobParams,
      Long jobInstance) {
    super();
    ScheduledTime = scheduledTime;
    this.taskType = taskType;
    this.userId = userId;
    this.jobParams = jobParams;
    this.jobInstance = jobInstance;
  }

  public TaskDetail() {}
  ;
}

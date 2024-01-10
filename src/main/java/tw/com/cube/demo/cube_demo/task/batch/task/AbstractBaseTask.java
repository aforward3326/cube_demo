package tw.com.cube.demo.cube_demo.task.batch.task;

import java.util.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.StoppableTasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import tw.com.cube.demo.cube_demo.task.batch.enums.TaskType;
import tw.com.cube.demo.cube_demo.task.batch.logging.TaskLogger;
import tw.com.cube.demo.cube_demo.task.batch.service.TaskManagerService;

@RequiredArgsConstructor
public abstract class AbstractBaseTask<T> implements StoppableTasklet, StepExecutionListener {
  protected static final Logger log = LoggerFactory.getLogger(AbstractBaseTask.class);

  @Autowired private TaskManagerService taskManagerService;
  protected final TaskLogger taskLogger = new TaskLogger();

  protected TaskType taskType = TaskType.UNKNOWN;
  private Date scheduledTime = new Date();

  protected JobParameters jobParams;
  private Iterator<T> itemsIterator;

  protected int total;
  protected int processed;
  protected long time;
  protected long eta;
  protected String fileName;
  public String importerFileName;

  @Override
  public final void beforeStep(StepExecution stepExecution) {
    jobParams = stepExecution.getJobParameters();
    JobParameter<?> taskType = jobParams.getParameter("taskType");
    if (Objects.nonNull(taskType) && taskType.getValue() instanceof TaskType) {
      this.taskType = (TaskType) taskType.getValue();
    }

    log.debug("execute beforeRun method ...");
    try {
      beforeRun();
    } catch (Exception e) {
      e.printStackTrace();
    }

    log.debug("execute getItemsForProcessing method ...");
    List<T> items = null;
    try {
      items = getItemsForProcessing();
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error("'items' cannat be null !!");
    }

    if (items != null) {
      itemsIterator = items.iterator();
      total = items.size();
      processed = 0; // 已執行筆數
      time = 0; // 執行時間，單位(ms)
      eta = 0; // 剩餘時間，單位(ms)
      fileName = null;

      if (total != 0) {
        //        taskLogger.clear();
      }

      log.info("total: " + items.size());

    } else {
      total = 0;
    }
  }

  public void setScheduledTime(Date scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  public Date getScheduledTime() {
    return scheduledTime;
  }

  public final RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

    ExecutionContext context = contribution.getStepExecution().getExecutionContext();

    // 如果items大小為0，直接結束任務
    if (total == 0) {
      // 發生讀入的錯誤訊息仍需印出
      //      context.put("taskLogger", taskLogger);
      return RepeatStatus.FINISHED;
    }

    // 判斷是否暫停
    if (taskManagerService.isPaused()) {
      try {
        context.put("paused", true);
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
      return RepeatStatus.CONTINUABLE;
    }
    context.put("paused", false);

    // 處理下一筆資料
    long startTime = System.currentTimeMillis();
    try {
      processRecord(itemsIterator.next());
    } finally {
      processed++;
      time += (System.currentTimeMillis() - startTime);
      eta = (long) ((total - processed) * (time / ((double) processed)));
      context.putLong("eta", eta);
      log.debug("total: " + total + ", processed: " + processed + ", eta: " + eta + " ms");
    }
    //    context.put("taskLogger", taskLogger);
    return RepeatStatus.continueIf(itemsIterator.hasNext());
  }

  public final ExitStatus afterStep(StepExecution stepExecution) {
    log.debug("execute afterRun method ...");
    afterRun();

    ExecutionContext context = stepExecution.getJobExecution().getExecutionContext();
    context.putString("fileName", Objects.nonNull(fileName) ? fileName : "");
    // context.put("taskLogger", taskLogger);
    return ExitStatus.COMPLETED;
  }

  @Override
  public final void stop() {}

  protected abstract List<T> getItemsForProcessing();

  protected abstract void beforeRun() throws Exception;

  protected abstract void processRecord(T t);

  protected abstract void afterRun();

  public int getProcessedNumber() {
    return processed;
  }
}

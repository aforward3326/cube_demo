package tw.com.cube.demo.cube_demo.task.batch.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;
import tw.com.cube.demo.cube_demo.task.batch.enums.TaskType;
import tw.com.cube.demo.cube_demo.task.batch.service.TaskManagerService;
import tw.com.cube.demo.cube_demo.task.batch.vo.TaskDetail;

@Component
@Getter
@Setter
public class GeneralTask extends AbstractBaseTask<String> {
  @Autowired private TaskManagerService taskManagerService;

  @Autowired private ExchangeTransactionService exchangeTransactionService;

  Logger logger = LoggerFactory.getLogger(GeneralTask.class);

  private List<String> files = new ArrayList<>();

  public GeneralTask() {
    this.taskType = TaskType.GeneralTask;
  }

  @Override
  protected void processRecord(String file) {
    try {
      tryProcessRecord(file);
    } catch (Exception e) {
      logger.error("flowId: " + file + "\t" + e.getMessage());
      taskLogger.error("flowId: " + file + "\t" + e.getMessage());
      taskLogger.info("Edit Date : " + new Date());
    }
  }

  public TaskType getTaskType() {
    return TaskType.GeneralTask;
  }

  @SuppressWarnings("unused")
  @Scheduled(cron = "0 0 18 * * ?")
  public void startGetAPI() {
    try {
      Date scheduledTime = new Date();
      TaskType taskType = TaskType.GeneralTask;
      String userId = "admin";
      JobParametersBuilder jobParamsBuilder = new JobParametersBuilder();
      jobParamsBuilder.addDate("scheduledTime", scheduledTime);
      jobParamsBuilder.addJobParameter("taskType", taskType, TaskType.class);

      TaskDetail task =
          new TaskDetail(scheduledTime, taskType, userId, jobParamsBuilder.toJobParameters());
      taskManagerService.addTask(task);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }
  }

  public void tryProcessRecord(String file) {
    System.out.println(file);
    Map<String, Object> logMap = new HashMap<>();
    StringBuilder message = new StringBuilder();
    log.info("General Task Start\n");
    message.append("General Task Start\n");
    exchangeTransactionService.getExchangeTransaction();
    for (String log : logMap.keySet()) {
      message.append(log + " : " + logMap.get(log) + "\n");
    }
    message.append("Edit Date : " + new Date() + "\n");
    taskLogger.info(message.toString());
    log.info(message.toString());
  }

  @Override
  public List<String> getItemsForProcessing() {

    return files;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void beforeRun() {}

  @Override
  protected void afterRun() {}
}

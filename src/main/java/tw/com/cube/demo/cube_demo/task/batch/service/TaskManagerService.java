package tw.com.cube.demo.cube_demo.task.batch.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tw.com.cube.demo.cube_demo.task.batch.enums.TaskType;
import tw.com.cube.demo.cube_demo.task.batch.utils.SpringContextHolder;
import tw.com.cube.demo.cube_demo.task.batch.vo.*;
import tw.com.cube.demo.cube_demo.task.batch.vo.TaskDetail;

/**
 * 排程任務管理 Service
 *
 * @author xiang20108
 */
@Slf4j
@Service
@EnableScheduling
public class TaskManagerService {
  public static final String GENERAL_JOB_NAME = "GeneralJob";

  @Getter private final List<TaskDetail> waitingQueue = new ArrayList<>();

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");

  private boolean paused = false;

  @Autowired
  @Qualifier("generalJobLauncher")
  private TaskExecutorJobLauncher jobLauncher;

  @Autowired private JobExplorer jobExplorer;

  @Scheduled(fixedDelay = 1000)
  private void runTask() {
    TaskDetail task = null;
    synchronized (this) {
      if (waitingQueue.size() == 0) {
        return;
      }
      log.debug("waitingQueue size: " + waitingQueue.size());
      Iterator<TaskDetail> taskIterator = waitingQueue.iterator();
      while (taskIterator.hasNext()) {
        task = taskIterator.next();
        if (Objects.isNull(task.getScheduledTime()) || task.getScheduledTime().before(new Date())) {
          taskIterator.remove();
          break;
        }
        task = null;
      }
    }
    if (Objects.isNull(task)) {
      return;
    }
    try {
      Job job = getJobByTaskType(task.getTaskType());
      JobExecution jobExecution = jobLauncher.run(job, task.getJobParams());
      while (jobExecution.isRunning()) {
        Thread.sleep(1000);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  /** 根據 taskType 取得 spring batch Job */
  private Job getJobByTaskType(TaskType taskType) {
    ApplicationContext ctx = SpringContextHolder.getContext();
    return switch (taskType) {
      case GeneralTask -> ctx.getBean(GENERAL_JOB_NAME, Job.class);
      default -> {
        throw new RuntimeException("job undefined");
      }
    };
  }

  /** 將任務加入等待 */
  public synchronized void addTask(TaskDetail task) {
    if (Objects.isNull(task.getJobParams().getDate("scheduledTime"))) {
      JobParametersBuilder paramsBuilder = new JobParametersBuilder(jobExplorer);
      paramsBuilder.addJobParameters(task.getJobParams());
      paramsBuilder.addDate("scheduledTime", task.getScheduledTime());
      task.setJobParams(paramsBuilder.toJobParameters());
    }
    JobParameter<?> taskType = task.getJobParams().getParameter("taskType");
    if (Objects.isNull(taskType) || !(taskType.getValue() instanceof TaskType)) {
      JobParametersBuilder paramsBuilder = new JobParametersBuilder(jobExplorer);
      paramsBuilder.addJobParameters(task.getJobParams());
      paramsBuilder.addJobParameter("taskType", task.getTaskType(), TaskType.class);
      task.setJobParams(paramsBuilder.toJobParameters());
    }
    taskType = null;
    if (Objects.isNull(task.getJobParams().getString("userId"))) {
      JobParametersBuilder paramsBuilder = new JobParametersBuilder(jobExplorer);
      paramsBuilder.addJobParameters(task.getJobParams());
      paramsBuilder.addString("userId", task.getUserId());
      task.setJobParams(paramsBuilder.toJobParameters());
    }
    waitingQueue.add(task);
  }

  /** 排程任務是否暫停執行 */
  public synchronized boolean isPaused() {
    return paused;
  }
}

package tw.com.cube.demo.cube_demo.task.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class TaskLauncherConfig {

  @Bean("generalJobLauncher")
  public TaskExecutorJobLauncher generalJobLauncher(
      @Qualifier("generalTaskExecutor") ThreadPoolTaskExecutor taskExecutor,
      JobRepository jobRepository)
      throws Exception {
    TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(taskExecutor);
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }

  @Bean("backendJobLauncher")
  public TaskExecutorJobLauncher backendJobLauncher(
      @Qualifier("backendTaskExecutor") ThreadPoolTaskExecutor taskExecutor,
      JobRepository jobRepository)
      throws Exception {
    TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(taskExecutor);
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}

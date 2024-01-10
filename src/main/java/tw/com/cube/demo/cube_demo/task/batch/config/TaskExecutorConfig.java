package tw.com.cube.demo.cube_demo.task.batch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.scope.context.JobSynchronizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/** 定義 Spring Batch TaskExecutor */
@Configuration
@SuppressWarnings("serial")
public class TaskExecutorConfig {

  @Bean("generalTaskExecutor")
  public ThreadPoolTaskExecutor generalTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor =
        new ThreadPoolTaskExecutor() {
          @Override
          public void execute(Runnable task) {
            if (JobSynchronizationManager.getContext() != null) {
              final JobExecution jobExecution =
                  JobSynchronizationManager.getContext().getJobExecution();
              super.execute(
                  () -> {
                    JobSynchronizationManager.register(jobExecution);
                    try {
                      task.run();
                    } finally {
                      JobSynchronizationManager.close();
                    }
                  });
            } else {
              super.execute(() -> task.run());
            }
          }
        };
    taskExecutor.setCorePoolSize(1);
    taskExecutor.setQueueCapacity(5);
    taskExecutor.setMaxPoolSize(5);
    taskExecutor.setThreadNamePrefix("general-");
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Bean("backendTaskExecutor")
  public ThreadPoolTaskExecutor backendTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor =
        new ThreadPoolTaskExecutor() {
          @Override
          public void execute(Runnable task) {
            if (JobSynchronizationManager.getContext() != null) {
              final JobExecution jobExecution =
                  JobSynchronizationManager.getContext().getJobExecution();
              super.execute(
                  () -> {
                    JobSynchronizationManager.register(jobExecution);
                    try {
                      task.run();
                    } finally {
                      JobSynchronizationManager.close();
                    }
                  });
            } else {
              super.execute(() -> task.run());
            }
          }
        };
    taskExecutor.setCorePoolSize(2);
    taskExecutor.setQueueCapacity(10);
    taskExecutor.setMaxPoolSize(10);
    taskExecutor.setThreadNamePrefix("backend-");
    taskExecutor.initialize();
    return taskExecutor;
  }
}

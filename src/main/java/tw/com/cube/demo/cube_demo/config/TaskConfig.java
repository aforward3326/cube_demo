package tw.com.cube.demo.cube_demo.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import tw.com.cube.demo.cube_demo.task.QuartzTask;

/** Quartz task Configuration */
@Configuration
public class TaskConfig {
  @Bean(name = "scheduler")
  public SchedulerFactoryBean schedulerFactory(Trigger... triggers) {
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    bean.setOverwriteExistingJobs(true);
    bean.setStartupDelay(1);
    bean.setTriggers(triggers);
    return bean;
  }

  // -------------  -------------\\
  @Bean(name = "jobDetail")
  public MethodInvokingJobDetailFactoryBean jobDetail(QuartzTask task) {
    MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
    jobDetail.setConcurrent(false);
    jobDetail.setTargetObject(task);
    jobDetail.setTargetMethod("startGetApi");
    return jobDetail;
  }

  @Bean
  public CronTriggerFactoryBean jobTrigger(JobDetail jobDetail) {
    String cron = "0 0 18  * * ? *"; // Setting task time
    CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(jobDetail);
    trigger.setCronExpression(cron);
    return trigger;
  }
}

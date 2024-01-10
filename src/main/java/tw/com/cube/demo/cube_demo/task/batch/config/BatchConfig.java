package tw.com.cube.demo.cube_demo.task.batch.config;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import tw.com.cube.demo.cube_demo.task.batch.task.GeneralTask;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
  public static final String GENERAL_JOB_NAME = "GeneralJob";

  public DataSource dataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
        .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
        .build();
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager getTransactionManager() {
    return new ResourcelessTransactionManager();
  }

  @Bean(name = "jobRepository")
  public JobRepository getJobRepository() throws Exception {
    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
    factory.setDataSource(dataSource());
    factory.setTransactionManager(getTransactionManager());
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  @Bean(GENERAL_JOB_NAME)
  public Job getAcqDetailsGlobalChangeJob(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      GeneralTask reindexer) {
    Step step =
        new StepBuilder("generalTask", jobRepository)
            .tasklet(reindexer, transactionManager)
            .build();
    return new JobBuilder(GENERAL_JOB_NAME, jobRepository)
        .start(step)
        .incrementer(new RunIdIncrementer())
        .build();
  }
}

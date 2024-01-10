package tw.com.cube.demo.cube_demo.task;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;

@Configuration
@Component
//@EnableScheduling
@RequiredArgsConstructor
public class QuartzTask {

  private final ExchangeTransactionService exchangeTransactionService;

  Logger logger = LoggerFactory.getLogger(QuartzTask.class);

  /** Call Get Exchange Transaction */
  public void startGetApi() {
    logger.info("==========Start Get Exchange Transaction==========");
    //    exchangeTransactionService.getExchangeTransaction();
    logger.info("==========End Get Exchange Transaction==========");
  }
}

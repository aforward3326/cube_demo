package tw.com.cube.demo.cube_demo.task;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;

@Configuration
@Component
@EnableScheduling
@RequiredArgsConstructor
public class QuartzTask {

  private final ExchangeTransactionService exchangeTransactionService;

  public void startGetApi() {
    exchangeTransactionService.getExchangeTransaction();
    System.out.println("get Exchange Transaction");
  }
}

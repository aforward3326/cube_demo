package tw.com.cube.demo.cube_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;

@SpringBootTest
class CubeDemoApplicationTests {
  @Autowired private ExchangeTransactionService exchangeTransactionService;

  /** test for loading from API */
  @Test
  void loadingFromAPI() {
    exchangeTransactionService.getExchangeTransaction();
  }
}

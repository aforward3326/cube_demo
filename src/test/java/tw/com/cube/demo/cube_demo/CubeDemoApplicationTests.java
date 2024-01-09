package tw.com.cube.demo.cube_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;
import tw.com.cube.demo.cube_demo.utils.DateUtil;

@SpringBootTest
class CubeDemoApplicationTests {
  @Autowired private ExchangeTransactionService exchangeTransactionService;
  @Autowired private DateUtil dateUtil;

  @Test
  void loadingFromAPI() {
    exchangeTransactionService.getExchangeTransaction();
  }
}

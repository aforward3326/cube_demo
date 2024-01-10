package tw.com.cube.demo.cube_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.ExchangeTransactionApiVo;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;

@SpringBootTest
class CubeDemoApplicationTests {
  @Autowired private ExchangeTransactionService exchangeTransactionService;

  /** test for loading from API */
  @Test
  void loadingFromAPI() {
    exchangeTransactionService.getExchangeTransaction();
  }

  @Test
  void forexAPI_Success() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2024-01/02");
    vo.setEndDate("2024/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_NoneResult() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2024/01/01");
    vo.setEndDate("2024/01/01");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_OutOfDate() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2022/01/02");
    vo.setEndDate("2024/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_NullValue() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2022/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_WrongFormatValue() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2022-01-02");
    vo.setEndDate("2022/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }
}

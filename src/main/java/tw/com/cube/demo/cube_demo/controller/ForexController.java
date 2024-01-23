package tw.com.cube.demo.cube_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.ExchangeTransactionApiVo;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;

@Controller
@RequestMapping("/forexApi")
public class ForexController extends AbstractBasicController {
  @Autowired ExchangeTransactionService exchangeTransactionService;

  /**
   * get exchange currency history
   *
   * @return ResponseEntity<String>
   */
  @PostMapping()
  public ResponseEntity<String> getHistory(@RequestBody ExchangeTransactionApiVo paramMap) {
    return returnResponse(exchangeTransactionService.getHistory(paramMap));
  }

  @GetMapping("/today")
  public ResponseEntity<String> getToday() {
    return returnResponse(exchangeTransactionService.getToday());
  }

  @GetMapping("/getByCurrencyUnit")
  public ResponseEntity<String> getByCurrencyUnit(
      @RequestParam("date") String date, @RequestParam("currency") String currency) {
    return returnResponse(exchangeTransactionService.getExchangeUnitData(date, currency));
  }
}

package tw.com.cube.demo.cube_demo.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.com.cube.demo.cube_demo.service.ExchangeTransactionService;

@Controller
@RequestMapping("/forexApi")
public class ForexController extends AbstractBasicController {
  @Autowired ExchangeTransactionService exchangeTransactionService;

  /**
   * get exchange currency history
   *
   * @param paramMap
   * @return ResponseEntity<String>
   */
  @PostMapping()
  public ResponseEntity<String> getHistory(@RequestBody Map<String, String> paramMap) {
    return returnResponse(exchangeTransactionService.getHistory(paramMap));
  }
}

package tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeTransactionApiVo implements Serializable {
  String startDate;

  String endDate;

  String currency;
}

package tw.com.cube.demo.cube_demo.dao.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeTransactionDataDto implements Serializable {
  String date;
  String currencyUnit;
  String currencyPrice;
  String exchangeCurrencyUnit;
  String exchangeCurrencyPrice;
}

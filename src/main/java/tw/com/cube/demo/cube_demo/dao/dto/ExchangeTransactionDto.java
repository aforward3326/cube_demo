package tw.com.cube.demo.cube_demo.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeTransactionDto implements Serializable {
  String date;

  @JsonProperty("usd")
  String currency;
}

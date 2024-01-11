package tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import tw.com.cube.demo.cube_demo.dao.dto.ExchangeTransactionDto;
import tw.com.cube.demo.cube_demo.dao.vo.ReturnVo;

@Getter
@Setter
public class ExchangeTransactionReturnVo extends ReturnVo {
  @JsonProperty("currency")
  List<ExchangeTransactionDto> currency;
}

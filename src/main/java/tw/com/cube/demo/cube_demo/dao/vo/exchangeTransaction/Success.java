package tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import tw.com.cube.demo.cube_demo.dao.dto.ExchangeTransactionDto;

@Getter
@Setter
public class Success implements Serializable {

  private Error error;

  private List<ExchangeTransactionDto> currency;
}

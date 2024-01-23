package tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import tw.com.cube.demo.cube_demo.dao.dto.ExchangeTransactionDataDto;
import tw.com.cube.demo.cube_demo.dao.vo.ReturnVo;

@Getter
@Setter
public class ExchangeTransactionDataReturnVo extends ReturnVo {
  private List<ExchangeTransactionDataDto> exchangeTransactionDataDtoList;
}

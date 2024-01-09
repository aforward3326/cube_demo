package tw.com.cube.demo.cube_demo.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("ExchangeTransaction")
public class ExchangeTransaction implements Serializable {
  @Id private String id;

  /** 日期 */
  private Date date;

  /** 原貨幣 */
  private String currencyUnit;

  /** 原貨幣數量 */
  private BigDecimal currencyPrice;

  /** 匯兌貨幣 */
  private String exchangeCurrencyUnit;

  /** 匯兌貨幣匯價 */
  private BigDecimal exchangeCurrencyPrice;

  public ExchangeTransaction(
      String id,
      Date date,
      String currencyUnit,
      BigDecimal currencyPrice,
      String exchangeCurrencyUnit,
      BigDecimal exchangeCurrencyPrice) {
    this.id = id;
    this.date = date;
    this.currencyUnit = currencyUnit;
    this.currencyPrice = currencyPrice;
    this.exchangeCurrencyUnit = exchangeCurrencyUnit;
    this.exchangeCurrencyPrice = exchangeCurrencyPrice;
  }
}

package tw.com.cube.demo.cube_demo.dao.domain;

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
  private Date date;
  private String currencyUnit;
  private BigDecimal currencyPrice;
  private String exchangeCurrencyUnit;
  private BigDecimal exchangeCurrencyPrice;

  //  private BigDecimal USD_NTD;
  //  private BigDecimal RMB_NTD;
  //  private BigDecimal EUR_USD;
  //  private BigDecimal USD_JPY;
  //  private BigDecimal GBP_USD;
  //  private BigDecimal AUD_USD;
  //  private BigDecimal USD_HKD;
  //  private BigDecimal USD_RMB;
  //  private BigDecimal USD_ZAR;
  //  private BigDecimal NZD_USD;

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

  //  public ExchangeTransaction(
  //      String id,
  //      Date date,
  //      BigDecimal USD_NTD,
  //      BigDecimal RMB_NTD,
  //      BigDecimal EUR_USD,
  //      BigDecimal USD_JPY,
  //      BigDecimal GBP_USD,
  //      BigDecimal AUD_USD,
  //      BigDecimal USD_HKD,
  //      BigDecimal USD_RMB,
  //      BigDecimal USD_ZAR,
  //      BigDecimal NZD_USD) {
  //    super();
  //    this.id = id;
  //    this.date = date;
  //    this.USD_NTD = USD_NTD;
  //    this.RMB_NTD = RMB_NTD;
  //    this.EUR_USD = EUR_USD;
  //    this.USD_JPY = USD_JPY;
  //    this.GBP_USD = GBP_USD;
  //    this.AUD_USD = AUD_USD;
  //    this.USD_HKD = USD_HKD;
  //    this.USD_RMB = USD_RMB;
  //    this.USD_ZAR = USD_ZAR;
  //    this.NZD_USD = NZD_USD;
  //  }
}

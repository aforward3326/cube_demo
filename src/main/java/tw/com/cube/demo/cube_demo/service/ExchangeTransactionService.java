package tw.com.cube.demo.cube_demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tw.com.cube.demo.cube_demo.dao.dto.ExchangeTransactionDto;
import tw.com.cube.demo.cube_demo.dao.po.ExchangeTransaction;
import tw.com.cube.demo.cube_demo.dao.repository.ExchangeTransactionRepository;
import tw.com.cube.demo.cube_demo.dao.vo.ReturnVo;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Error;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.ExchangeTransactionApiVo;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Fail;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Success;
import tw.com.cube.demo.cube_demo.utils.DateUtil;
import tw.com.cube.demo.cube_demo.utils.MessageType;

@Service
@RequiredArgsConstructor
public class ExchangeTransactionService extends BasicService {
  Logger logger = LoggerFactory.getLogger(ExchangeTransactionService.class);
  private final DateUtil dateUtil;
  private final ExchangeTransactionRepository exchangeTransactionRepository;

  /** get data from API then write tp DB */
  public void getExchangeTransaction() {
    logger.info("==========Start Get Exchange Transaction==========");
    try {
      URL url = new URL("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      StringBuilder response = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();
      List<Map<String, Object>> object = convertJson(response.toString());
      if (Objects.nonNull(object)) {
        write(object);
      }
      logger.info("==========End Get Exchange Transaction==========");
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.info("==========End Get Exchange Transaction==========");
    }
  }

  /**
   * format data to json
   *
   * @return List<Map<String, Object>>
   */
  private List<Map<String, Object>> convertJson(String response) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(response, new TypeReference<>() {});
    } catch (Exception e) {
      logger.error(e.getMessage());
      return null;
    }
  }

  /** write USD/NTD to DB */
  private void write(List<Map<String, Object>> responseObject) {
    for (Map<String, Object> map : responseObject) {
      List<ExchangeTransaction> exchangeTransactions = new ArrayList<>();
      Date dated = dateUtil.parseDate((String) map.get("Date"));
      Date date = dateUtil.dateTime(dated);
      BigDecimal USD_NTD = new BigDecimal((String) map.get("USD/NTD"));
      List<ExchangeTransaction> getExist =
          exchangeTransactionRepository.findHistory(date, "USD", "NTD");
      if (getExist.isEmpty()) {
        exchangeTransactions.add(
            new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "NTD", USD_NTD));
      }
      exchangeTransactionRepository.saveAll(exchangeTransactions);
    }
  }

  /** get exchange currency history */
  public String getHistory(ExchangeTransactionApiVo params) {
    ReturnVo result = new ReturnVo();
    Fail fail = new Fail();
    Success success = new Success();
    Error error = new Error();
    String startDateParam = params.getStartDate();
    String endDateParam = params.getEndDate();
    String currency = params.getCurrency();
    if (Objects.isNull(startDateParam)
        || Objects.isNull(endDateParam)
        || Objects.isNull(currency)) {
      error.setCode(MessageType.MSG_E002.getCode());
      error.setMessage(MessageType.MSG_E002.getMessage());
      fail.setError(error);
      result.setFail(fail);
      return apiResponse(result);
    }
    try {
      Date startDate = dateUtil.parseDate2(startDateParam);
      Date endDate = dateUtil.parseDate2(endDateParam);
      if (startDate.before(dateUtil.pastYear(1)) || endDate.after(dateUtil.pastDay(1))) {
        error.setCode(MessageType.MSG_E001.getCode());
        error.setMessage(MessageType.MSG_E001.getMessage());
        fail.setError(error);
        result.setFail(fail);
        return apiResponse(result);
      }
      List<ExchangeTransaction> exchangeTransactions =
          exchangeTransactionRepository.findHistoryByCurrencyUnit(
              startDate, endDate, currency.toUpperCase());
      List<ExchangeTransactionDto> exchangeTransactionList = new ArrayList<>();
      for (ExchangeTransaction exchangeTransaction : exchangeTransactions) {
        ExchangeTransactionDto exchangeTransactionDto = new ExchangeTransactionDto();
        exchangeTransactionDto.setDate(dateUtil.formatDateString(exchangeTransaction.getDate()));
        exchangeTransactionDto.setCurrency(
            exchangeTransaction.getExchangeCurrencyPrice().toString());
        exchangeTransactionList.add(exchangeTransactionDto);
      }
      if (exchangeTransactionList.isEmpty()) {
        error.setCode(MessageType.MSG_E090.getCode());
        error.setMessage(MessageType.MSG_E090.getMessage());
        fail.setError(error);
        result.setFail(fail);
        return apiResponse(result);
      }
      error.setCode(MessageType.MSG_0000.getCode());
      error.setMessage(MessageType.MSG_0000.getMessage());
      success.setError(error);
      success.setCurrency(exchangeTransactionList);
      result.setSuccess(success);
      return (currency).equalsIgnoreCase("USD")
          ? apiResponse(result)
          : apiResponse(result).replace("usd", currency.toLowerCase());

    } catch (Exception e) {
      logger.error(e.getMessage());
      error.setCode(MessageType.MSG_E999.getCode());
      error.setMessage(MessageType.MSG_E999.getMessage());
      fail.setError(error);
      result.setFail(fail);
      return apiResponse(result);
    }
  }
}

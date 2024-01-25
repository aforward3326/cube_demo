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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import tw.com.cube.demo.cube_demo.config.ApiUriConfig;
import tw.com.cube.demo.cube_demo.dao.dto.ExchangeTransactionDataDto;
import tw.com.cube.demo.cube_demo.dao.dto.ExchangeTransactionDto;
import tw.com.cube.demo.cube_demo.dao.po.ExchangeTransaction;
import tw.com.cube.demo.cube_demo.dao.repository.ExchangeTransactionRepository;
import tw.com.cube.demo.cube_demo.dao.vo.ReturnVo;
import tw.com.cube.demo.cube_demo.dao.vo.SelectVo;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.*;
import tw.com.cube.demo.cube_demo.dao.vo.exchangeTransaction.Error;
import tw.com.cube.demo.cube_demo.exception.InvalidDateFormatException;
import tw.com.cube.demo.cube_demo.exception.NoDataException;
import tw.com.cube.demo.cube_demo.utils.DateUtil;
import tw.com.cube.demo.cube_demo.utils.MessageType;

@Service
@RequiredArgsConstructor
public class ExchangeTransactionService extends AbstractBasicService {
  Logger logger = LoggerFactory.getLogger(ExchangeTransactionService.class);
  private final DateUtil dateUtil;
  private final ExchangeTransactionRepository exchangeTransactionRepository;
  private final ApiUriConfig apiUriConfig;
  private final MongoTemplate mongoTemplate;

  /** get data from API then write tp DB */
  public void getExchangeTransaction() {
    String endTime = "";
    try {
      logger.info("===========Start get Exchange Transaction============");
      logger.info("Start Time : {}", dateUtil.formatDateString(new Date(), 5));
      URL url = new URL(apiUriConfig.getForexApi());
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
      } else {
        throw new NoDataException("No Data Found");
      }
      endTime = dateUtil.formatDateString(new Date(), 5);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      logger.info("End Time : {}", endTime);
      logger.info("===========End get Exchange Transaction============");
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
    }
    return null;
  }

  /** write USD/NTD to DB */
  private void write(List<Map<String, Object>> responseObject) {
    try {
      for (Map<String, Object> map : responseObject) {
        List<ExchangeTransaction> exchangeTransactions = new ArrayList<>();
        Date dated = dateUtil.parseDate((String) map.get("Date"), 1);
        Date date = dateUtil.formatDate(dated, 5);
        BigDecimal USD_NTD = new BigDecimal((String) map.get("USD/NTD"));
        List<ExchangeTransaction> getExist =
            exchangeTransactionRepository.findHistory(date, "USD", "NTD");
        if (getExist.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "NTD", USD_NTD));
        }
        BigDecimal RMB_NTD = new BigDecimal((String) map.get("RMB/NTD"));
        List<ExchangeTransaction> getExist1 =
            exchangeTransactionRepository.findHistory(date, "RMB", "NTD");
        if (getExist1.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "RMB", BigDecimal.ONE, "NTD", RMB_NTD));
        }
        BigDecimal EUR_USD = new BigDecimal((String) map.get("EUR/USD"));
        List<ExchangeTransaction> getExist2 =
            exchangeTransactionRepository.findHistory(date, "EUR", "USD");
        if (getExist2.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "EUR", BigDecimal.ONE, "USD", EUR_USD));
        }
        BigDecimal USD_JPY = new BigDecimal((String) map.get("USD/JPY"));
        List<ExchangeTransaction> getExist3 =
            exchangeTransactionRepository.findHistory(date, "USD", "JPY");
        if (getExist3.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "JPY", USD_JPY));
        }
        BigDecimal GBP_USD = new BigDecimal((String) map.get("GBP/USD"));
        List<ExchangeTransaction> getExist4 =
            exchangeTransactionRepository.findHistory(date, "GBP", "USD");
        if (getExist4.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "GBP", BigDecimal.ONE, "USD", GBP_USD));
        }
        BigDecimal AUD_USD = new BigDecimal((String) map.get("AUD/USD"));
        List<ExchangeTransaction> getExist5 =
            exchangeTransactionRepository.findHistory(date, "AUD", "USD");
        if (getExist5.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "AUD", BigDecimal.ONE, "USD", AUD_USD));
        }
        BigDecimal USD_HKD = new BigDecimal((String) map.get("USD/HKD"));
        List<ExchangeTransaction> getExist6 =
            exchangeTransactionRepository.findHistory(date, "USD", "HKD");
        if (getExist6.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "HKD", USD_HKD));
        }
        BigDecimal USD_RMB = new BigDecimal((String) map.get("USD/RMB"));
        List<ExchangeTransaction> getExist7 =
            exchangeTransactionRepository.findHistory(date, "USD", "RMB");
        if (getExist7.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "RMB", USD_RMB));
        }
        BigDecimal USD_ZAR = new BigDecimal((String) map.get("USD/ZAR"));
        List<ExchangeTransaction> getExist8 =
            exchangeTransactionRepository.findHistory(date, "USD", "ZAR");
        if (getExist8.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "ZAR", USD_ZAR));
        }
        BigDecimal NZD_USD = new BigDecimal((String) map.get("NZD/USD"));
        List<ExchangeTransaction> getExist9 =
            exchangeTransactionRepository.findHistory(date, "NZD", "USD");
        if (getExist9.isEmpty()) {
          exchangeTransactions.add(
              new ExchangeTransaction(null, date, "NZD", BigDecimal.ONE, "USD", NZD_USD));
        }
        exchangeTransactionRepository.saveAll(exchangeTransactions);
        if (exchangeTransactions.isEmpty()) {
          logger.warn("No new data");
        }
      }
    } catch (InvalidDateFormatException ie) {
      logger.error(ie.getMessage());
    }
  }

  public String getToday() {
    Error error = new Error();
    ExchangeTransactionDataReturnVo result = new ExchangeTransactionDataReturnVo();
    List<ExchangeTransactionDataDto> exchangeTransactionDataDtoList = new ArrayList<>();
    List<ExchangeTransaction> exchangeTransactions = new ArrayList<>();
    try {
      int i = 1;
      Date startDate = dateUtil.pastDay(1);
      while (exchangeTransactions.isEmpty() && startDate.after(dateUtil.pastYear(1))) {
        startDate = dateUtil.pastDay(i);
        exchangeTransactions = exchangeTransactionRepository.findPastDayHistory(startDate);
        i++;
      }

      if (!exchangeTransactions.isEmpty()) {
        for (ExchangeTransaction et : exchangeTransactions) {
          ExchangeTransactionDataDto etd = new ExchangeTransactionDataDto();
          etd.setDate(dateUtil.formatDateString(et.getDate(), 3));
          etd.setCurrencyUnit(et.getCurrencyUnit());
          etd.setCurrencyPrice(et.getCurrencyPrice().toString());
          etd.setExchangeCurrencyUnit(et.getExchangeCurrencyUnit());
          etd.setExchangeCurrencyPrice(et.getExchangeCurrencyPrice().toString());
          exchangeTransactionDataDtoList.add(etd);
        }
        error.setCode(MessageType.MSG_0000.getCode());
        error.setMessage(MessageType.MSG_0000.getMessage());
        result.setExchangeTransactionDataDtoList(exchangeTransactionDataDtoList);
        result.setError(error);
      } else {
        error.setCode(MessageType.MSG_E090.getCode());
        error.setMessage(MessageType.MSG_E090.getMessage());
        result.setError(error);
      }
    } catch (InvalidDateFormatException ie) {
      logger.error(ie.getMessage());
      error.setCode(MessageType.MSG_E003.getCode());
      error.setMessage(MessageType.MSG_E003.getMessage());
      result.setError(error);
    }
    return apiResponse(result);
  }

  public String getExchangeUnitData(String date, String currencyUnit) {
    Error error = new Error();
    ExchangeTransactionDataReturnVo result = new ExchangeTransactionDataReturnVo();
    List<ExchangeTransactionDataDto> exchangeTransactionDataDtoList = new ArrayList<>();
    List<ExchangeTransaction> exchangeTransactions;
    try {
      Date startDate = dateUtil.parseDate(date, 2);
      if (Objects.nonNull(date)) {

        exchangeTransactions =
            exchangeTransactionRepository.findByExchangeCurrencyUnit(startDate, currencyUnit);
      } else {
        exchangeTransactions =
            exchangeTransactionRepository.findByExchangeCurrencyUnit(currencyUnit);
      }
      if (!exchangeTransactions.isEmpty()) {
        for (ExchangeTransaction et : exchangeTransactions) {
          ExchangeTransactionDataDto etd = new ExchangeTransactionDataDto();
          etd.setDate(dateUtil.formatDateString(et.getDate(), 3));
          etd.setCurrencyUnit(et.getCurrencyUnit());
          etd.setCurrencyPrice(et.getCurrencyPrice().toString());
          etd.setExchangeCurrencyUnit(et.getExchangeCurrencyUnit());
          etd.setExchangeCurrencyPrice(et.getExchangeCurrencyPrice().toString());
          exchangeTransactionDataDtoList.add(etd);
        }
        error.setCode(MessageType.MSG_0000.getCode());
        error.setMessage(MessageType.MSG_0000.getMessage());
        result.setExchangeTransactionDataDtoList(exchangeTransactionDataDtoList);
        result.setError(error);
      } else {
        error.setCode(MessageType.MSG_E090.getCode());
        error.setMessage(MessageType.MSG_E090.getMessage());
        result.setError(error);
      }
    } catch (InvalidDateFormatException ie) {
      logger.error(ie.getMessage());
      error.setCode(MessageType.MSG_E003.getCode());
      error.setMessage(MessageType.MSG_E003.getMessage());
      result.setError(error);
    }
    return apiResponse(result);
  }

  /** get exchange currency history */
  public String getHistory(ExchangeTransactionApiVo params) {
    ExchangeTransactionReturnVo result = new ExchangeTransactionReturnVo();
    Error error = new Error();
    String startDateParam = params.getStartDate().trim();
    String endDateParam = params.getEndDate().trim();
    String currency = params.getCurrency().toUpperCase().trim();
    try {
      String checkData = checkData(startDateParam, endDateParam, currency);

      if (Objects.nonNull(checkData)) {
        return checkData;
      }
      String checkValueFormat = checkValueFormat(currency);
      if (Objects.nonNull(checkValueFormat)) {
        return checkValueFormat;
      }

      Date startDate = dateUtil.parseDate(startDateParam, 2);
      Date endDate = dateUtil.parseDate(endDateParam, 2);
      String checkDateRange = checkDateRange(startDate, endDate);
      if (Objects.nonNull(checkDateRange)) {
        return checkDateRange;
      }
      List<ExchangeTransaction> exchangeTransactions =
          exchangeTransactionRepository.findHistoryByCurrencyUnit(
              startDate, endDate, currency.toUpperCase());
      if (!exchangeTransactions.isEmpty()) {
        return searchExchangeTransactionResult(exchangeTransactions);
      } else {
        error.setCode(MessageType.MSG_E090.getCode());
        error.setMessage(MessageType.MSG_E090.getMessage());
        result.setError(error);
        return apiResponse(result);
      }
    } catch (InvalidDateFormatException ie) {
      logger.error(ie.getMessage());
      error.setCode(MessageType.MSG_E003.getCode());
      error.setMessage(MessageType.MSG_E003.getMessage());
      result.setError(error);
      return apiResponse(result);
    } catch (Exception e) {
      logger.error(e.getMessage());
      error.setCode(MessageType.MSG_E999.getCode());
      error.setMessage(MessageType.MSG_E999.getMessage());
      result.setError(error);
      return apiResponse(result);
    }
  }

  private String checkData(String startDateParam, String endDateParam, String currency) {
    if (Objects.isNull(startDateParam)
        || Objects.isNull(endDateParam)
        || Objects.isNull(currency)) {
      ExchangeTransactionReturnVo result = new ExchangeTransactionReturnVo();
      Error error = new Error();
      error.setCode(MessageType.MSG_E002.getCode());
      error.setMessage(MessageType.MSG_E002.getMessage());
      result.setError(error);
      return apiResponse(result);
    } else {
      return null;
    }
  }

  private String checkDateRange(Date startDate, Date endDate) throws InvalidDateFormatException {
    ExchangeTransactionReturnVo result = new ExchangeTransactionReturnVo();
    Error error = new Error();
    if (startDate.before(dateUtil.pastYear(1)) || endDate.after(dateUtil.pastDay(1))) {
      error.setCode(MessageType.MSG_E001.getCode());
      error.setMessage(MessageType.MSG_E001.getMessage());
      result.setError(error);
      return apiResponse(result);
    } else {
      return null;
    }
  }

  private String checkValueFormat(String currency) throws InvalidDateFormatException {
    ExchangeTransactionReturnVo result = new ExchangeTransactionReturnVo();
    Error error = new Error();
    if (!currency.equals("USD")) {
      error.setCode(MessageType.MSG_E004.getCode());
      error.setMessage(MessageType.MSG_E004.getMessage());
      result.setError(error);
      return apiResponse(result);
    }
    return null;
  }

  private String searchExchangeTransactionResult(List<ExchangeTransaction> exchangeTransactions)
      throws InvalidDateFormatException {
    ExchangeTransactionReturnVo result = new ExchangeTransactionReturnVo();
    Error error = new Error();
    List<ExchangeTransactionDto> exchangeTransactionList = new ArrayList<>();
    for (ExchangeTransaction exchangeTransaction : exchangeTransactions) {
      ExchangeTransactionDto exchangeTransactionDto = new ExchangeTransactionDto();
      exchangeTransactionDto.setDate(dateUtil.formatDateString(exchangeTransaction.getDate(), 1));
      exchangeTransactionDto.setCurrency(exchangeTransaction.getExchangeCurrencyPrice().toString());
      exchangeTransactionList.add(exchangeTransactionDto);
    }
    error.setCode(MessageType.MSG_0000.getCode());
    error.setMessage(MessageType.MSG_0000.getMessage());
    result.setError(error);
    result.setCurrency(exchangeTransactionList);
    return apiResponse(result)
        .replace("currency", exchangeTransactions.get(0).getCurrencyUnit().toLowerCase());
  }

  public String getExchangeSelect() {
    ReturnVo returnVo = new ReturnVo();

    Map<String, Object> result = new HashMap<>();

    Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("exchangeCurrencyUnit"));

    List<Map> list =
        mongoTemplate
            .aggregate(aggregation, ExchangeTransaction.class, Map.class)
            .getMappedResults();
    List<SelectVo> select = new ArrayList<>();
    for (Map obj : list) {
      SelectVo selectVo = new SelectVo();
      selectVo.setLabel((String) obj.get("_id"));
      selectVo.setValue((String) obj.get("_id"));
      select.add(selectVo);
    }
    result.put("result", select);
    returnVo.setResult(result);
    return apiResponse(returnVo);
  }
}

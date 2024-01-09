package tw.com.cube.demo.cube_demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tw.com.cube.demo.cube_demo.dao.domain.ExchangeTransaction;
import tw.com.cube.demo.cube_demo.dao.repository.ExchangeTransactionRepository;
import tw.com.cube.demo.cube_demo.utils.DateUtil;

@Service
@RequiredArgsConstructor
public class ExchangeTransactionService extends AbstractBasicService {
  private final DateUtil dateUtil;
  private final ExchangeTransactionRepository exchangeTransactionRepository;

  public void getExchangeTransaction() {
    try {
      URL url = new URL("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      StringBuffer response = new StringBuffer();
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();
      List<Map<String, Object>> object = convertJson(response.toString());
      write(object);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<Map<String, Object>> convertJson(String response) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(response, new TypeReference<List<Map<String, Object>>>() {});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private void write(List<Map<String, Object>> responseObject) {
    for (Map<String, Object> map : responseObject) {
      List<ExchangeTransaction> exchangeTransactions = new ArrayList<>();
      Date datef = dateUtil.parseDate((String) map.get("Date"));
      Date date = dateUtil.dateTime(datef);
      BigDecimal USD_NTD = new BigDecimal((String) map.get("USD/NTD"));
      exchangeTransactions.add(
          new ExchangeTransaction(null, date, "USD", BigDecimal.ONE, "NTD", USD_NTD));
      exchangeTransactionRepository.saveAll(exchangeTransactions);
    }
  }

  public String getHistory(Map<String, String> params) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> fail = new HashMap<>();
    Map<String, Object> success = new HashMap<>();
    Map<String, Object> error = new HashMap<>();
    String startDateParam = params.get("startDate");
    String endDateParam = params.get("endDate");
    String currency = params.get("currency");
    if (Objects.isNull(currency)) {
      for (String key : params.keySet()) {
        if (key.contains("currency")) {
          currency = params.get(key);
        }
      }
    }
    if (Objects.isNull(startDateParam)
        || Objects.isNull(endDateParam)
        || Objects.isNull(currency)) {
      error.put("code", "E001");
      error.put("messqge", "起迄日期及幣別不得為空");
      result.put("error", error);
      fail.put("error", error);
      result.put("Failed", fail);
      return apiResponse(result);
    }
    try {
      Date startDate = dateUtil.parseDate2(startDateParam);
      Date endDate = dateUtil.parseDate2(endDateParam);
      if (startDate.before(dateUtil.pastYear(1)) || endDate.after(dateUtil.pastDay(1))) {
        error.put("code", "E001");
        error.put("messqge", "日期區間不符");
        fail.put("error", error);
        result.put("Failed", fail);
        return apiResponse(result);
      }
      List<ExchangeTransaction> exchangeTransactions =
          exchangeTransactionRepository.findHistoryByCurrencyUnit(
              startDate, endDate, currency.toUpperCase());
      List<Map<String, String>> exchangeTransactionList = new ArrayList<>();
      for (ExchangeTransaction exchangeTransaction : exchangeTransactions) {
        Map<String, String> map = new HashMap<>();
        map.put("date", dateUtil.formatDateString(exchangeTransaction.getDate()));
        map.put(currency, exchangeTransaction.getExchangeCurrencyPrice().toString());
        exchangeTransactionList.add(map);
      }
      error.put("code", "0000");
      error.put("message", "成功");
      success.put("error", error);
      success.put("currency", exchangeTransactionList);
      result.put("Success", success);
      return apiResponse(result);

    } catch (Exception e) {
      e.printStackTrace();
      error.put("code", "E001");
      error.put("messqge", "其他錯誤");
      fail.put("error", error);
      result.put("Failed", fail);
      return apiResponse(result);
    }
  }
}

# Cube_Demo_Project

This is a demo project for CUBE

## Function

1. A Task insert data(JSON) from API
2. Use API get data

## Installation

```bash
git clone https://github.com/aforward3326/cube_demo.git
```

## Environment

### Change database config

src.main.resource.application.properties

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/cube
logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
```

### Change Api Uri

src.main.resource.application.properties

```properties
forex.api=https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates 
```

## Schedule

1. branch master ---> Quartz Task

2. branch dev-scheduled-task ---> @Schedule Task

3. branch dev-spring-batch ---> Spring Batch  

   test: GET localhost:8080/forexApi/batch

   ```java
   @Controller
   @RequestMapping("/forexApi")
   public class ForexController extends BasicController {
     @Autowired ExchangeTransactionService exchangeTransactionService;
     @Autowired GeneralTask generalTask;
     
   //...ellipsis code
   
     @GetMapping("/batch")
     public void loadingFromAPI_SpringBatch() {
       generalTask.startGetAPI();
     }
   }
   
   ```

   

## Unit Test

cube_demo/src/test/java/tw/com/cube/demo/cube_demo/CubeDemoApplicationTests.java

```java
/** test for loading from API */
  @Test
  void loadingFromAPI() {
    exchangeTransactionService.getExchangeTransaction();
  }

/*for branch dev-spring-batch*/
  @Test
  void loadingFromAPI_SpringBatch() {
    generalTask.startGetAPI();
  }

  @Test
  void forexAPI_Success() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2024/01/02");
    vo.setEndDate("2024/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_NoneResult() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2024/01/01");
    vo.setEndDate("2024/01/01");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_OutOfDate() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2022/01/02");
    vo.setEndDate("2024/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_NullValue() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2022/01/02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

@Test
  void forexAPI_Error_WrongFormatValue() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2024-01-02");
    vo.setEndDate("2024-01-02");
    vo.setCurrency("usd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }

  @Test
  void forexAPI_Error_WrongCurrency() {
    ExchangeTransactionApiVo vo = new ExchangeTransactionApiVo();
    vo.setStartDate("2024/01/02");
    vo.setEndDate("2024/01/02");
    vo.setCurrency("busd");
    System.out.println(exchangeTransactionService.getHistory(vo));
  }
```






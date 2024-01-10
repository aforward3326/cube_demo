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

edit url

```java
@Service
@RequiredArgsConstructor
public class ExchangeTransactionService extends AbstractBasicService {
  Logger logger = LoggerFactory.getLogger(ExchangeTransactionService.class);
  private final DateUtil dateUtil;
  private final ExchangeTransactionRepository exchangeTransactionRepository;

  /** get data from API then write tp DB */
  public void getExchangeTransaction() {
    logger.info("==========Start Get Exchange Transaction==========");
    try {
      URL url = new URL("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"); 
  //...ellipsis code
      }
      logger.info("==========End Get Exchange Transaction==========");
    } catch (Exception e) {
      logger.error(e.getMessage());
      logger.info("==========End Get Exchange Transaction==========");
    }
  }
 //...ellipsis code
  
}
```

## Schedule

1. branch master ---> Quartz Task

2. branch dev-scheduled-task ---> @Schedule Task

3. branch dev-spring-task ---> Spring Batch  

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

/*for branch dev-spring-task*/
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
```






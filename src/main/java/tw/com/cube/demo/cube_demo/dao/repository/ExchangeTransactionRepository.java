package tw.com.cube.demo.cube_demo.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.cube.demo.cube_demo.dao.po.ExchangeTransaction;

@Repository
public interface ExchangeTransactionRepository
    extends MongoRepository<ExchangeTransaction, String> {

  @Query("{'$and': [{'date':  ?0 }, {'currencyUnit': ?1},{'exchangeCurrencyUnit': ?2}]}")
  List<ExchangeTransaction> findHistory(
      Date date, String currencyUnit, String exchangeCurrencyUnit);

  @Query("{'$and': [{'date': {'$gte':  ?0 , '$lte':  ?1 }}, {'currencyUnit': ?2}]}")
  List<ExchangeTransaction> findHistoryByCurrencyUnit(
      Date startDate, Date endDate, String currencyUnit);

  @Query("{'$and': [{'date':   ?0 }]}")
  List<ExchangeTransaction> findPastDayHistory(Date startDate);

  @Query("{'$and': [{'exchangeCurrencyUnit': ?1}]}")
  List<ExchangeTransaction> findByExchangeCurrencyUnit(String exchangeCurrencyUnit);

  @Query("{'$and': [{'date':   ?0 },{'exchangeCurrencyUnit': ?1}]}")
  List<ExchangeTransaction> findByExchangeCurrencyUnit(Date date, String exchangeCurrencyUnit);

  @Aggregation(pipeline = {"{ $group: { _id: '$exchangeCurrencyUnit' } }"})
  List<Map<String, Object>> groupByExchangeCurrencyUnit();
}

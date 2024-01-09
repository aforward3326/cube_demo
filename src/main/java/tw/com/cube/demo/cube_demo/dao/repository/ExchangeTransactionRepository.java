package tw.com.cube.demo.cube_demo.dao.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tw.com.cube.demo.cube_demo.dao.domain.ExchangeTransaction;

@Repository
public interface ExchangeTransactionRepository
    extends MongoRepository<ExchangeTransaction, String> {

  List<ExchangeTransaction> findByCurrencyUnit(String CurrencyUnit);

  @Query("{'$and': [{'date': {'$gte':  ?0 , '$lte':  ?1 }}, {'currencyUnit': ?2}]}")
  List<ExchangeTransaction> findHistoryByCurrencyUnit(
      Date startDate, Date endDate, String CurrencyUnit);
}

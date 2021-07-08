package project;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Delete;
import com.scalar.db.api.Result;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.IntValue;
import com.scalar.db.io.Key;
import com.scalar.db.io.TextValue;
import com.scalar.db.service.TransactionModule;
import com.scalar.db.service.TransactionService;
import java.util.concurrent.ThreadLocalRandom;

import java.io.IOException;
import java.util.Optional;

public class TravelTransaction extends Travel {
  private final TransactionService service;

  public TravelTransaction() throws IOException {
    Injector injector = Guice.createInjector(new TransactionModule(dbConfig));
    service = injector.getInstance(TransactionService.class);
    service.with(NAMESPACE, TABLENAME);
  }

  @Override
  public void add(int price, String from, String to) throws TransactionException {
    // Start a transaction
    DistributedTransaction tx = service.start();
    int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
    Put put = new Put(new Key(new IntValue(ID, randomNum)))
        .withValue(new IntValue(PRICE, price))
        .withValue(new TextValue(FROM, from))
        .withValue(new TextValue(TO, to));
    tx.put(put);

    // Commit the transaction (records are automatically recovered in case of failure)
    tx.commit();
  }

  @Override
  public void delete(int id) throws TransactionException {
    // Start a transaction
    DistributedTransaction tx = service.start();

    // Retrieve the needed data
    Get get = new Get(new Key(new IntValue(ID, id)));
    Optional<Result> result = tx.get(get);
    if(result.isPresent()){
      Delete del =
          new Delete(new Key(new IntValue(ID, id)));
      tx.delete(del);

      // Commit the transaction (records are automatically recovered in case of failure)
      tx.commit();
    }else {
      System.out.println("travel not found");
    }
  }

  @Override
  public void close() {
    service.close();
  }
}

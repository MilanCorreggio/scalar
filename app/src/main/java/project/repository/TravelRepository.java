package project.repository;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scalar.db.api.Delete;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Result;
import com.scalar.db.config.DatabaseConfig;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.IntValue;
import com.scalar.db.io.Key;
import com.scalar.db.io.TextValue;
import com.scalar.db.service.TransactionModule;
import com.scalar.db.service.TransactionService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;
import project.model.Travel;

public class TravelRepository extends ScalarDbReadOnlyTransactionRepository<Travel> {

  protected static final String NAMESPACE = "travel";
  protected static final String TABLENAME = "japan";
  protected static final String ID = "id";
  protected static final String PRICE = "price";
  protected static final String FROM = "from";
  protected static final String TO = "to";
  protected DatabaseConfig dbConfig;
  private static final String SCALARDB_PROPERTIES =
      System.getProperty("user.dir") + File.separator + "scalardb.properties";

  private final TransactionService service;

  public TravelRepository() throws IOException {
    dbConfig = new DatabaseConfig(new FileInputStream(SCALARDB_PROPERTIES));
    Injector injector = Guice.createInjector(new TransactionModule(dbConfig));
    service = injector.getInstance(TransactionService.class);
    service.with(NAMESPACE, TABLENAME);
  }

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

  public void updatePrice(int id, int price) throws TransactionException {
    // Start a transaction
    DistributedTransaction tx = service.start();

    // Retrieve the needed data
    Get get = new Get(new Key(new IntValue(ID, id)));
    Optional<Result> result = tx.get(get);

    if(result.isPresent()){
      Put put =
          new Put(new Key(new IntValue(ID, id))).withValue(new IntValue(PRICE, price));
      tx.put(put);
      tx.commit();
    }else {
      System.out.println("travel not found");
    }
  }

  public void read(int id ) throws TransactionException {
    // Start a transaction
    DistributedTransaction tx = service.start();

    // Retrieve the needed data
    Get get = new Get(new Key(new IntValue(ID, id)));
    Optional<Result> optresult = tx.get(get);

    if(optresult.isPresent()){
     System.out.println(parse(optresult.get()));
    }else {
      System.out.println("travel not found");
    }
  }

  public void close() {
    service.close();
  }

  @Override
  Travel parse(@Nonnull Result result) {
    return Travel.builder()
        .to(((TextValue) result.getValue(Travel.TO).get()).getString().get())
        .from(((TextValue) result.getValue(Travel.FROM).get()).getString().get())
        .price(((IntValue) result.getValue(Travel.PRICE).get()).get())
        .id(((IntValue) result.getValue(Travel.ID).get()).get())
        .build();
  }
}

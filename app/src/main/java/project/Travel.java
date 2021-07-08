package project;

import com.scalar.db.config.DatabaseConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Travel {
  private static final String SCALARDB_PROPERTIES =
      System.getProperty("user.dir") + File.separator + "scalardb.properties";
  protected static final String NAMESPACE = "travel";
  protected static final String TABLENAME = "japan";
  protected static final String ID = "id";
  protected static final String PRICE = "price";
  protected static final String FROM = "from";
  protected static final String TO = "to";
  protected DatabaseConfig dbConfig;

  public Travel() throws IOException {
    dbConfig = new DatabaseConfig(new FileInputStream(SCALARDB_PROPERTIES));
  }

  abstract void delete(int id) throws Exception;

  abstract void add(int price, String from, String to) throws Exception;

  abstract void close();
}

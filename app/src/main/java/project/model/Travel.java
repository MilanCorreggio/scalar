package project.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Travel {
  public static final String ID = "id";
  public static final String PRICE = "price";
  public static final String TO = "to";
  public static final String FROM = "from";
  int id;
  int price;
  String to;
  String from;
}

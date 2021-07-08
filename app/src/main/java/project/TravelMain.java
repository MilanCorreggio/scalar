package project;

public class TravelMain {
  public static void main(String[] args) throws Exception {
    String mode = null;
    int price = 0;
    int id = 0;
    String to = null;
    String from = null;


    for (int i = 0; i < args.length; ++i) {
      if ("-mode".equals(args[i])) {
        mode = args[++i];
      } else if ("-price".equals(args[i])) {
        price = Integer.parseInt(args[++i]);
      } else if ("-to".equals(args[i])) {
        to = args[++i];
      } else if ("-from".equals(args[i])) {
        from = args[++i];
      } else if ("-id".equals(args[i])) {
        id = Integer.parseInt(args[++i]);
      } else if ("-help".equals(args[i])) {
        printUsageAndExit();
      }
    }

    TravelTransaction travel = null;
    travel = new TravelTransaction();
    if (mode.equalsIgnoreCase("add")) {
      travel.add(price, from, to);
    } else if (mode.equalsIgnoreCase("delete")) {
      travel.delete(id);
    }
    travel.close();
  }

  private static void printUsageAndExit() {
    System.err.println(
        "Travel:  -mode adde -price number -to city -from city \n or \n"
            + "-mode delete -id id");
    System.exit(1);
  }
}

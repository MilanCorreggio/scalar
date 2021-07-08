package project;


import project.repository.TravelRepository;

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

    TravelRepository travel;
    travel = new TravelRepository();
    assert mode != null;
    if (mode.equalsIgnoreCase("add")) {
      travel.add(price, from, to);
    } else if (mode.equalsIgnoreCase("delete")) {
      travel.delete(id);
    } else if (mode.equalsIgnoreCase("updatePrice")) {
      travel.updatePrice(id, price);
    } else if (mode.equalsIgnoreCase("read")) {
      travel.read(id);
    }
    travel.close();
  }

  private static void printUsageAndExit() {
    System.err.println(
        "Travel:  -mode add -price number -to city -from city \n or \n"
            + "-mode delete/read -id id \n or \n"
            + "-mode updatePrice -id id -price price");
    System.exit(1);
  }
}

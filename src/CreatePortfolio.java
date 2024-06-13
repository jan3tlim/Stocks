import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The CreatePortfolio class handles the user interactions for creating a new portfolio.
 * It allows the user to specify the name of the new portfolio and add stocks to it.
 */
public class CreatePortfolio implements IStockControllerCommands {
  StockController user;
  IView v;
  private Scanner in;
  IStockControllerCommands cmd = null;
  AlphaVantageAPI a = new AlphaVantageAPI();

  /**
   * Constructs a new CreatePortfolio instance with the specified view, input scanner, and client.
   *
   * @param v    the view to interact with the user
   * @param in   the scanner for user input
   */
  public CreatePortfolio(IView v, Scanner in, StockController user) {
    this.v = v;
    this.user = user;
    this.in = in;
  }

  /**
   * Executes the portfolio creation process.
   * Displays a menu for creating a new portfolio, prompts the user for the portfolio name, and
   * allows the user to add stocks to the portfolio.
   * Validates stock ticker symbols and fetches historical stock data using the AlphaVantageAPI.
   *
   * @param m the model to operate on
   */
  public void goController(IModel m) {

    boolean quit = false;
    while (!quit) {
      v.goBack();
      v.showCreatePortfolio();

      String s = in.next();
      Client c = user.getUser();
      Portfolio p = c.createPortfolio(s);
      user.setClient(c);
      if(!Objects.equals(s, "b")){
      v.goBack();
      v.showAddStocks();
      v.writeMessage("Ticker:");
      s = in.next();}
      if (s.equals("b")) {
        quit = true;
      } else {
        String t ="";
        while (t.isEmpty()){

          s = in.next();
          if (!a.tickerCSVToList().contains(s)) {
            v.showOptionError();
            v.writeMessage("Error: Invalid stock symbol: " + s);
          }else{
            t = s;}
        }
          v.writeMessage("Quantity:");
          int quant = v.getValidInteger(in);
        TreeMap<LocalDate, Double> stockHistory = a.fetchStockHistory(
                a.makeCSVFile("TIME_SERIES_DAILY", t));
        v.writeMessage("What's today's date?");
        Scanner sc = new Scanner(System.in);
        LocalDate l = v.provideDate(sc);
        p.addStock(new Stock(t, stockHistory), quant, l);
        v.writeMessage("Successfully added " + s + " " + t + " stocks in " +
                p.getName() + "\n");
      }



    }
  }
}

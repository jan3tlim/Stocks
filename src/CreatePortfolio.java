import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The CreatePortfolio class handles the user interactions for creating a new portfolio.
 * It allows the user to specify the name of the new portfolio and add stocks to it.
 */
public class CreatePortfolio implements IStockControllerCommands {
  Client user;
  IView v;
  private Scanner in;
  IStockControllerCommands cmd = null;
  AlphaVantageAPI a = new AlphaVantageAPI();

  /**
   * Constructs a new CreatePortfolio instance with the specified view, input scanner, and client.
   *
   * @param v    the view to interact with the user
   * @param in   the scanner for user input
   * @param user the client who is creating a new portfolio
   */
  public CreatePortfolio(IView v, Scanner in, Client user) {
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
    v.showCreatePortfolio();
    String s = in.next();
    Portfolio p = user.createPortfolio(s);
    boolean quit = false;
    while (!quit) {
      v.goBack();
      v.showAddStocks();
      s = in.next();
      if (s.equals("b")) {
        quit = true;
        cmd = new ManagePortfolioMenu(v, in, user);
      } else {
        v.writeMessage("Ticker:");
        s = in.next();
        while (!a.tickerCSVToList().contains(s)) {
          v.showOptionError();
          v.writeMessage("Error: Invalid stock symbol: " + s);
          v.writeMessage("Ticker:");
          s = in.next();
        }
        String t = s;
        v.writeMessage("Quantity:");
        s = in.next();
        while (!s.matches("[0-9]+")) {
          v.showOptionError();
          v.writeMessage("Quantity:");
          s = in.next();
        }
        TreeMap<LocalDate, Double> stockHistory = a.fetchStockHistory(
                a.makeCSVFile("TIME_SERIES_DAILY", t));
        v.writeMessage("What's today's date?");
        Scanner sc = new Scanner(System.in);
        LocalDate l = v.provideDate(sc);
        p.addStock(new Stock(t, stockHistory), Integer.parseInt(s), l);
        v.writeMessage("Successfully added " + s + " " + t + " stocks in " +
                p.getName() + "\n");
      }
      if (cmd != null) {
        cmd.goController(m);
      }
    }
  }
}

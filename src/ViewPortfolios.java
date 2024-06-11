import java.time.LocalDate;
import java.util.Scanner;

/**
 * The ViewPortfolios class handles the user interactions for viewing and
 * managing existing portfolios.
 * It allows the user to view details of a specific portfolio and add stocks to it.
 */
public class ViewPortfolios implements IStockControllerCommands {
  Client user;
  IView v;
  private Scanner in;
  IStockControllerCommands cmd = null;
  AlphaVantageAPI a = new AlphaVantageAPI();

  /**
   * Constructs a new ViewPortfolios instance with the specified view, input scanner, and client.
   *
   * @param v    the view to interact with the user
   * @param in   the scanner for user input
   * @param user the client whose portfolios are being viewed
   */
  public ViewPortfolios(IView v, Scanner in, Client user) {
    this.v = v;
    this.user = user;
    this.in = in;
  }

  /**
   * Executes the process for viewing and managing existing portfolios.
   * Displays the user's existing portfolios, allows the user to select a portfolio,
   * view its details, and add stocks to the selected portfolio.
   * It validates stock ticker symbols and fetches historical stock data using the AlphaVantageAPI.
   *
   * @param m the model to operate on
   */
  public void goController(IModel m) {
    boolean quit = false;
    while (!quit) {
      v.goBack();
      v.viewExistingPortfolios(user);
      String s = in.next();
      if (s.equals("b")) {
        quit = true;
        cmd = new ManagePortfolioMenu(v, in, user);
      } else {
        if (s.equals("b")) {
          quit = true;
          cmd = new ManagePortfolioMenu(v, in, user);
        } else {
          while (!user.getPortfolios().containsKey(s)) {
            v.showOptionError();
            v.writeMessage("That portfolio name does not exist in your portfolios.\n");
            v.viewExistingPortfolios(user);
          }
        }
        IPortfolio p = user.getPortfolios().get(s);
        v.showSpecificPortfolio(s, user);
        v.writeMessage("To check the value of your portfolio on a certain date, \n");
        Scanner sc = new Scanner(System.in);
        LocalDate l = v.provideDate(sc);
        v.writeMessage("The value of your portfolio is " + p.calculatePortfolioValue(l));
      }
    }
    if (cmd != null) {
      cmd.goController(m);
    }
  }

}

import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The ViewPortfolios class handles the user interactions for viewing and
 * managing existing portfolios.
 * It allows the user to view details of a specific portfolio and add stocks to it.
 */
public class ViewPortfolios implements IStockControllerCommands {
  StockController user;
  IView v;
  private Scanner in;
  AlphaVantageAPI a = new AlphaVantageAPI();

  /**
   * Constructs a new ViewPortfolios instance with the specified view, input scanner, and client.
   *
   * @param v    the view to interact with the user
   * @param in   the scanner for user input
   * @param user the client whose portfolios are being viewed
   */
  public ViewPortfolios(IView v, Scanner in, StockController user) {
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
      v.viewExistingPortfolios(user.getUser());
      String s = in.next();
      if (s.equals("b")) {
        quit = true;

        //cmd = new ManagePortfolioMenu(v, in, user);
        //cmd.goController(m);
      } else {
        String t = "";
        while (t.isEmpty()) {
          if (!user.getUser().getPortfolios().containsKey(s)) {
            v.showOptionError();
            v.writeMessage("That portfolio name does not exist in your portfolios.\n");
            v.viewExistingPortfolios(user.getUser());
          }else{
            t = s;
          }
        }

        Client c = user.getUser();
        IPortfolio p = c.getPortfolios().get(t);
        IPortfolioOptions ipo = new PortfolioOptions(in,v);
        ipo.PortfolioOperation(p);
        user.setClient(c);

      }



    }

  }






}

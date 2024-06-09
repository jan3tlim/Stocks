import java.util.Scanner;

/**
 * The ManagePortfolio class handles the user interactions for managing a client's portfolio.
 * It allows the user to create new portfolios, view existing portfolios, and navigate back to
 * the previous menu.
 */
public class ManagePortfolioMenu implements IStockControllerCommands {
  Client user;
  IView v;
  private Scanner in;
  IStockControllerCommands cmd = null;

  /**
   * Constructs a new ManagePortfolio instance with the specified view, input scanner, and client.
   *
   * @param v    the view to interact with the user
   * @param in   the scanner for user input
   * @param user the client whose portfolios are being managed
   */
  public ManagePortfolioMenu(IView v, Scanner in, Client user) {
    this.v = v;
    this.user = user;
    this.in = in;
  }

  /**
   * Executes the portfolio management commands based on user input.
   * Displays a menu for managing portfolios, allowing the user to create new portfolios,
   * view existing portfolios, or go back to the previous menu.
   *
   * @param m the model to operate on
   */
  @Override
  public void goController(IModel m) {
    boolean quit = false;
    while (!quit) {
      v.showManagePortfolioMenu();
      v.goBack();
      String s = in.next();
      try {
        switch (s) {
          case "b":
            quit = true;
            break;
          case "1":
            cmd = new CreatePortfolio(v, in, user);
            break;
          case "2":
            cmd = new ViewPortfolios(v, in, user);
            break;
          default:
            v.showOptionError();
            in.next();
            break;
        }
        if (cmd != null && !quit) {
          cmd.goController(m);
        }
      } catch (IllegalArgumentException e) {
        v.writeMessage("Error: " + e.getMessage() + System.lineSeparator());
      }
    }
  }
}

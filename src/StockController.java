
import java.util.Scanner;

/**
 * The StockController class implements the IStockController interface to handle
 * the main control flow for user interactions in a stock management application.
 * It takes user input through a Readable source and outputs responses through an
 * Appendable target.
 */
public class StockController implements IStockController {
  final Readable in;
  final Appendable out;

  /**
   * Constructs a StockController instance with the specified input and output streams.
   *
   * @param in  The input stream for reading user input.
   * @param out The output stream for writing messages to the user.
   */
  StockController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Starts the main control loop of the application, displaying the main menu
   * and processing user input to navigate through different functionalities
   * such as viewing stock information or managing portfolios.
   */
  @Override
  public void goController() {

    IModel md = new Model();
    Scanner in = new Scanner(this.in);
    IView vd = new TextView(out);

    boolean quit = false;
    while (!quit) {
      String dirPath = "runtime_data";
      DirectoryCreator.ensureDirectoryExists(dirPath);
//      IClient user = new Client("user");
      Client user = new Client("user");
      user.loadPortfolios(dirPath);
      IStockControllerCommands cmd = null;
      vd.welcomeScreen();
      vd.showMainMenu();
      String s = in.next();
      try {
        switch (s) {
          case "q":
            quit = true;
            break;
          case "1":
            cmd = new StockInformation(vd, in);
            break;
          case "2":
            cmd = new ManagePortfolioMenu(vd, in, user);
            break;
          default:
            vd.showOptionError();
            in.next();
        }
        if (cmd != null) {
          cmd.goController(md);
        }
      } catch (IllegalArgumentException e) {
        vd.writeMessage("Error: " + e.getMessage() + System.lineSeparator());
      }
    }
  }


}


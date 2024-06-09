import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interface for View.
 */
public interface IView {
  void welcomeScreen();

  void goBack();

  void showMainMenu();

  void showStockInfoMenu();

  void showManagePortfolioMenu();

  void showStockStats(String ticker);

  void showCreatePortfolio();

  void viewExistingPortfolios(Client c);

  void showSpecificPortfolio(String name, Client c);

  void writeMessage(String s);

  String doubleToString(Double d);

  void showOptionError();

  void showAddStocks();

  LocalDate provideDate(Scanner in);

  public int getValidInteger(Scanner in);

  void getListCrossovers(ArrayList<LocalDate> a);
}
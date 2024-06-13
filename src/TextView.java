import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Implementation of the IView interface for text-based user interaction.
 */
public class TextView implements IView {
  private Appendable out;

  /**
   * Constructor to initialize the output stream.
   *
   * @param out The output stream to which messages will be printed.
   */
  public TextView(Appendable out) {
    this.out = out;
  }

  private void messageAppend(String message) throws IllegalStateException {
    try {
      out.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Displays the welcome screen.
   */
  @Override
  public void welcomeScreen() {
    messageAppend("Welcome to Stocks!\n\n" +
            "Navigate the program by inputting the number next to each menu item.\n" +
            "Enter 'q' to quit the program.\n");
  }

  /**
   * Displays the go back instruction.
   */
  @Override
  public void goBack() {
    messageAppend("\n\nEnter 'b' to come back to the most recent menu.\n" +
            "To quit the program, navigate back to main menu.\n");
  }

  /**
   * Displays the main menu.
   */
  @Override
  public void showMainMenu() {
    messageAppend("Main Menu:\n\n" +
            "1. Stock Information\n" +
            "2. Manage your portfolio(s)\n");
  }

  /**
   * Displays the stock information menu.
   */
  @Override
  public void showStockInfoMenu() {
    messageAppend("Stock Information:\n\n" +
            "Popular Stock Tickers:\n" +
            "AAPL " +
            "GOOG " +
            "AMZN " +
            "SMCI " +
            "MSFT\n\n" +
            "Enter desired ticker to view specific stock details:\n");
  }

  /**
   * Displays the manage portfolio menu.
   */
  @Override
  public void showManagePortfolioMenu() {
    messageAppend("Manage Your Portfolio(s):\n\n" +
            "1. Create a new portfolio\n" +
            "2. View and edit existing portfolio(s)\n");
  }

  /**
   * Displays the stock statistics menu for a given ticker.
   *
   * @param ticker The stock ticker symbol.
   */
  @Override
  public void showStockStats(String ticker) {
    messageAppend(ticker + "\n\n" +
            "Navigate the stock functionalities by inputting the number next to each menu item\n" +
            "1. Give Closing Price\n" +
            "2. Calculate Gain or Loss\n" +
            "3. Get X-Day Moving Average\n" +
            "4. Get X-Day Crossover\n");
  }

  /**
   * Displays the create portfolio screen.
   */
  @Override
  public void showCreatePortfolio() {
    messageAppend("Create New Portfolio:\n\n" +
            "What would you like to name your new portfolio?\n");
  }

  /**
   * Displays the existing portfolios for a client.
   *
   * @param c The client whose portfolios are to be viewed.
   */
  @Override
  public void viewExistingPortfolios(Client c) {
    messageAppend("Existing Portfolios:\n\n");
    for (Map.Entry<String, Portfolio> entry : c.getPortfolios().entrySet()) {
      messageAppend(entry.getKey() + "\n");
    }
    messageAppend("\nWhich portfolio would you like to view?\n");
  }

  @Override
  public void showSpecificPortfolio(IPortfolio p, LocalDate l) {
    messageAppend(p.printStocks(l));
  }

  /**
   * Displays the screen to add stocks to the portfolio.
   */
  @Override
  public void showAddStocks() {
    messageAppend("To add stocks to your portfolio, enter ticker and quantity.\n");
  }

  /**
   * Prompts the user to provide a date.
   *
   * @param in The scanner to read the date input.
   * @return The date provided by the user.
   */
  @Override
  public LocalDate provideDate(Scanner in) {
    int day = 0;
    int month = 0;
    int year = 0;
    LocalDate date = null;

    messageAppend("Please provide the date in the form of day, month, year.\n");

    boolean valid = false;
    while (!valid) {
      messageAppend("\nDay: ");
      day = getValidInteger(in);

      messageAppend("\nMonth: ");
      month = getValidInteger(in);

      messageAppend("\nYear: ");
      year = getValidInteger(in);

      try {
        date = LocalDate.of(year, month, day);
        valid = true; // If date is valid, exit the loop
      } catch (DateTimeException e) {
        messageAppend("Invalid date provided. Please enter a valid day, month, and year.\n");
      }
    }
    return date;
  }

  /**
   * Gets a valid integer input from the user.
   *
   * @param in The scanner to read the integer input.
   * @return The valid integer input from the user.
   */
  @Override
  public int getValidInteger(Scanner in) {
    while (!in.hasNextInt()) {
      in.next(); // Consume the non-integer input
      messageAppend("Invalid input. Please enter an integer.\n");
    }
    return in.nextInt();
  }

  /**
   * Displays a list of crossover dates.
   *
   * @param a The list of crossover dates.
   */
  @Override
  public void getListCrossovers(ArrayList<LocalDate> a) {
    String result = a.stream()
            .map(LocalDate::toString)
            .collect(Collectors.joining(", "));
    messageAppend(result);
  }

  @Override
  public void showPortfolioOptions() {
    messageAppend("\nPortfolio Function Options:\n"
            + "1. Calculate value of portfolio on a specific date \n"
            + "2. Purchase Stock on a specific date\n"
            + "3. Sell Stock on a specific date\n"
            + "4. Rebalance portfolio on a specific date\n"
            + "5. Composition of portfolio on a specific date\n"
            + "6. Performance of portfolio over time chart \n"
            + "7. The distribution of values on a specific date\n");
  }

  /**
   * Writes a message to the output.
   *
   * @param message The message to be written.
   */
  @Override
  public void writeMessage(String message) {
    messageAppend("\n" + message);
  }

  /**
   * Converts a double value to a string representation.
   *
   * @param d The double value to be converted.
   * @return The string representation of the double value.
   */
  @Override
  public String doubleToString(Double d) {
    return Double.toString(d);
  }

  /**
   * Displays an error message for an invalid option.
   */
  @Override
  public void showOptionError() {
    messageAppend("Given an invalid input, Please enter a valid option. "
            + "Input Any character to Continue\n");
  }


}
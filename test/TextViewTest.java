import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * class to test view.
 */
public class TextViewTest {
  private TextView textView;
  private ByteArrayOutputStream outputStream;


  /**
   * Sets up the environment before each test.
   */
  @Before
  public void setUp() {
    PrintStream printStream;
    outputStream = new ByteArrayOutputStream();
    printStream = new PrintStream(outputStream);
    textView = new TextView(printStream);
  }

  /**
   * Tests the welcomeScreen method.
   */
  @Test
  public void testWelcomeScreen() {
    textView.welcomeScreen();
    String expectedOutput = "Welcome to Stocks!\n\n" +
            "Navigate the program by inputting the number next to each menu item.\n" +
            "Enter 'q' to quit the program.\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the goBack method.
   */
  @Test
  public void testGoBack() {
    textView.goBack();
    String expectedOutput = "Enter 'b' to come back to the most recent menu.\n" +
            "To quit the program, navigate back to main menu.\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the showMainMenu method.
   */
  @Test
  public void testShowMainMenu() {
    textView.showMainMenu();
    String expectedOutput = "Main Menu:\n\n" +
            "1. Stock Information\n" +
            "2. Manage your portfolio(s)\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the showStockInfoMenu method.
   */
  @Test
  public void testShowStockInfoMenu() {
    textView.showStockInfoMenu();
    String expectedOutput = "Stock Information:\n\n" +
            "Popular Stock Tickers:\n" +
            "AAPL GOOG AMZN SMCI MSFT\n\n" +
            "Enter desired ticker to view specific stock details:\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the showManagePortfolioMenu method.
   */
  @Test
  public void testShowManagePortfolioMenu() {
    textView.showManagePortfolioMenu();
    String expectedOutput = "Manage Your Portfolio(s):\n\n" +
            "1. Create a new portfolio\n" +
            "2. View and edit existing portfolio(s)\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the showStockStats method.
   */
  @Test
  public void testShowStockStats() {
    textView.showStockStats("AAPL");
    String expectedOutput = "AAPL\n\n" +
            "Navigate the stock functionalities by inputting the number next to each menu item\n" +
            "1. Give Closing Price\n" +
            "2. Calculate Gain or Loss\n" +
            "3. Get X-Day Moving Average\n" +
            "4. Get X-Day Crossover\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the showCreatePortfolio method.
   */
  @Test
  public void testShowCreatePortfolio() {
    textView.showCreatePortfolio();
    String expectedOutput = "Create New Portfolio:\n\n" +
            "What would you like to name your new portfolio?\n";
    assertEquals(expectedOutput, outputStream.toString());
  }


  /**
   * Tests the showAddStocks method.
   */
  @Test
  public void testShowAddStocks() {
    textView.showAddStocks();
    String expectedOutput = "To add stocks to your portfolio, "
            + "enter ticker and quantity (ex. AAPL,2).\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the getListCrossovers method.
   */
  @Test
  public void testGetListCrossoversM() {
    ArrayList<LocalDate> crossovers = new ArrayList<>();
    crossovers.add(LocalDate.of(2023, 1, 1));
    crossovers.add(LocalDate.of(2023, 2, 1));

    textView.getListCrossovers(crossovers);
    String expectedOutput = crossovers.toString() + "\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the writeMessage method.
   */
  @Test
  public void testWriteMessage() {
    textView.writeMessage("");
    assertEquals("", outputStream.toString());
  }

  /**
   * Tests the showOptionError method.
   */
  @Test
  public void testShowOptionError() {
    textView.showOptionError();
    String expectedOutput = "Given an invalid input, "
            + "Please enter a valid option. Input Any character to Continue\n\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the provideDate method to ensure it correctly
   * reads and parses a date input from the user.
   */
  @Test
  public void testProvideDate() {
    String input = "10\n5\n2023\n";
    Scanner scanner = new Scanner(input);

    LocalDate expectedDate = LocalDate.of(2023, 5, 10);
    LocalDate actualDate = textView.provideDate(scanner);

    assertEquals(expectedDate, actualDate);
  }

  /**
   * Tests the getListCrossovers method to ensure it
   * correctly outputs a list of crossover dates.
   */
  @Test
  public void testGetListCrossovers() {
    ArrayList<LocalDate> crossovers = new ArrayList<>();
    crossovers.add(LocalDate.of(2023, 1, 1));
    crossovers.add(LocalDate.of(2023, 2, 1));

    textView.getListCrossovers(crossovers);

    String expectedOutput = crossovers.toString() + "\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the getValidInteger method to ensure it correctly
   * reads a valid integer input from the user.
   */
  @Test
  public void testGetValidInteger() {
    String input = "42\n";
    Scanner scanner = new Scanner(input);

    int expectedValue = 42;
    int actualValue = textView.getValidInteger(scanner);

    assertEquals(expectedValue, actualValue);
  }

  /**
   * Tests the getValidInteger method to ensure it handles invalid input correctly.
   */
  @Test
  public void testGetValidIntegerWithInvalidInput() {
    String input = "abc\n42\n";
    Scanner scanner = new Scanner(input);

    int expectedValue = 42;
    int actualValue = textView.getValidInteger(scanner);

    String expectedOutput = "Invalid input. Please enter an integer.\n";
    assertEquals(expectedOutput, outputStream.toString());
    assertEquals(expectedValue, actualValue);
  }


  /**
   * Tests the provideDate method to ensure it handles invalid date input correctly.
   */
  @Test
  public void testProvideDateWithInvalidInput() {
    String input = "32\n13\n2023\n10\n5\n2023\n";
    Scanner scanner = new Scanner(input);

    LocalDate expectedDate = LocalDate.of(2023, 5, 10);
    LocalDate actualDate = textView.provideDate(scanner);

    String expectedOutput = "Please provide the date in the form of day, month, year.\n\n" +
            "Day: \nMonth: \nYear: Invalid date provided. Please enter a valid day, month, and y" +
            "ear.\n" +
            "\nDay: \nMonth: \nYear: ";

    assertEquals(expectedOutput, outputStream.toString());
    assertEquals(expectedDate, actualDate);
  }

  /**
   * Tests the ViewExistingPortfolios method to ensure it outputs correct portfolios.
   */
  @Test
  public void testViewExistingPortfolios() {
    Client client = new Client("TestClient");
    client.createPortfolio("Portfolio1");
    client.createPortfolio("Portfolio2");

    textView.viewExistingPortfolios(client);

    String expectedOutput = "Existing Portfolios:\n\nPortfolio1\nPortfolio2\n\n" +
            "Which portfolio would you like to view?\n";
    assertEquals(expectedOutput, outputStream.toString());
  }

  /**
   * Tests the showSpecificPortfolios method to ensure it outputs individual portfolios correctly.
   */
  @Test
  public void testShowSpecificPortfolio() {
    Client client = new Client("TestClient");
    Portfolio portfolio = client.createPortfolio("PortfolioWithStocks");
    String expectedOutput = "'PortfolioWithStocks'\n" +
            "{}To add stocks to your portfolio, enter ticker and quantity.\n";
    textView.showSpecificPortfolio("PortfolioWithStocks", client);
    assertEquals(expectedOutput, outputStream.toString());
    Stock stock1 = new Stock("AAPL", new TreeMap<>());
    Stock stock2 = new Stock("GOOG", new TreeMap<>());
    portfolio.addStock(stock1, 10);
    portfolio.addStock(stock2, 5);

    textView.showSpecificPortfolio("PortfolioWithStocks", client);

    expectedOutput += "'PortfolioWithStocks'\n" +
            "{GOOG, 5; AAPL, 10; }To add stocks to your portfolio, enter ticker and quantity.\n";
    assertEquals(expectedOutput, outputStream.toString());
  }


}


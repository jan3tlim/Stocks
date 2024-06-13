import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Tests for the Portfolio class.
 */
public class PortfolioTest {
  private Portfolio portfolio;
  private Stock apple;
  private Stock google;
  private Stock amazon;
  private Client client;

  @Before
  public void setUp() {
    client = new Client("Test Client");
    portfolio = new Portfolio("Tech Portfolio", client);

    TreeMap<LocalDate, Double> applePrices = new TreeMap<>();
    applePrices.put(LocalDate.of(2024, 1, 1), 150.0);
    applePrices.put(LocalDate.of(2024, 1, 15), 155.0);
    applePrices.put(LocalDate.of(2024, 2, 1), 160.0);
    applePrices.put(LocalDate.of(2024, 3, 1), 170.0);
    applePrices.put(LocalDate.of(2024, 6, 1), 120.0);
    applePrices.put(LocalDate.of(2026, 1, 1), 100.0);

    TreeMap<LocalDate, Double> googlePrices = new TreeMap<>();
    googlePrices.put(LocalDate.of(2024, 1, 1), 2500.0);
    googlePrices.put(LocalDate.of(2024, 1, 15), 2530.0);
    googlePrices.put(LocalDate.of(2024, 2, 1), 2550.0);
    googlePrices.put(LocalDate.of(2024, 3, 1), 2600.0);
    googlePrices.put(LocalDate.of(2024, 6, 1), 2100.0);
    googlePrices.put(LocalDate.of(2026, 1, 1), 2000.0);

    TreeMap<LocalDate, Double> amazonPrices = new TreeMap<>();
    amazonPrices.put(LocalDate.of(2024, 1, 1), 2500.0);
    amazonPrices.put(LocalDate.of(2024, 1, 15), 2530.0);
    amazonPrices.put(LocalDate.of(2024, 2, 1), 2550.0);
    amazonPrices.put(LocalDate.of(2024, 3, 1), 2600.0);
    amazonPrices.put(LocalDate.of(2024, 6, 1), 2100.0);
    amazonPrices.put(LocalDate.of(2026, 1, 1), 2000.0);

    apple = new Stock("AAPL", applePrices);
    google = new Stock("GOOGL", googlePrices);
    amazon = new Stock("AMZN", amazonPrices);
  }

  /**
   * Tests adding a valid stock quantity to the portfolio on a valid date.
   */
  @Test
  public void testAddStock() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    assertTrue(portfolio.getStocks(LocalDate.of(2024, 1, 1)).
            containsKey(apple));
    assertEquals(Double.valueOf(10), portfolio.getStocks(LocalDate.of(2024, 1,
            1)).get(apple));
  }

  /**
   * Tests the exception handling for adding a stock with an invalid date.
   */
  @Test(expected = java.time.DateTimeException.class)
  public void testAddStockInvalidDate() {
    portfolio.addStock(apple, 10, LocalDate.of(2030, -1, 1));

  }

  /**
   * Tests adding a negative quantity of stock to ensure the method throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNegativeStock() {
    portfolio.addStock(apple, -5, LocalDate.of(
            2024, 1, 1));
  }


  /**
   * Tests adding a zero quantity of stock to ensure the method throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddZeroStock() {
    portfolio.addStock(apple, 0, LocalDate.of(2024, 1, 1));
  }

  /**
   * Tests removing a specific quantity of a stock from the portfolio.
   */
  @Test
  public void testRemoveStock() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.removeStock(apple, 5, LocalDate.of(2024, 1, 1));
    assertEquals(Double.valueOf(5), portfolio.getStocks(LocalDate.of(2024, 1,
            1)).get(apple));
    portfolio.removeStock(apple, 5, LocalDate.of(2024, 1, 1));
    assertFalse(portfolio.getStocks(LocalDate.of(2024, 1, 1))
            .containsKey(apple));
  }

  /**
   * Tests removing stock that is not present in the portfolio to ensure the correct exception is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveStockNotInPortfolio() {
    portfolio.removeStock(apple, 10, LocalDate.of(2024, 1, 1));
  }

  /**
   * Tests removing a negative quantity of stock to ensure the method throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNegativeStock() {
    portfolio.removeStock(apple, -5, LocalDate.of(2024, 1, 1));
  }

  /**
   * Tests the exception handling for adding a stock with an invalid date.
   */
  @Test(expected = java.time.DateTimeException.class)
  public void testRemoveStockInvalidDate() {
    portfolio.removeStock(apple, 10, LocalDate.of(2030, -1, 1));

  }

  /**
   * Tests adding a zero quantity of stock to ensure the method throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveZeroStock() {
    portfolio.removeStock(apple, 0, LocalDate.of(2024, 1, 1));
  }

  /**
   * Tests removing a quantity of stock that exceeds available value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveMoreThanAvailable() {
    portfolio.addStock(apple, 5, LocalDate.of(2024, 1, 1));
    portfolio.removeStock(apple, 10, LocalDate.of(2024, 1, 1));
  }

  /**
   * Tests calculating the total portfolio value on a specific date.
   */
  @Test
  public void testCalculatePortfolioValue() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    assertEquals((Double) 6500.0, portfolio.calculatePortfolioValue(LocalDate.of(2024,
            1, 1)));
  }

  /**
   * Tests the output format of the printStocks method to ensure it correctly formats the string.
   */
  @Test
  public void testPrintStocks() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    String expected = "'Tech Portfolio'\n{GOOGL, 2.0; AAPL, 10.0; }";
    assertEquals(expected, portfolio.printStocks(LocalDate.of(2024,
            2, 1)));
  }

  /**
   * Tests the performance charting over time to ensure it includes correct time intervals and formatting.
   */
  @Test
  public void testPerformanceOverTime() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate start = LocalDate.of(2024, 1, 1);
    LocalDate end = LocalDate.of(2024, 3, 1);
    String chart = portfolio.performanceOverTime(start, end);

    System.out.println(chart);

    assertTrue(chart.contains("Performance of portfolio 'Tech Portfolio'"));
    assertTrue(chart.contains("Jan 2024"));
    assertTrue(chart.contains("Feb 2024"));
    assertTrue(chart.contains("Mar 2024"));
    assertTrue(chart.contains("Scale: * = "));
  }

  /**
   * Tests the performance charting over time to ensure it throws an exception for invalid dates.
   */
  @Test
  public void testPerformanceOverTimeWithInvalidDates() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate start = LocalDate.of(2024, 3, 1);
    LocalDate end = LocalDate.of(2024, 1, 1);

    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.performanceOverTime(start, end);
    });
  }

  @Test
  public void testDistributionOfValueWithOneStock() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2024, 1, 1);
    String distribution = portfolio.distributionOfValueOnDate(date);
    String expected = "Distribution of value on 2024-01-01:\n" +
            "AAPL: $1500.0\n" +
            "Total Portfolio Value: $1500.0";
    assertEquals(expected, distribution);
  }

  /**
   * Tests the detailed distribution of portfolio values on a given date, ensuring correct value calculation and format.
   */
  @Test
  public void testDistributionOfValueOnDate() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2024, 1, 1);
    String distribution = portfolio.distributionOfValueOnDate(date);
    String expected = "Distribution of value on 2024-01-01:\n" +
            "GOOGL: $5000.0\n" +
            "AAPL: $1500.0\n" +
            "Total Portfolio Value: $6500.0";
    assertEquals(expected, distribution);
  }

  /**
   * Tests the distribution of value for a date where the portfolio contains no stocks, expecting a message indicating no value.
   */
  @Test
  public void testDistributionOfValueWithNoStocks() {
    LocalDate date = LocalDate.of(2024, 2, 1);
    String distribution = portfolio.distributionOfValueOnDate(date);
    String expected = "The portfolio has no value on " + date;
    assertEquals(expected, distribution);
  }

  /**
   * Tests the distribution of value for an invalid date.
   */
  @Test(expected = java.time.DateTimeException.class)
  public void testDistributionOfValueWithInvalidDates() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    portfolio.distributionOfValueOnDate(LocalDate.of(2024, -2, 1));
  }


  /**
   * Tests the distribution of value for a date where the portfolio has no value.
   */
  @Test
  public void testDistributionOfValueOnDateWithNoValue() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2023, 12, 31);
    String distribution = portfolio.distributionOfValueOnDate(date);
    String expected = "The portfolio has no value on 2023-12-31";
    assertEquals(expected, distribution);
  }

  /**
   * Tests calculating the value of the portfolio for a future date prior to any stocks being added, expecting a result of zero.
   */
  @Test
  public void testCalculatePortfolioValueBeforeBeforeStockAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2023, 12, 31);
    assertEquals(0.0, portfolio.calculatePortfolioValue(date), 0.001);
  }

  /**
   * Tests calculating the value of the portfolio with one portfolio added for that specific date.
   */
  @Test
  public void testCalculatePortfolioValueOfDateWhenOnlyOneStockWasAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 1, 1);
    double expectedValue = 10 * 150.0;
    assertEquals(expectedValue, portfolio.calculatePortfolioValue(date), 0.001);
  }

  /**
   * Tests calculating the value of the portfolio for an invalid date.
   */
  @Test(expected = java.time.DateTimeException.class)
  public void testCalculatePortfolioValueInvalidDate() {
    portfolio.calculatePortfolioValue(LocalDate.of(2024, -1, 1));
  }

  /**
   * Tests calculating the value of the portfolio with multiple stocks.
   */
  @Test
  public void testCalculatePortfolioValueMultipleStocks() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    portfolio.addStock(amazon, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2024, 1, 1);
    double expectedValue = 10 * 150.0;
    expectedValue += (4 * 2500);
    assertEquals(expectedValue, portfolio.calculatePortfolioValue(date), 0.001);
  }

  /**
   * Tests calculating the value of a stock.
   */
  @Test
  public void testCalculateStockValue() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 1);
    double expectedValue = 10 * 160.0;
    assertEquals(expectedValue, portfolio.calculateStockValue(apple, date), 0.001);
  }

  /**
   * Tests calculating the value of a stock before it was added so the value is 0.
   */
  @Test
  public void testCalculateStockValueBeforeStockAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2023, 12, 31);
    assertEquals(0.0, portfolio.calculateStockValue(apple, date), 0.001);
  }

  /**
   * Tests the calculating the value of a stock.
   */
  @Test
  public void testCalculateStockValueOnDateAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 1);
    double expectedValue = 2 * 2550.0;
    assertEquals(expectedValue, portfolio.calculateStockValue(google, date), 0.001);
  }

  /**
   * Tests the removal of stock after it was added and ensures the value calculation
   * adjusts accordingly.
   */
  @Test
  public void testCalculateStockValueAfterStockRemoved() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    portfolio.removeStock(apple, 10, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 2);
    assertEquals(0.0, portfolio.calculateStockValue(apple, date), 0.001);
  }

  /**
   * Tests the rebalancing functionality with mock scanner input to ensure the portfolio adjusts stocks to meet target percentages.
   */
  @Test
  public void testRebalance() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));

    Scanner mockScanner = new Scanner("50\n50\nyes\n");

    String beforeRebalance = portfolio.printStocks(LocalDate.of(2024, 1,
            1));
    System.out.println("Before rebalance: " + beforeRebalance);
    IView v = new TextView(System.out);

    portfolio.rebalance(mockScanner, LocalDate.of(2024, 1, 1),v);

    String afterRebalance = portfolio.printStocks(LocalDate.of(2024,
            1, 1));
    System.out.println("After rebalance: " + afterRebalance);

    Map<IStock, Double> expectedDistribution = portfolio.getStocks(LocalDate.of(2024,
            1, 1));
    double totalValue = portfolio.calculatePortfolioValue(LocalDate.of(2024,
            1, 1));
    double expectedAppleQuantity = (totalValue / 2) / apple.getClosingPrice(LocalDate.of(
            2024, 1, 1));
    double expectedGoogleQuantity = (totalValue / 2) / google.getClosingPrice(LocalDate.of(
            2024, 1, 1));

    assertEquals(21.67, expectedDistribution.get(apple), 0.001);
    assertEquals(expectedGoogleQuantity, expectedDistribution.get(google), 0.001);
  }

  @Test
  public void testRebalanceWithDifferentTargets() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));

    // Mocking user input for different targets
    Scanner mockScanner = new Scanner("30\n70\nyes\n");

    // Before rebalancing
    String beforeRebalance = portfolio.printStocks(LocalDate.of(2024,
            1, 1));
    System.out.println("Before rebalance: " + beforeRebalance);
    IView v = new TextView(System.out);
    portfolio.rebalance(mockScanner, LocalDate.of(2024, 1, 1),v);

    // After rebalancing
    String afterRebalance = portfolio.printStocks(LocalDate.of(2024,
            1, 1));
    System.out.println("After rebalance: " + afterRebalance);

    // Check the distribution
    Map<IStock, Double> expectedDistribution = portfolio.getStocks(LocalDate.of(2024,
            1, 1));
    double totalValue = portfolio.calculatePortfolioValue(LocalDate.of(2024,
            1, 1));
    double expectedAppleQuantity = (totalValue * 0.30) / apple.getClosingPrice(LocalDate.of(
            2024, 1, 1));
    double expectedGoogleQuantity = (totalValue * 0.70) / google.getClosingPrice(LocalDate.of(
            2024, 1, 1));

    assertEquals(30.33, expectedDistribution.get(apple), 0.001);
    assertEquals(0.78, expectedDistribution.get(google), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPerformanceOverTimeWithLessThanFiveDays() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate start = LocalDate.of(2024, 1, 1);
    LocalDate end = LocalDate.of(2024, 1, 2);
    String chart = portfolio.performanceOverTime(start, end);
  }
  @Test
  public void testPerformanceOverTimeWithDaysInterval() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate start = LocalDate.of(2024, 1, 1);
    LocalDate end = LocalDate.of(2024, 1, 15); // Span less than 30 days
    String chart = portfolio.performanceOverTime(start, end);

    System.out.println(chart);

    assertTrue(chart.contains("Performance of portfolio 'Tech Portfolio'"));
    assertTrue(chart.contains("01 Jan 2024"));
    assertTrue(chart.contains("15 Jan 2024"));
    assertTrue(chart.contains("Scale: * = "));
  }

  @Test
  public void testPerformanceOverTimeWithMonthsInterval() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate start = LocalDate.of(2024, 1, 1);
    LocalDate end = LocalDate.of(2024, 6, 1); // Span more than 30 days
    // but less than 2 years
    String chart = portfolio.performanceOverTime(start, end);

    System.out.println(chart);

    assertTrue(chart.contains("Performance of portfolio 'Tech Portfolio'"));
    assertTrue(chart.contains("Jan 2024"));
    assertTrue(chart.contains("Jun 2024"));
    assertTrue(chart.contains("Scale: * = "));
  }

  @Test
  public void testPerformanceOverTimeWithYearsInterval() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate start = LocalDate.of(2024, 1, 1);
    LocalDate end = LocalDate.of(2026, 1, 1); // Span more than 2 years
    String chart = portfolio.performanceOverTime(start, end);

    System.out.println(chart);

    assertTrue(chart.contains("Performance of portfolio 'Tech Portfolio'"));
    assertTrue(chart.contains("2024"));
    assertTrue(chart.contains("2026"));
    assertTrue(chart.contains("Scale: * = "));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceWithWrongDate() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate wrongDate = LocalDate.of(2024, 5, 1);
    Scanner scanner = new Scanner(System.in);
    IView view = new TextView(System.out);
    portfolio.rebalance(scanner, wrongDate, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalanceWithNoStocks() {
    LocalDate date = LocalDate.of(2024, 5, 1);
    Scanner scanner = new Scanner(System.in);
    IView view = new TextView(System.out);
    portfolio.rebalance(scanner, date, view);
  }

  @Test
  public void testRebalancePercentagesDoNotAddUp() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));

    String inputData = "60\n20\n";
    Scanner scanner = new Scanner(inputData);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outContent);
    IView view = new TextView(printStream); // Pass the custom PrintStream to TextView

    portfolio.rebalance(scanner, LocalDate.of(2024, 1, 1), view);

    String output = outContent.toString();
    assertTrue(output.contains("The target percentages do not add up to 100%. Please try again."));
  }

  @Test
  public void testRebalanceWithZeroPercentageForOneStock() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2024, 1, 1);
    Scanner scanner = new Scanner("0\n100\nyes\n");
    scanner.useDelimiter("\n");
    IView view = new TextView(System.out);
    portfolio.rebalance(scanner, date, view);
    assertEquals(43.33, portfolio.getStocks(date).get(apple), 0.01);
    assertNull(portfolio.getStocks(date).get(google));
  }

  @Test
  public void testRebalanceWithZeroPercentageForTwoStocks() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    portfolio.addStock(amazon, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2024, 1, 1);
    Scanner scanner = new Scanner("0\n0\n100\nyes\n");
    scanner.useDelimiter("\n");
    IView view = new TextView(System.out);
    portfolio.rebalance(scanner, date, view);
    assertNull(portfolio.getStocks(date).get(google));
    assertNull(portfolio.getStocks(date).get(apple));
    assertEquals(4.6, portfolio.getStocks(date).get(amazon), 0.01);
  }

  @Test
  public void testPrintStocksEmptyPortfolio() {
    LocalDate testDate = LocalDate.of(2024, 1, 1);
    String expectedOutput = "'Tech Portfolio'\n{}";
    assertEquals(expectedOutput, portfolio.printStocks(testDate));
  }

  @Test
  public void testPrintStocksWithMultipleStocks() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 5, LocalDate.of(2024, 1, 1));

    LocalDate testDate = LocalDate.of(2024, 1, 1);
    String expectedOutput = "'Tech Portfolio'\n{GOOGL, 5.0; AAPL, 10.0; }";
    assertEquals(expectedOutput, portfolio.printStocks(testDate));
  }

  @Test
  public void testPrintStocksNoStocksOnDate() {
    // Add stocks with a different date
    portfolio.addStock(apple, 10, LocalDate.of(2024, 2, 1));
    portfolio.addStock(google, 5, LocalDate.of(2024, 2, 1));

    LocalDate testDate = LocalDate.of(2024, 1, 1);
    String expectedOutput = "'Tech Portfolio'\n{}";
    assertEquals(expectedOutput, portfolio.printStocks(testDate));
  }

  @Test(expected = java.time.DateTimeException.class)
  public void testPrintStocksInvalidDate() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 2, 1));
    portfolio.addStock(google, 5, LocalDate.of(2024, 2, 1));
    portfolio.printStocks(LocalDate.of(2024, -1, 1));
  }

  @Test
  public void testSavePortfolio() throws IOException {

    Client client = new Client("Test Client");
    Portfolio portfolio = new Portfolio("Tech Portfolio", client);
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));

    File tempFile = File.createTempFile("portfolio", ".txt");
    tempFile.deleteOnExit();

    portfolio.savePortfolio(tempFile.getAbsolutePath());

    List<String> lines = Files.readAllLines(tempFile.toPath());
    assertTrue(lines.contains("name:Tech Portfolio"));
    assertTrue(lines.contains("stocks:"));
    assertTrue(lines.stream().anyMatch(line -> line.contains("ticker:AAPL:quantity:" +
            "10.0:dateAdded:2024-01-01")));

    tempFile.delete();
  }

  @Test
  public void testLoadPortfolio() throws IOException {
    File tempFile = File.createTempFile("portfolio", ".txt");
    List<String> content = Arrays.asList(
            "name:Tech Portfolio",
            "stocks:",
            "ticker:AAPL:quantity:10.0:dateAdded:2024-01-01"
    );
    Files.write(tempFile.toPath(), content);


    Client client = new Client("Test Client");
    Portfolio portfolio = new Portfolio("Empty Portfolio", client);


    portfolio.loadPortfolio(tempFile);


    assertEquals("Tech Portfolio", portfolio.getName());
    assertNotNull(portfolio.getStocks(LocalDate.of(2024, 1, 1)));
    assertEquals(10.0, portfolio.getStocks(LocalDate.of(2024, 1,
            1)).get(apple), 0.01);


    tempFile.delete();
  }


}
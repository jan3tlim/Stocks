import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Portfolio class.
 */
public class PortfolioTest {
  private Portfolio portfolio;
  private Stock apple;
  private Stock google;
  private Client client;

  @Before
  public void setUp() {
    client = new Client("Test Client");
    portfolio = new Portfolio("Tech Portfolio", client);

    TreeMap<LocalDate, Double> applePrices = new TreeMap<>();
    applePrices.put(LocalDate.of(2024, 1, 1), 150.0);
    applePrices.put(LocalDate.of(2024, 2, 1), 160.0);
    applePrices.put(LocalDate.of(2024, 3, 1), 170.0);

    TreeMap<LocalDate, Double> googlePrices = new TreeMap<>();
    googlePrices.put(LocalDate.of(2024, 1, 1), 2500.0);
    googlePrices.put(LocalDate.of(2024, 2, 1), 2550.0);
    googlePrices.put(LocalDate.of(2024, 3, 1), 2600.0);

    apple = new Stock("AAPL", applePrices);
    google = new Stock("GOOGL", googlePrices);
  }

  @Test
  public void testAddStock() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    assertTrue(portfolio.getStocks(LocalDate.of(2024, 1, 1)).containsKey(apple));
    assertEquals(10, (int) portfolio.getStocks(LocalDate.of(2024, 1, 1)).get(apple));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNegativeStock() {
    portfolio.addStock(apple, -5, LocalDate.of(2024, 1, 1));
  }

  @Test
  public void testRemoveStock() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.removeStock(apple, 5, LocalDate.of(2024, 1, 1));
    assertEquals(5, (int) portfolio.getStocks(LocalDate.of(2024, 1, 1)).get(apple));
    portfolio.removeStock(apple, 5, LocalDate.of(2024, 1, 1));
    assertFalse(portfolio.getStocks(LocalDate.of(2024, 1, 1)).containsKey(apple));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveMoreThanAvailable() {
    portfolio.addStock(apple, 5, LocalDate.of(2024, 1, 1));
    portfolio.removeStock(apple, 10, LocalDate.of(2024, 1, 1));
  }

  @Test
  public void testCalculatePortfolioValue() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    assertEquals((Double) 6500.0, portfolio.calculatePortfolioValue(LocalDate.of(2024, 1, 1)));
  }


  @Test
  public void testPrintStocks() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    String expected = "'Tech Portfolio'\n{GOOGL, 2; AAPL, 10; }";
    assertEquals(expected, portfolio.printStocks(LocalDate.of(2024, 2, 1)));
  }

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

  @Test
  public void testDistributionOfValueOnDifferentDate() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2024, 2, 1);
    String distribution = portfolio.distributionOfValueOnDate(date);
    String expected = "Distribution of value on 2024-02-01:\n" +
            "GOOGL: $5100.0\n" +
            "AAPL: $1600.0\n" +
            "Total Portfolio Value: $6700.0";
    assertEquals(expected, distribution);
  }

  @Test
  public void testDistributionOfValueOnDateWithNoValue() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 1, 1));
    LocalDate date = LocalDate.of(2023, 12, 31);
    String distribution = portfolio.distributionOfValueOnDate(date);
    String expected = "The portfolio has no value on 2023-12-31";
    assertEquals(expected, distribution);
  }

  @Test
  public void testCalculatePortfolioValueBeforeExistence() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2023, 12, 31);
    assertEquals(0.0, portfolio.calculatePortfolioValue(date), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCalculatePortfolioValueBeforeStockAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 1, 15);
    double expectedValue = 10 * 150.0;  // Only apple is present
    assertEquals(expectedValue, portfolio.calculatePortfolioValue(date), 0.001);
  }

  @Test
  public void testCalculateStockValue() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 1);
    double expectedValue = 10 * 160.0;
    assertEquals(expectedValue, portfolio.calculateStockValue(apple, date), 0.001);
  }

  @Test
  public void testCalculateStockValueBeforeStockAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2023, 12, 31);
    assertEquals(0.0, portfolio.calculateStockValue(apple, date), 0.001);
  }

  @Test
  public void testCalculateStockValueOnDateAdded() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 1);
    double expectedValue = 2 * 2550.0;
    assertEquals(expectedValue, portfolio.calculateStockValue(google, date), 0.001);
  }

  @Test
  public void testCalculateStockValueAfterStockRemoved() {
    portfolio.addStock(apple, 10, LocalDate.of(2024, 1, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    portfolio.removeStock(apple, 10, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 2);
    assertEquals(0.0, portfolio.calculateStockValue(apple, date), 0.001);
  }
}
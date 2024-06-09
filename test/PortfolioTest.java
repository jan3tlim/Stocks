import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;

import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * portfolio test.
 */
public class PortfolioTest {
  private Portfolio portfolio;
  private Stock apple;
  private Stock google;


  /**
   * set up for test.
   */
  @Before
  public void setUp() {
    TreeMap<LocalDate, Double> applePrices;
    TreeMap<LocalDate, Double> googlePrices;
    Client client;
    client = new Client("Janet Lim");
    portfolio = new Portfolio("Tech Portfolio", client);

    applePrices = new TreeMap<>();
    applePrices.put(LocalDate.of(2024, 6, 1), 173.5);
    applePrices.put(LocalDate.of(2024, 5, 1), 170.5);

    googlePrices = new TreeMap<>();
    googlePrices.put(LocalDate.of(2024, 6, 1), 173.5);
    googlePrices.put(LocalDate.of(2024, 5, 1), 170.5);

    apple = new Stock("AAPL", applePrices);
    google = new Stock("GOOG", googlePrices);

  }

  /**
   * test add stock.
   */
  @Test
  public void testAddStock() {
    portfolio.addStock(apple, 10, LocalDate.now());
    assertTrue(portfolio.getStocks().containsKey(apple));
    assertEquals(10, (int) portfolio.getStocks().get(apple));

    assertTrue(portfolio.getDatesAdded().containsKey(apple));
    assertEquals(LocalDate.now(), portfolio.getDatesAdded().get(apple));
  }

  /**
   * test add negative quantity.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNegativeQuantity() {
    portfolio.addStock(apple, -5, LocalDate.now());
  }


  /**
   * test remove stock.
   */
  @Test
  public void testRemoveStock() {
    portfolio.addStock(apple, 10, LocalDate.now());
    portfolio.removeStock(apple, 5, LocalDate.now());
    assertEquals(5, (int) portfolio.getStocks().get(apple));
    portfolio.removeStock(apple, 5, LocalDate.now());
    assertFalse(portfolio.getStocks().containsKey(apple));
  }

  /**
   * test remove negative quantity.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNegativeQuantity() {
    portfolio.removeStock(apple,
            -5, LocalDate.now());
  }

  /**
   * test calculate portfolio value.
   */
  @Test
  public void testCalculatePortfolioValue() {
    portfolio.addStock(apple, 1, LocalDate.now());
    assertEquals((Double) 173.5,
            portfolio.calculatePortfolioValue(LocalDate.of(2024, 6, 1)));
    portfolio.addStock(google, 1, LocalDate.now());
    assertEquals((Double) 347.0,
            portfolio.calculatePortfolioValue(LocalDate.of(2024, 6, 1)));
  }

  /**
   * Calculate gain or loss with Invalid date a negative month.
   */
  @Test(expected = java.time.DateTimeException.class)
  public void testCalculatePortfolio() {
    portfolio.calculatePortfolioValue(LocalDate.of(2024, -6, 1));
  }


}

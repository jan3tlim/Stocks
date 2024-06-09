import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;

import java.util.TreeMap;

import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 * class to test stocks.
 */
public class StockTest {

  IStock amazon;
  IStock google;
  IStock samsung;
  IStock sony;
  IStock apple;

  /**
   * set up for stock test.
   *
   * @throws ParseException h.
   */
  @Before
  public void setUp() throws ParseException {


    samsung = new Stock("samsung");
    sony = new Stock("sony");

    TreeMap<LocalDate, Double> appleHistory = new TreeMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");

    try {

      appleHistory.put(LocalDate.parse("5/28/24", formatter), 178.02);
      appleHistory.put(LocalDate.parse("5/24/24", formatter), 176.33);
      appleHistory.put(LocalDate.parse("5/23/24", formatter), 175.06);
      appleHistory.put(LocalDate.parse("5/22/24", formatter), 178.0);
      appleHistory.put(LocalDate.parse("5/21/24", formatter), 179.54);
      appleHistory.put(LocalDate.parse("5/20/24", formatter), 178.46);
      appleHistory.put(LocalDate.parse("5/17/24", formatter), 177.29);
      appleHistory.put(LocalDate.parse("5/16/24", formatter), 175.43);
      appleHistory.put(LocalDate.parse("5/15/24", formatter), 173.88);
      appleHistory.put(LocalDate.parse("5/14/24", formatter), 171.93);
      appleHistory.put(LocalDate.parse("5/13/24", formatter), 170.9);
      appleHistory.put(LocalDate.parse("5/10/24", formatter), 170.29);
      appleHistory.put(LocalDate.parse("5/9/24", formatter), 171.58);
      appleHistory.put(LocalDate.parse("5/8/24", formatter), 171.16);
      appleHistory.put(LocalDate.parse("5/7/24", formatter), 172.98);
      appleHistory.put(LocalDate.parse("5/6/24", formatter), 169.83);
      appleHistory.put(LocalDate.parse("5/3/24", formatter), 168.99);
      appleHistory.put(LocalDate.parse("5/2/24", formatter), 168.46);
      appleHistory.put(LocalDate.parse("5/1/24", formatter), 165.57);
      appleHistory.put(LocalDate.parse("4/30/24", formatter), 164.64);
      appleHistory.put(LocalDate.parse("4/29/24", formatter), 167.9);
      appleHistory.put(LocalDate.parse("4/26/24", formatter), 173.69);
      appleHistory.put(LocalDate.parse("4/25/24", formatter), 157.95);
      appleHistory.put(LocalDate.parse("4/24/24", formatter), 161.1);
      appleHistory.put(LocalDate.parse("4/23/24", formatter), 159.92);
      appleHistory.put(LocalDate.parse("4/22/24", formatter), 157.95);
      appleHistory.put(LocalDate.parse("4/19/24", formatter), 155.72);
      appleHistory.put(LocalDate.parse("4/18/24", formatter), 157.46);
      appleHistory.put(LocalDate.parse("4/17/24", formatter), 156.88);
      appleHistory.put(LocalDate.parse("4/16/24", formatter), 156.0);
      appleHistory.put(LocalDate.parse("4/15/24", formatter), 156.33);
      appleHistory.put(LocalDate.parse("4/12/24", formatter), 159.19);
      appleHistory.put(LocalDate.parse("4/11/24", formatter), 160.79);
      appleHistory.put(LocalDate.parse("4/10/24", formatter), 157.66);
      appleHistory.put(LocalDate.parse("4/9/24", formatter), 158.14);
      appleHistory.put(LocalDate.parse("4/8/24", formatter), 156.14);
      appleHistory.put(LocalDate.parse("4/5/24", formatter), 153.94);
      appleHistory.put(LocalDate.parse("4/4/24", formatter), 151.94);
      appleHistory.put(LocalDate.parse("4/3/24", formatter), 156.37);
      appleHistory.put(LocalDate.parse("4/2/24", formatter), 155.87);
      appleHistory.put(LocalDate.parse("4/1/24", formatter), 156.5);
      appleHistory.put(LocalDate.parse("3/28/24", formatter), 152.26);
      appleHistory.put(LocalDate.parse("3/27/24", formatter), 151.94);
      appleHistory.put(LocalDate.parse("3/26/24", formatter), 151.7);
      appleHistory.put(LocalDate.parse("3/25/24", formatter), 151.15);

    } catch (DateTimeParseException e) {
      System.err.println("Error parsing date: " + e.getMessage());
      // Handle the exception, e.g., log it or throw a custom exception
    }

    apple = new Stock("AAPL", appleHistory);

    TreeMap<LocalDate, Double> googleHistory = new TreeMap<>();

    try {
      // Sample data for google's stock prices over various dates
      googleHistory.put(LocalDate.parse("5/28/24", formatter), 2734.87);
      googleHistory.put(LocalDate.parse("5/24/24", formatter), 2700.45);
      googleHistory.put(LocalDate.parse("5/23/24", formatter), 2688.24);
      googleHistory.put(LocalDate.parse("5/22/24", formatter), 2695.00);
      googleHistory.put(LocalDate.parse("5/21/24", formatter), 2712.56);
      googleHistory.put(LocalDate.parse("5/20/24", formatter), 2708.89);
      googleHistory.put(LocalDate.parse("5/17/24", formatter), 2690.11);
      googleHistory.put(LocalDate.parse("5/16/24", formatter), 2685.93);
      googleHistory.put(LocalDate.parse("5/15/24", formatter), 2675.88);
      googleHistory.put(LocalDate.parse("5/14/24", formatter), 2660.50);
      googleHistory.put(LocalDate.parse("5/13/24", formatter), 2655.32);
      googleHistory.put(LocalDate.parse("5/10/24", formatter), 2649.77);
      googleHistory.put(LocalDate.parse("5/9/24", formatter), 2652.86);
      googleHistory.put(LocalDate.parse("5/8/24", formatter), 2638.29);
      googleHistory.put(LocalDate.parse("5/7/24", formatter), 2620.54);
      googleHistory.put(LocalDate.parse("5/6/24", formatter), 2605.23);
      googleHistory.put(LocalDate.parse("5/3/24", formatter), 2594.67);
      googleHistory.put(LocalDate.parse("5/2/24", formatter), 2585.56);
      googleHistory.put(LocalDate.parse("5/1/24", formatter), 2572.87);
      googleHistory.put(LocalDate.parse("4/30/24", formatter), 2550.89);

    } catch (DateTimeParseException e) {
      System.err.println("Error parsing date: " + e.getMessage());
      // Handle the exception, e.g., log it or throw a custom exception
    }

    google = new Stock("GOOGL", googleHistory);


    AlphaVantageAPI api = new AlphaVantageAPI();
    amazon = new Stock("AMZN", api.fetchStockHistory("AMZN_data.csv"));

  }


  /**
   * Tests the calculation of the 30-day moving average.
   */
  @Test
  public void calculate30DayMovingAverage() {
    double result =
            apple.calculateXDayMovingAverage(
                    LocalDate.of(2024, 5, 22), 30);
    assertEquals(169.89, result, 0.01);
  }

  /**
   * Tests the calculation of the moving average when data
   * is not available, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void DoNotHaveDataOfMovingAverage() {
    double result = apple.calculateXDayMovingAverage(
            LocalDate.of(2024, 7, 22), 30);
  }

  /**
   * Tests the calculation of the 100-day moving average
   * when data is not available, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void DoNotHaveDataOfTheLast60DayMovingAverage() {
    double result = apple.calculateXDayMovingAverage(
            LocalDate.of(2024, 4, 22), 100);
  }

  /**
   * Tests the calculation of gain/loss over a specific period.
   */
  @Test
  public void calculateGainLoss() {
    double result = apple.calculateGainLoss(
            LocalDate.of(2024, 4, 22),
            LocalDate.of(2024, 5, 22));
    assertEquals(20.05, result, 0.01);
  }

  /**
   * Tests the calculation of gain/loss when the end date is greater than
   * the start date, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void ifCalculateGainLossEndDateGreaterThanStartDate() {
    double result = apple.calculateGainLoss(
            LocalDate.of(2024, 5, 22),
            LocalDate.of(2024, 4, 22));
  }

  /**
   * Tests the calculation of gain/loss when the date is not in the data,
   * expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void ifCalculateGainLossNotInData() {
    double result = apple.calculateGainLoss(
            LocalDate.of(2024, 4, 22),
            LocalDate.of(2024, 9, 22));
  }

  /**
   * Tests finding X-day crossovers in the past 30 days.
   */
  @Test
  public void findXDayCrossoverOfPast30Days() {
    ArrayList<LocalDate> result = apple.findXDayCrossover(
            LocalDate.of(2024, 4, 21),
            LocalDate.of(2024, 5, 22), 30);
    assertEquals(23, result.size());
  }

  /**
   * Tests finding X-day crossovers when the end date is greater
   * than the start date, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void ifFindXDayCrossoversEndDateGreaterThanStartDate() {
    ArrayList<LocalDate> result = apple.findXDayCrossover(
            LocalDate.of(2024, 5, 22),
            LocalDate.of(2024, 4, 22), 30);
  }

  /**
   * Tests finding X-day crossovers when the date is not in the data,
   * expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void ifFindXDayCrossoversDateIsNotInData() {
    ArrayList<LocalDate> result = apple.findXDayCrossover(
            LocalDate.of(2024, 4, 22),
            LocalDate.of(2024, 8, 22), 30);
  }

  /**
   * Tests finding X-day crossovers when the X-day period is too
   * far back, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void ifFindXDayCrossoversXDayIsTooFarBack() {
    ArrayList<LocalDate> result = apple.findXDayCrossover(
            LocalDate.of(2024, 4, 22),
            LocalDate.of(2024, 5, 22), 100);
  }

  /**
   * Tests adding the latest closing price.
   */
  @Test
  public void addLatestClosing() {
    apple.addClosing(177.34, LocalDate.of(2024, 5, 29));
    double result = apple.getClosingPrice(LocalDate.of(2024, 5, 29));
    assertEquals(177.34, result, 0.01);
  }

  /**
   * Tests the retrieval of the closing price for a specific date.
   */
  @Test
  public void getClosing() {
    double result = apple.getClosingPrice(LocalDate.of(2024, 3, 28));
    assertEquals(152.26, result, 0.01);
  }

  /**
   * Tests adding a closing price for a date that already exists,
   * expecting an IllegalArgumentException.
   */
  @Test
  public void testAddClosingWithExistingDate() {
    try {
      apple.addClosing(180.00, LocalDate.of(2024, 5, 22));
    } catch (IllegalArgumentException e) {
      assertEquals("only takes closing prices of the latest dates not in history",
              e.getMessage());
    }
  }

  /**
   * Tests adding a closing price for a date that is before the last recorded date,
   * expecting an IllegalArgumentException.
   */
  @Test
  public void testAddClosingWithDateBeforeLast() {
    try {
      apple.addClosing(185.00, LocalDate.of(2024, 4, 1));
    } catch (IllegalArgumentException e) {
      assertEquals("only takes closing prices of the latest dates not in history",
              e.getMessage());
    }
  }

  /**
   * Tests the retrieval of the closing price for a date not present in the data,
   * expecting an IllegalArgumentException.
   */
  @Test
  public void getClosingPriceNotPresentInData() {
    try {
      double result = apple.getClosingPrice(LocalDate.of(2024, 6, 1));
    } catch (IllegalArgumentException e) {
      assertEquals("we do not have data of the given date", e.getMessage());
    }
  }

  /**
   * Tests the retrieval of the ticker symbol for apple.
   */
  @Test
  public void testTicker() {
    assertEquals("AAPL", apple.getTicker());
  }

  /**
   * Tests the calculation of the 30-day moving average for an empty stock (sony),
   * expecting an IllegalArgumentException.
   */
  @Test
  public void testEmptyStockCalculateXDayMovingAverage() {
    try {
      double result = sony.calculateXDayMovingAverage(
              LocalDate.of(2024, 5, 22), 30);
    } catch (IllegalArgumentException e) {
      assertEquals("we do not have data of the given date", e.getMessage());
    }
  }

  /**
   * Tests the calculation of gain/loss for an empty stock (sony),
   * expecting an IllegalArgumentException.
   */
  @Test
  public void testEmptyStockCalculateGainLoss() {
    try {
      double result = sony.calculateGainLoss(
              LocalDate.of(2024, 4, 22),
              LocalDate.of(2024, 5, 22));
    } catch (IllegalArgumentException e) {
      assertEquals("we do not have data of the given dates", e.getMessage());
    }
  }

  /**
   * Tests finding X-day crossovers for an empty stock (sony),
   * expecting an IllegalArgumentException.
   */
  @Test
  public void testEmptyStockFindXDayCrossover() {
    try {
      ArrayList<LocalDate> result = sony.findXDayCrossover(
              LocalDate.of(2024, 4, 22),
              LocalDate.of(2024, 5, 22), 30);
    } catch (IllegalArgumentException e) {
      assertEquals("we do not have data of the given dates", e.getMessage());
    }
  }

  /**
   * Tests adding a closing price to an empty stock (sony) and retrieving it.
   */
  @Test
  public void testAddClosingToEmptyStock() {
    sony.addClosing(150.00, LocalDate.of(2024, 5, 30));
    double result = sony.getClosingPrice(LocalDate.of(2024, 5, 30));
    assertEquals(150.00, result, 0.01);
  }

  /**
   * Tests the calculation of the 20-day moving average for google.
   */
  @Test
  public void calculate30DayMovingAverageForgoogle() {
    double result = google.calculateXDayMovingAverage(
            LocalDate.of(2024, 5, 22), 20);
    assertEquals(2655.41, result, 0.01);
  }

  /**
   * Tests the calculation of the 30-day moving average
   * for google when data is not available, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void googleDoNotHaveDataOfMovingAverage() {
    double result = google.calculateXDayMovingAverage(
            LocalDate.of(2024, 7, 22), 30);
  }

  /**
   * Tests the calculation of the 100-day moving average for
   * google when data is not available, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void googleDoNotHaveDataOfTheLast60DayMovingAverage() {
    double result = google.calculateXDayMovingAverage(
            LocalDate.of(2024, 4, 22), 100);
  }

  /**
   * Tests the calculation of gain/loss for google over a specific period.
   */
  @Test
  public void googleCalculateGainLoss() {
    double result = google.calculateGainLoss(
            LocalDate.of(2024, 4, 30),
            LocalDate.of(2024, 5, 22));
    assertEquals(144.11, result, 0.01);
  }

  /**
   * Tests the calculation of gain/loss for google when the end
   * date is greater than the start date, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void googleIfCalculateGainLossEndDateGreaterThanStartDate() {
    double result = google.calculateGainLoss(
            LocalDate.of(2024, 5, 22),
            LocalDate.of(2024, 4, 30));
  }

  /**
   * Tests the calculation of gain/loss for google when the date is
   * not in the data, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void googleIfCalculateGainLossNotInData() {
    double result = google.calculateGainLoss(
            LocalDate.of(2024, 4, 30),
            LocalDate.of(2024, 9, 22));
  }

  /**
   * Tests finding X-day crossovers for google in the past 30 days.
   */
  @Test
  public void googleFindXDayCrossoverOfPast30Days() {
    ArrayList<LocalDate> result = google.findXDayCrossover(
            LocalDate.of(2024, 5, 12),
            LocalDate.of(2024, 5, 22), 10);
    assertEquals(8, result.size());
  }

  /**
   * Tests the retrieval of the closing price for amazon on a specific date.
   */
  @Test
  public void amazonClosingPrice() {
    double result = amazon.getClosingPrice(
            LocalDate.of(2024, 2, 12));
    assertEquals(172.34, result, 0.01);
  }

  /**
   * Test the calculation of gain or loss over a few days for amazon stock.
   */
  @Test
  public void amazonCalculateGainLossOverDays() {
    double result = amazon.calculateGainLoss(
            LocalDate.of(2024, 5, 1),
            LocalDate.of(2024, 5, 15));
    assertEquals(6.99, result, 0.01);
  }

  /**
   * Test the calculation of gain or loss over several months for amazon stock.
   */
  @Test
  public void amazonCalculateGainLossOverMonths() {
    double result = amazon.calculateGainLoss(
            LocalDate.of(2024, 2, 1),
            LocalDate.of(2024, 5, 1));
    assertEquals(19.72, result, 0.01);
  }

  /**
   * Test the calculation of gain or loss over a year for amazon stock.
   */
  @Test
  public void amazonCalculateGainLossOverYears() {
    double result = amazon.calculateGainLoss(
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2024, 1, 1));
    assertEquals(64.11, result, 0.01);
  }

  /**
   * Test the calculation of a 5-day moving average for amazon stock.
   */
  @Test
  public void amazonCalculateXDayMovingAverageFewDays() {
    double result = amazon.calculateXDayMovingAverage(
            LocalDate.of(2024, 5, 20), 5);
    assertEquals(184.464, result, 0.01);
  }

  /**
   * Test the calculation of a 90-day moving average for amazon stock.
   */
  @Test
  public void amazonCalculateXDayMovingAverageFewMonths() {
    double result = amazon.calculateXDayMovingAverage(
            LocalDate.of(2024, 5, 20), 90);
    assertEquals(179.84, result, 0.01);
  }

  /**
   * Test the calculation of a 730-day moving average for amazon stock.
   */
  @Test
  public void amazonCalculateXDayMovingAverageFewYears() {
    double result = amazon.calculateXDayMovingAverage(
            LocalDate.of(2024, 5, 20), 730);
    assertEquals(171.849, result, 0.01);
  }

  /**
   * Test to find dates where the closing price crosses above the
   * 5-day moving average within a 10-day period.
   */
  @Test
  public void amazonFindXDayCrossoverFewDays() {
    ArrayList<LocalDate> result = amazon.findXDayCrossover(
            LocalDate.of(2024, 5, 10),
            LocalDate.of(2024, 5, 20), 5);
    assertEquals(0, result.size());
  }

  /**
   * Test to find dates where the closing price crosses above
   * the 60-day moving average within a 60-day period.
   */
  @Test
  public void amazonFindXDayCrossoverFewMonths() {
    ArrayList<LocalDate> result = amazon.findXDayCrossover(
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 5, 1), 60);
    assertEquals(37, result.size());
  }

  /**
   * Test to find dates where the closing price crosses above
   * the 365-day moving average over two years.
   */
  @Test
  public void amazonFindXDayCrossoverFewYears() {
    ArrayList<LocalDate> result = amazon.findXDayCrossover(
            LocalDate.of(2022, 5, 1),
            LocalDate.of(2024, 5, 1), 365);
    assertEquals(229, result.size());
  }

  /**
   * Invalid TickerMakingStock.
   */
  @Test
  public void testInvalidTicker() {
    try {
      IStock smasung = new Stock("Invalid",new TreeMap<LocalDate,Double>());
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker Symbol",
              e.getMessage());
    }
  }

}





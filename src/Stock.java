import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.time.LocalDate;

/**
 * The Stock class represents a stock with a ticker symbol and historical price data.
 * It provides methods for calculating x-day moving averages, gain/loss over a period,
 * and finding x-day crossovers.
 */
public class Stock implements IStock, Comparable<Stock> {

  private final String ticker;
  private TreeMap<LocalDate, Double> stockHistory;

  /**
   * Constructs a Stock instance with the given ticker and historical data.
   *
   * @param ticker       the stock ticker symbol
   * @param stockHistory a TreeMap containing the historical prices of the stock,
   *                     with LocalDate as the key and Double as the value representing the price
   */
  public Stock(String ticker, TreeMap<LocalDate, Double> stockHistory) {
    AlphaVantageAPI api = new AlphaVantageAPI();
    if (api.tickerCSVToList().contains(ticker)) {
      this.ticker = ticker;
    } else {
      throw new IllegalArgumentException("Invalid Ticker Symbol");
    }

    this.stockHistory = new TreeMap<>(stockHistory);
  }

  /**
   * Constructs a Stock instance with the given ticker.
   *
   * @param ticker the stock ticker symbol
   */
  public Stock(String ticker) {
    this.ticker = ticker;
    this.stockHistory = new TreeMap<>();
  }


  /**
   * Calculates the x-day moving average of the stock for a given date.
   *
   * @param date the date for which to calculate the x-day moving average
   * @param xDay the number of days to use for the moving average
   * @return the x-day moving average value
   * @throws IllegalArgumentException if the date is not found in the stock history
   */
  @Override
  public double calculateXDayMovingAverage(LocalDate date, int xDay) {

    date = dateErrorHandler(date);

    if (!stockHistory.containsKey(date)) {
      throw new IllegalArgumentException("we do not have data of the given date");
    }

    LocalDate endDate = date.minusDays(xDay);
    endDate = dateErrorHandler(endDate);

    if (!stockHistory.containsKey(endDate)) {
      throw new IllegalArgumentException("we do not have data of the last " +
              Integer.toString(xDay) + " days");
    }

    NavigableMap<LocalDate, Double> subMap = stockHistory.subMap(endDate,
            true, date, true);

    double add = 0.00;
    for (Map.Entry<LocalDate, Double> entry : subMap.entrySet()) {
      add += entry.getValue();
    }
    return add / subMap.size();

  }

  /**
   * Calculates the gain or loss of the stock between two dates.
   *
   * @param startDate the start date of the period
   * @param endDate   the end date of the period
   * @return the gain or loss value
   * @throws IllegalArgumentException if the dates are not found in the stock history or if
   *                                  start date is after end date
   */
  @Override
  public double calculateGainLoss(LocalDate startDate, LocalDate endDate) {

    startDate = dateErrorHandler(startDate);
    endDate = dateErrorHandler(endDate);

    if (!(stockHistory.containsKey(startDate) && stockHistory.containsKey(endDate))) {
      throw new IllegalArgumentException("we do not have data of the given dates");
    }

    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date must be before end date.");
    }
    return stockHistory.get(endDate) - stockHistory.get(startDate);
  }

  /**
   * Finds the dates where the stock price crosses the x-day moving average between two dates.
   *
   * @param startDate the start date of the period
   * @param endDate   the end date of the period
   * @param xDay      the number of days for the x-day moving average
   * @return a list of dates where the stock price crosses the x-day moving average
   * @throws IllegalArgumentException if the dates are not found in the stock history or
   *                                  if start date is after end date
   */
  @Override
  public ArrayList<LocalDate> findXDayCrossover(LocalDate startDate, LocalDate endDate,
                                                int xDay) {

    startDate = dateErrorHandler(startDate);
    endDate = dateErrorHandler(endDate);

    if (!(stockHistory.containsKey(startDate) && stockHistory.containsKey(endDate))) {
      throw new IllegalArgumentException("we do not have data of the given dates");
    }

    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date must be before end date.");
    }


    NavigableMap<LocalDate, Double> subMap = stockHistory.subMap(startDate, true,
            endDate, true);

    ArrayList<LocalDate> result = new ArrayList<>();
    double x_dayMovingAverage = 0.00;
    for (Map.Entry<LocalDate, Double> entry : subMap.entrySet()) {
      x_dayMovingAverage = calculateXDayMovingAverage(entry.getKey(), xDay);
      if (entry.getValue() > x_dayMovingAverage) {
        result.add(entry.getKey());
      }
    }

    return result;
  }

  /**
   * Adds a closing price to the stock history for the given date.
   *
   * @param price   the closing price of the stock
   * @param endDate the date of the closing price
   * @throws IllegalArgumentException if the date is before the last date in history or
   *                                  already exists
   */
  @Override
  public void addClosing(double price, LocalDate endDate) {
    if (stockHistory.isEmpty()) {
      stockHistory.put(endDate, price);
    } else {
      if (endDate.isBefore(stockHistory.lastEntry().getKey()) ||
              stockHistory.containsKey(endDate)) {
        throw new IllegalArgumentException("only takes closing prices of the latest " +
                "dates not in history");
      }
      stockHistory.put(endDate, price);
    }
  }

  /**
   * Retrieves the closing price of the stock for the given date.
   *
   * @param endDate the date for which to retrieve the closing price
   * @return the closing price for the given date
   * @throws IllegalArgumentException if the date is not found in the stock history
   */
  @Override
  public double getClosingPrice(LocalDate endDate) {
    endDate = dateErrorHandler(endDate);

    if (!(stockHistory.containsKey(endDate))) {
      throw new IllegalArgumentException("we do not have data of the given date");
    }

    return stockHistory.getOrDefault(endDate, 0.0);
  }

  /**
   * Adjusts the given date to the nearest available date in the stock history if the exact
   * date is not available.
   *
   * @param givenDate the date to adjust
   * @return the nearest available date in the stock history
   */
  private LocalDate dateErrorHandler(LocalDate givenDate) {

    if (stockHistory.containsKey(givenDate)) {
      return givenDate;
    } else if (stockHistory.containsKey(givenDate.plusDays(1))) {
      return givenDate.plusDays(1);
    } else if (stockHistory.containsKey(givenDate.minusDays(1))) {
      return givenDate.minusDays(1);
    } else if (stockHistory.containsKey(givenDate.plusDays(2))) {
      return givenDate.plusDays(2);
    } else if (stockHistory.containsKey(givenDate.minusDays(2))) {
      return givenDate.minusDays(2);
    }
    return givenDate;
  }


  /**
   * Returns the ticker symbol of the stock.
   *
   * @return the stock ticker symbol
   */
  @Override
  public String getTicker() {
    return ticker;
  }

  /**
   * Compares this stock to another stock based on their ticker symbols.
   *
   * @param other the stock to be compared
   * @return a negative integer, zero, or a positive integer as this stock's ticker
   *                      is less than, equal to, or greater than the specified stock's ticker
   */
  @Override
  public int compareTo(Stock other) {
    return this.ticker.compareTo(other.ticker);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Stock stock = (Stock) obj;
    return ticker.equals(stock.ticker);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return ticker.hashCode();
  }


}

import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Interface representing a portfolio of stocks.
 */
public interface IPortfolio {
  /**
   * Adds a specified quantity of a stock to the portfolio.
   *
   * @param stock    the stock to be added
   * @param quantity the quantity of the stock to be added
   * @param currentDate
   */
  void addStock(IStock stock, int quantity, LocalDate currentDate);

  /**
   * Removes a specified quantity of a stock from the portfolio.
   *
   * @param stock    the stock to be removed
   * @param quantity the quantity of the stock to be removed
   * @param currentDate
   */
  void removeStock(IStock stock, int quantity, LocalDate currentDate);

  /**
   * Calculates the total value of the portfolio on the a certain date.
   *
   * @param currentDate the certain date
   * @return the total value of the portfolio
   */
  Double calculatePortfolioValue(LocalDate currentDate);

  /**
   * Returns the portfolio's name.
   *
   * @return the name of the portfolio
   */
  String getName();

  /**
   * Returns the portfolio's stocks.
   *
   * @return the HashMap of stocks in the portfolio
   */
  HashMap<IStock, Integer> getStocks();

  /**
   * Returns the portfolio's stocks.
   *
   * @return the HashMap of stocks in the portfolio
   */
  TreeMap<IStock, LocalDate> getDatesAdded();
}

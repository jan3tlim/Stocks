import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Interface representing a portfolio of stocks.
 */
public interface IPortfolio {
  /**
   * Adds a specified quantity of a stock to the portfolio.
   *
   * @param stock       the stock to be added
   * @param quantity    the quantity of the stock to be added
   * @param currentDate the current date
   */
  void addStock(IStock stock, double quantity, LocalDate currentDate);

  /**
   * Removes a specified quantity of a stock from the portfolio.
   *
   * @param stock       the stock to be removed
   * @param quantity    the quantity of the stock to be removed
   * @param currentDate the current date
   */
  void removeStock(IStock stock, double quantity, LocalDate currentDate);

  /**
   * Calculates the total value of the portfolio on a certain date.
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
  HashMap<IStock, Double> getStocks(LocalDate l);


  /**
   * Returns the portfolio's owner.
   *
   * @return the Client that owns the portfolio
   */
  Client getOwner();

  Double calculateStockValue(IStock s, LocalDate currentDate);
  String printStocks(LocalDate l);

  String distributionOfValueOnDate(LocalDate date);
  void rebalance(Scanner in, LocalDate date,IView v);
  String performanceOverTime(LocalDate start, LocalDate end);

  void savePortfolio(String filename);
  void loadPortfolio(File flile);

}
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Interface representing model.
 */
public interface IModel {

  /**
   * make a stock with its information.
   * @param stockHistory the history of the stocks closing prices
   * @param ticker the ticker to identify the stock
   * @return The IStock
   */
  public IStock makeStock(TreeMap<LocalDate, Double> stockHistory, String ticker);


  /**
   * make a default client.
   * @return the client
   */
  public IClient makeClient();




}

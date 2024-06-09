import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * The Portfolio class represents a collection of stocks owned by a client.
 * Each portfolio has a name, an owner, and a collection of stocks with their quantities.
 */
public class Portfolio implements IPortfolio {

  private HashMap<IStock, Integer> stocks;
  private Client owner;
  private String name;

  private TreeMap<IStock, LocalDate> datesAdded;

  /**
   * Constructs a new Portfolio with the specified name and owner.
   *
   * @param portfolioName the name of the portfolio
   * @param owner         the client who owns this portfolio
   */
  public Portfolio(String portfolioName, Client owner) {
    this.stocks = new HashMap<IStock, Integer>();
    this.owner = owner;
    this.name = portfolioName;
    this.datesAdded = new TreeMap<IStock, LocalDate>();
  }

  /**
   * Adds the specified quantity of a stock to the portfolio.
   * If the stock is already in the portfolio, its quantity is increased by the specified amount.
   * Otherwise, the stock is added to the portfolio with the specified quantity.
   *
   * @param stock    the stock to add
   * @param quantity the quantity of the stock to add
   */
  @Override
  public void addStock(IStock stock, int quantity, LocalDate currentDate) {
    if (quantity < 0) {
      throw new IllegalArgumentException("You cannot add zero or negative stocks.");
    }
    if (this.stocks.containsKey(stock)) {
      int currentQuantity = this.stocks.get(stock);
      this.stocks.put(stock, currentQuantity + quantity);
      this.datesAdded.put(stock, currentDate);
    } else {
      this.stocks.put(stock, quantity);
      this.datesAdded.put(stock, currentDate);
    }
  }

  /**
   * Removes the specified quantity of a stock from the portfolio.
   * If the quantity to remove is greater than or equal to the current quantity,
   * the stock is removed from the portfolio.
   * Otherwise, the stock's quantity is decreased by the specified amount.
   *
   * @param stock    the stock to remove
   * @param quantity the quantity of the stock to remove
   */
  @Override
  public void removeStock(IStock stock, int quantity, LocalDate currentDate) {
    if (quantity < 0) {
      throw new IllegalArgumentException("You cannot remove zero or negative stocks.");
    }
    if (this.stocks.containsKey(stock)) {
      int currentQuantity = this.stocks.get(stock);
      if (currentQuantity <= quantity) {
        this.stocks.remove(stock);
        this.datesAdded.remove(stock, currentDate);
      } else {
        this.stocks.put(stock, currentQuantity - quantity);
        this.datesAdded.put(stock, currentDate);
      }
    }
  }

  /**
   * Calculates the total value of the portfolio on the specified date.
   *
   * @param currentDate the date on which to calculate the portfolio value
   * @return the total value of the portfolio on the specified date
   */
  @Override
  public Double calculatePortfolioValue(LocalDate currentDate) {
    double totalValue = 0.0;

    for (Map.Entry<IStock, Integer> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      int quantity = entry.getValue();
      Double price = stock.getClosingPrice(currentDate);
      totalValue += price * quantity;
    }

    return totalValue;
  }

  /**
   * Returns the name of the portfolio.
   *
   * @return the name of the portfolio
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Returns the collection of stocks in the portfolio with their quantities.
   *
   * @return a HashMap containing the stocks and their quantities
   */
  @Override
  public HashMap<IStock, Integer> getStocks() {
    return stocks;
  }

  /**
   * Returns the dates when the stocks were added to the portfolio.
   *
   * @return a TreeMap containing the stocks and their dates of addition
   */
  @Override
  public TreeMap<IStock, LocalDate> getDatesAdded() {
    return datesAdded;
  }

  /**
   * Compares this portfolio to the specified object. The result is true if and only if
   * the argument is not null and is a Portfolio object that has the same name and owner
   * as this portfolio.
   *
   * @param o the object to compare this portfolio against
   * @return true if the given object represents a Portfolio equivalent to this portfolio,
   *                    false otherwise
   */
  @Override
  public boolean equals(Object o) {
    Portfolio other = (Portfolio) o;
    return Objects.equals(name, other.name) &&
            Objects.equals(owner, other.owner);
  }

  /**
   * Returns a hash code for this portfolio.
   *
   * @return a hash code value for this portfolio
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, owner);
  }

  /**
   * Returns a string representation of this portfolio.
   *
   * @return a string representation of this portfolio
   */
  @Override
  public String toString() {
    String s = "";
    for (Map.Entry<IStock, Integer> e : stocks.entrySet()) {
      s += e.getKey().getTicker() + ", " + e.getValue() + "; ";
    }
    return "'" + name + "'" +
            "\n{" + s +
            '}';
  }

}

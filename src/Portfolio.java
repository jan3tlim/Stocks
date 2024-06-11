import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Portfolio class represents a collection of stocks owned by a client.
 * Each portfolio has a name, an owner, and a collection of stocks with their quantities.
 */
public class Portfolio implements IPortfolio {

  private HashMap<IStock, Integer> stocks;
  private Client owner;
  private String name;

  private TreeMap<LocalDate, Portfolio> versions;
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
    this.versions = new TreeMap<LocalDate, Portfolio>();
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
    this.versions.put(currentDate, this.clone());
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
    // Create a snapshot of the current portfolio state
    this.versions.put(currentDate, this.clone());


  }

  /**
   * Calculates the total value of the portfolio on the specified date.
   *
   * @param currentDate the date on which to calculate the portfolio value
   * @return the total value of the portfolio on the specified date
   */
  @Override
  public Double calculatePortfolioValue(LocalDate currentDate) {
    IPortfolio version = getVersionForDate(currentDate);
    double totalValue = 0.0;
    if (version == null) {
      return totalValue;
    }

    for (Map.Entry<IStock, Integer> entry : version.getStocks().entrySet()) {
      IStock stock = entry.getKey();
      int quantity = entry.getValue();
      Double price = stock.getClosingPrice(currentDate);
      totalValue += price * quantity;
    }

    return totalValue;
  }

  /**
   * Returns a clone of the current version of IPortfolio
   * @return the cloned Portfolio
   */
  @Override
  public Portfolio clone() {
    Portfolio cloned = new Portfolio(this.name, this.owner);
    cloned.stocks = new HashMap<>(this.stocks); // Create a new copy
    return cloned;
  }

  /**
   * Returns the version of the Portfolio on the given date
   *
   * @param date the date
   * @return the version of the Portfolio on the given date
   */
  @Override
  public Portfolio getVersionForDate(LocalDate date) {
    Map.Entry<LocalDate, Portfolio> entry = versions.floorEntry(date);
    if (entry != null) {
      return entry.getValue();
    }
    return null;
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
   * Returns the version history of the portfolio
   *
   * @return a TreeMap containing the versions of the Portfolio and their respective date
   */
  @Override
  public TreeMap<LocalDate, Portfolio> getVersions() {
    return versions;
  }

  /**
   * Returns the portfolio's owner.
   *
   * @return the Client that owns the portfolio
   */
  @Override
  public Client getOwner(){
    return owner;
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


  @Override
  public void savePortfolio(String filename) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      writer.write("{\n");
      writer.write(String.format("\"name\": \"%s\",\n",this.name));
      writer.write("\"stocks\": [\n");


      int i = 0;
      for (Map.Entry<IStock, Integer> entry : stocks.entrySet()) {
        writer.write(String.format("  {\"ticker\": \"%s\", \"quantity\": %d, \"dateAdded\": \"%s\"}",
                entry.getKey().getTicker(), entry.getValue(),datesAdded.get(entry.getKey()).format(formatter)));

        i = i + 1;
        if (i < stocks.size() - 1) {
          writer.write(",\n");
        }
      }
      writer.write("\n]\n");
      writer.write("}\n");
    }catch (IOException e){
      e.fillInStackTrace();
    }

  }

  @Override
  public void loadPortfolio(String filename) {
    Portfolio portfolio = null;
    String name = null;

    Pattern namePattern = Pattern.compile("\"name\":\\s*\"([^\"]+)\"");
    Pattern stockPattern = Pattern.compile("\\{\"ticker\":\\s*\"([^\"]+)\",\\s*\"quantity\":\\s*(\\d+),\\s*\"dateAdded\":\\s*\"(\\d{4}-\\d{2}-\\d{2})\"\\}");

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      StringBuilder jsonBuilder = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        jsonBuilder.append(line.trim());
      }
      String json = jsonBuilder.toString();

      Matcher nameMatcher = namePattern.matcher(json);
      if (nameMatcher.find()) {
        this.name = nameMatcher.group(1);

      }

      Matcher stockMatcher = stockPattern.matcher(json);
      AlphaVantageAPI api = new AlphaVantageAPI();
      while (stockMatcher.find()) {
        IStock stock = api.makeStock(stockMatcher.group(1));
        int quantity = Integer.parseInt(stockMatcher.group(2));
        LocalDate dateAdded = LocalDate.parse(stockMatcher.group(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.stocks.put(stock,quantity);
        this.datesAdded.put(stock,dateAdded);
      }
    } catch (IOException e){
      e.fillInStackTrace();
    }

  }


}

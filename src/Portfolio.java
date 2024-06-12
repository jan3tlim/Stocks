import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

//import static jdk.internal.org.jline.utils.Colors.s;

/**
 * The Portfolio class represents a collection of stocks owned by a client.
 * Each portfolio has a name, an owner, and a collection of stocks with their quantities.
 */
public class Portfolio implements IPortfolio {

  //  private HashMap<IStock, Integer> stocks;
  private Client owner;
  private String name;

  //  private TreeMap<LocalDate, Portfolio> versions;
  private HashMap<IStock, TreeMap<LocalDate, Integer>> stocks;

  /**
   * Constructs a new Portfolio with the specified name and owner.
   *
   * @param portfolioName the name of the portfolio
   * @param owner         the client who owns this portfolio
   */
  public Portfolio(String portfolioName, Client owner) {
    this.owner = owner;
    this.name = portfolioName;
    this.stocks = new HashMap<IStock, TreeMap<LocalDate, Integer>>();
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
      if (this.stocks.get(stock).containsKey(currentDate)) {
        int currentQuantity = this.stocks.get(stock).get(currentDate);
        this.stocks.get(stock).put(currentDate, quantity + currentQuantity);
      }
      this.stocks.get(stock).put(currentDate, quantity);
    } else {
      TreeMap<LocalDate, Integer> newTree = new TreeMap<>();
      newTree.put(currentDate, quantity);
      this.stocks.put(stock, newTree);
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
    if (!this.stocks.containsKey(stock)) {
      throw new IllegalArgumentException("Stock not found in the portfolio.");
    }
    int totalQuantity = 0;
    for (Map.Entry<LocalDate, Integer> entry : this.stocks.get(stock).entrySet()) {
      int i = entry.getValue();
      totalQuantity += i;
    }
    if (totalQuantity == 0) {
      throw new IllegalArgumentException("You have zero " + stock.getTicker() + " stocks.");
    }
    if (totalQuantity - quantity < 0) {
      throw new IllegalArgumentException("Removal quantity cannot exceed existing quantity. You " +
              "currently have " + totalQuantity + " " + stock.getTicker() + " stocks.");
    }
    if (this.stocks.get(stock).containsKey(currentDate)) {
      int currentQuantity = this.stocks.get(stock).get(currentDate);
      this.stocks.get(stock).put(currentDate, currentQuantity - quantity);
    } else {
      this.stocks.get(stock).put(currentDate, -quantity);
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
    for (Map.Entry<IStock, TreeMap<LocalDate, Integer>> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      Double price = calculateStockValue(stock, currentDate);
      totalValue += price;
    }
    return totalValue;
  }

  /**
   * Calculates the total value of the stock multiplied by the quantity
   * on the specified date.
   *
   * @param s the stock
   * @param currentDate the date on which to calculate the stock value
   * @return the total value of the stock on the specified date
   */
  @Override
  public Double calculateStockValue(IStock s, LocalDate currentDate) {
    double totalValue = 0.0;
    Double price = s.getClosingPrice(currentDate);
    for (Map.Entry<LocalDate, Integer> entry : stocks.get(s).headMap(currentDate,
            true).entrySet()) {
      int quantity = entry.getValue();
      totalValue += quantity;
    }
    totalValue *= price;
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
  public HashMap<IStock, Integer> getStocks(LocalDate l) {
    HashMap<IStock, Integer> stockList = new HashMap<>();
    for (Map.Entry<IStock, TreeMap<LocalDate, Integer>> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      int stockNum = 0;
      if (!stocks.get(stock).headMap(l, true).isEmpty()){
        for (Map.Entry<LocalDate, Integer> entry1 : stocks.get(stock).headMap(l,
                true).entrySet()) {
          int i = entry1.getValue();
          stockNum += i;
        }
      }
      if (stockNum > 0) {
        stockList.put(stock, stockNum);
      }
    }
    return stockList;
  }

  public String distributionOfValueOnDate(LocalDate date){
    double totalPortfolioValue = calculatePortfolioValue(date);
    if (totalPortfolioValue == 0) {
      return "The portfolio has no value on " + date;
    }

    StringBuilder distribution = new StringBuilder();
    distribution.append("Distribution of value on ").append(date).append(":\n");

    for (Map.Entry<IStock, TreeMap<LocalDate, Integer>> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      double stockValue = calculateStockValue(stock, date);
      if (stockValue > 0){
        distribution.append(stock.getTicker()).append(": $").append(stockValue).append("\n");
      }
    }

    distribution.append("Total Portfolio Value: $").append(totalPortfolioValue);
    return distribution.toString();
  }

  @Override
  public String performanceOverTime(LocalDate start, LocalDate end){
    if (!end.isAfter(start)){
      throw new IllegalArgumentException("Your end date must come after your start date.");
    }
    StringBuilder chart = new StringBuilder();
    chart.append("Performance of portfolio '").append(this.getName()).append("' from ")
            .append(start).append(" to ").append(end).append("\n");

    List<LocalDate> dates = new ArrayList<>();
    LocalDate current = start;
    while (!current.isAfter(end)) {
      dates.add(current);
      current = current.plusMonths(1);  // Change to plusDays(1) or plusYears(1) as needed
    }

    Map<LocalDate, Double> values = new HashMap<>();
    double maxValue = Double.MIN_VALUE;
    for (LocalDate date : dates) {
      double value = calculatePortfolioValue(date);
      values.put(date, value);
      if (value > maxValue) {
        maxValue = value;
      }
    }

    int maxAsterisks = 50;
    double scale = maxValue / maxAsterisks;
    if (scale == 0) {
      scale = 1;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
    for (LocalDate date : dates) {
      double value = values.get(date);
      int numAsterisks = (int) (value / scale);
      chart.append(date.format(formatter)).append(": ").append("*".repeat(numAsterisks)).append("\n");
    }

    chart.append("Scale: * = ").append(scale).append(" units\n");
    return chart.toString();
  }

// what if user tries to add or remove stock to a date that is not today
  /**
   * Returns the portfolio's owner.
   *
   * @return the Client that owns the portfolio
   */
  @Override
  public Client getOwner() {
    return owner;
  }

  /**
   * Compares this portfolio to the specified object. The result is true if and only if
   * the argument is not null and is a Portfolio object that has the same name and owner
   * as this portfolio.
   *
   * @param o the object to compare this portfolio against
   * @return true if the given object represents a Portfolio equivalent to this portfolio,
   * false otherwise
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
  public String printStocks(LocalDate l) {
    String s = "";
    for (Map.Entry<IStock, Integer> e : getStocks(l).entrySet()) {
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
      writer.write("name:" + this.name + "\n");
      writer.write("stocks:\n");

      for (Map.Entry<IStock, TreeMap<LocalDate, Integer>> entry : stocks.entrySet()) {
        IStock s = entry.getKey();
        for(Map.Entry<LocalDate, Integer> entry2 : stocks.get(s).entrySet()) {
          writer.write("ticker:" + s.getTicker() +
                  ":quantity:" + entry2.getValue() +
                  ":dateAdded:" + entry2.getKey().format(formatter) + "\n");
        }
      }
    } catch (IOException e) {
      e.fillInStackTrace();
    }
  }

  @Override
  public void loadPortfolio(File filename) {
    AlphaVantageAPI api = new AlphaVantageAPI();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println("Line: " + line);
        if (line.startsWith("name:")) {
          this.name = line.split(":")[1];
        } else if (line.startsWith("ticker:")) {
          String[] parts = line.split(":");
          System.out.println("Parts: " + Arrays.toString(parts));
          String ticker = parts[1];
          int quantity = Integer.parseInt(parts[3]);
          LocalDate dateAdded = LocalDate.parse(parts[5], formatter);
          if(quantity > 0){
          this.addStock(api.makeStock(ticker), quantity, dateAdded);
          }

          if(quantity < 0){
            this.removeStock(api.makeStock(ticker), -quantity, dateAdded);
          }
        }

      }
    } catch (IOException e) {
      e.fillInStackTrace();
    }
  }


}

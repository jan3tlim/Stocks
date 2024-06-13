import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

//import static jdk.internal.org.jline.utils.Colors.s;

/**
 * The Portfolio class represents a collection of stocks owned by a client.
 * Each portfolio has a name, an owner, and a collection of stocks with their quantities.
 */
public class Portfolio implements IPortfolio {

  private Client owner;
  private String name;
  private HashMap<IStock, TreeMap<LocalDate, Double>> stocks;

  /**
   * Constructs a new Portfolio with the specified name and owner.
   *
   * @param portfolioName the name of the portfolio
   * @param owner         the client who owns this portfolio
   */
  public Portfolio(String portfolioName, Client owner) {
    this.owner = owner;
    this.name = portfolioName;
    this.stocks = new HashMap<IStock, TreeMap<LocalDate, Double>>();
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
  public void addStock(IStock stock, double quantity, LocalDate currentDate) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("You cannot add zero or negative stocks.");
    }

    if (this.stocks.containsKey(stock)) {
      if (this.stocks.get(stock).containsKey(currentDate)) {
        double currentQuantity = this.stocks.get(stock).get(currentDate);
        this.stocks.get(stock).put(currentDate, quantity + currentQuantity);
      } else {
        this.stocks.get(stock).put(currentDate, quantity);
      }
    } else {
      TreeMap<LocalDate, Double> newTree = new TreeMap<>();
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
  public void removeStock(IStock stock, double quantity, LocalDate currentDate) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("You cannot remove zero or negative stocks.");
    }
    if (!this.stocks.containsKey(stock)) {
      throw new IllegalArgumentException("Stock not found in the portfolio.");
    }
    double totalQuantity = 0;
    for (Map.Entry<LocalDate, Double> entry : this.stocks.get(stock).entrySet()) {
      double i = entry.getValue();
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
      double currentQuantity = this.stocks.get(stock).get(currentDate);
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
    for (Map.Entry<IStock, TreeMap<LocalDate, Double>> entry : stocks.entrySet()) {
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
   * @param s           the stock
   * @param currentDate the date on which to calculate the stock value
   * @return the total value of the stock on the specified date
   */
  @Override
  public Double calculateStockValue(IStock s, LocalDate currentDate) {
    double totalValue = 0.0;
    Double price = s.getClosingPrice(currentDate);
    for (Map.Entry<LocalDate, Double> entry : stocks.get(s).headMap(currentDate,
            true).entrySet()) {
      double quantity = entry.getValue();
      totalValue += quantity;
    }
    totalValue *= price;
    return Math.round(totalValue * 100.0) / 100.0;
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
  public HashMap<IStock, Double> getStocks(LocalDate l) {
    HashMap<IStock, Double> stockList = new HashMap<>();
    for (Map.Entry<IStock, TreeMap<LocalDate, Double>> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      double stockNum = 0;
      if (!stocks.get(stock).headMap(l, true).isEmpty()) {
        for (Map.Entry<LocalDate, Double> entry1 : stocks.get(stock).headMap(l,
                true).entrySet()) {
          double i = entry1.getValue();
          stockNum += i;
        }
      }
      if (stockNum > 0) {
        stockList.put(stock, Math.round(stockNum * 100.0) / 100.0);
      }
    }
    return stockList;
  }

  @Override
  public String distributionOfValueOnDate(LocalDate date) {
    double totalPortfolioValue = calculatePortfolioValue(date);
    if (totalPortfolioValue == 0) {
      return "The portfolio has no value on " + date;
    }

    StringBuilder distribution = new StringBuilder();
    distribution.append("Distribution of value on ").append(date).append(":\n");

    for (Map.Entry<IStock, TreeMap<LocalDate, Double>> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      double stockValue = calculateStockValue(stock, date);
      if (stockValue > 0) {
        distribution.append(stock.getTicker()).append(": $").append(stockValue).append("\n");
      }
    }

    distribution.append("Total Portfolio Value: $").append(totalPortfolioValue);
    return distribution.toString();
  }

  @Override
  public String performanceOverTime(LocalDate start, LocalDate end) {
    if (!end.isAfter(start)) {
      throw new IllegalArgumentException("Your end date must come after your start date.");
    }
    if (!dateContainsPrice(start)) {
      throw new IllegalArgumentException("No data available for the start date: " + start);
    }

    if (!dateContainsPrice(end)) {
      throw new IllegalArgumentException("No data available for the end date: " + end);
    }

    StringBuilder chart = new StringBuilder();
    chart.append("Performance of portfolio '").append(this.getName()).append("' from ")
            .append(start).append(" to ").append(end).append("\n");

    long totalDays = ChronoUnit.DAYS.between(start, end);
    List<LocalDate> dates = new ArrayList<>();

    // Determine the interval for the dates
    TemporalUnit interval;
    DateTimeFormatter formatter;
    if (totalDays <= 5) {
      throw new IllegalArgumentException("Time span too short");
    } else if (totalDays <= 30) {
      interval = ChronoUnit.DAYS;
      formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    } else if (totalDays <= 730) {
      interval = ChronoUnit.MONTHS;
      formatter = DateTimeFormatter.ofPattern("MMM yyyy");
    } else {
      interval = ChronoUnit.YEARS;
      formatter = DateTimeFormatter.ofPattern("yyyy");
    }

    // Generate the dates based on the interval
    LocalDate current = start;
    while (!current.isAfter(end)) {
      if (dateContainsPrice(current)) {
        dates.add(current);
      }
      if (interval == ChronoUnit.DAYS) {
        current = current.plusDays(1);
      } else if (interval == ChronoUnit.MONTHS) {
        current = current.plusMonths(1);
      } else if (interval == ChronoUnit.YEARS) {
        current = current.plusYears(1);
      }
    }

    if (dates.isEmpty()) {
      return "No data available for the given date range.";
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

    for (LocalDate date : dates) {
      double value = values.get(date);
      int numAsterisks = (int) (value / scale);
      chart.append(date.format(formatter)).append(": ").append("*".repeat(numAsterisks)).append("\n");
    }

    chart.append("Scale: * = ").append(String.format("$%.2f", scale)).append(" units\n");
    return chart.toString();
  }

  private boolean dateContainsPrice(LocalDate date) {
    for (Map.Entry<IStock, TreeMap<LocalDate, Double>> entry : stocks.entrySet()) {
      IStock stock = entry.getKey();
      try {
        Double price = stock.getClosingPrice(date);
        if (price != null) {
          return true;
        }
      } catch (Exception e) {
        // If getClosingPrice throws an exception, we consider it as no information available.
        return false;
      }
    }
    return false;
  }

  public void rebalance(Scanner in, LocalDate date,IView v) {

    if (stocks.isEmpty()){
      throw new IllegalArgumentException("No stock available.");
    }
    for (Map.Entry<IStock, TreeMap<LocalDate, Double>> entry : stocks.entrySet()) {
      if (!entry.getValue().containsKey(date)){
        throw new IllegalArgumentException("No stock available for the given date range.");
      }
    }
    Map<IStock, Double> targetDistribution = new HashMap<>();

    double totalPercentage = 0.0;
    for (IStock stock : stocks.keySet()) {
      v.writeMessage("Enter the target percentage for %s: " + stock.getTicker());
      double percentage = in.nextDouble() / 100.0;
      targetDistribution.put(stock, percentage);
      totalPercentage += percentage;
    }

    in.nextLine();

    if (Math.abs(totalPercentage - 1.0) > 0.001) {
      v.writeMessage("The target percentages do not add up to 100%. Please try again.");
      return;
    }

    v.writeMessage("The new distribution will be:");
    for (Map.Entry<IStock, Double> entry : targetDistribution.entrySet()) {
      IStock stock = entry.getKey();
      double targetValue = calculatePortfolioValue(date) * entry.getValue();
      double currentValue = calculateStockValue(stock, date);
      v.writeMessage(stock.getTicker() + " : Current Value = " + String.format("$%.2f", currentValue) + ", Target Value =" + String.format("$%.2f", targetValue));
    }

    v.writeMessage("Do you want to proceed with rebalancing? (enter yes to proceed, "
            + "anything else to cancel)");
    String response = in.nextLine().trim().toLowerCase();

    if (!response.equals("yes")) {
      v.writeMessage("Rebalancing cancelled.");
      return;
    }


    double totalPortfolioValue = calculatePortfolioValue(date);
    for (Map.Entry<IStock, Double> entry : targetDistribution.entrySet()) {
      IStock stock = entry.getKey();
      double currentStockValue = calculateStockValue(stock, date);
      double targetStockValue = totalPortfolioValue * entry.getValue();
      double price = stock.getClosingPrice(date);

      if (currentStockValue < targetStockValue) {
        double sharesToBuy = ((targetStockValue - currentStockValue) / price);
        addStock(stock, sharesToBuy, date);
      } else if (currentStockValue > targetStockValue) {
        double sharesToSell = ((currentStockValue - targetStockValue) / price);
        removeStock(stock, sharesToSell, date);
      }
    }

    v.writeMessage("Portfolio rebalanced successfully.");
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
    for (Map.Entry<IStock, Double> e : getStocks(l).entrySet()) {
      s += e.getKey().getTicker() + ", " + Math.round(e.getValue() * 100.0) / 100.0 + "; ";
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

      for (Map.Entry<IStock, TreeMap<LocalDate, Double>> entry : stocks.entrySet()) {
        IStock s = entry.getKey();
        for (Map.Entry<LocalDate, Double> entry2 : stocks.get(s).entrySet()) {
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
        if (line.startsWith("name:")) {
          this.name = line.split(":")[1];
        } else if (line.startsWith("ticker:")) {
          String[] parts = line.split(":");
          String ticker = parts[1];
          double quantity = Double.parseDouble(parts[3]);
          LocalDate dateAdded = LocalDate.parse(parts[5], formatter);
          if (quantity > 0) {
            this.addStock(api.makeStock(ticker), quantity, dateAdded);
          }

          if (quantity < 0) {
            this.removeStock(api.makeStock(ticker), -quantity, dateAdded);
          }
        }

      }
    } catch (IOException e) {
      e.fillInStackTrace();
    }
  }

  public static void main(String[] args) {
    Client client = new Client("Test Client");
    Portfolio portfolio = new Portfolio("Tech Portfolio", client);

    TreeMap<LocalDate, Double> applePrices = new TreeMap<>();
    applePrices.put(LocalDate.of(2024, 1, 1), 150.0);
    applePrices.put(LocalDate.of(2024, 2, 1), 160.0);
    applePrices.put(LocalDate.of(2024, 3, 1), 170.0);
    applePrices.put(LocalDate.of(2024, 4, 30), 170.0);

    TreeMap<LocalDate, Double> googlePrices = new TreeMap<>();
    googlePrices.put(LocalDate.of(2024, 1, 1), 2500.0);
    googlePrices.put(LocalDate.of(2024, 2, 1), 2550.0);
    googlePrices.put(LocalDate.of(2024, 3, 1), 2600.0);
    googlePrices.put(LocalDate.of(2024, 4, 30), 2600.0);

    Stock apple = new Stock("AAPL", applePrices);
    Stock google = new Stock("GOOGL", googlePrices);

    portfolio.addStock(apple, 10, LocalDate.of(2024, 2, 1));
    portfolio.addStock(google, 2, LocalDate.of(2024, 2, 1));
    LocalDate date = LocalDate.of(2024, 2, 1);
    System.out.println(portfolio.printStocks(date));
    Scanner s = new Scanner(System.in);
    IView v = new TextView(System.out);
    portfolio.rebalance(s, date,v);
    System.out.println(portfolio.printStocks(date));
    portfolio.addStock(apple, 10, LocalDate.of(2024, 4, 30));
    portfolio.addStock(google, 2, LocalDate.of(2024, 4, 30));
  }




}
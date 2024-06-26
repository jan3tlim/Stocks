import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The Client class represents a client who can manage multiple portfolios.
 * Each client has a name and a collection of portfolios.
 */
public class Client implements IClient {
  private String name;
  private HashMap<String, Portfolio> portfolios;

  /**
   * Constructs a new Client with the specified name.
   *
   * @param name the name of the client
   */
  public Client(String name) {
    this.name = name;
    this.portfolios = new HashMap<>();
  }

  /**
   * Returns the collection of portfolios managed by this client.
   *
   * @return a HashMap containing the client's portfolios, where the keys are portfolio names
   * and the values are Portfolio objects
   */
  @Override
  public HashMap<String, Portfolio> getPortfolios() {
    return portfolios;
  }

  /**
   * Creates a new portfolio with the specified name and adds it to the client's collection
   * of portfolios.
   *
   * @param portfolioName the name of the new portfolio
   * @return the newly created Portfolio object
   */
  @Override
  public Portfolio createPortfolio(String portfolioName) {
    Portfolio newPortfolio = new Portfolio(portfolioName, this);
    portfolios.put(portfolioName, newPortfolio);
    return newPortfolio;
  }

  /**
   * Getter method for name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  @Override
  public void savePortfolios(String directory) {
    for (Map.Entry<String, Portfolio> entry : portfolios.entrySet()) {
      String filename = directory + "/" + entry.getKey().replace(" ", "_") + ".json";
      entry.getValue().savePortfolio(filename);
    }

  }

  @Override
  public void loadPortfolios(String directory) {
    File dir = new File(directory);
    File[] files = dir.listFiles((dir1, name) -> name.endsWith(".json"));
    if (files != null) {
      for (File file : files) {
        String filename = file.getName();
        String portfolioName = filename.substring(0, filename.length() - 5).replace("_", " ");
        Portfolio portfolio = new Portfolio(portfolioName, this);
        portfolio.loadPortfolio(filename);
        portfolios.put(portfolioName, portfolio);
      }
    }


  }
}

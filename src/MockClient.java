import java.util.HashMap;

/**
 * Mock for client.
 */
public class MockClient implements IClient {
  private String name;
  private HashMap<String, Portfolio> portfolios;

  public MockClient() {
    this.name = "user1";
    this.portfolios = new HashMap<>();
  }

  @Override
  public HashMap<String, Portfolio> getPortfolios() {
    return portfolios;
  }

  @Override
  public Portfolio createPortfolio(String portfolioName) {
    Portfolio portfolio = new Portfolio(portfolioName, new Client(name));
    portfolios.put(portfolioName, portfolio);
    return portfolio;
  }
}

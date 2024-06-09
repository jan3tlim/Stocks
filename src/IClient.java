import java.util.HashMap;

/**
 * Interface representing a client.
 */
public interface IClient {
  /**
   * Gets the list of portfolios owned by the client.
   *
   * @return a HashMap of portfolios owned by the client
   */
  HashMap<String, Portfolio> getPortfolios();

  /**
   * Creates a new portfolio with the specified name for the client.
   *
   * @param portfolioName the name of the new portfolio
   * @return the newly created portfolio
   */
  Portfolio createPortfolio(String portfolioName);
}

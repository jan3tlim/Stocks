import java.util.HashMap;

/**
 * The IClient interface defines the operations for managing a client's investment portfolios.
 * Implementations of this interface provide functionality to create, retrieve, save,
 * and load portfolios.
 * <p>
 * This interface serves as a contract for client-related operations, including
 * handling multiple portfolios and ensuring that the portfolios can be persistently
 * stored and retrieved from a specified directory.
 * </p>
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

  /**
   * Saves all portfolios owned by the client to the specified directory.
   * <p>
   * This method serializes the current state of each portfolio and writes them to the given
   * directory.The saved files can be used to restore the portfolios later, maintaining the
   * client's investment records.
   * </p>
   *
   * @param directory the directory path where the portfolios will be saved.
   *                                  The directory must be accessible and writable.
   */
  void savePortfolios(String directory);

  /**
   * Loads portfolios from the specified directory into the client's account.
   * <p>
   * This method reads the portfolio files from the given directory and restores them into the
   * client's collection of portfolios. It is useful for reloading the investment state after
   * application restarts or moving data between different systems.
   * </p>
   *
   * @param directory the directory path from where the portfolios will be loaded.
   *                  The directory must contain valid portfolio files.
   */
  void loadPortfolios(String directory);
}

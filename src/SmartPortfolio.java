public class SmartPortfolio extends Portfolio implements ISmartPortfolio{
  /**
   * Constructs a new Portfolio with the specified name and owner.
   *
   * @param portfolioName the name of the portfolio
   * @param owner         the client who owns this portfolio
   */
  public SmartPortfolio(String portfolioName, Client owner) {
    super(portfolioName, owner);
  }


}

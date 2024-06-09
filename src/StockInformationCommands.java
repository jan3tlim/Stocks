/**
 * Interface for StockInformationCommands.
 */
public interface StockInformationCommands {
  /**
   * calls the different commands for the specific stock wants to look into.
   *
   * @param m the stock to be checked.
   */
  void goController(IStock m);
}

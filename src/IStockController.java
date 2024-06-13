/**
 * Interface for controlling the operations related to stock management.
 * This interface defines the fundamental operations necessary for initializing
 * and managing the stock market application's main control flow.
 */
public interface IStockController {

  /**
   * Initializes and starts the main control loop of the stock management program.
   * This method is responsible for starting the program's operations, such as setting up
   * necessary configurations, initializing the user interface, and entering the main event loop.
   * It should be called once to start the application.
   */
  void goController();

}

import java.io.InputStreamReader;

/**
 * The ProgramMainStock class serves as the entry point for the stock management application.
 * It initializes the necessary components and starts the application by invoking the controller.
 */
public class ProgramMainStock {

  /**
   * The main method initializes the application by creating a Readable and Appendable
   * for input and output, respectively, and starts the StockController.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IStockController stockController = new StockController(rd,ap);
    stockController.goController();
  }

}

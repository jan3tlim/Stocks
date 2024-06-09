import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Mock for Model.
 */
public class MockModel implements IModel {

  @Override
  public IStock makeStock(TreeMap<LocalDate, Double> stockHistory, String ticker) {
    return new MockStock(ticker, stockHistory);
  }

  @Override
  public IClient makeClient() {
    return new MockClient();
  }

}

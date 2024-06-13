import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class PortfolioOptionsTest {
  private PortfolioOptions portfolioOptions;
  private ByteArrayOutputStream outputStream;

  private IView view;
  private IPortfolio portfolio;
  private String expected;
  @Before
  public void setUp() {

    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new MockView(printStream);
    portfolio = new MockPortfolio();
    expected = "";
  }

  private void setInputStream(String data) {
    ByteArrayInputStream inputStream;
    Scanner scanner;
    inputStream = new ByteArrayInputStream(data.getBytes());
    scanner = new Scanner(inputStream);
    portfolioOptions = new PortfolioOptions(scanner, view);
  }

  @Test
  public void testQuitOption() {
    String input = "b";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testCalculatePortfolioValue() {
    String input = "1\nb\n";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testAddStock() {
    String input = "2\nGOOG\nb\n";
    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testRemoveStock() {
    String input = "2\nGOOG\nb\n";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testRebalancePortfolio() {
    String input = "4\nb\n";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testPrintStocks() {
    String input = "5\nb\n";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testPerformanceOverTime() {
    String input = "6\nb\n";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void testDistributionOfValue() {
    String input = "7\nb\n";

    setInputStream(input);
    portfolioOptions.PortfolioOperation(portfolio);

    assertEquals(expected, outputStream.toString());
  }

}

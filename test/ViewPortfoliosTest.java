

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * viewPortfolioTest.
 */
public class ViewPortfoliosTest {
  private Client user;

  private IView view;
  private ByteArrayOutputStream outputStream;

  private AlphaVantageAPI api;

  /**
   * set up.
   */
  @Before
  public void setUp() {
    IModel model;
    model = new MockModel();
    AlphaVantageAPI api;
    user = new Client("");
    api = new MockAlphaVantageAPI();
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new TextView(printStream);
  }

  /**
   * testgo.
   */
  @Test
  public void testGo() {

    ViewPortfolios viewPortfolios;
    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected1 = new StringBuilder();


    // Add a portfolio to the user
    Portfolio portfolio = user.createPortfolio("TestPortfolio");

    // Simulate user inputs
    input.append("TestPortfolio\n");
    input.append("AAPL,10\n");
    input.append("b\n");

    expected.append("Existing Portfolios:\n");
    expected.append("TestPortfolio\n");
    expected.append("Which portfolio would you like to view?\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");
    expected.append("Portfolio {name='TestPortfolio', owner=user1, stocks={}}\n");
    expected.append("To add stocks to your portfolio, enter ticker and quantity (ex. AAPL,2).\n");
    expected.append("Successfully added 10 AAPL stocks in TestPortfolio\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "avigate back to main menu.\n");

    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
    viewPortfolios = new ViewPortfolios(view, scanner, user);



    assertEquals(expected1.toString(), outputStream.toString());
  }
}
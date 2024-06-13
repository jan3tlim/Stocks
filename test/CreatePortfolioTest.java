import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * test creating a portfolio.
 */
public class CreatePortfolioTest {
  private Client user;

  private IView view;
  private ByteArrayOutputStream outputStream;



  @Before
  public void setUp() {
    AlphaVantageAPI api;
    IModel model;
    model = new MockModel();
    user = new Client("");
    api = new MockAlphaVantageAPI();
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new TextView(printStream);
  }

  /**
   * testing he go method show correct portfolio.
   */
  @Test
  public void testGo() {
    CreatePortfolio createPortfolio;

    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected1 = new StringBuilder();

    // Simulate user inputs
    input.append("NewPortfolio\n");
    input.append("AAPL,10\n");
    input.append("b\n");

    expected.append("Create New Portfolio:\n\nWhat would you like to name your new portfolio?\n");
    expected.append("To add stocks to your portfolio, enter ticker and quantity (ex. AAPL,2).\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");
    expected.append("Successfully added 10 AAPL stocks in NewPortfolio\n");

    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");

    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
//    createPortfolio = new CreatePortfolio(view, scanner, user);

    assertEquals(expected1.toString(), outputStream.toString());
  }
}
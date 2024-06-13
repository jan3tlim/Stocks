
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * manage.
 */

public class ManagePortfolioMenuTest {
  private Client user;

  private IView view;
  private ByteArrayOutputStream outputStream;
  private ManagePortfolioMenu managePortfolioMenu;

  /**
   * testing the setup.
   */
  @Before
  public void setUp() {
    IModel model;
    model = new MockModel();
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new TextView(printStream);
  }

  /**
   * create a portfolio.
   */
  @Test
  public void testGo_CreatePortfolio() {
    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected1 = new StringBuilder();

    input.append("1\n");
    input.append("NewPortfolio\n");
    input.append("AAPL,10\n");
    input.append("b\n");

    expected.append("Manage Your Portfolio(s):\n\n");
    expected.append("1. Create a new portfolio\n");
    expected.append("2. View and edit existing portfolio(s)\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, " +
            "navigate back to main menu.\n");
    expected.append("Create New Portfolio:\n\nWhat would you like to name your new portfolio?\n");
    expected.append("To add stocks to your portfolio, enter ticker and quantity (ex. AAPL,2).\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");
    expected.append("Successfully added 10 AAPL stocks in NewPortfolio\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");

    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
//    managePortfolioMenu = new ManagePortfolioMenu(view, scanner, user);


    assertEquals(expected1.toString(), outputStream.toString());
  }

  /**
   * got the portfolio write.
   */
  @Test
  public void testGo_ViewPortfolios() {
    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected1 = new StringBuilder();


    Portfolio portfolio = new MockClient().createPortfolio("TestPortfolio");

    input.append("2\n");
    input.append("TestPortfolio\n");
    input.append("AAPL,10\n");
    input.append("b\n");

    expected.append("Manage Your Portfolio(s):\n\n");
    expected.append("1. Create a new portfolio\n");
    expected.append("2. View and edit existing portfolio(s)\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");
    expected.append("Existing Portfolios:\n");
    expected.append("TestPortfolio\n");
    expected.append("Which portfolio would you like to view?\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");
    expected.append("Portfolio {name='TestPortfolio', owner=user1, stocks={}}\n");
    expected.append("To add stocks to your portfolio, enter ticker and quantity (ex. AAPL,2).\n");
    expected.append("Successfully added 10 AAPL stocks in TestPortfolio\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");

    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
//    managePortfolioMenu = new ManagePortfolioMenu(view, scanner, user);


    assertEquals(expected1.toString(), outputStream.toString());
  }
}
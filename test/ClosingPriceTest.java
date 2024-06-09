
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import java.util.Scanner;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * test closing price interface.
 */
public class ClosingPriceTest {
  private IStock stock;
  private IView view;


  /**
   * set up to test it.
   */
  @Before
  public void setUp() {
    stock = new MockStock("AAPL", new TreeMap<>());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new TextView(printStream);
  }

  /**
   * testing that interface is correctly shown.
   */
  @Test
  public void testGo() {
    ClosingPrice closingPrice;
    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected1 = new StringBuilder();
    ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();


    input.append("10\n5\n2023\n");
    expected.append("Give us the date that you would like to know the stock AAPL closing price\n");
    expected.append("Please provide the date in the form of day, month, year.\n");
    expected.append("Day: Month: Year: ");
    expected.append("\nthe closing price of the give date is  --- 100.0\n\n");


    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");
    expected.append("\ninput any character other than b if you would like to find the "
            + "closing price of another date\n\n");
    input.append("b\n");


    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
    closingPrice = new ClosingPrice(view, scanner);

    closingPrice.goController(stock);

    assertEquals(expected1.toString(), outputStream1.toString());
  }
}



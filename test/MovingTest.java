

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
 * moving test.
 */
public class MovingTest {
  private IStock stock;
  private IView view;
  private ByteArrayOutputStream outputStream;


  @Before
  public void setUp() {
    stock = new MockStock("AAPL", new TreeMap<>());
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new TextView(printStream);
  }

  /**
   * testing go.
   */
  @Test
  public void testGo() {
    Moving moving;
    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected1 = new StringBuilder();


    input.append("5\n");
    expected.append("\nprovide us with the how many days or x-day would like your x-day moving "
            + "average to look back of stock AAPL history\n");
    expected.append("5\n");
    input.append("10\n5\n2023\n");
    expected.append("\n\nGive us the date that you would like to know the stock AAPL "
            + "Moving average of\n");
    expected.append("Please provide the date in the form of day, month, year.\n");
    expected.append("Day: ");
    expected.append("Month: ");
    expected.append("Year: ");
    expected.append("\nthe x-day moving average of the given date and x-day is  --- 100.0\n\n");
    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the program, "
            + "navigate back to main menu.\n");

    input.append("b\n");
    expected.append("\ninput any character other than b if you would like to find the x-day "
            + "moving average of another date and X-day\n");
    input.append("b\n");
    expected1.append("Please provide the date in the form of day, month, year.");
    expected1.append("\nDay: Month: Year: Enter 'b' to come back to the most recent menu.");
    expected1.append("\nTo quit the program, navigate back to main menu.\n");
    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
    moving = new Moving(view, scanner);

    moving.goController(stock);
    assertEquals(expected1.toString(), outputStream.toString());
  }
}

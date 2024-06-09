
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
 * crossover test are correctly show.
 */
public class CrossoverTest {
  private IStock stock;
  private IView view;


  /**
   * set up.
   */
  @Before
  public void setUp() {
    stock = new MockStock("AAPL", new TreeMap<>());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new TextView(printStream);
  }

  /**
   * testing the go method.
   */
  @Test
  public void testGo() {
    Crossover crossover;
    StringBuilder input = new StringBuilder();
    StringBuilder expected = new StringBuilder();
    StringBuilder expected2 = new StringBuilder();
    ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();

    // Simulate user inputs for x-day, start date, and end date
    input.append("5\n");
    expected.append("\nprovide us with the how many days or x-day would like your x-day "
            + "crossover to look back at of stock AAPL history\n");
    expected.append("5\n");

    expected.append("\nprovide us with the time period you would like the stock AAPL x-day "
            + "crossover.\nStart Date:\n");
    expected.append("Please provide the date in the form of day, month, year.\n");
    expected.append("Day: ");
    input.append("1\n");
    expected.append("Month: ");
    input.append("1\n");
    expected.append("Year: ");
    input.append("2023\n");
    expected.append("End Date: ");

    expected.append("Please provide the date in the form of day, month, year.\n");
    expected.append("Day: ");
    input.append("31\n");
    expected.append("Month: ");
    input.append("12\n");
    expected.append("Year: ");
    input.append("2023\n");

    expected.append("\nthe dates that there is an x-day crossovers of the give time " +
            "period and x-days are  ---\n");
    expected.append("[2023-01-01, 2023-02-01]\n\n");

    expected.append("Enter 'b' to come back to the most recent menu.\nTo quit the " +
            "program, navigate back to main menu.\n");

    expected.append("\ninput any character other that b if you would like to find the "
            + "x-day crossover average of another time period and X-day\n\n");
    input.append("b\n");


    InputStream inputStream = new ByteArrayInputStream(input.toString().getBytes());
    Scanner scanner = new Scanner(inputStream);
    crossover = new Crossover(view, scanner);

    crossover.goController(stock);

    assertEquals(expected2.toString(), outputStream1.toString());
  }
}


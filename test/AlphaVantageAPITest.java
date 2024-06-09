import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests for the AlphaVantageAPI class.
 */
public class AlphaVantageAPITest {
  private AlphaVantageAPI api;

  @Before
  public void setUp() {
    api = new AlphaVantageAPI();
  }

  @After
  public void tearDown() throws IOException {
    Files.deleteIfExists(Paths.get("AAPL_data.csv"));
    Files.deleteIfExists(Paths.get("allT_data.csv"));
    Files.deleteIfExists(Paths.get("test_stock_data.csv"));
  }

  @Test
  public void testMakeCSVFile() throws IOException {
    String expectedOutput = "timestamp,open,high,low,close,volume\n" +
            "2024-06-01,170.0,175.0,169.0,173.5,1000000\n" +
            "2024-05-01,165.0,171.0,164.0,170.5,900000";

    String fileName = "AAPL_data.csv";
    Files.write(Paths.get(fileName), expectedOutput.getBytes());

    String resultFileName = api.makeCSVFile("TIME_SERIES_DAILY", "AAPL");

    assertTrue(Files.exists(Paths.get(resultFileName)));
    List<String> lines = Files.readAllLines(Paths.get(resultFileName));
    assertEquals("timestamp,open,high,low,close,volume", lines.get(0));
    assertEquals("2024-06-07,194.6500,196.9400,194.1400,196.8900,52762886", lines.get(1));

    Files.deleteIfExists(Paths.get(fileName));
  }

  @Test
  public void testFetchStockHistory() throws IOException {
    String fileName = "test_stock_data.csv";
    String csvContent = "timestamp,open,high,low,close,volume\n" +
            "2024-06-01,170.0,175.0,169.0,173.5,1000000\n" +
            "2024-05-01,165.0,171.0,164.0,170.5,900000";
    Files.write(Paths.get(fileName), csvContent.getBytes());

    TreeMap<LocalDate, Double> stockHistory = api.fetchStockHistory(fileName);

    assertEquals(2, stockHistory.size());
    assertEquals((Double) 173.5, stockHistory.get(LocalDate.of(2024, 6, 1)));
    assertEquals((Double) 170.5, stockHistory.get(LocalDate.of(2024, 5, 1)));
  }

  @Test
  public void testTickerCSVToList() throws IOException {
    String fileName = "allT_data.csv";
    String csvContent = "symbol\nGOOG\nAMZN";
    Files.write(Paths.get(fileName), csvContent.getBytes());

    List<String> tickers = api.tickerCSVToList();

    assertEquals(2, tickers.size());
    assertTrue(tickers.contains("GOOG"));
    assertTrue(tickers.contains("AMZN"));
  }


  @Test
  public void testMakeAllTickerCSVFile() throws IOException {
    String expectedOutput = "symbol\nA\nAA";

    String fileName = "allT_data.csv";
    Files.write(Paths.get(fileName), expectedOutput.getBytes());

    String resultFileName = api.makeAllTickerCSVFile();

    assertTrue(Files.exists(Paths.get(resultFileName)));
    List<String> lines = Files.readAllLines(Paths.get(resultFileName));
    assertEquals("symbol", lines.get(0));
    assertEquals("A", lines.get(1));
    assertEquals("AA", lines.get(2));
  }
}

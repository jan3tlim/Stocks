import java.net.URL;

public interface IAlphaVantageAPI {

  /**
   * Creates a URL for accessing Alpha Vantage API data.
   *
   * @param function    the function type for the API call (e.g., "TIME_SERIES_DAILY")
   * @param stockSymbol the stock ticker symbol
   * @return a URL object pointing to the Alpha Vantage API endpoint for the given function and
   *                  stock symbol
   */
  URL urlMaker(String function, String stockSymbol);


  String returnData(URL url);


}

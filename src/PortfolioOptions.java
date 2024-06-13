import java.time.LocalDate;
import java.util.Scanner;

public class PortfolioOptions implements IPortfolioOptions{
  IView v;
  private Scanner in;

  PortfolioOptions(Scanner in,IView v){
    this.v = v;
    this.in = in;
  }

  @Override
  public void PortfolioOperation(IPortfolio p) {
    boolean quit = false;
    while(!quit){
      AlphaVantageAPI api = new AlphaVantageAPI();

      v.goBack();
      v.showPortfolioOptions();
      String s = in.next();
      LocalDate l;
      try {
        switch (s) {
          case "b":
            quit = true;
            break;
          case "1":
            l = v.provideDate(in);
            v.writeMessage("The value of your portfolio is " + String.format("$%.2f",p.calculatePortfolioValue(l)));
            break;
          case "2":
            l = v.provideDate(in);
            String t = "";
            while(t.isEmpty()){
              v.writeMessage("Stock Ticker: ");
              s = in.next();
              if (api.tickerCSVToList().contains(s)) {
                try {
                  t = s;
                  v.writeMessage("quantity:");
                  int i = v.getValidInteger(in);
                  p.addStock(api.makeStock(s),i,l);
                  v.writeMessage("the stock "+t+" is ADDED for shares " + i);
                } catch (IllegalArgumentException e) {
                  v.writeMessage("Error: " + e.getMessage() + System.lineSeparator());
                }
              } else {
                v.writeMessage("Invalid Ticker,provide a valid stock ticker");
              }}

            break;
          case "3":
            l = v.provideDate(in);
            String t1 = "";
            while(t1.isEmpty()){
              v.writeMessage("Stock Ticker: ");
              s = in.next();
              if (api.tickerCSVToList().contains(s)) {
                try {
                  t1 = s;
                  v.writeMessage("quantity:");
                  int i = v.getValidInteger(in);
                  p.removeStock(api.makeStock(s),i,l);
                  v.writeMessage("the stock "+t1+" is removed for shares " + i);
                } catch (IllegalArgumentException e) {
                  v.writeMessage("Error: " + e.getMessage() + System.lineSeparator());
                }
              } else {
                v.writeMessage("Invalid Ticker,provide a valid stock ticker");
              }}
            break;
          case "4":
            l = v.provideDate(in);
            p.rebalance(in,l,v);
            break;
          case "5":
            l = v.provideDate(in);
            v.writeMessage(p.printStocks(l));
            break;
          case "6":
            v.writeMessage("\nprovide us with the time period you would like to "
                    + "see performance overtime the stock. \nStart Date:\n");
            LocalDate sd = v.provideDate(in);
            v.writeMessage("\nEnd Date:\n ");
            LocalDate ed = v.provideDate(in);
            v.writeMessage(p.performanceOverTime(sd,ed));
            break;
          case "7":
            l = v.provideDate(in);
            v.writeMessage(p.distributionOfValueOnDate(l));
            break;
          default:
            v.showOptionError();
            in.next();
            break;
        }
      } catch (IllegalArgumentException e) {
        v.writeMessage("Error: " + e.getMessage() + System.lineSeparator());
      }
    }
  }
}

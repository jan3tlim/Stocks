WORKING FEATURES
=================
1. Allow a user to examine the gain or loss of a stock over a specified period.

2. Allow a user to examine the x-day moving average of a stock for a specified date and a
specified value of x.

3. Allow a user to determine which days are x-day crossovers for a specified stock over a
specified date range and a specified value of x.

4. Allow a user to create one or more portfolios with shares of one or more stock, and find
the value of that portfolio on a specific date. You can assume that all the stocks and their
quantities will exist on that date.

5. Purchase a specific number of shares of a specific stock on a specified date, and add them to
the portfolio

6. Sell a specific number of shares of a specific stock on a specified date from a given portfolio

7. Determine the composition of a portfolio at a specific date. Note that the composition may
change over time. The composition must include (a) the list of stocks and (b) the number of
shares of each stock

8. Determine the value of a portfolio on a specific date (to be exact, the end of that day).
The value for a portfolio before the date of its first purchase would be 0, since each stock
in the portfolio now was purchased at a specific point in time.

9. The distribution of value of a portfolio on a specific date (to be exact, the end of that day).
The distribution of value should include (a) the stock itself (b) the value of each individual
stock in the portfolio. The sum of the values in (b) should equal to the value of that portfolio
on that date.

10. Persist a portfolio so that it can be saved and loaded (i.e. save to and retrieve from files)
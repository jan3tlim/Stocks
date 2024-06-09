SETUP DIRECTIONS:
=================
Open Terminal/Command Prompt: Navigate to the directory where the JAR file is located.

Data Files: Ensure any necessary data files (e.g., allT_data.csv) are also in this directory if your application requires them.
Run the JAR File: Use the following command to execute the program:
java -jar Stockss.jar

DEMO OF PROGRAM
------------------

Start Program: Execute the JAR file as described above.

Main Menu:
You will see the main menu. Select either 1 or 2.

"1" will take you to the Stock Information Menu, where you can find out information
(ex. gain/loss, xday crossover price) on your chosen stock.
"2" will take you to the Manage Portfolios Menu, where you can create a portfolio and view its contents.

Stock Information Menu:

You will prompted to type in a valid ticker. Once the ticker is validated,
these options will pop up:
"1" will return closing price.
"2" will calculate gain or loss.
"3" will calculate the x-day moving average.
"4" will calculate the x-day crossover.

Manage Portfolios Menu:

"1" will create a new portfolio. Then, you will asked to name the portfolio and given the option to
add stocks of your choice. You must input an existing ticker and the quantity you would like.

"2" will show the portfolios you have made. The program will prompt you to input the name of the
portfolio you would like to view specifically. You will be shown the stocks inside your chosen portfolio. You will then be prompted to input a date which the program will use to find the total value of your portfolio on that specific date.

Example 1
----------
Check an Apple stocks closing price on 2-2-2022.

Select 1 in Main Menu.

Type in "AAPL" when prompted to type in ticker.

Type in 1 to for "Give Closing Price"

Type in 2-2-2022 in the prompted format

Press b twice to navigate back to Manage Portfolios Menu.
Press b to navigate back Main Menu.
Press q to quit the program.

Example 2
----------
Create a New Portfolio, Add Stocks, and View Those Stocks.

Select 2 in the Main Menu.

Enter 1  in the Manage Portfolios Menu to create a new portfolio.
When prompted for the portfolio name, enter "Tech Portfolio".

When asked if you want to add stocks, enter any letter other than "b", which will take you back to
the Manage Portfolios Menu.
The program will then prompt you type in the ticker. Type in AAPL and press enter.
The program will ask for the quantity, type in 5 and press enter.
This adds 5 shares of Apple Inc. to the portfolio.

Enter b to go back to the previous menu.

Enter 2 to view existing portfolios.
Enter "Tech Portfolio" to select the first portfolio.
Calculate the portfolio value by entering a specific date (e.g., "2024-06-01").

Press b twice to navigate back to Manage Portfolios Menu.
Press b to navigate back Main Menu.
Press q to quit the program.
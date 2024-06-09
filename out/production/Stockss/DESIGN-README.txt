Design Description
===================

OVERVIEW
-----------
The Stock Portfolio Management System is designed to provide users with a comprehensive tool to manage their stock investments. The system allows users to create and manage portfolios, view detailed stock information, and perform various stock-related calculations. The application aims to simplify the process of tracking and analyzing stock investments, making it accessible to both novice and experienced investors.

COMPONENTS
-----------
MODEL:

Client: Represents a user of the system, containing user details and their stock portfolios.
Portfolio: Represents a collection of stocks owned by the client. Contains methods to add and manage stocks.
Stock: Represents an individual stock with attributes like ticker symbol, historical prices, and quantity.
AlphaVantageAPI: Handles fetching stock data from the Alpha Vantage API.
Model: Implements the IModel interface, acting as the primary data handler for the application.

VIEW:

TextView: Provides a text-based interface for user interaction. Displays menus, prompts, and outputs based on user input.

CONTROLLER:

StockController: Handles the overall control flow of the application, delegating tasks to appropriate models and views.
StockInformation: Manages the stock-related functionalities and calculations.
ManagePortfolioMenu: Manages the portfolio-related functionalities such as creating and viewing portfolios.
StockInformationCommands: Handles commands related to stock information such as getting the closing price, calculating gain or loss, and retrieving moving averages and crossovers.

Instructions and Example Flow:

When the user starts the application (ProgramMainStock), the StockController initializes the
TextView and presents the main menu. User inputs are captured and processed by the controller,
which then updates the model and refreshes the view accordingly. For instance, when viewing a portfolio, the ViewPortfolios command retrieves portfolio data from the Client model and
uses the TextView to display it.

FILE DESCRIPTIONS
------------------
CalculateGorL.java: Contains logic for calculating gain or loss of stocks.
StockInformationCommands.java: Contains commands related to fetching and displaying stock information.
Moving.java: Implements logic for calculating moving averages.
Crossover.java: Implements logic for calculating stock crossovers.
ClosingPrice.java: Contains functionality for retrieving closing prices of stocks.
AddStockToPortfolio.java: Handles adding stocks to a portfolio.
ViewPortfolios.java: Manages viewing and editing of existing portfolios.
CreatePortfolio.java: Manages the creation of new portfolios.
IModel.java: Interface defining the model operations.
Portfolio.java: Represents a stock portfolio and contains methods to manage stocks within the portfolio.
StockController.java: Main controller handling user interactions and delegating tasks to appropriate components.
Model.java: Implements the IModel interface, handling the primary data operations.
Stock.java: Represents individual stocks and contains methods related to stock attributes.
IStock.java: Interface defining stock operations.
AlphaVantageAPI.java: Handles interaction with the Alpha Vantage API to fetch stock data.
ProgramMainStock.java: Main class to run the application.
IView.java: Interface defining view operations.
TextView.java: Text-based implementation of the view interface.
IStockController.java: Interface defining controller operations.
IClient.java: Interface defining client operations.
ViewTest.java: Test class for view components.
PortfolioTest.java: Test class for portfolio-related operations.
ClientTest.java: Test class for client-related operations.
StockTest.java: Test class for stock-related operations.
allT_data.csv: CSV file containing stock ticker data.
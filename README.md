# DeutscheBankCodeChallenge
Project/challenge for Deutsche Bank developer position.

Instructions: 

Run MarketAnalysisApplication in packag com.fdmgroup.api as Spring Boot application.

This will create an H2 Database at localhost:8088 
with API end points at 'localhost:8088/api/v1/statistic' and extensions '/date/{date}/index', '/date/{date}/ticker/{ticker}', as well as 'localhost:8088/api/v1/trade' and extensions '/date/{date}', '/date/{date}/ticker/{ticker}'.
Here, date is expected in the format yyyy-mm-dd, e.g. to access daily statistic summary for June 2nd 2023 of company with ticker 'ABC', 
navigate to 'localhost:8088/api/v1/statistic/date/2023-06-02/ticker/ABC'.

After running the application, the Setup script in package com.fdmgroup.api
initializes the database from data in the supplied CSV file. For demonstration, it then
prints the summary statistics to the console.

Please note: the API has not been fully tested and commented due to time constraints.
The Setup script is an ad-hoc solution for demonstration purposes. The API was 
developped to be compatible with a more polished front-end that was beyond of the scope
of this challenge.
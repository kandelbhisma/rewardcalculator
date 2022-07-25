# Web API Developer
#The rest API to get customer rewards based on customer Id

#A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.
- Solve using Spring Boot
- Create a RESTful endpoint
- Make up a data set to best demonstrate your solution
- Check solution into GitHub

``
http://localhost:8080/api/customers/{customerId}/reward
``

#Steps to run the project locally:
1. Import the project in IDE (IntelliJ preferred)
2. Select H2 as Database in Database Window
3. Please provide following configuration for H2 database connectivity:
   - Name: RewardsCalculator
   - User: charter
   - Password: charter
   - database: default
   - URL: jdbc:h2:mem:default
4. Run the main class file (TotalRewardsPointCalculatorApplication.java)
5. Run the following endpoints on postman or alternative application:
   - http://localhost:8080/api/customers/2100/reward
   - http://localhost:8080/api/customers/2200/reward
   - http://localhost:8080/api/customers/2300/reward
   - http://localhost:8080/api/customers/2400/reward
   - http://localhost:8080/api/customers/2500/reward
   - http://localhost:8080/api/customers/2600/reward
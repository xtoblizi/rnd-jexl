# rnd-jexl
This project explores the use of JEXL for easy application logic configurability. It is a public and open-source project.

# Tools
It is a Spring Boot Java application.
Uses Java 25 and Spring boot version 4.0.6, H2 (run time db)

# To test the application.
Run the application from its main method.
A bootstrap operation kicks in. A PostConstruct method executes the implementation : 
 ExecuteTransactionDiscountComputation, 
 ExecuteCustomerTransactionFraudEvaluation,
 ExecuteCustomerTransactionFraudEvaluationV2

# Extendability
You can add a controller layer as to provide a custom expression via an endpoint and any other desired code for testing and practice.

# Validation
There is a JexlValidator class in the jexlValidator package. You can use that to initially validate the expression if you wish to
provide and accept a custom expression from a presentation layer. This is to prevent expression exceptions at the point of evaluation (runtime).

# Author
Ogbosuka Christopher -
Software Architect

# License
Feel free to modify and tweak the codebase as deemed fit for your use.

# Closing Overview
This code showcases how to use JEXL for easy business process computation without little to no overhead on the system availability. 

The idea speaks to flexibility for a system to easily create and adjust certain business aspects without the need for code changes and redeployment.

Business aspects such as pricing computation, fraud flagging evaluation, discount computation, loyalty bonus generation and computation, etc., can be easily configured and even adjusted 
while the system is up and running. 

This eventually increases system availability and business benefits.

In this code, we have only explored discount creation and a lean fraud evaluation implementation.
We have covered areas on 
1. Security. How to create the JEXL with basic restrictions and security configuration
2. Invoking the values of variables, object fields and methods and even components properties/method as expression parameters.


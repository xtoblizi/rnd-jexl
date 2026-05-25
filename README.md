# rnd-jexl
This project explore usage of jexl for easy application logic configurability. It is a public and open source project.

# Tools
It is a spring boot java application.
Uses Java 25 and Spring boot version 4.0.6, H2 run time db

# To test the application.
Run the application from it's main method.
A boostrap operation kicks in. A PostConstruct method called : "ExecuteBootstrap" initiates the discount evaluation and computation process.

# Extendability
You can add a controller layer as to provide a custom expression via endpoint

# Validation
There is a JexlValidator class in the jexlValidator package. You can use that to first validate expression if you wish to
provide and accept custom expression from a presentation layer. This is to prevent expression exception at the point of evaluation.

# Author
Ogbosuka Christopher
Software Architect

# License
Feel free to modify and tweak code base as deem fit for your use.

# Overview
This code showcases how to usage JEXL for easy business process computation without little to no overhead on the system availability. 

The idea speaks to flexibility for a system to easily create and adjust certain business aspect of a system without the need for code change and redeployment.

Business aspects such as: Pricing computation, fee computation, discount computation, loyalty bonus generation and computation, etc. can be easily configured and even adjusted 
while system is up and running. Increasing system availability, business overall turnover and better customer satisfaction.

In this code we have only explored discount creation and computation.


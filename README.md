# plan-generator
Loan repayment plan generator
Simple RESTful API build for generating loan repayment plan for Lendico
Plan generator developed as a Maven application using Spring Boot & Java8


--How to run Plan Generator ?

1.Clone this repository:

  $ git clone https://github.com/PradnyaJN/plan-generator

2.Build with Maven

  $ cd <git_home>/plan-generator/plan-generator
  $ mvn install

3. Execute jar file - 
Above mvn install command will package application as a Jar file along wth embedded tomcat server as part of Spring boot standalone application.
Start server by launching application using generated Jar plan-generator-0.0.1-SNAPSHOT.jar

  $ cd <git_home>/plan-generator/plan-generator/target 
  $ java -jar plan-generator-0.0.1-SNAPSHOT.jar

Above command will start plan-generator application on the tomcat server at port specified in application.properties file (currently set to 8081)

--How to Generate a Plan:

Submit HTTP POST request to http://localhost:8081/generate-plan using HTTP client ,
Provide Loan details in JSON format as specified below,

Example JSON payload,

{
  "loanAmount": "100",
  "nominalRate": "5",
  "duration": 4,
  "startDate": "2019-04-26T00:00:01Z"
}

Plan generator will send response for repayment plan in JSON format specified below: 

[
  {
    "borrowerPaymentAmount": 25.26,
    "date": "2019-04-26T00:00:00Z",
    "initialOutstandingPrincipal": 100,
    "interest": 0.42,
    "principal": 24.84,
    "remainingOutstandingPrincipal": 75.16
  },
  {
    "borrowerPaymentAmount": 25.26,
    "date": "2019-05-26T00:00:00Z",
    "initialOutstandingPrincipal": 75.16,
    "interest": 0.31,
    "principal": 24.95,
    "remainingOutstandingPrincipal": 50.21
  },
  {
    "borrowerPaymentAmount": 25.26,
    "date": "2019-06-26T00:00:00Z",
    "initialOutstandingPrincipal": 50.21,
    "interest": 0.21,
    "principal": 25.05,
    "remainingOutstandingPrincipal": 25.16
  },
  {
    "borrowerPaymentAmount": 25.26,
    "date": "2019-07-26T00:00:00Z",
    "initialOutstandingPrincipal": 25.16,
    "interest": 0.1,
    "principal": 25.16,
    "remainingOutstandingPrincipal": 0
  }
]




Developed With:- Java 1.8,Eclipse Mars.2 Release,Apache Tomcat 8.0

#Readme to run application

1) Run goprint_master.sql in mysql
2) Change Database configurations in File :-  goprint-service\src\main\resources\META-INF\db-config.properties
3) Run maven command 'mvn clean install' to package with test cases or 'mvn clean install -DskipTests' to skipped test cases from inside goprint-aggregator project(where pom exists) 


#Demonstrating Rest API

1) Download postman plugin in chrome.
2) Import the file into postman :- Notes-Rest-Operations.postman_collection.json.

			OR
1)Provided curlMethods.txt file containing cURL commands to test rest api




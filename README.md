# Book-Store-Services
      REST APIs for online book store
This is a simple Java Spring Boot/Webflux application to store and get books. It stores and get books of different categories:
---
# Functionality And Approach Taken:
Book-Store-Services uses R2DBC (H2 inmemory DB) and consists of database named “bookstoredb” which has only one table named: “book”. In this project the database structure is present in "model" folder.
Book-Store-Services  supports CURD & checkout price with promocode functionality:
---
# Tools/Framework
<ul>
<li>Speing web flux for reactinf rest services</li>
<li>JUNIT/Mockito: Junit is the unit testing framework for the Java programming language. Mockito is a mocking framework.</li>
<li>Jacoco: Jacoco is a Java Code Coverage tool. The jacoco maven plugin is used in this project. This generate unit test code coverage report.</li>
<li>Lombok: Project Lombok is a java library that automatically plugs into editor and build tools, spicing up java. Getter, Setters, Construcutors can be created with annotation without writing the code with the help of Lombok.</li>
<li>Open API: this is open-source framework that helps developers design, build, document and consume RESTful Web services.</li>
</ul>

---
# Steps To Run The Application
<ul>
<li>This project uses project Lombok. So, if you do not have Lombok plugins inserted into your IDE, please install it into your IDD.</li>
<li>Now you can run your application. The table “book” will be automatically created in “bookstoredb”. By default, it runs in port:8080. You can open up the swagger ui from  following url: **http://localhost:8080/swagger-ui.html** .</li>
<li>You can also use jacoco plugins to generate unit test reports. For that go to the project root folder and run the following two commands in command prompt:
    <ul><li>mvn clean test</li>
    <li>mvn jacoco:report</li>
    </ul>    
</li>
</ul>

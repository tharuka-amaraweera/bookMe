<!DOCTYPE html>
<html>

<head>
</head>

<body>
    <h1>Hotel Reservation Website</h1>
    <p>Welcome to BOOKINN Hotel Reservation Website! This project is a hotel booking platform that allows users to browse, search, and reserve hotel rooms. Below, you'll find information about the technologies used and instructions for running and testing the application.</p>

  <h2>Technologies Used</h2>
    <p>The Hotel Reservation Website is built using a combination of technologies, including:</p>
    <ul>
        <li><strong>Frontend</strong>: AngularJS is used for the frontend development. AngularJS is a JavaScript framework for building dynamic web applications.</li>
        <li><strong>Backend</strong>: The backend of the application is powered by Spring Boot. Spring Boot is a Java-based framework that simplifies the development of RESTful APIs and web services.</li>
        <li><strong>Database</strong>: PostgreSQL is used as the database management system. PostgreSQL is a powerful, open-source relational database.</li>
        <li><strong>Unit Testing</strong>: JUnit and Mockito are employed for unit testing. JUnit is a popular testing framework for Java applications, and Mockito is used for mocking dependencies in tests.</li>
    </ul>

<h2>Getting Started</h2>
    <p>To get started with this project, follow these steps:</p>
    <ol>
        <li><strong>Clone the Repository</strong>: Clone this GitHub repository to your local machine using the following command:</li>
        <pre><code>git clone &lt;https://github.com/tharuka-amaraweera/bookMe&gt;</code></pre>
        <li><strong>Set Up the Database</strong>: Make sure you have PostgreSQL installed and create a database for the project. Update the database configuration in the Spring Boot application properties.</li>
        <li><strong>Build and Run the Backend</strong>: Navigate to the Spring Boot project directory and use Maven to build the backend application. Then, run it using <code>java -jar</code>.</li>
        <li><strong>Build and Run the Frontend</strong>: Navigate to the AngularJS project directory and install the required dependencies using npm. Build the frontend application and serve it using a development server.</li>
        <li><strong>Access the Application</strong>: Open a web browser and access the website at <code>http://localhost:&lt;4200&gt;</code>. Replace <code>&lt;port&gt;</code> with the port on which the frontend is running.</li>
    </ol>

<h2>Running Tests</h2>
    <p>To run unit tests for the application, use the following command within the Spring Boot project directory:</p>
    <pre><code>mvn test</code></pre>
    <p>This command will execute the JUnit tests and provide you with test results.</p>
</body>

</html>

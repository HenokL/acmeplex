# MovieTheaterReservation
Movie Theater Ticket Reservation Application

## Steps to Set Up the Back End

### 1. Prerequisites
Make sure you have the following installed:
- **Java 17**: You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/).
- **Maven**: Download it from the [Apache Maven website](https://maven.apache.org/download.cgi).

### 2. Install Dependencies
Navigate to the root of the backend project and run the following command to install dependencies:
 ```bash
mvn install
```

### 2. Database configuration
For the TA/Professor only. We have included the database configuration file in the submission. You will need to update the database configuration to match the correct settings for your environment, such as the database URL, username, and password.

Example:
 ```bash
  SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/moviedb
  SPRING_DATASOURCE_USERNAME=root
  SPRING_DATASOURCE_PASSWORD=password
```


## Steps to Set Up the Front End
### 1. Prerequisites
Make sure you have the following installed:
- **Node.js**: Download the latest stable version from [Node.js](https://nodejs.org).
- **npm** or **Yarn**: Comes with Node.js. Alternatively, install Yarn globally:
  ```bash
  npm install -g yarn


### 2. Install Dependencies
Run the following command to install the necessary dependencies:
```bash
npm install
```

### 3. Configure Environment Variables
Create a .env file in the root of the project. Use the .env.example file as a template. 

Then add the Back-end URL and port values.
Example:
```bash
REACT_APP_BACK_END_URL="http://localhost"
REACT_APP_BACK_END_PORT="8080"
```

### 4. Start the Development Server
Run the app in development mode:
```bash
npm start
```

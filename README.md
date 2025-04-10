# 🗳️ Online Voting System

An **Online Voting System** built using **Java Swing** for the graphical user interface and **MySQL** for data storage. This system allows users to register, log in, and cast a vote securely. Admins can manage users, candidates, and view real-time results.

---

## 📌 Features

- 🧑‍💼 User Registration and Login  
- 🗳️ Cast vote to preferred candidates (one vote per user)  
- 👥 Candidate management by Admin  
- 🔐 Admin login to manage election process  
- 📊 View real-time results  
- 🖥️ Desktop GUI using Java Swing  
- 🗃️ MySQL database for persistent data storage  

---

## 🛠️ Tech Stack

- **Java (Swing)** – Desktop GUI  
- **MySQL** – Backend database  
- **JDBC** – Database connectivity  
- **IDE** – IntelliJ IDEA / Eclipse / NetBeans  

---


## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/simple3957/online-voting-system.git
cd OnlineVotingSystem
```

### 2. Configure the Database

- Open MySQL and create a new database:

```sql
CREATE DATABASE voting_system;
```

- Import the schema file:

```bash
mysql -u root -p voting_system < schema.sql
```

### 3. Update DB Credentials

Open `DatabaseConnection.java` and update:

```java
String url = "jdbc:mysql://localhost:3306/voting_system";
String user = "root";
String password = "your_mysql_password";
```

### 4. Run the Application

Use your favorite IDE or run from terminal:

```bash
javac -d bin src/*.java
java -cp bin Main
```

---

## 🧾 Database Schema

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    has_voted BOOLEAN DEFAULT FALSE
);

CREATE TABLE candidates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    party VARCHAR(100)
);

CREATE TABLE votes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    candidate_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(id)
);
```

---

## 🔐 User Roles

### 👤 User
- Register and log in
- View candidates
- Cast vote (once only)

### 🛡️ Admin
- Log in using admin credentials
- View registered users and vote status
- Add/edit/delete candidates
- View voting results

---

## 📈 Future Enhancements

- 🧾 Generate downloadable result reports (PDF/CSV)
- 🔐 OTP/Email-based authentication
- 🌐 Web-based version using React + Spring Boot
- 📊 Data visualization (pie charts, bar graphs)

---

## 👨‍💻 Author

**Your Name**  
GitHub: [@simple3957](https://github.com/simple3957)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

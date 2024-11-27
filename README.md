## VitalPay

The VitalPay automated Billing for Healthcare Facilities system is designed to address the inefficiencies of traditional billing and administrative processes in healthcare facilities by providing a modern, integrated solution.

## SQL Syntax for creating table

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(8) UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Create default admin user

```sql
INSERT INTO users (user_id, first_name, last_name, address, username, password, role)
VALUES ('A-664784', 'Isaac Rei', 'Aniceta', '2 St. Barangka, Marikina City', 'admin', 'admin123', 'Admin');
```

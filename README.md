## VitalPay

The VitalPay automated Billing for Healthcare Facilities system is designed to address the inefficiencies of traditional billing and administrative processes in healthcare facilities by providing a modern, integrated solution.

## SQL Syntax for creating users table

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(8) UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    archive VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## SQL Syntax for creating patients table

```sql
CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id VARCHAR(8) UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    date_of_birth VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender TINYINT(1) NOT NULL DEFAULT 1,
    blood_group VARCHAR(50) NOT NULL,
    major_diseases VARCHAR(255),
    symptoms VARCHAR(255),
    diagnosis VARCHAR(255),
    medicines VARCHAR(255),
    ward_required TINYINT(1) NOT NULL DEFAULT 1,
    type_of_ward VARCHAR(255) NOT NULL,
    insurance_provider VARCHAR(255),
    company_name VARCHAR(255),
    id_card VARCHAR(255),
    created_by VARCHAR(255) NOT NULL,
    archive TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Create row admin user

```sql
INSERT INTO users (user_id, first_name, last_name, address, username, password, role, archive)
VALUES ('A-664784', 'Isaac Rei', 'Aniceta', '2 St. Barangka, Marikina City', 'admin123', 'Admin', 'no');
```

## Create row patient

```sql
INSERT INTO patients (patient_id, first_name, middle_name, last_name, phone, date_of_birth, address, gender, blood_group, major_diseases, symptoms, diagnosis, medicines, ward_required, type_of_ward, insurance_provider, company_name, id_card, created_by, archive) 
VALUES ('P-123456', 'John', 'A.', 'Doe', '123-456-7890', '1990-05-15', '123 Elm Street', 1, 'O+', 'Hypertension', 'Headache, Fatigue', 'Migraine', 'Paracetamol, Ibuprofen', 1, 'General Ward', 'HealthCare Inc.', 'Global Tech Solutions', 'ID12345678', 'A-664784', 0);
```
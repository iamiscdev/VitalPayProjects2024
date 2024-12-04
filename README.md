## VitalPay

- The VitalPay automated Billing for Healthcare Facilities system is designed to address the inefficiencies of traditional billing and administrative processes in healthcare facilities by providing a modern, integrated solution.

# SQL Syntax for creating users table

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

# SQL Syntax for creating patients table

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
    type_of_ward VARCHAR(25) NOT NULL,
    insurance_provider VARCHAR(255),
    company_name VARCHAR(255),
    id_card VARCHAR(255),
    created_by VARCHAR(255) NOT NULL,
    archive TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

# SQL Syntax for creating patients_prescription table

```sql
CREATE TABLE patients_prescription (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id VARCHAR(8) NOT NULL,
    drug_code VARCHAR(20) NOT NULL,
    drug_name VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    unit_price DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(patient_id, drug_code) -- Composite unique key
);
```


# SQL Syntax for creating patients_diagnostics table

```sql
CREATE TABLE patients_diagnostics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id VARCHAR(8) NOT NULL,
    test_name VARCHAR(20) NOT NULL,
    test_description VARCHAR(50) NOT NULL,
    test_cost DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

# Create columns admin user

```sql
INSERT INTO users (user_id, first_name, last_name, address, username, password, role, archive)
VALUES ('A-664784', 'Isaac Rei', 'Aniceta', '2 St. Barangka, Marikina City', 'admin123', 'Admin', 'no');
```

# Create columns patient

```sql
INSERT INTO patients (patient_id, first_name, middle_name, last_name, phone, date_of_birth, address, gender, blood_group, major_diseases, symptoms, diagnosis, medicines, ward_required, type_of_ward, insurance_provider, company_name, id_card, created_by, archive) 
VALUES ('P-123456', 'John', 'A.', 'Doe', '123-456-7890', '1990-05-15', '123 Elm Street', 1, 'O+', 'Hypertension', 'Headache, Fatigue', 'Migraine', 'Paracetamol, Ibuprofen', 1, 'General', 'HealthCare Inc.', 'Global Tech Solutions', 'ID12345678', 'A-664784', 0);
```


# Create columns patients_prescription

```sql
INSERT INTO patients_prescription (patient_id, drug_code, drug_name, quantity, unit_price) 
VALUES ('P-123456', 'D001', 'Paracetamol', 10, 5.00), ('P-123456', 'D002', 'Ibuprofen', 5, 12.50), ('P-123456', 'D003', 'Amoxicillin', 7, 8.75), ('P-123456', 'D004', 'Cough Syrup', 2, 20.00), ('P-123456', 'D005', 'Vitamin C', 30, 2.50);
```


# Create columns patients_diagnostics

```sql
INSERT INTO patients_diagnostics (patient_id, test_name, test_description, test_cost)
VALUES ('P-123456', 'X-Ray', 'Chest X-Ray', 1200.50);
```
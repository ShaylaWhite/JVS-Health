

## JVS-Health API

## Description
The JVS-Health API is a comprehensive Health Management System designed to efficiently manage medical information, specifically focusing on Doctors, Patients, and Prescriptions. It enables healthcare providers to perform essential tasks such as registration, authentication, patient management, prescription assignments/mangement.

## Models
The API works with the following models:

- Doctor: Represents registered healthcare professionals.
- Patient: Represents individuals under medical care.
- Prescriptions : Represents specific prescription  treatments.

## User Stories

### User Story 1: Doctor Registration

As a Guest User, I want to be able to register for an account with a username and password, so I can access the JVS's features.

**Acceptance Criteria**:

- I can access the /auth/doctors/register/ endpoint.
- I can provide a unique username and a secure password for registration.
- Upon successful registration, I receive a confirmation message.

### User Story 2: Doctor Login

As a Registered User, I want to log in and obtain a JWT token with my username and password, so I can access authenticated features.

 **Acceptance Criteria**:

- I can access the /auth/doctors/login/ endpoint.
- I can provide my registered username and password.
- Upon successful login, I receive a JWT token that I can use for authentication in subsequent requests.

### User Story 3: Browse Patients

As a Provider, I want to browse the list of patients by name, ID (primary key), date of birth, and Prescriptions ID to efficiently manage patients in the system.

 **Acceptance Criteria**:

- I can access the /api/doctors/patients endpoint.
- I can view a list of all my patients.

### User Story 4: Assign Prescriptions

As a Provider, I want to assign prescriptions from the database to my patients.

 **Acceptance Criteria**:

- I can access the /api/doctors/patients/{patientId}/prescriptions endpoint to assign a specific prescriptions
- I must provide my JWT token for authentication.
- I assign prescriptions.
- Upon successful assignment, the prescription's availability status changes, and it's associated with the patient.



### User Story 6: Doctor Update Prescription's Information

 As a Provider, I want to manage patients in the system, including adding, updating, and deleting prescription information.

**Acceptance Criteria**:

- I can access the /api/doctors/patients/{patientId}/prescriptions/{prescriptionId} endpoint for CRUD operations (Create, Read, Update, Delete) within the system.
- I must provide my JWT token for authentication.
- I can create a new prescription with doctor id(forgein key), patient id (forgein key), and details.
- I can update prescriptions details.
- I can delete prescriptions from the system.


### API Endpoints

To interact with the JVS-Health API, you can use the following endpoints:

| Request Type | URL                                    | Functionality                                                  | Access  |
|--------------|----------------------------------------|----------------------------------------------------------------|---------|
| POST         | `/auth/doctors/register/`              | Register a new user (Doctor) with a username and password (Public) | Public  |
| POST         | `/auth/doctors/login/`                 | Log in and obtain a JWT token with a username and password (Public) | Public  |
| GET          | `/api/doctors/patients/`               | Get a list of all patients (Private, requires JWT)            | Private |
| GET          | `/api/doctors/patients/{patientId}`    | Get information about a specific patient (Private, requires JWT) | Private |
| POST         | `/api/doctors/patients/{patientId}`               | Create a new patient (Admin, requires JWT)                     | Admin   |
| PUT          | `/api/doctors/patients/{patientId}`    | Update patient details (Admin, requires JWT)                   | Admin   |
| DELETE       | `/api/doctors/patients/{patientId}`    | Delete patient details (Admin, requires JWT)                   | Admin   |
| POST         | `/api/doctors/patients/{patientId}/prescriptions/{prescriptionId}` | Create a new prescription assignment for a patient (Admin, requires JWT) | Admin   |
| PUT          | `/api/doctors/patients/{patientId}/prescriptions/{prescriptionId}`    | Update prescription details (Admin, requires JWT)                   | Admin   |
| DELETE       | `/api/doctors/patients/{patientId}/prescriptions/{prescriptionId}`    | Delete prescription details (Admin, requires JWT)                   | Admin   |

## ERD Tables

**Doctor Table:**
Fields:
- doctor_id (Primary Key): Unique identifier for each doctor.
- username: The username of the doctor for authentication.
- password: The hashed password for authentication.


**Patient Table:**

Fields:
- patient_id (Primary Key): Unique identifier for each patient.
- name: The name of the patient.
- date_of_birth: The date of birth of the patient.

**Prescriptions Table:**

Fields:
- doctor_id (Foreign Key): Unique identifier for each flu shot.
- patient_id (Foreign Key): References the patient_id in the Patient Table, indicating which patient received the flu shot.
- details: Indicates information about the prescriptions is available or assigned to a patient.



## ERD Diagram

<img src="/JVS Health.png" alt="JVS Health ERD Diagram" title="JVS Health ERD Diagram">



## Annotations

How to implement Many to Many Tables
- https://www.baeldung.com/jpa-many-to-many



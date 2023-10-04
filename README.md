


<img src="/light blue creative modern medical clinic presentation.png" alt="JVS Health ERD Diagram" title="JVS Health ERD Diagram"> 



## Description
The JVS-Health API is a comprehensive Health Management System designed to efficiently manage medical information, specifically focusing on Doctors, Patients, and Prescriptions. It enables healthcare providers to perform essential tasks such as registration, authentication, patient management, prescription assignments/mangement.

## General Approach

The project follows a structured approach to building a secure and functional REST API for JVS Health management. It utilizes Spring Boot for creating a web application, integrates Spring Security with JWT token authentication for user access, and utilizes the H2 database to store data related to doctors, patients, and prescriptions. The application adheres to the principles of the MVC (Model-View-Controller) design pattern by separating controllers and services for improved code organization.

## Tools and Technologies Used

- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- H2 Database
- JavaDoc
- Git and GitHub
- Spring Profiles
- Lucidchart: Lucidchart is a cloud-based diagramming tool that offers a wide range of diagram templates, including ERDs. It provides an intuitive interface for creating and collaborating on diagrams.
- Website: Lucidchart

# Models
The API works with the following models:

- Doctor: Represents registered healthcare professionals.
- Patient: Represents individuals under medical care.
- Prescriptions : Represents specific prescription  treatments.

# User Stories

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


### User Story 5: Doctor Update Prescription's Information

 As a Provider, I want to manage patients in the system, including adding, updating, and deleting prescription information.

**Acceptance Criteria**:

- I can access the /api/doctors/patients/{patientId}/prescriptions/{prescriptionId} endpoint for CRUD operations (Create, Read, Update, Delete) within the system.
- I must provide my JWT token for authentication.
- I can create a new prescription with doctor id(forgein key), patient id (forgein key), and details.
- I can update prescriptions details.
- I can delete prescriptions from the system.


# Business Logic for JVS-Health API

The business logic for the JVS-Health API, which includes key functionalities for managing Doctors, Patients, and Prescriptions.

## Doctor Management

### Registration of Doctors

- Doctors can register themselves by providing their details, including first name, last name, email address, and password.
- Passwords are securely hashed before being stored in the database.
- Doctors' email addresses must be unique to ensure no duplicate registrations.

### Doctor Authentication

- Registered Doctors can log in to the system using their email address and password.
- Upon successful authentication, the system generates a JSON Web Token (JWT) for the Doctor, which is used for secure access to protected endpoints.

## Patient Management

### Patient Creation

- Doctors can create patient profiles, providing details such as the patient's name and birthdate.
- Patients are associated with the Doctor who created their profile.
- Duplicate patient profiles with the same name and birthdate for the same Doctor are not allowed.

### Fetching Patient Information

- Doctors can retrieve information about their patients, including their names and birthdates.
- Patients are associated with the Doctor who manages them.

### Updating Patient Information

- Doctors can update patient information, including their name and birthdate.
- The system ensures that updates are only allowed for patients associated with the authenticated Doctor.

### Patient Deletion

- Doctors can delete patient profiles they manage.
- Patient records are removed from the system when deleted.

## Prescription Management

### Creating Prescriptions

- Doctors can create prescriptions for their patients, specifying details about the prescription.
- Each prescription is associated with a Doctor and a Patient.

### Fetching Prescriptions

- Doctors can retrieve a list of prescriptions for a specific patient.
- The system ensures that Doctors can only access prescriptions for their own patients.

### Updating Prescriptions

- Doctors can update prescription details, such as the prescription details itself.
- Only the Doctor who created the prescription can modify it.

### Deleting Prescriptions

- Doctors can delete prescriptions for their patients.
- Deleted prescriptions are removed from the system.

### Fetching Prescription by ID

- Doctors can retrieve a specific prescription by its unique ID.

## Prescription Validation

- The system validates the authenticity and authorization of Doctors for each action.
- Access control is implemented to ensure Doctors can only manage their own patients and prescriptions.

## Authentication and Security
The JVS-Health API ensures data privacy, access control, and a secure environment for healthcare providers to manage medical information efficiently. It enables Doctors to register, authenticate, manage patients, and create and manage prescriptions for their patients while maintaining the confidentiality and integrity of the medical data.

- Authentication is performed using JWT (JSON Web Tokens) to ensure secure access to protected endpoints.
- Passwords are securely hashed and stored in the database.
- JWT tokens are generated upon successful login and must be included in the headers of protected API requests for authorization.
- A custom user details service and authentication filter are implemented to handle user authentication.




# API Endpoints

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

# ERD Tables

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



# ERD Diagram

<img src="/JVS Health (1).png" alt="JVS Health ERD Diagram" title="JVS Health ERD Diagram">

## Challenges Faced
- Mock MVC Challenges: Initially, none of our tests were passing due to issues in our test implementation with Mock MVC. We had to create a mock MVC user to resolve this.
- Method Naming Typo: A typo in the naming of a method within the DocService class resulted in 2 hours of unnecessary debugging.

## Key Achievements
- Early Project Completion: We successfully completed the project ahead of schedule, allowing us more time for refinement.
- Hands-on Collaboration: We had extensive hands-on experience with both driver and navigator roles. This helped us improve our soft skills and become more proficient in discussing and coding together.
- Enhanced Java and Spring Boot Skills: As a group, we have become more comfortable and confident in using Java and Spring Boot.
- Effective Team Chemistry: We fostered a great team chemistry, creating an atmosphere where everyone's opinions and ideas were not only heard but also considered.

## Annotations

How to implement Many to Many Tables
- https://www.baeldung.com/jpa-many-to-many


## Acknowledgments
We would like to express our gratitude to the following individuals for their invaluable assistance and guidance throughout this project:

- [Suresh Melvin Sigera's GitHub Profile](https://git.generalassemb.ly/sureshmelvinsigera) - Suresh provided essential support when we faced challenges in implementing testing with Mock MVC. He provide key details and gudiance about how to mock a logged in user properly. 


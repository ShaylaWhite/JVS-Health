
## Project Title and Description
## JVS-Health API


## Description
The JVS-Health API is a comprehensive Health Management System designed to efficiently manage medical information, specifically focusing on Doctors, Patients, and Flu Shots. It enables healthcare providers to perform essential tasks such as registration, authentication, patient management, and flu shot assignments.

## Models
The API works with the following models:

- Doctor: Represents registered healthcare professionals.
-Patient: Represents individuals under medical care.
-Flu Shot: Represents specific flu shot treatments.

## User Stories

#### User Story 1: Doctor Registration
As a Guest User, I want to be able to register for an account with a username and password, so I can access the JVS's features.

Acceptance Criteria:

I can access the /auth/doctors/register/ endpoint.
I can provide a unique username and a secure password for registration.
Upon successful registration, I receive a confirmation message.

#### User Story 2: Doctor Login
As a Registered User, I want to log in and obtain a JWT token with my username and password, so I can access authenticated features.

Acceptance Criteria:

I can access the /auth/doctors/login/ endpoint.
I can provide my registered username and password.
Upon successful login, I receive a JWT token that I can use for authentication in subsequent requests.

#### User Story 3: Browse Patients
As a Provider, I want to browse the list of patients by name, ID (primary key), date of birth, and flu shot ID to efficiently manage patients in the system.

Acceptance Criteria:

I can access the /api/doctors/patients endpoint.
I can view a list of all my patients.

#### User Story 4: Assign Flu Shots
As a Provider, I want to assign flu shots from the database to my patients.

Acceptance Criteria:

I can access the /api/doctors/patients/{patientId}/shots endpoint to assign a specific flu shot.
I must provide my JWT token for authentication.
I can only assign flu shots that are available.
Upon successful assignment, the flu shot's availability status changes, and it's associated with the patient.


#### User Story 5: Doctor Update Patient's Information
As a Provider, I want to manage patients in the system, including adding, updating, and deleting patient information.

Acceptance Criteria:

I can access the /api/doctors/patients/{patientId} endpoint for CRUD operations (Create, Read, Update, Delete) within the system.
I must provide my JWT token for authentication.
I can create a new patient with name, ID (primary key), date of birth, and flu shot ID (both foreign keys).
I can update patient details.
I can delete patients from the system.


### API Endpoints


POST /auth/doctors/register/

Register a new user (Doctor) with a username and password (Public).
POST /auth/doctors/login/

Log in and obtain a JWT token with a username and password (Public).
GET /api/doctors/patients/

Get a list of all patients (Private, requires JWT).
GET /api/doctors/patients/{patientId}

Get information about a specific patient (Private, requires JWT).
POST /api/doctors/patients/{patientId}/shots

Create a new flu shot assignment for a patient (Admin, requires JWT).
PUT /api/doctors/patients/{patientId}

Update patient details (Admin, requires JWT).
DELETE /api/doctors/patients/{patientId}

Delete patient details (Admin, requires JWT).

## API Endpoints
To understand the functionality of the REST API and its endpoints, refer to the documentation provided in the code using JavaDoc comments. Additionally, we have documented the REST API endpoints for user awareness:

| Request Type | URL                               | Functionality        | Access  |
|--------------|-----------------------------------|----------------------|---------|
| POST         | /auth/doctors/register/            | Register a new user with a username and password (Public) | Public |
| POST         | /auth/doctors/login/               | Log in and obtain a JWT token with a username and password (Public) | Public |
| GET          | /api/doctors/patients/             | Get a list of all pateints (Private, requires JWT) | Private |
| GET          | POST /api/doctors/patients/{patientId}/shots  | Get a specific pateints (Private, requires JWT) | Private |
| POST         | PUT /api/doctors/patients/{patientId}  | Create a new flu shot assignment for a patient (Admin, requires JWT) | Admin |
| PUT          | /api/doctors/patients/{patientId}  | Update patient details (Admin, requires JWT) | Admin |
| DEL          | /api/doctors/patients/{patientId}  | Delete patient details (Admin, requires JWT) | Admin |


## ERD Diagram





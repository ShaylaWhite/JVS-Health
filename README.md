
## Project Title and Description

**Title**: JVS-Health

**Description**: The Health Management System API is designed to facilitate the management of patients, prescriptions and shots for. It allows providers to add, search, update, and delete medical information efficiently.
- Models : Doctors , Prescriptions, Shots and Patients 

### User Stories

#### User Story 1: Doctor Registration

- **As a Guest User**, I want to be able to register for an account with a username and password, so I can access the JVS's features.
- **Acceptance Criteria**:
    - I can access the `/auth/doctors/register/` endpoint.
    - I can provide a unique username and a secure password for registration.
    - Upon successful registration, I receive a confirmation message.

#### User Story 2: Doctor Login

- **As a Registered User**, I want to log in and obtain a JWT token with my username and password, so I can access authenticated features.
- **Acceptance Criteria**:
    - I can access the `/auth/doctors/login/` endpoint.
    - I can provide my registered username and password.
    - Upon successful login, I receive a JWT token that I can use for authentication in subsequent requests.
 
 
#### User Story 3: Browse Patients

- **As a Provider**, I want to browse the list of patients by name, id(primary key), dob, prescripton id and shot id so I efficently manage patients in the system.
- **Acceptance Criteria**:
    - I can access the `/api/doctors/patients` endpoint.
    - I can view a  list of all my patients.
   

#### User Story 4: Assign Prescriptions

- **As a Provider**, I want to assign prescriptions from the database to my patients.
- **Acceptance Criteria**:
    - I can access the `/api/doctors/patients/{patientId}/prescriptions` endpoint to assign a specific prescription.
    - I must provide my JWT token for authentication.
    - I can only assign prescriptions that are available.
    - Upon successful assignment, the prescriptions's availability status changes, and it's associated with the patient.

#### User Story 5: Assign Shots

- **As a Provider**, I want to assign shots from the database to my patients.
- **Acceptance Criteria**:
    - I can access the `/api/doctors/patients/{patientId}/shots` endpoint to assign a specific shot.
    - I must provide my JWT token for authentication.
    - I can only assign shots that are available.
    - Upon successful assignment, the shots's availability status changes, and it's associated with the patient.


#### User Story 6: Doctor Update Patient's Information

 **As a Provider**, I want to manage patients in the system, including adding, updating, and deleting patients information.
- **Acceptance Criteria**:
    - I can access the `/api/doctors/patients/{patientId}` endpoint for CRUD operations (Create, Read, Update, Delete) within the system.
    - I must provide my JWT token for authentication.
    - I can create a new patient with name, id(primary key), dob, prescripton id and shot id(both forigen keys)
    - I can update patients details.
    - I can delete patients from the system.

## API Endpoints
To understand the functionality of the REST API and its endpoints, refer to the documentation provided in the code using JavaDoc comments. Additionally, we have documented the REST API endpoints for user awareness:

| Request Type | URL                               | Functionality        | Access  |
|--------------|-----------------------------------|----------------------|---------|
| POST         | /auth/doctors/register/            | Register a new user with a username and password (Public) | Public |
| POST         | /auth/doctors/login/               | Log in and obtain a JWT token with a username and password (Public) | Public |
| GET          | /api/doctors/patients/             | Get a list of all pateints (Private, requires JWT) | Private |
| GET          | /api/doctors/patients/{patientId}  | Get a specific pateints (Private, requires JWT) | Private |
| POST         | /api/doctors/patients/{patientId}  | Create a new patient (Admin, requires JWT) | Admin |
| PUT          | /api/doctors/patients/{patientId}  | Update patient details (Admin, requires JWT) | Admin |
| DEL          | /api/doctors/patients/{patientId}  | Delete patient details (Admin, requires JWT) | Admin |
| POST         | /api/doctors/patients/{patientId}/prescriptions | Create a new prescritption for a patient (Admin, requires JWT) | Admin |
| POST         | /api/doctors/patients/{patientId}/shots | Create a new shot for a patient (Admin, requires JWT) | Admin |



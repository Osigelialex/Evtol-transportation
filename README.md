## Project Overview: Modern Transportation System for Medication Delivery with eVTOL Vehicles

![image](https://github.com/Osigelialex/evtol-transportation/assets/97721950/7e510983-ed0b-4761-963c-93617d22c603)


### Description
This project implements a backend API for a cutting-edge transportation system utilizing electric Vertical Takeoff and Landing (eVTOL) vehicles for medication delivery. It manages a fleet of eVTOLs and tracks medications, providing RESTful API endpoints for registering new eVTOLs, loading medications onto them (including weight and battery level checks), checking loaded medications for specific eVTOLs, finding available eVTOLs for loading, and obtaining the battery level of a specific eVTOL. Additionally, it implements a scheduled task to monitor and update evTOL battery levels.

### Technology Stack
- **Spring Boot**: For building the backend API.
- **Database**: The choice of database is not included in this project, allowing flexibility in selection.

### Frontend Repository
[Link to Frontend Repository](https://github.com/Osigelialex/evtol-transportation-frontend)

### Assumptions
- User authentication and authorization are not implemented in this basic version.
- Medication delivery workflow, such as dispatching and delivery confirmation, is not covered.

### Running the Application
1. Configure a database connection (details depend on your chosen database).
2. Build the project using your preferred method (e.g., Maven).
3. Run the application.

### API Endpoints
**Dashboard**
- **GET /api/v1/dashboard**: Gets dashboard information

**Deliveries**
- **GET /api/v1/deliveries**: Gets a list of deliveries
- **POST /api/v1/deliveries**: Creates a new delivery

**Evtols**
- **POST /api/v1/evtol/register**: Registers a new eVTOL.
- **GET /api/v1/evtol**: Get a list of all eVTOLs.
- **GET /api/v1/available-evtols**: Get a list of evtols available for loading
- **GET /api/v1/loaded-evtols**: Get a list of loaded evtols.
- **GET /api/v1/evtol/{serialNumber}**: Retrieves information about an evtol.
- **GET /api/v1/evtol/{serialNumber}/medications**: Retrieves loaded medications for an eVTOL.

**Medications**
- **GET /api/v1/medications**: Retrieves all medications
- **GET /api/v1/medications/{evtolSerialNumber}**: Retrieve medications for an evtol
- **POST /api/v1/medications**: Add a new medication to the system
- **POST /api/v1/medications/{evtolSerialNumber}/load**: load an evtol with a medication

**Note:** Replace `{serialNumber}` and `{evtolSerialNumber}` with the actual serial number in requests.

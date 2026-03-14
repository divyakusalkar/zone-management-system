# 🗺️ Zone Management System

A full-stack web application for managing **Zones** associated with Brands, Companies, and Groups.



## Tech Stack

| Layer     | Technology                                       |
|-----------|--------------------------------------------------|
| Frontend  | React 18, Vite, Tailwind CSS, React Router v6, Axios |
| Backend   | Spring Boot 3.4, Spring Data JPA, Lombok, Validation |
| Database  | MySQL 8+                                         |



## Project Structure

```
Zone Management System/
├── database/
│   ├── schema.sql          # DDL – creates all tables
│   └── sample_data.sql     # INSERT sample groups/companies/brands/zones
│
├── backend/                # Spring Boot Maven project
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/zonemanagement/backend/
│       │   ├── BackendApplication.java
│       │   ├── config/        CorsConfig.java
│       │   ├── controller/    ZoneController, BrandController, CompanyController, GroupController
│       │   ├── dto/           ZoneDTO, BrandDTO, CompanyDTO, GroupDTO, ApiResponse, PagedResponse
│       │   ├── entity/        Zone, Brand, Company, Group
│       │   ├── exception/     GlobalExceptionHandler, ResourceNotFoundException, BadRequestException
│       │   ├── repository/    ZoneRepository, BrandRepository, CompanyRepository, GroupRepository
│       │   └── service/       ZoneService, BrandService, CompanyService, GroupService
│       └── resources/
│           └── application.properties
│
└── frontend/               # Vite + React project
    ├── index.html
    ├── package.json
    ├── vite.config.js
    ├── tailwind.config.js
    └── src/
        ├── App.jsx
        ├── main.jsx
        ├── index.css
        ├── components/     Layout, ZoneTable, ZoneFilters, Pagination, ConfirmModal, Spinner
        ├── hooks/          useZones.js, useReferenceData.js
        ├── pages/          ZoneDashboard, AddZone, EditZone
        ├── services/       api.js, zoneService.js, referenceService.js
        └── utils/          helpers.js
```



## Prerequisites

- **Java 21+**
- **Maven 3.9+**
- **Node.js 20+** (npm 10+)
- **MySQL 8+**



## Setup & Run

### 1. Database

```sql
-- Run schema first, then sample data
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
```

### 2. Backend

**Configure** database credentials in  
`backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/zone_management_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

**Run:**

```bash
cd backend
mvn spring-boot:run
```

The API starts at **http://localhost:8080**

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

The UI starts at **http://localhost:5173**

> The Vite dev-server proxies `/api/*` → `http://localhost:8080`, so no CORS issues during development.



## API Reference

All responses follow the envelope:
```json
{
  "success": true,
  "message": "...",
  "data": { ... },
  "timestamp": "2026-03-07T10:00:00"
}
```



### Zones

#### GET `/api/zones`
Returns paginated, filterable list of zones.

**Query Params:**

| Parameter   | Type    | Description                       |
|-------------|---------|-----------------------------------|
| `brandId`   | Integer | Filter by brand ID                |
| `companyId` | Integer | Filter by company ID              |
| `groupId`   | Integer | Filter by group ID                |
| `search`    | String  | Partial match on zone name        |
| `page`      | Integer | Page number (0-based, default: 0) |
| `size`      | Integer | Page size (default: 10)           |
| `sortBy`    | String  | Field to sort by (default: zoneId)|
| `sortDir`   | String  | `asc` or `desc` (default: asc)    |

**Response `data`:**
```json
{
  "content": [
    {
      "zoneId": 1,
      "zoneName": "Marol Zone",
      "brandId": 1,
      "brandName": "FreshMart",
      "companyId": 1,
      "companyName": "Alpha Retail Pvt Ltd",
      "groupId": 1,
      "groupName": "Alpha Group",
      "isActive": true,
      "createdAt": "2026-03-07T10:00:00",
      "updatedAt": "2026-03-07T10:00:00"
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 12,
  "totalPages": 2,
  "last": false
}
```



#### GET `/api/zones/{id}`
Returns a single zone.



#### POST `/api/zones`
Create a new zone.

**Request Body:**
```json
{
  "zoneName": "Marol Zone",
  "brandId": 1
}
```

**Validations:**
- `zoneName` — required, max 50 chars
- `brandId` — required

**Response:** `201 Created` with the created zone object.



#### PUT `/api/zones/{id}`
Update an existing zone.

**Request Body:** Same as POST.

**Response:** `200 OK` with the updated zone object.



#### DELETE `/api/zones/{id}`
Soft-delete a zone (`is_active = false`).

**Response:** `200 OK`



### Brands

#### GET `/api/brands`
Returns all active brands.

**Query Params:**

| Parameter   | Type    | Description              |
|-------------|---------|--------------------------|
| `companyId` | Integer | Filter by company        |
| `groupId`   | Integer | Filter by group          |



### Companies

#### GET `/api/companies`
Returns all active companies.

**Query Params:**

| Parameter | Type    | Description       |
|-----------|---------|-------------------|
| `groupId` | Integer | Filter by group   |



### Groups

#### GET `/api/groups`
Returns all active groups.



## Error Responses

| HTTP Status | Scenario                          |
|-------------|-----------------------------------|
| `400`       | Bad request / missing params      |
| `404`       | Resource not found                |
| `422`       | Validation failure (field errors) |
| `500`       | Unexpected server error           |

```json
{
  "success": false,
  "message": "Zone not found with id: '99'",
  "data": null,
  "timestamp": "2026-03-07T10:00:00"
}
```



## Frontend Pages

| Route              | Page              | Description                   |
|--------------------|-------------------|-------------------------------|
| `/zones`           | Zone Dashboard    | Table with filters, search, pagination, edit/delete actions |
| `/zones/add`       | Add Zone          | Form to create a new zone     |
| `/zones/edit/:id`  | Edit Zone         | Form to update an existing zone |



## Features

- ✅ Paginated zone table
- ✅ Filter by Group / Company / Brand (cascading)
- ✅ Zone name search
- ✅ Add / Edit zone with form validation
- ✅ Soft delete with confirmation modal
- ✅ Toast notifications (success & error)
- ✅ Loading spinners
- ✅ Responsive design (Tailwind CSS)
- ✅ Global exception handling (backend)
- ✅ DTO pattern (backend)
- ✅ CORS configured for local development

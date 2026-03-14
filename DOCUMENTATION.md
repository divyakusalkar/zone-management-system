# Zone Management System - Complete Documentation

**Version:** 1.0.0  
**Date:** March 14, 2026  
**Status:** Production Ready

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Tech Stack](#tech-stack)
3. [Project Structure](#project-structure)
4. [Database Schema](#database-schema)
5. [Backend Architecture](#backend-architecture)
6. [Frontend Architecture](#frontend-architecture)
7. [API Endpoints](#api-endpoints)
8. [Installation & Setup](#installation--setup)
9. [Running the Application](#running-the-application)
10. [Features](#features)
11. [Configuration](#configuration)
12. [Development Guidelines](#development-guidelines)
13. [Deployment](#deployment)

---

## Project Overview

**Zone Management System** is a modern, full-stack web application designed to manage zones, brands, companies, and groups within an organizational hierarchy. The system provides a user-friendly interface for CRUD operations with advanced filtering, pagination, and search capabilities.

### Key Objectives
- Organize and manage hierarchical business structures
- Efficient zone management with brand, company, and group associations
- RESTful API for seamless integration
- Responsive, modern UI with real-time updates
- Comprehensive error handling and validation

---

## Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Backend Framework** | Spring Boot | 3.4.3 |
| **Language (Backend)** | Java | 21+ |
| **Language (Frontend)** | JavaScript/JSX | ES2020+ |
| **Frontend Framework** | React | 18.3.1 |
| **Build Tool (Frontend)** | Vite | 5.2.12 |
| **Styling** | Tailwind CSS | 3.4.4 |
| **Routing (Frontend)** | React Router | 6.23.1 |
| **HTTP Client** | Axios | 1.7.2 |
| **Database** | MySQL | 8.0+ |
| **ORM** | Spring Data JPA | Included in Spring Boot |
| **Validation** | Jakarta Bean Validation | Included in Spring Boot |
| **Build Tool (Backend)** | Maven | 3.9+ |
| **Node.js** | Node.js | 20+ |

---

## Project Structure

```
Zone Management System/
│
├── database/
│   ├── schema.sql              # Database DDL - Creates all tables and relationships
│   └── sample_data.sql         # Sample data for testing
│
├── backend/                    # Spring Boot Application
│   ├── pom.xml                 # Maven configuration
│   ├── src/main/java/com/zonemanagement/backend/
│   │   ├── BackendApplication.java         # Spring Boot entry point
│   │   ├── config/
│   │   │   └── CorsConfig.java             # CORS configuration for frontend access
│   │   ├── controller/
│   │   │   ├── ZoneController.java         # Zone REST endpoints
│   │   │   ├── BrandController.java        # Brand REST endpoints
│   │   │   ├── CompanyController.java      # Company REST endpoints
│   │   │   └── GroupController.java        # Group REST endpoints
│   │   ├── dto/
│   │   │   ├── ZoneDTO.java                # Zone data transfer object
│   │   │   ├── BrandDTO.java               # Brand data transfer object
│   │   │   ├── CompanyDTO.java             # Company data transfer object
│   │   │   ├── GroupDTO.java               # Group data transfer object
│   │   │   ├── ApiResponse.java            # Generic API response wrapper
│   │   │   └── PagedResponse.java          # Pagination response wrapper
│   │   ├── entity/
│   │   │   ├── Zone.java                   # Zone JPA entity
│   │   │   ├── Brand.java                  # Brand JPA entity
│   │   │   ├── Company.java                # Company JPA entity
│   │   │   └── Group.java                  # Group JPA entity
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java # Centralized exception handling
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── BadRequestException.java
│   │   ├── repository/
│   │   │   ├── ZoneRepository.java         # Zone data access
│   │   │   ├── BrandRepository.java        # Brand data access
│   │   │   ├── CompanyRepository.java      # Company data access
│   │   │   └── GroupRepository.java        # Group data access
│   │   └── service/
│   │       ├── ZoneService.java            # Zone business logic
│   │       ├── BrandService.java           # Brand business logic
│   │       ├── CompanyService.java         # Company business logic
│   │       └── GroupService.java           # Group business logic
│   └── src/main/resources/
│       └── application.properties          # Spring Boot configuration
│
├── frontend/                   # React + Vite Application
│   ├── package.json            # NPM dependencies
│   ├── vite.config.js          # Vite bundler configuration
│   ├── tailwind.config.js       # Tailwind CSS configuration
│   ├── postcss.config.js        # PostCSS configuration
│   ├── index.html               # HTML entry point
│   └── src/
│       ├── main.jsx             # React entry point
│       ├── App.jsx              # Root component
│       ├── index.css            # Global styles with Tailwind imports
│       ├── components/
│       │   ├── Layout.jsx            # Main layout wrapper
│       │   ├── ZoneTable.jsx         # Zone list table display
│       │   ├── ZoneFilters.jsx       # Filtering controls
│       │   ├── Pagination.jsx        # Pagination component
│       │   ├── ConfirmModal.jsx      # Delete confirmation modal
│       │   └── Spinner.jsx           # Loading spinner
│       ├── hooks/
│       │   ├── useZones.js           # Custom hook for zone operations
│       │   └── useReferenceData.js   # Custom hook for dropdown data
│       ├── pages/
│       │   ├── ZoneDashboard.jsx     # Main zone list page
│       │   ├── AddZone.jsx           # Create new zone page
│       │   └── EditZone.jsx          # Edit existing zone page
│       ├── services/
│       │   ├── api.js                # Axios instance configuration
│       │   ├── zoneService.js        # Zone API calls
│       │   └── referenceService.js   # Reference data API calls
│       └── utils/
│           └── helpers.js            # Utility functions
│
├── README.md                   # Project overview
└── DOCUMENTATION.md            # This file

```

---

## Database Schema

### Entity Relationship Diagram

```
┌─────────────┐
│   GROUPS    │
│─────────────│
│ group_id(PK)│
│ group_name  │
│ is_active   │
│ created_at  │
│ updated_at  │
└──────┬──────┘
       │ 1:N
       ├─────────────────────────────┐
       │                             │
    ┌──▼──────────────┐        ┌─────┬───┐
    │  COMPANIES      │        │ COMPANIES│
    │─────────────────│        │──────────│
    │ company_id(PK)  │        │company_id│
    │ company_name    │        │group_id  │
    │ group_id(FK)────┼────────┤(FK)      │
    │ is_active       │        │ ...      │
    │ created_at      │        └──────────┘
    │ updated_at      │
    └────────┬────────┘
             │ 1:N
             │
        ┌────▼─────────┐
        │    BRANDS    │
        │──────────────│
        │ brand_id(PK) │
        │ brand_name   │
        │ company_id───┼──→ COMPANIES
        │ is_active    │
        │ created_at   │
        │ updated_at   │
        └────────┬─────┘
                 │ 1:N
                 │
            ┌────▼──────────┐
            │     ZONES     │
            │───────────────│
            │ zone_id(PK)   │
            │ zone_name     │
            │ brand_id──────┼──→ BRANDS
            │ is_active     │
            │ created_at    │
            │ updated_at    │
            └──────────────┘
```

### Table Details

#### GROUPS
- **Purpose:** Top-level organizational grouping
- **Columns:**
  - `group_id` (INT, PK, AUTO_INCREMENT)
  - `group_name` (VARCHAR 100, NOT NULL)
  - `is_active` (BOOLEAN, DEFAULT TRUE)
  - `created_at` (DATETIME, DEFAULT CURRENT_TIMESTAMP)
  - `updated_at` (DATETIME, DEFAULT CURRENT_TIMESTAMP ON UPDATE)

#### COMPANIES
- **Purpose:** Companies within groups
- **Columns:**
  - `company_id` (INT, PK, AUTO_INCREMENT)
  - `company_name` (VARCHAR 100, NOT NULL)
  - `group_id` (INT, FK → GROUPS.group_id)
  - `is_active` (BOOLEAN, DEFAULT TRUE)
  - `created_at` (DATETIME)
  - `updated_at` (DATETIME)

#### BRANDS
- **Purpose:** Brands under companies
- **Columns:**
  - `brand_id` (INT, PK, AUTO_INCREMENT)
  - `brand_name` (VARCHAR 100, NOT NULL)
  - `company_id` (INT, FK → COMPANIES.company_id)
  - `is_active` (BOOLEAN, DEFAULT TRUE)
  - `created_at` (DATETIME)
  - `updated_at` (DATETIME)

#### ZONES
- **Purpose:** Geographic or logical zones under brands
- **Columns:**
  - `zone_id` (INT, PK, AUTO_INCREMENT)
  - `zone_name` (VARCHAR 50, NOT NULL)
  - `brand_id` (INT, FK → BRANDS.brand_id)
  - `is_active` (BOOLEAN, DEFAULT TRUE)
  - `created_at` (DATETIME)
  - `updated_at` (DATETIME)

---

## Backend Architecture

### Design Patterns Used

1. **MVC (Model-View-Controller)** - Separation of concerns with controllers, services, and repositories
2. **DTO (Data Transfer Object)** - Decoupling API contracts from entities
3. **Repository Pattern** - Abstraction of data access layer
4. **Service Layer Pattern** - Business logic encapsulation
5. **Global Exception Handler** - Centralized error handling

### Component Overview

#### Controllers
All controllers follow RESTful conventions:
- **ZoneController**: `/api/zones` - Full CRUD + filtering
- **BrandController**: `/api/brands` - Reference data operations
- **CompanyController**: `/api/companies` - Reference data operations
- **GroupController**: `/api/groups` - Reference data operations

#### Services
- Implement business logic
- Handle validation and error scenarios
- Use repositories for data access
- Return DTOs instead of entities

#### Repositories
- Extend `JpaRepository` for standard CRUD operations
- Custom query methods for advanced filtering
- Automatic transaction management

#### DTOs
- Clean separation between API and database
- Version-independent API contracts
- Type-safe data transfer

---

## Frontend Architecture

### Component Hierarchy

```
App.jsx
├── Layout
│   ├── Navigation
│   └── Routes
│       ├── ZoneDashboard
│       │   ├── ZoneFilters
│       │   ├── ZoneTable (with Pagination)
│       │   └── ConfirmModal
│       ├── AddZone
│       └── EditZone
```

### Custom Hooks

#### `useZones()`
- Manages zone list state
- Handles fetching, filtering, sorting
- Pagination support
- CRUD operations

#### `useReferenceData()`
- Loads dropdown data (groups, companies, brands)
- Caches data to avoid redundant API calls
- Dependency tracking

### Services

#### `api.js`
- Axios instance configuration
- Base URL setup
- Request/response interceptors
- Error handling middleware

#### `zoneService.js`
- Zone API operations
- Filtering and search
- Pagination handling

#### `referenceService.js`
- Groups, Companies, Brands API calls
- Dropdown data fetching

---

## API Endpoints

### Zone Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/zones` | List all zones with pagination |
| GET | `/api/zones/{id}` | Get zone by ID |
| POST | `/api/zones` | Create new zone |
| PUT | `/api/zones/{id}` | Update zone |
| DELETE | `/api/zones/{id}` | Delete zone |

**Query Parameters for GET /api/zones:**
- `search` - Filter by zone name
- `brandId` - Filter by brand
- `page` - Page number (0-based)
- `size` - Page size (default: 10)
- `sort` - Sort field and direction

### Brand Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/brands` | List all brands |
| GET | `/api/brands/{id}` | Get brand by ID |
| POST | `/api/brands` | Create new brand |
| PUT | `/api/brands/{id}` | Update brand |
| DELETE | `/api/brands/{id}` | Delete brand |

### Company Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/companies` | List all companies |
| GET | `/api/companies/{id}` | Get company by ID |
| GET | `/api/companies?groupId={id}` | Get companies by group |
| POST | `/api/companies` | Create new company |
| PUT | `/api/companies/{id}` | Update company |
| DELETE | `/api/companies/{id}` | Delete company |

### Group Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/groups` | List all groups |
| GET | `/api/groups/{id}` | Get group by ID |
| POST | `/api/groups` | Create new group |
| PUT | `/api/groups/{id}` | Update group |
| DELETE | `/api/groups/{id}` | Delete group |

### Response Format

#### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "id": 1,
    "name": "Zone Name",
    "brandId": 1,
    "isActive": true,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
}
```

#### Error Response
```json
{
  "success": false,
  "message": "Error description",
  "error": "ERROR_CODE"
}
```

#### Paginated Response
```json
{
  "success": true,
  "data": [
    { /* zone object */ },
    { /* zone object */ }
  ],
  "currentPage": 0,
  "totalPages": 5,
  "totalElements": 45,
  "pageSize": 10
}
```

---

## Installation & Setup

### Prerequisites

Before starting, ensure you have the following installed:

- **Java Development Kit (JDK) 21+**
  - Download from: https://www.oracle.com/java/technologies/downloads/
  - Verify: `java -version`

- **Maven 3.9+**
  - Download from: https://maven.apache.org/download.cgi
  - Verify: `mvn -version`

- **Node.js 20+ (includes npm 10+)**
  - Download from: https://nodejs.org/
  - Verify: `node -v` and `npm -v`

- **MySQL 8.0+**
  - Download from: https://www.mysql.com/downloads/
  - Create a MySQL user for development

### Step 1: Clone the Repository

```bash
git clone https://github.com/divyakusalkar/zone-management-system.git
cd zone-management-system
```

### Step 2: Database Setup

1. **Create Database and Tables:**

```bash
mysql -u root -p < database/schema.sql
```

2. **Load Sample Data:**

```bash
mysql -u root -p zone_management_db < database/sample_data.sql
```

3. **Verify Tables:**

```sql
USE zone_management_db;
SHOW TABLES;
-- Should display: brands, companies, groups, zones
```

### Step 3: Backend Configuration

1. **Navigate to backend directory:**

```bash
cd backend
```

2. **Update database credentials in `src/main/resources/application.properties`:**

```properties
# MySQL Connection
spring.datasource.url=jdbc:mysql://localhost:3306/zone_management_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_mysql_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
server.servlet.context-path=/

# Logging
logging.level.root=INFO
logging.level.com.zonemanagement=DEBUG
```

3. **Build the backend:**

```bash
mvn clean install
```

### Step 4: Frontend Setup

1. **Navigate to frontend directory:**

```bash
cd ../frontend
```

2. **Install dependencies:**

```bash
npm install
```

3. **Create `.env` file (optional, if needed):**

```env
VITE_API_URL=http://localhost:8080
```

---

## Running the Application

### Development Mode

**Terminal 1 - Backend:**

```bash
cd backend
mvn spring-boot:run
```

Backend will start at: `http://localhost:8080`

**Terminal 2 - Frontend:**

```bash
cd frontend
npm run dev
```

Frontend will start at: `http://localhost:5173`

### Production Build

**Backend:**

```bash
cd backend
mvn clean package
java -jar target/backend-1.0.0.jar
```

**Frontend:**

```bash
cd frontend
npm run build
# Distributable files in dist/ directory
```

---

## Features

### Zone Management

✅ **Create Zones**
- Add new zones with brand assignment
- Automatic timestamp tracking
- Active/Inactive status

✅ **Read Zones**
- List all zones with pagination
- Search by zone name
- Filter by brand
- Sort by name, brand, creation date

✅ **Update Zones**
- Modify zone name
- Change brand association
- Toggle active status

✅ **Delete Zones**
- Soft delete support (mark inactive)
- Confirmation dialog
- Cascade considerations

### Reference Data Management

✅ **Groups** - Top-level organizational units
✅ **Companies** - Within groups
✅ **Brands** - Within companies
✅ **Zones** - Within brands

### User Interface Features

✅ **Responsive Design** - Works on desktop and tablet
✅ **Real-time Filtering** - Search and filter zones instantly
✅ **Pagination** - Efficient data loading
✅ **Error Handling** - User-friendly error messages
✅ **Loading States** - Visual feedback during operations
✅ **Confirmation Dialogs** - Prevent accidental deletions
✅ **Toast Notifications** - Success/error feedback

---

## Configuration

### Backend Configuration (`application.properties`)

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/zone_management_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=validate

# Server
server.port=8080
server.servlet.context-path=/

# CORS (for frontend access)
# Configured in com.zonemanagement.backend.config.CorsConfig

# Logging
logging.level.root=INFO
logging.level.com.zonemanagement=DEBUG

# Spring Boot
spring.application.name=zone-management-backend
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

### Frontend Configuration (`vite.config.js`)

```javascript
import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    host: 'localhost'
  }
})
```

---

## Development Guidelines

### Code Style

**Backend:**
- Follow Google Java Style Guide
- Use meaningful variable and method names
- Document public APIs with Javadoc
- One responsibility per class/method

**Frontend:**
- Use camelCase for variables and functions
- Use PascalCase for components
- Consistent JSDoc comments
- Minimal component responsibilities

### Git Workflow

1. Create feature branch: `git checkout -b feature/zone-management`
2. Commit frequently: `git commit -m "Descriptive message"`
3. Push changes: `git push origin feature/zone-management`
4. Create Pull Request with detailed description

### Testing

**Backend:**
- Unit tests with JUnit 5
- Integration tests with Spring Test
- Target 80%+ code coverage

**Frontend:**
- Component tests with Vitest
- User interaction testing
- Mock API responses

### Documentation

- Update README for significant changes
- Add inline comments for complex logic
- Keep API documentation current
- Maintain database schema documentation

---

## Deployment

### Prerequisites for Deployment

- Server with Java 21 runtime
- MySQL database setup
- Node.js and npm (for frontend build)
- Nginx or Apache for frontend reverse proxy

### Deployment Steps

#### 1. Backend Deployment

```bash
# Build JAR file
mvn clean package

# Transfer to server
scp target/backend-1.0.0.jar user@server:/opt/app/

# Run on server
java -jar /opt/app/backend-1.0.0.jar
```

#### 2. Frontend Deployment

```bash
# Build production bundle
npm run build

# Transfer dist folder to server
scp -r dist/* user@server:/var/www/zone-management/

# Configure Nginx
# proxy_pass http://localhost:8080
```

#### 3. Database Backup

```bash
mysqldump -u root -p zone_management_db > backup.sql
```

---

## Troubleshooting

### Backend Issues

**Issue:** Database connection error
**Solution:** Verify MySQL is running and credentials are correct in `application.properties`

**Issue:** Port 8080 already in use
**Solution:** Change `server.port` in `application.properties` or kill existing process

**Issue:** CORS errors in frontend
**Solution:** Verify CorsConfig allows frontend URL

### Frontend Issues

**Issue:** API calls fail with 404
**Solution:** Verify backend is running on port 8080 and URLs are correct

**Issue:** Modules not found errors
**Solution:** Run `npm install` to install dependencies

**Issue:** Styling not applied
**Solution:** Rebuild Tailwind CSS, clear browser cache

---

## Support & Contact

For issues, questions, or contributions:
- GitHub Issues: https://github.com/divyakusalkar/zone-management-system/issues
- Email: divyakusalkar26@gmail.com

---

## License

This project is proprietary and confidential.

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | Mar 14, 2026 | Initial release with full CRUD functionality |

---

**End of Documentation**

-- ============================================================
-- Zone Management System - Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS zone_management_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE zone_management_db;

-- ------------------------------------------------------------
-- Table: groups
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `groups` (
  `group_id`   INT           NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(100)  NOT NULL,
  `is_active`  BOOLEAN       NOT NULL DEFAULT TRUE,
  `created_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Table: companies
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `companies` (
  `company_id`   INT          NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(100) NOT NULL,
  `group_id`     INT          NOT NULL,
  `is_active`    BOOLEAN      NOT NULL DEFAULT TRUE,
  `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`company_id`),
  CONSTRAINT `fk_companies_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Table: brands
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `brands` (
  `brand_id`   INT          NOT NULL AUTO_INCREMENT,
  `brand_name` VARCHAR(100) NOT NULL,
  `company_id` INT          NOT NULL,
  `is_active`  BOOLEAN      NOT NULL DEFAULT TRUE,
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`brand_id`),
  CONSTRAINT `fk_brands_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Table: zones
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `zones` (
  `zone_id`   INT          NOT NULL AUTO_INCREMENT,
  `zone_name` VARCHAR(50)  NOT NULL,
  `brand_id`  INT          NOT NULL,
  `is_active` BOOLEAN      NOT NULL DEFAULT TRUE,
  `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`zone_id`),
  CONSTRAINT `fk_zones_brand` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`brand_id`)
) ENGINE=InnoDB;

-- ============================================================
-- Zone Management System - Sample Data
-- ============================================================

USE zone_management_db;

-- Groups
INSERT INTO `groups` (`group_name`) VALUES
  ('Alpha Group'),
  ('Beta Group'),
  ('Gamma Group');

-- Companies
INSERT INTO `companies` (`company_name`, `group_id`) VALUES
  ('Alpha Retail Pvt Ltd',   1),
  ('Alpha Foods Pvt Ltd',    1),
  ('Beta Enterprises Ltd',   2),
  ('Gamma Solutions Ltd',    3);

-- Brands
INSERT INTO `brands` (`brand_name`, `company_id`) VALUES
  ('FreshMart',       1),
  ('QuickBite',       1),
  ('AlphaGrocers',    2),
  ('BetaExpress',     3),
  ('GammaHub',        4);

-- Zones
INSERT INTO `zones` (`zone_name`, `brand_id`, `is_active`) VALUES
  ('Marol Zone',       1, TRUE),
  ('Kurla Zone',       1, TRUE),
  ('Andheri Zone',     1, TRUE),
  ('Bandra Zone',      2, TRUE),
  ('Dadar Zone',       2, TRUE),
  ('Borivali Zone',    2, FALSE),
  ('Thane Zone',       3, TRUE),
  ('Vashi Zone',       3, TRUE),
  ('Powai Zone',       4, TRUE),
  ('Goregaon Zone',    4, FALSE),
  ('Mulund Zone',      5, TRUE),
  ('Malad Zone',       5, TRUE);

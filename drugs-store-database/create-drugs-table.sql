-- drugstore.Drugs definition

CREATE TABLE `Drugs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `drug_id` varchar(64) NOT NULL,
  `brand_name` varchar(126) NOT NULL,
  `generic_name` varchar(126) NOT NULL,
  `manufacturer_name` varchar(256) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `product_type` varchar(256) DEFAULT NULL,
  `substance_name` varchar(512) DEFAULT NULL,
  `warnings` text,
  `drug_interactions` text,
  `precautions` text,
  `do_not_use` text,
  `pregnancy_or_breast_feeding` text,
  `dosage_and_administration` text,
  PRIMARY KEY (`id`),
  KEY `brand_name` (`brand_name`)
) ENGINE=InnoDB AUTO_INCREMENT=80792 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
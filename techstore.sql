-- Удаление существующих таблиц
DROP TABLE IF EXISTS `delivery`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `store_inventory`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `brand`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Структура таблиці `brand`
CREATE TABLE `brand` (
                         `brand_id` int(11) NOT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `brand` (`brand_id`, `name`) VALUES
                                             (1, 'Samsung'),
                                             (2, 'Apple'),
                                             (3, 'Huawei'),
                                             (4, 'Xiaomi'),
                                             (5, 'OnePlus'),
                                             (6, 'Sony'),
                                             (7, 'LG'),
                                             (8, 'Google'),
                                             (9, 'Motorola'),
                                             (10, 'Nokia');

-- Структура таблиці `customer`
CREATE TABLE `customer` (
                            `customer_id` int(11) NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) DEFAULT NULL,
                            `contact` varchar(255) DEFAULT NULL,
                            `address` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `customer` (`customer_id`, `name`, `contact`, `address`) VALUES
                                                                         (1, 'John Doe', '123-456-7890', '123 Main St'),
                                                                         (2, 'Jane Smith', '987-654-3210', '456 Oak St'),
                                                                         (3, 'Michael Johnson', '567-890-1234', '789 Elm St'),
                                                                         (4, 'Emily Brown', '234-567-8901', '456 Pine St'),
                                                                         (5, 'David Wilson', '890-123-4567', '789 Maple St');

-- Структура таблиці `delivery`
CREATE TABLE `delivery` (
                            `delivery_id` int(11) NOT NULL AUTO_INCREMENT,
                            `product_id` int(11) DEFAULT NULL,
                            `date` date DEFAULT NULL,
                            `quantity` int(11) DEFAULT NULL,
                            PRIMARY KEY (`delivery_id`),
                            KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `delivery` (`delivery_id`, `product_id`, `date`, `quantity`) VALUES
                                                                             (1, 1, '2024-05-14', 10),
                                                                             (2, 2, '2024-05-15', 8),
                                                                             (3, 3, '2024-05-16', 12),
                                                                             (4, 4, '2024-05-17', 6),
                                                                             (5, 5, '2024-05-18', 9),
                                                                             (6, 6, '2024-05-19', 7),
                                                                             (7, 7, '2024-05-20', 11),
                                                                             (8, 8, '2024-05-21', 5),
                                                                             (9, 9, '2024-05-22', 15),
                                                                             (10, 10, '2024-05-23', 8);

CREATE TRIGGER `delivery_trigger` AFTER INSERT ON `delivery` FOR EACH ROW BEGIN
    IF NEW.quantity <= 0 THEN
        UPDATE Product SET status = 'Out of Stock' WHERE product_id = NEW.product_id;
    END IF;
END;

-- Структура таблиці `employee`
CREATE TABLE `employee` (
                            `employee_id` int(11) NOT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `employee` (`employee_id`, `name`) VALUES
                                                   (1, 'Alice Brown'),
                                                   (2, 'Bob Green'),
                                                   (3, 'Charlie White'),
                                                   (4, 'Eva Black'),
                                                   (5, 'Frank Red');

-- Структура таблиці `orders`
CREATE TABLE `orders` (
                          `order_id` int(11) NOT NULL AUTO_INCREMENT,
                          `customer_id` int(11) DEFAULT NULL,
                          `employee_id` int(11) DEFAULT NULL,
                          `product_id` int(11) DEFAULT NULL,
                          `date` date DEFAULT NULL,
                          `total_amount` decimal(10,2) DEFAULT NULL,
                          `status` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`order_id`),
                          KEY `customer_id` (`customer_id`),
                          KEY `employee_id` (`employee_id`),
                          KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `orders` (`order_id`, `customer_id`, `employee_id`, `product_id`, `date`, `total_amount`, `status`) VALUES
                                                                                                                    (1, 1, 1, 1, '2024-05-14', 799.99, 'Completed'),
                                                                                                                    (2, 2, 2, 2, '2024-05-15', 999.99, 'Processing'),
                                                                                                                    (3, 3, 3, 3, '2024-05-16', 899.99, 'Pending'),
                                                                                                                    (4, 4, 4, 4, '2024-05-17', 699.99, 'Completed'),
                                                                                                                    (5, 5, 5, 5, '2024-05-18', 899.99, 'Processing'),
                                                                                                                    (6, 1, 1, 6, '2024-05-19', 1199.99, 'Completed'),
                                                                                                                    (7, 2, 2, 7, '2024-05-20', 599.99, 'Processing'),
                                                                                                                    (8, 3, 3, 8, '2024-05-21', 699.99, 'Pending'),
                                                                                                                    (9, 4, 4, 9, '2024-05-22', 499.99, 'Completed'),
                                                                                                                    (10, 5, 5, 10, '2024-05-23', 499.99, 'Processing');

-- Структура таблиці `product`
CREATE TABLE `product` (
                           `product_id` int(11) NOT NULL AUTO_INCREMENT,
                           `brand_id` int(11) DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `price` decimal(10,2) DEFAULT NULL,
                           `processor` varchar(255) DEFAULT NULL,
                           `memory` varchar(255) DEFAULT NULL,
                           `RAM` varchar(255) DEFAULT NULL,
                           `screenType` varchar(255) DEFAULT NULL,
                           `materialOfCorps` varchar(255) DEFAULT NULL,
                           `colour` varchar(255) DEFAULT NULL,
                           `dimension` varchar(255) DEFAULT NULL,
                           `Battery` varchar(255) DEFAULT NULL,
                           `status` varchar(20) DEFAULT 'Available',
                           PRIMARY KEY (`product_id`),
                           KEY `brand_id` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `product` (`product_id`, `brand_id`, `name`, `price`, `processor`, `memory`, `RAM`, `screenType`, `materialOfCorps`, `colour`, `dimension`, `Battery`, `status`) VALUES
                                                                                                                                                                                 (1, 1, 'Samsung Galaxy S21', 799.99, 'Snapdragon 865', '128GB', '8GB', 'AMOLED', 'Glass', 'Black', '6.2"', '4000mAh', 'Available'),
                                                                                                                                                                                 (2, 2, 'iPhone 12', 999.99, 'A14 Bionic', '256GB', '4GB', 'OLED', 'Aluminum', 'White', '6.1"', '2815mAh', 'Available'),
                                                                                                                                                                                 (3, 3, 'Huawei P40 Pro', 899.99, 'Kirin 990', '256GB', '8GB', 'OLED', 'Glass', 'Blue', '6.58"', '4200mAh', 'Available'),
                                                                                                                                                                                 (4, 4, 'Xiaomi Mi 11', 699.99, 'Snapdragon 888', '256GB', '8GB', 'AMOLED', 'Glass', 'Gray', '6.81"', '4600mAh', 'Available'),
                                                                                                                                                                                 (5, 5, 'OnePlus 9 Pro', 899.99, 'Snapdragon 888', '256GB', '12GB', 'AMOLED', 'Glass', 'Green', '6.7"', '4500mAh', 'Available'),
                                                                                                                                                                                 (6, 6, 'Sony Xperia 1 III', 1199.99, 'Snapdragon 888', '256GB', '12GB', 'OLED', 'Glass', 'Black', '6.5"', '4500mAh', 'Available'),
                                                                                                                                                                                 (7, 7, 'LG Velvet', 599.99, 'Snapdragon 765G', '128GB', '6GB', 'OLED', 'Glass', 'White', '6.8"', '4300mAh', 'Available'),
                                                                                                                                                                                 (8, 8, 'Google Pixel 5', 699.99, 'Snapdragon 765G', '128GB', '8GB', 'OLED', 'Aluminum', 'Black', '6.0"', '4080mAh', 'Available'),
                                                                                                                                                                                 (9, 9, 'Motorola Edge', 499.99, 'Snapdragon 765G', '128GB', '6GB', 'OLED', 'Glass', 'Blue', '6.7"', '4500mAh', 'Available'),
                                                                                                                                                                                 (10, 10, 'Nokia 8.3 5G', 499.99, 'Snapdragon 765G', '128GB', '8GB', 'IPS LCD', 'Glass', 'Gray', '6.81"', '4500mAh', 'Available');

-- Структура таблиці `store_inventory`
CREATE TABLE `store_inventory` (
                                   `store_inventory_id` int(11) NOT NULL,
                                   `product_id` int(11) DEFAULT NULL,
                                   `quantity` int(11) DEFAULT NULL,
                                   PRIMARY KEY (`store_inventory_id`),
                                   KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `store_inventory` (`store_inventory_id`, `product_id`, `quantity`) VALUES
                                                                                   (1, 1, 15),
                                                                                   (2, 2, 20),
                                                                                   (3, 3, 18),
                                                                                   (4, 4, 10),
                                                                                   (5, 5, 25),
                                                                                   (6, 6, 13),
                                                                                   (7, 7, 17),
                                                                                   (8, 8, 8),
                                                                                   (9, 9, 30),
                                                                                   (10, 10, 16);

CREATE TRIGGER `store_inventory_trigger` AFTER UPDATE ON `store_inventory` FOR EACH ROW BEGIN
    IF NEW.quantity <= 0 THEN
        UPDATE Product SET status = 'Out of Stock' WHERE product_id = NEW.product_id;
    END IF;
END;

-- Обмеження зовнішнього ключа збережених таблиць
ALTER TABLE `delivery`
    ADD CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

ALTER TABLE `orders`
    ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
    ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
    ADD CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

ALTER TABLE `product`
    ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`);

ALTER TABLE `store_inventory`
    ADD CONSTRAINT `store_inventory_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 4.0.6deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 13-06-2014 a las 15:46:14
-- Versión del servidor: 5.5.37-0ubuntu0.13.10.1
-- Versión de PHP: 5.5.3-1ubuntu2.3
create database ULEBANK_FINAL;

USE ULEBANK_FINAL;

GRANT ALL ON ULEBANK_FINAL.* TO root@'%' IDENTIFIED BY 'toor';
GRANT ALL ON ULEBANK_FINAL.* TO root@localhost IDENTIFIED BY 'toor';

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ACCOUNTS`
--

CREATE TABLE IF NOT EXISTS `ACCOUNTS` (
  `account_number` varchar(64) COLLATE utf8_bin NOT NULL,
  `balance` double NOT NULL,
  `last_liquidation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `liquidation_frequency` int(11) NOT NULL,
  `max_overdraft` double NOT NULL,
  PRIMARY KEY (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ACCOUNTS_AUTHORIZEDS`
--

CREATE TABLE IF NOT EXISTS `ACCOUNTS_AUTHORIZEDS` (
  `account_number` varchar(64) COLLATE utf8_bin NOT NULL,
  `client_id` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`account_number`),
  KEY `account_number` (`account_number`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ACCOUNTS_CLIENTS`
--

CREATE TABLE IF NOT EXISTS `ACCOUNTS_CLIENTS` (
  `client_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `account_number` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`client_id`,`account_number`),
  KEY `account_number` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ACCOUNT_DIRECT_DEBITS`
--

CREATE TABLE IF NOT EXISTS `ACCOUNT_DIRECT_DEBITS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `BUYABLE`
--

CREATE TABLE IF NOT EXISTS `BUYABLE` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `amount` bigint(20) NOT NULL,
  `purchased_amount` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `employee_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `profitability` double DEFAULT NULL,
  `fee_id` int(11) DEFAULT NULL,
  `discriminator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fee_id` (`fee_id`),
  KEY `employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CARDS`
--

CREATE TABLE IF NOT EXISTS `CARDS` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `pin` varchar(64) COLLATE utf8_bin NOT NULL,
  `buy_limit_diary` double NOT NULL,
  `buy_limit_monthly` double NOT NULL,
  `cash_limit_diary` double NOT NULL,
  `cash_limit_monthly` double NOT NULL,
  `emission_date` varchar(64) COLLATE utf8_bin NOT NULL,
  `expiration_date` varchar(64) COLLATE utf8_bin NOT NULL,
  `discriminator` varchar(20) COLLATE utf8_bin NOT NULL,
  `cvv` varchar(64) COLLATE utf8_bin NOT NULL,
  `fees` int(32) NOT NULL,
  `account_number` varchar(64) COLLATE utf8_bin NOT NULL,
  `client_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `month_day` timestamp NULL DEFAULT NULL,
  `transaction_list` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  KEY `account_number` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CLIENTS`
--

CREATE TABLE IF NOT EXISTS `CLIENTS` (
  `id` varchar(12) COLLATE utf8_bin NOT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `surnames` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(256) COLLATE utf8_bin NOT NULL,
  `civil_state` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `phone_number1` int(11) DEFAULT NULL,
  `phone_number2` int(11) DEFAULT NULL,
  `profession` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `birth_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enterprise_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `discriminator` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `DIRECT_DEBIT`
--

CREATE TABLE IF NOT EXISTS `DIRECT_DEBIT` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `account_direct_debit_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_direct_debit_id` (`account_direct_debit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `EMPLOYEE`
--

CREATE TABLE IF NOT EXISTS `EMPLOYEE` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `surnames` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `salary` double NOT NULL,
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `office_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `office_id` (`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `FEES`
--

CREATE TABLE IF NOT EXISTS `FEES` (
  `client_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `maximun_age` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `discriminator` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `is_percent` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `FEE_CASES`
--

CREATE TABLE IF NOT EXISTS `FEE_CASES` (
  `fee_case_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `amount_formula` varchar(256) NOT NULL,
  `triggering_conditions` varchar(256) NOT NULL,
  `subject` tinytext NOT NULL,
  `liquidation_fee_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`fee_case_id`),
  KEY `liquidation_fee_id` (`liquidation_fee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `GENERIC_HANDLER`
--

CREATE TABLE IF NOT EXISTS `GENERIC_HANDLER` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `discriminator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `HISTORY`
--

CREATE TABLE IF NOT EXISTS `HISTORY` (
  `history_id` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `HISTORY_TRANSACTIONS`
--

CREATE TABLE IF NOT EXISTS `HISTORY_TRANSACTIONS` (
  `history_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `transaction_id` varchar(64) COLLATE utf8_bin NOT NULL,
  KEY `history_id` (`history_id`),
  KEY `transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `INVESTMENT_ACCOUNTS`
--

CREATE TABLE IF NOT EXISTS `INVESTMENT_ACCOUNTS` (
  `investmentAccountID` bigint(20) unsigned NOT NULL,
  `clientID` varchar(32) COLLATE utf8_bin NOT NULL,
  `accountID` varchar(32) COLLATE utf8_bin NOT NULL,
  `buyStockageFee` bigint(20) unsigned NOT NULL,
  `sellStockageFee` bigint(20) unsigned NOT NULL,
  `transactionHistory` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`investmentAccountID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Brokerage: Investment accounts';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `LIQUIDATION_FEE`
--

CREATE TABLE IF NOT EXISTS `LIQUIDATION_FEE` (
  `liquidation_fee_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`liquidation_fee_id`),
  KEY `account_number` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `LOANS`
--

CREATE TABLE IF NOT EXISTS `LOANS` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `payment_period` varchar(64) COLLATE utf8_bin NOT NULL,
  `interest` double NOT NULL,
  `initial_capital` double NOT NULL,
  `amortization_time` int(11) NOT NULL,
  `debt` double NOT NULL,
  `strategy_loan` varchar(64) COLLATE utf8_bin NOT NULL,
  `amortized` double NOT NULL,
  `fees` int(11) NOT NULL,
  `account_number` varchar(64) COLLATE utf8_bin NOT NULL,
  `client_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `period_fee` double NOT NULL,
  `creating_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(512) COLLATE utf8_bin NOT NULL,
  `rate_interest` double DEFAULT NULL,
  `recalc_interest` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `last_interest` double DEFAULT NULL,
  `discriminator` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_number` (`account_number`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `OFFICES`
--

CREATE TABLE IF NOT EXISTS `OFFICES` (
  `office_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `bank_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `local_cost` int(11) NOT NULL,
  `utlities_cost` int(11) NOT NULL,
  `employe_cost` int(11) NOT NULL,
  `total_expenses` int(11) NOT NULL,
  `total_income` int(11) NOT NULL,
  `balance` int(11) NOT NULL,
  `account_number` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`office_id`),
  KEY `account_number` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PACK`
--

CREATE TABLE IF NOT EXISTS `PACK` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `account_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `amount` bigint(20) NOT NULL,
  `price` double DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `discriminartor` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`,`account_number`),
  KEY `account_number` (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `SCHEDULEDPAYMENTS`
--

CREATE TABLE IF NOT EXISTS `SCHEDULEDPAYMENTS` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `id_loan` varchar(64) COLLATE utf8_bin NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expiration_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `amount_fee` double NOT NULL,
  `amortization` double NOT NULL,
  `interests` double NOT NULL,
  `outstanding_capital` double NOT NULL,
  `is_paid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_loan` (`id_loan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TRANSACTIONS`
--

CREATE TABLE IF NOT EXISTS `TRANSACTIONS` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `amount` double NOT NULL,
  `subject` varchar(64) COLLATE utf8_bin NOT NULL,
  `effective_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `extra_information` varchar(64) COLLATE utf8_bin NOT NULL,
  `direct_debit_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `related` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `sender_account` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `pack` int(11) DEFAULT NULL,
  `operator` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `discriminator` varchar(20) COLLATE utf8_bin NOT NULL ,
  PRIMARY KEY (`id`),
  KEY `pack` (`pack`),
  KEY `direct_debit_id` (`direct_debit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ACCOUNTS`
--
ALTER TABLE `ACCOUNTS`
  ADD CONSTRAINT `ACCOUNTS_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `ACCOUNTS_AUTHORIZEDS`
--
ALTER TABLE `ACCOUNTS_AUTHORIZEDS`
  ADD CONSTRAINT `fk_Account` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ClientId` FOREIGN KEY (`client_id`) REFERENCES `CLIENTS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `ACCOUNTS_CLIENTS`
--
ALTER TABLE `ACCOUNTS_CLIENTS`
  ADD CONSTRAINT `ACCOUNTS_CLIENTS_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `CLIENTS` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ACCOUNTS_CLIENTS_ibfk_2` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `BUYABLE`
--
ALTER TABLE `BUYABLE`
  ADD CONSTRAINT `BUYABLE_ibfk_3` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `BUYABLE_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `EMPLOYEE` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `BUYABLE_ibfk_2` FOREIGN KEY (`fee_id`) REFERENCES `FEES` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `CARDS`
--
ALTER TABLE `CARDS`
  ADD CONSTRAINT `CARDS_ibfk_3` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `CARDS_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE,
  ADD CONSTRAINT `CARDS_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `CLIENTS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `CLIENTS`
--
ALTER TABLE `CLIENTS`
  ADD CONSTRAINT `CLIENTS_ibfk_1` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `DIRECT_DEBIT`
--
ALTER TABLE `DIRECT_DEBIT`
  ADD CONSTRAINT `DIRECT_DEBIT_ibfk_2` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `DIRECT_DEBIT_ibfk_1` FOREIGN KEY (`account_direct_debit_id`) REFERENCES `ACCOUNT_DIRECT_DEBITS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `EMPLOYEE`
--
ALTER TABLE `EMPLOYEE`
  ADD CONSTRAINT `EMPLOYEE_ibfk_2` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `EMPLOYEE_ibfk_1` FOREIGN KEY (`office_id`) REFERENCES `OFFICES` (`office_id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `FEES`
--
ALTER TABLE `FEES`
  ADD CONSTRAINT `FEES_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `CLIENTS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `FEE_CASES`
--
ALTER TABLE `FEE_CASES`
  ADD CONSTRAINT `FEE_CASES_ibfk_1` FOREIGN KEY (`liquidation_fee_id`) REFERENCES `LIQUIDATION_FEE` (`liquidation_fee_id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `HISTORY`
--
ALTER TABLE `HISTORY`
  ADD CONSTRAINT `HISTORY_ibfk_1` FOREIGN KEY (`history_id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `HISTORY_TRANSACTIONS`
--
ALTER TABLE `HISTORY_TRANSACTIONS`
  ADD CONSTRAINT `HISTORY_TRANSACTIONS_ibfk_1` FOREIGN KEY (`history_id`) REFERENCES `HISTORY` (`history_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `HISTORY_TRANSACTIONS_ibfk_2` FOREIGN KEY (`transaction_id`) REFERENCES `TRANSACTIONS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `LIQUIDATION_FEE`
--
ALTER TABLE `LIQUIDATION_FEE`
  ADD CONSTRAINT `LIQUIDATION_FEE_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `LOANS`
--
ALTER TABLE `LOANS`
  ADD CONSTRAINT `LOANS_ibfk_3` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `LOANS_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE,
  ADD CONSTRAINT `LOANS_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `CLIENTS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `OFFICES`
--
ALTER TABLE `OFFICES`
  ADD CONSTRAINT `OFFICES_ibfk_2` FOREIGN KEY (`office_id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `OFFICES_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `PACK`
--
ALTER TABLE `PACK`
  ADD CONSTRAINT `PACK_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `BUYABLE` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `PACK_ibfk_2` FOREIGN KEY (`account_number`) REFERENCES `ACCOUNTS` (`account_number`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `SCHEDULEDPAYMENTS`
--
ALTER TABLE `SCHEDULEDPAYMENTS`
  ADD CONSTRAINT `SCHEDULEDPAYMENTS_ibfk_2` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `SCHEDULEDPAYMENTS_ibfk_1` FOREIGN KEY (`id_loan`) REFERENCES `LOANS` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `TRANSACTIONS`
--
ALTER TABLE `TRANSACTIONS`
  ADD CONSTRAINT `TRANSACTIONS_ibfk_3` FOREIGN KEY (`id`) REFERENCES `GENERIC_HANDLER` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `TRANSACTIONS_ibfk_1` FOREIGN KEY (`pack`) REFERENCES `PACK` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `TRANSACTIONS_ibfk_2` FOREIGN KEY (`direct_debit_id`) REFERENCES `DIRECT_DEBIT` (`id`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

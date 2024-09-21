-- MySQL Workbench Synchronization
-- Generated: 2024-09-20 18:38
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Renan

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `SPO_MONOLITO_DATABASE` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `SPO_MONOLITO_DATABASE`.`Produto` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `valor` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `SPO_MONOLITO_DATABASE`.`Pedido` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dataHora` DATETIME NOT NULL,
  `status` INT(1) NOT NULL,
  `identificadorPagamentoExterno` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `SPO_MONOLITO_DATABASE`.`Item` (
  `pedidoId` BIGINT(20) NOT NULL,
  `produtoId` BIGINT(20) NOT NULL,
  `quantidade` INT(11) NOT NULL,
  INDEX `fk_Item_Pedido_idx` (`pedidoId` ASC) VISIBLE,
  INDEX `fk_Item_Produto1_idx` (`produtoId` ASC) VISIBLE,
  PRIMARY KEY (`pedidoId`, `produtoId`),
  CONSTRAINT `fk_Item_Pedido`
    FOREIGN KEY (`pedidoId`)
    REFERENCES `SPO_MONOLITO_DATABASE`.`Pedido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Item_Produto1`
    FOREIGN KEY (`produtoId`)
    REFERENCES `SPO_MONOLITO_DATABASE`.`Produto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `SPO_MONOLITO_DATABASE`.`Estoque` (
  `produtoId` BIGINT(20) NOT NULL,
  `quantidadeDisponivel` INT(11) NOT NULL,
  `quantidadeReservada` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`produtoId`),
  CONSTRAINT `fk_Estoque_Produto1`
    FOREIGN KEY (`produtoId`)
    REFERENCES `SPO_MONOLITO_DATABASE`.`Produto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

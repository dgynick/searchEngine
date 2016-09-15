CREATE TABLE `121Crawler`.`Website` (
  `Id` INT NOT NULL AUTO_INCREMENT COMMENT '	',
  `Url` VARCHAR(2048) NOT NULL,
  `Noramlized_Url` VARCHAR(2058) NULL,
  `Html_Content` MEDIUMTEXT NULL,
  `Text_Content` MEDIUMTEXT NULL,
  `Outgoing_Links` MEDIUMTEXT NULL,
  `Visited_Normalized` INT NULL,
  `Visited` INT NULL,
  PRIMARY KEY (`Id`));


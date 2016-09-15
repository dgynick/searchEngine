CREATE TABLE `121Crawler`.`FinalTfIdf` (
  `id`     INT         NOT NULL AUTO_INCREMENT,
  `term`   VARCHAR(50) NOT NULL,
  `docid`  INT         NOT NULL,
  `suffix` VARCHAR(50) NOT NULL,
  `termid` INT         NOT NULL,
  `score`  DOUBLE      NULL,
  PRIMARY KEY (`term`, `docid`, `suffix`, `termid`),
  INDEX `fk_FinalTfIdf_docid_idx` (`docid` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_FinalTfIdf_docid`
  FOREIGN KEY (`docid`)
  REFERENCES `121Crawler`.`Website` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
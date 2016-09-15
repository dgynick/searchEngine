CREATE TABLE if not exists 121Crawler.TfIdf (
  id INT NOT NULL AUTO_INCREMENT,
  termid INT NOT NULL,
  docid INT NOT NULL,
  score DOUBLE NOT NULL,
  PRIMARY KEY (id, termid, docid, score),
  INDEX fk_termid_idx (termid ASC),
  INDEX fk_docid_idx (docid ASC),
  CONSTRAINT fk_termid
    FOREIGN KEY (termid)
    REFERENCES 121Crawler.Terms (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_docid
    FOREIGN KEY (docid)
    REFERENCES 121Crawler.Website (Id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

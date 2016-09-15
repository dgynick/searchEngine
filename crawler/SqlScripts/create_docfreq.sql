CREATE TABLE IF NOT EXISTS 121Crawler.DocFreq (
  id INT NOT NULL AUTO_INCREMENT,
  termid INT NOT NULL UNIQUE,
  doc_freq INT NOT NULL,
  PRIMARY KEY (id, termid, doc_freq),
  INDEX fk_termid_idx (termid ASC),
  CONSTRAINT fk_termid_docfreq
    FOREIGN KEY (termid)
    REFERENCES 121Crawler.Terms (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
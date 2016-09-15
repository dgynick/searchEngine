CREATE TABLE if not exists  121Crawler.ParsedDoc (
  id INT NOT NULL AUTO_INCREMENT,
  doc_id INT NOT NULL UNIQUE,
  PRIMARY KEY (id, doc_id));

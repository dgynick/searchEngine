CREATE TABLE length (
  docid  INTEGER,
  length DOUBLE
);
INSERT INTO length
  SELECT
    docid,
    sqrt(sum(score * score))
  FROM 121Crawler.FinalTfIdf_complete
  GROUP BY docid;
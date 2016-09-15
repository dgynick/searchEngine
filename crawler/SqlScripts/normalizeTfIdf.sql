DROP TABLE IF EXISTS FinalTfIdf_normalized;
CREATE TABLE FinalTfIdf_normalized (
  docid INTEGER,
  term nvarchar(50),
  score DOUBLE
);

INSERT INTO FinalTfIdf_normalized
  SELECT
    F.docid,
    F.term,
    F.score / L.length
  FROM FinalTfIdf_complete F, length L
  WHERE L.docid = F.docid;
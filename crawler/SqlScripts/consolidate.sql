drop table if exists FinalTfIdf_complete;
create table FinalTfIdf_complete(term nvarchar(50), docid Integer, score double) ;
insert into FinalTfIdf_complete
SELECT term,docid,sum(score) FROM 121Crawler.FinalTfIdf group by term,docid limit 1000000000;
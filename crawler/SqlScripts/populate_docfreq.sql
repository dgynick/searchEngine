insert into DocFreq(termid,doc_freq)
select termid, count(*) from IndexVector group by termid;
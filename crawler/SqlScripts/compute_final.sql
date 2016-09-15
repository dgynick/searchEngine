create table Terms(termid Integer auto_increment, term text, primary key(termid));
insert into Terms(term) (select term from terms_body) union (select term from terms_title) union
(select term from terms_head) union (select term from terms_coretext);
alter table Terms engine = innoDB;

create table term_body2(termid Integer, term text, finalId Integer, primary key(termid));
alter table terms_body add(finalId Integer);
alter table terms_body add foreign key(finalId ) references Terms(termid);
update terms_body T set finalid = (select termid from Terms TT where TT.term=T.term) where T.id >-1;

alter table terms_head add(finalId Integer);
alter table terms_head add foreign key(finalId ) references Terms(termid);
create table temp(termid Integer, term text);
insert into temp
select * from Terms where term in (select term from terms_head);
update terms_head T set finalid =
(select termid from temp TT where TT.term=T.term) where T.id >-1;

alter table terms_title add(finalId Integer);
alter table terms_title add foreign key(finalId ) references Terms(termid);
drop table temp;
create table temp(termid Integer, term text);
insert into temp
select * from Terms where term in (select term from terms_title);
update terms_title T set finalid =
(select termid from temp TT where TT.term=T.term) where T.id >-1;

create table temp2(termid Integer, finalId Integer, term text);
insert into temp2
select TT.id as termid, T.termid, T.term  as finalId from terms T,terms_coretext TT where T.term=TT.term;

create table InterResult(finalId Integer, docid Integer, score double, part Integer);
insert into InterResult
select B.finalId,A.docid,A.score*0.3, 1 from tfidf_body A, terms_body B where A.termid=B.id;
insert into InterResult
select B.finalId,A.docid,A.score*0.3, 2 from tfidf_coretext A, temp2 B where A.termid=B.termid;
insert into InterResult
select B.finalId,A.docid,A.score*0.1, 3 from tfidf_head A, terms_head B where A.termid=B.id;
insert into InterResult
select B.finalId,A.docid,A.score*0.3, 4 from tfidf_title A, terms_title B where A.termid=B.id;

create table finalResult(finalId Integer, docid Integer, score double);
insert into finalResult
select finalId, docid,sum(score) from InterResult group by (finalId,docid);



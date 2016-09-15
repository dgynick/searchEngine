select I.termid, I.docid, (1+log(I.freq))*log(42498/D.doc_freq)
from DocFreq D, IndexVector I where D.termid=I.termid;
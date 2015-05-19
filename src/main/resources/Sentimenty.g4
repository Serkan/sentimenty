grammar Sentimenty;
review : sentence+ ;
sentence : senti_pattern+ SEPERATOR+
         | EOF
         ;
senti_pattern : MODIFIER+ MODIFIED #MultMod
             | MODIFIER CONJ MODIFIER #Conj_NoNoun
             | MODIFIER CONJ senti_pattern #Conj_Pattern
             ;

/*ADVERB : WORD'/RB' | WORD'/RBS' | WORD'/RBR' ; */
MODIFIER : WORD'/'ADJ_TAG ;
MODIFIED : WORD'/'NOUN_TAG ;

CONJ : WORD'/CC' | ',' ;
ADJ_TAG : 'JJ' | 'JJR' | 'JJS' ;
NOUN_TAG : 'NN' | 'NNS' | 'NNP' | 'NNPS' ;

WORD :  [A-z]+ ;
WS : [ \t\r\n]+ -> skip ;
OTHER : WORD'/'WORD+'$'* -> skip ;
SEPERATOR : './.' | '!/.' ;
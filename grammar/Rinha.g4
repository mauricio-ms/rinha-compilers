grammar Rinha;

compilationUnit: statement* ;

statement: print ;

print: 'print' '(' term ')';

term: STRING;

STRING : '"' .*? '"' ;
WS : [ \t\n\r]+ -> skip;
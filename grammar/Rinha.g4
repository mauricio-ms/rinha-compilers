grammar Rinha;

@header {
  package antlr;
}

compilationUnit: statement* ;

variableDeclaration
    : 'let' assignable '=' singleExpression
    ;

singleExpression
    : literal
    | ID
    | STRING
    ;

literal
    : numericLiteral
    ;

numericLiteral
    : INT
    ;

assignable
    : ID
    ;

statement
    : variableDeclaration
    | print
    ;

print: 'print' '(' singleExpression ')' ;

ID: LETTER (LETTER|DIGIT)* ;

INT: DIGIT+ ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' | '_' ;
fragment
    DIGIT: '0'..'9' ;

STRING : '"' .*? '"' ;
WS : [ \t\n\r]+ -> skip ;
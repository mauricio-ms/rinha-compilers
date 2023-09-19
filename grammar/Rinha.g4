grammar Rinha;

@header {
  package antlr;
}

compilationUnit: statement* ;

statement
    : variableDeclaration
    | functionDeclaration
    | print
    | singleExpression
    ;

variableDeclaration
    : 'let' assignable '=' singleExpression
    ;

assignable
    : ID
    ;

functionDeclaration
    : 'let' ID '=' 'fn' '(' formalParameterList? ')' '=' '>' '{' statement* '}' ';'? ;

formalParameterList
    : ID (',' ID)*
    ;

singleExpression
    : functionCall
    | ifStatement
    | literal
    | ID
    ;

functionCall
    : ID '(' singleExpressionList? ')'
    ;

singleExpressionList
    : singleExpression (',' singleExpression)*
    ;

literal
    : INT
    | BOOL
    | STRING
    ;

ifStatement
    : 'if' '(' singleExpression ')' '{' statement* '}' ('else' '{' statement* '}')?
    ;

print: 'print' '(' singleExpression ')' ;

INT: DIGIT+ ;
BOOL: 'true' | 'false' ;

ID: LETTER (LETTER|DIGIT)* ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' | '_' ;
fragment
    DIGIT: '0'..'9' ;

STRING : '"' .*? '"' ;
WS : [ \t\n\r]+ -> skip ;
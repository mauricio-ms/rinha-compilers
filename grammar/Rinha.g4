grammar Rinha;

@header {
  package antlr;
}

compilationUnit: statement* ;

statement
    : block
    | variableDeclaration
    | functionDeclaration
    | print
    | singleExpression
    ;

block : '{' statement* '}' ;

variableDeclaration
    : 'let' assignable '=' singleExpression
    ;

assignable
    : ID
    ;

functionDeclaration
    : 'let' ID '=' 'fn' '(' formalParameterList? ')' '=' '>' block ';'? ;

formalParameterList
    : ID (',' ID)*
    ;

singleExpression
    : singleExpression bop=('*' | '/') singleExpression
    | singleExpression bop=('+' | '-') singleExpression
    | functionCall
    | ifStatement
    | literal
    | id
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

id: ID ;

ifStatement
    : 'if' '(' singleExpression ')' block (ELSE block)?
    ;

ELSE: 'else' ;

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
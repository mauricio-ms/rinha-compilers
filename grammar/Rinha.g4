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

block : '{' statement* '}' eos ;

variableDeclaration
    : 'let' assignable '=' singleExpression eos
    ;

assignable
    : ID
    ;

functionDeclaration
    : 'let' ID '=' 'fn' '(' formalParameterList? ')' '=' '>' block eos ;

formalParameterList
    : ID (',' ID)*
    ;

print: 'print' '(' singleExpression ')' eos ;

eos
    : ';'?
    ;

singleExpression
    : singleExpression bop=('*' | '/' | '%') singleExpression
    | singleExpression bop=('+' | '-') singleExpression
    | singleExpression bop=('<=' | '>=' | '<' | '>') singleExpression
    | singleExpression bop=('==' | '!=') singleExpression
    | singleExpression bop='&&' singleExpression
    | singleExpression bop='||' singleExpression
    | functionCall
    | ifStatement
    | tuple
    | literal
    | id
    ;

functionCall
    : ID '(' singleExpressionList? ')' eos
    ;

singleExpressionList
    : singleExpression (',' singleExpression)*
    ;

tuple : '(' singleExpression ',' singleExpression ')' ;

literal
    : INT
    | BOOL
    | STRING
    ;

id: ID ;

ifStatement
    : 'if' '(' singleExpression ')' block (ELSE block)? eos
    ;

ELSE: 'else' ;

INT: DIGIT+ ;
BOOL: 'true' | 'false' ;

ID: LETTER (LETTER|DIGIT)* ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' | '_' ;
fragment
    DIGIT: '0'..'9' ;

STRING : '"' .*? '"' ;
WS : [ \t\n\r]+ -> skip ;
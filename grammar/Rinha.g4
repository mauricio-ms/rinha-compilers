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

functionDeclaration
    : 'let' ID '=' 'fn' '(' formalParameterList? ')' '=' '>' '{' statement* '}' ';'? ;

formalParameterList
    : ID (',' ID)*
    ;

singleExpression
    : functionCall
    | literal
    | ID
    | STRING
    ;

functionCall
    : ID '(' singleExpressionList? ')'
    ;

singleExpressionList
    : singleExpression (',' singleExpression)*
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

print: 'print' '(' singleExpression ')' ;

ID: LETTER (LETTER|DIGIT)* ;

INT: DIGIT+ ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' | '_' ;
fragment
    DIGIT: '0'..'9' ;

STRING : '"' .*? '"' ;
WS : [ \t\n\r]+ -> skip ;
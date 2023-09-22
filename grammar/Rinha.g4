grammar Rinha;

@header {
  package antlr;
}

compilationUnit: statement* ;

statement
    : block
    | functionDeclaration
    | variableDeclaration
    | assignment
    | singleExpression
    ;

block
    : '{' statement* '}' eos ;

functionDeclaration
    : 'let' ID '=' functionDefinition eos ;

variableDeclaration
    : 'let' ID '=' singleExpression eos ;

formalParameterList
    : ID (',' ID)* ;

assignment
    : ID '=' singleExpression eos ;

eos: ';'? ;

singleExpression
    : singleExpression bop=('*' | '/' | '%') singleExpression
    | singleExpression bop=('+' | '-') singleExpression
    | singleExpression bop=('<=' | '>=' | '<' | '>') singleExpression
    | singleExpression bop=('==' | '!=') singleExpression
    | singleExpression bop='&&' singleExpression
    | singleExpression bop='||' singleExpression
    | functionDefinition
    | functionCall
    | ifStatement
    | print
    | first
    | second
    | tuple
    | literal
    | id
    ;

functionDefinition
    : 'fn' '(' formalParameterList? ')' '=' '>' block ;

functionCall
    : ID '(' singleExpressionList? ')' eos ;

singleExpressionList
    : singleExpression (',' singleExpression)* ;

tuple
    : '(' singleExpression ',' singleExpression ')' ;

literal
    : INT
    | BOOL
    | STRING
    ;

id: ID ;

ifStatement
    : 'if' '(' singleExpression ')' block (ELSE block)? eos ;

print
    : 'print' '(' singleExpression ')' eos ;

first
    : 'first' '(' singleExpression ')' ;

second
    : 'second' '(' singleExpression ')' ;

ELSE: 'else' ;

INT: DIGIT+ ;
BOOL: 'true' | 'false' ;

ID: LETTER (LETTER|DIGIT)* ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' | '_' ;
fragment
    DIGIT: '0'..'9' ;

STRING: '"' (ESC | ~["\\])* '"' ;
fragment ESC : '\\' ["\\/bfnrt] ;
WS: [ \t\n\r]+ -> skip ;
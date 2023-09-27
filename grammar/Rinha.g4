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
    | term
    ;

block
    : '{' statement* '}' eos ;

functionDeclaration
    : 'let' ID '=' functionDefinition eos ;

variableDeclaration
    : 'let' ID '=' term eos ;

formalParameterList
    : ID (',' ID)* ;

assignment
    : ID '=' term eos ;

eos: ';'? ;

term
    : term '(' termList? ')'
    | uop=('+' | '-') term
    | term bop=('*' | '/' | '%') term
    | term bop=('+' | '-') term
    | term bop=('<=' | '>=' | '<' | '>') term
    | term bop=('==' | '!=') term
    | term bop='&&' term
    | term bop='||' term
    | functionDefinition
    | ifStatement
    | print
    | first
    | second
    | tuple
    | literal
    | id
    | '(' term ')'
    ;

termList
    : term (',' term)* ;

functionDefinition
    : 'fn' '(' formalParameterList? ')' '=' '>' block ;

tuple
    : '(' term ',' term ')' ;

literal
    : INT
    | BOOL
    | STRING
    ;

id: ID ;

ifStatement
    : 'if' '(' term ')' block (ELSE block)? eos ;

print
    : 'print' '(' term ')' eos ;

first
    : 'first' '(' term ')' ;

second
    : 'second' '(' term ')' ;

ELSE: 'else' ;

INT: DIGIT+ ;
BOOL: 'true' | 'false' ;

ID
    : '_'
    | LETTER (LETTER|DIGIT|'_')* ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' ;
fragment
    DIGIT: '0'..'9' ;

STRING: '"' (ESC | ~["\\])* '"' ;
fragment ESC : '\\' ["\\/bfnrt] ;
WS: [ \t\n\r]+ -> skip ;
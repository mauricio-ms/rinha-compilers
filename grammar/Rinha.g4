grammar Rinha;

compilationUnit: (term eos | NL)* ;

eos
    : ';'
    | NL
    | EOF
    ;

term
    : term '(' termList ')'
    | uop=('+' | '-') term
    | term bop=('*' | '/' | '%') term
    | term bop=('+' | '-') term
    | term bop=('<=' | '>=' | '<' | '>') term
    | term bop=('==' | '!=') term
    | term bop='&&' term
    | term bop='||' term
    | let
    | functionDefinition
    | if
    | print
    | first
    | second
    | tuple
    | literal
    | id
    | '(' term ')'
    ;

termList
    : termListOp? ;
termListOp
    : term (',' term)*
    ;

let : 'let' ID '=' term eos NL* term ;

functionDefinition
    : 'fn' '(' formalParameterList? ')' '=' '>' block ;
formalParameterList
    : ID (',' ID)* ;
block
    : '{' (term eos)* '}' ;

if : 'if' '(' term ')' then 'else' otherwise ;
then : '{' term eos? '}' ;
otherwise : '{' term eos? '}' ;

print
    : 'print' '(' term ')' ;

first
    : 'first' '(' term ')' ;

second
    : 'second' '(' term ')' ;

tuple
    : '(' term ',' term ')' ;

literal
    : INT
    | BOOL
    | STRING
    ;

id: ID ;

INT: DIGIT+ ;
BOOL: 'true' | 'false' ;

ID
    : '_'
    | LETTER (LETTER|DIGIT|'_')* ;

fragment
    LETTER: 'a'..'z' | 'A'..'Z' ;
fragment
    DIGIT: '0'..'9' ;

NL : '\r'? '\n' ;

STRING: '"' (ESC | ~["\\])* '"' ;
fragment ESC : '\\' ["\\/bfnrt] ;
WS: [ \t\n\r]+ -> skip ;
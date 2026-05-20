grammar CvDsl;


cv_document  : T_START import_stmt* config_block? section+ T_END EOF ;

import_stmt  : T_IMPORT T_STRING ;

config_block : T_CONFIG T_LBRACE pair* T_RBRACE ;

section      : T_SECTION T_LABEL T_LBRACE content* T_RBRACE ;

content      : pair
             | list_field
             | object_list
             | bullet_list
             ;

pair         : T_KEY value ;

list_field   : T_KEY T_LSQUARE ( value (T_COMMA value)* T_COMMA? )? T_RSQUARE ;

bullet_list  : T_KEY (T_DASH value)+ ;

object_list  : T_KEY T_LSQUARE ( object_block (T_COMMA object_block)* T_COMMA? )? T_RSQUARE ;

object_block : T_LBRACE content* T_RBRACE ;

value        : T_STRING
             | T_MULTILINE
             | T_DATE
             | T_PRESENT
             | T_URL
             | T_EMAIL
             | T_PHONE
             | T_NUMBER
             | T_BOOLEAN
             ;



T_START   : 'CV_START' ;
T_END     : 'CV_END'   ;
T_CONFIG  : 'CONFIG'   ;
T_SECTION : 'SECTION'  ;
T_IMPORT  : 'IMPORT'   ;

T_PRESENT : 'PRESENT' | 'NOW' ;

T_BOOLEAN : 'TRUE' | 'FALSE' ;


T_LBRACE  : '{' ;
T_RBRACE  : '}' ;
T_LSQUARE : '[' ;
T_RSQUARE : ']' ;
T_COMMA   : ',' ;
T_DASH    : '-' ;


T_MULTILINE
    : '"""' .*? '"""'
    ;


T_STRING
    : '"' ( ~["\r\n\\] | '\\' . )* '"'
    ;


T_URL
    : 'http' 's'? '://' ~[ \t\r\n"']+
    ;


T_DATE
    : [0-9][0-9][0-9][0-9] '-' [0-9][0-9]
    ;


T_EMAIL
    : [a-zA-Z0-9._%+\-]+ '@' [a-zA-Z0-9.\-]+ '.' [a-zA-Z][a-zA-Z]+
    ;


T_PHONE
    : '+'? [0-9] PHONE_TAIL
    ;

fragment PHONE_TAIL
    : [0-9 \-][0-9 \-][0-9 \-][0-9 \-][0-9 \-][0-9 \-][0-9 \-][0-9 \-]
      ( [0-9 \-]
        ( [0-9 \-]
          ( [0-9 \-]
            ( [0-9 \-]
              ( [0-9 \-]
                ( [0-9 \-] )?
              )?
            )?
          )?
        )?
      )?
    ;


T_NUMBER
    : [0-9]+
    ;


T_KEY
    : [A-Z_]+ ':'
    ;

T_LABEL
    : [a-zA-Z] ( [a-zA-Z0-9_]* [a-zA-Z0-9] )?
    ;

T_COMMENT
    : '#' ~[\r\n]* -> skip
    ;


T_BLOCK_COMM
    : '/*' .*? '*/' -> skip
    ;


WS
    : [ \t\r\n]+ -> skip
    ;
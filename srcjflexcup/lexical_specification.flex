// Circuit1.flex
//         ../jflex-1.8.2/bin/jflex -d src srcjflexcup/lexical_specification.flex
// CS2A Language Processing
//
// Description of lexer for circuit description language.
//
// Ian Stark
package main.java.compiler;
import java_cup.runtime.Symbol; //This is how we pass tokens to the parser

%%
%public
// Declarations for JFlex
%unicode // We wish to read text files
%cup // Declare that we expect to use Java CUP
%class Lexer
// Abbreviations for regular expressions

whitespace = [ \r\n\t\f]
digit = [0-9]
number = {digit}+
value1 = {number}("."{number})?
%%

"-"          { return new Symbol(sym.MINUS); }    // Token MINUS
"+"          { return new Symbol(sym.PLUS); }     // Token PLUS
"*"          { return new Symbol(sym.TIMES); }    // Token TIMES
"*"          { return new Symbol(sym.TIMES); }    // Token TIMES
"("          { return new Symbol(sym.LPAREN); }   // Token LPAREN
")"          { return new Symbol(sym.RPAREN); }   // Token RPAREN
";"          { return new Symbol(sym.SEMI); }     // Token SEMI

{number}        { return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }     // Token NUMBER
{value1}        { return new Symbol(sym.NUMBER, Integer.parseInt(yytext())); }     // Token value

{whitespace} {}
[^] {throw new Error("lol");}

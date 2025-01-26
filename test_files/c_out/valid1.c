#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define BUFFER_SIZE  1024*4

char* buffer;
char* string_concat(char* s1, char* s2)
{
    char* ns = malloc(strlen(s1) + strlen(s2) + 1);
    strcpy(ns, s1);
    strcat(ns, s2);
    return ns;
}

char* int2str(int n)
{
    char buffer[BUFFER_SIZE];
    int len = sprintf(buffer,"%d",n);
    char *ns = malloc(len + 1);
    sprintf(ns,"%d",n);
    return ns;
}

char* char2str(char c)
{
    char *ns = malloc(2);
    sprintf(ns, "%c", c);
    return ns;
}

    char* float2str(float f)
    {
        char buffer[BUFFER_SIZE];
        int len = sprintf(buffer,"%f", f);
        char *ns = malloc(len + 1);
        sprintf(ns, "%f", f);
        return ns;
    }

char* bool2str(int b)
{
    char* ns = NULL;
    if(b)
    {
        ns = malloc(5);
        strcpy(ns, "true");
    }
    else
    {
        ns = malloc(6);
        strcpy(ns, "false");
    }
    return ns;
}



void sommac_fun(double a,double d,double b,char* *size,double *result);

char* stampa_fun(char* messaggio);

int c = 1;
double a,b,x;
char* taglia,*ans1,*ans;
double risultato;
void sommac_fun(double a,double d,double b,char* *size,double *result){*result = a + b + c + d;
if(*result > 100){*size = "grande";
}else{if(*result > 50){*size = "media";
}else{*size = "piccola";
}}}
char* stampa_fun(char* messaggio){int i = 0;while(i < 4){
{printf("%s\n","");i = i + 1;
}
}
printf("%s\n",messaggio);return "ok";}
int main() {
a = 1;

b = 2.2;

x = 3;

risultato = 0.0;

ans = "no";

sommac_fun(a, x, b, &taglia, &risultato)
;
stampa_fun(string_concat(string_concat(string_concat(string_concat(string_concat(string_concat(string_concat("La somma di ", float2str(a)), " e "), float2str(b)), " incrementata di "), int2str(c)), " è "), taglia))
;
stampa_fun(string_concat("Ed è pari a ", float2str(risultato)))
;
printf("%s","Vuoi continuare? (si/no) - inserisci due volte la risposta\n");
buffer = (char*) malloc((1024*5)*sizeof(char) );
scanf("%[^\n]", buffer);
ans= (char*) malloc( (strlen(buffer) + 1) *sizeof(char) );
strcpy(ans,buffer);
 free(buffer);
getchar();
buffer = (char*) malloc((1024*5)*sizeof(char) );
scanf("%[^\n]", buffer);
ans1= (char*) malloc( (strlen(buffer) + 1) *sizeof(char) );
strcpy(ans1,buffer);
 free(buffer);
getchar();
for(int i = 3.12;i<=30;i++){buffer = (char*) malloc((1024*5)*sizeof(char) );
scanf("%[^\n]", buffer);
ans= (char*) malloc( (strlen(buffer) + 1) *sizeof(char) );
strcpy(ans,buffer);
 free(buffer);
getchar();}
while(strcmp( "si", ans) == 0){
{printf("%s","Inserisci un intero: ");scanf("%lf", &a);getchar();printf("%s","Inserisci un reale: ");scanf("%lf", &b);getchar();sommac_fun(a, x, b, &taglia, &risultato);
stampa_fun(string_concat(string_concat(string_concat(string_concat(string_concat(string_concat(string_concat("La somma di ", float2str(a)), " e "), float2str(b)), " incrementata di "), int2str(c)), " è "), taglia));
stampa_fun(string_concat("Ed è pari a ", float2str(risultato)));
printf("%s","Vuoi continuare? (si/no): ");buffer = (char*) malloc((1024*5)*sizeof(char) );
scanf("%[^\n]", buffer);
ans= (char*) malloc( (strlen(buffer) + 1) *sizeof(char) );
strcpy(ans,buffer);
 free(buffer);
getchar();}
}

printf("%s\n","");
printf("%s\n","Ciao");
    // Main content here
}

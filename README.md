
# Toy3 Compiler

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Descrizione

**Toy3 Compiler** è un compilatore didattico per il linguaggio **Toy3**, progettato per facilitare l’apprendimento dei principi fondamentali dei compilatori. Il compilatore traduce programmi scritti in Toy3 in codice C standard, compilabile con GCC o Clang, permettendo di generare eseguibili nativi.

Il progetto è sviluppato in Java ed è basato su strumenti consolidati come **JFlex** (per l’analisi lessicale) e **JavaCup** (per l’analisi sintattica), integrando tecniche di analisi semantica e generazione di codice tramite pattern Visitor.

---

## Funzionalità principali

- **Analisi Lessicale**: il lexer trasforma il codice sorgente in token significativi.
- **Analisi Sintattica**: il parser costruisce un albero sintattico astratto (AST).
- **Analisi Semantica**: gestione dello scope, controllo dei tipi, inferenza e verifica dei parametri.
- **Generazione di codice C**: l’AST viene tradotto in codice C leggibile e compilabile.
- Supporto per costrutti classici: funzioni, variabili, riferimenti, comandi di controllo (if, while), blocchi.

---

## Struttura del progetto

```
srcjflexcup/
  ├── lexical_specification_toy3.flex   # Definizione lexer JFlex
  ├── grammar_specification_toy3.cup    # Definizione parser JavaCup
src/
  └── main/                             # Codice Java del compilatore
tests/
  └── valid1/valid1.txt                 # File di test Toy3
  └── valid2/valid2.txt
  └── valid3/valid3.txt
  └── valid4/valid4.txt
test_files/
  └── c_out/                           # Codice C generato
pom.xml                              # Configurazione Maven
```

---

## Prerequisiti

- Java JDK 11+  
- Maven 3.6+  
- GCC/Clang (per compilare codice C generato, opzionale)  

Assicurati che `java` e `mvn` siano nel tuo PATH.

---

## Installazione e compilazione

Clona il repository:

```bash
git clone https://github.com/tuonome/toy3_compiler.git
cd toy3_compiler
```

Per compilare il progetto Java:

```bash
mvn compile
```

---

## Esecuzione

Per compilare un singolo file Toy3 e generare il corrispondente codice C:

```bash
mvn exec:java -Dexec.mainClass="main.Main" -Dexec.args="tests/valid1/valid1.txt"
```

Il file `.c` risultante sarà salvato in `test_files/c_out`.

---

## Script per compilare più test

Per compilare automaticamente i test `valid1`, `valid2`, `valid3` e `valid4`, usa lo script bash:

```bash
#!/bin/bash

OUTPUT_DIR="test_files/c_out"
mkdir -p "$OUTPUT_DIR"

TESTS=(
  "tests/valid1/valid1.txt"
  "tests/valid2/valid2.txt"
  "tests/valid3/valid3.txt"
  "tests/valid4/valid4.txt"
)

mvn compile || { echo "Errore compilazione!"; exit 1; }

for testfile in "${TESTS[@]}"
do
  echo "Compilando $testfile..."
  mvn exec:java -Dexec.mainClass="main.Main" -Dexec.args="$testfile" || echo "Errore su $testfile"
done

echo "Generazione completata."
```

Salva come `compile_tests.sh`, rendi eseguibile (`chmod +x compile_tests.sh`) ed esegui con `./compile_tests.sh`.

---

## Come funziona internamente

### 1. Analisi Lessicale

Il lexer è generato con **JFlex** a partire dal file `lexical_specification_toy3.flex`. Esso trasforma il testo sorgente in token (parole chiave, identificatori, numeri, operatori, separatori).

### 2. Analisi Sintattica

Il parser è generato con **JavaCup** usando `grammar_specification_toy3.cup`. Produce un albero sintattico astratto (AST) rappresentante la struttura grammaticale del programma.

### 3. Analisi Semantica

Attraverso l’uso di **visitor** l’AST viene attraversato per:

- Gestire gli ambienti di scope e le tabelle dei simboli.
- Verificare dichiarazioni e usi delle variabili.
- Inferire e controllare i tipi.
- Validare i parametri passati per riferimento.

### 4. Generazione del codice C

Un ulteriore visitor attraversa l’AST e traduce ogni nodo in codice C equivalente, con particolare attenzione a mantenere la semantica corretta (es. variabili per riferimento tradotte come puntatori).

---

## Contribuire

Contributi, segnalazioni di bug e richieste di funzionalità sono benvenute!  
Apri una issue o una pull request.

---

## Licenza

Questo progetto è rilasciato sotto licenza MIT — vedi il file [LICENSE](LICENSE) per i dettagli.

---

## Contatti

Per domande o supporto, contattami a: `tuo_email@example.com`

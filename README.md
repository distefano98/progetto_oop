# Progetto Programmazione ad Oggetti (Luca Di Stefano - Natalia Miccini ) 


## Descrizione progetto

L'applicazione da noi sviluppata all'avvio effettua la decodifica del JSON al quale segue il download del dataset in formato ".csv" presente nell'URL :
http://data.europa.eu/euodp/data/api/3/action/package_show?id=esener-2

Il dataset presenta un'indagine dettagliata condotta dall'EU-OSHA ( European Union information agency for occupational safety and health) che esamina la gestione dei rischi per la salute e la sicurezza negli ambienti di lavoro europei. L'indagine ESENER rende disponibili dati su come vengono gestiti, nella pratica, i rischi per la salute e la sicurezza.
ESENER-2 ha raccolto le risposte di quasi 50 000 imprese sulla gestione della sicurezza e salute sul lavoro (SSL) e sui rischi sul lavoro, analizzando in particolare la partecipazione dei lavoratori e i rischi psicosociali e includendo 
per la prima volta l'agricoltura e la pesca e le microimprese con 5–10 dipendenti.
Sono state poste agli intervistati domande (definite nel dataset "QUESTION_CODE") sui principali fattori di rischio nelle loro imprese, su come li gestiscono e perché.


## Funzionamento applicazione
Attraverso SpringBoot l'applicazione crea un server locale all'indirizzo http://localhost:8080 sul quale l'utente , attraverso **API REST GET** può ottenere diverse informazioni , quali :

* Restituzione dei **metadati**  cioè l'elenco degli attributi e del tipo.
* restituzione dei **dati**  di tutto il dataset oppure di una specifica parte a seguito dell'inserimento di filtri.
* restituzione delle **statistiche** sui dati specificando l'attributo rispetto al quale si vogliono ricavare. Si possono inserire filtri se si vuole considerare solo un porzione del dataset.<br/>
  sugli attributi di tipo ***string*** le operazioni che si possono compiere sono : 
  - conteggio degli elementi
  - conteggio elementi unici
  - conteggio percentuale degli elementi unici
  
  per gli attributi di tipo ***numerico*** le operazioni che si possono effettuare sono : 
  - massimo 
  - minimo
  - somma 
  - deviazione standard
  - media 
  - conteggio degli elementi <br/>
  
 **Operatori Logici**

| Operatore | Descrizione |
| --- | --- |
| $or | Operatore logico "or" |
| $and | Operatore logico "and" |
| $in  |   |
| $nin |  |
| $not | operatore logico "not" |

**Operatori Condizionali**

| Operatore | Descrizione |
| --- | --- |
| $bt | >=value <= |
| $eq | == |
| $gt | > |
| $gte | >= |
| $lt | < |
| $lte | <= |

  
## Inserimento di filtri nella richiesta POST
|**body** | **descrizione**|
|
  
  
  
## Schema delle richieste
| **Link** | **Descrizione** |
| --- | --- |
| http://localhost:8080/metadata | Restituzione metadati |
| http://localhost:8080/ | Restituzione dati |
| http://localhost:8080/stats/number | Restituzione statistiche di tipo numerico |
| http://localhost:8080/stats/string?field=NOME_CAMPO&value=VALORE | Restituzione statistiche sulle stringhe <br/> specificando il nome del campo e il dato che si vuole studiare |





<br>


## Diagrammi UML

### Diagramma delle classi 

### Diagramma dei casi d'uso

### Diagramma delle sequenze


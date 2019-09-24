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
  - conteggio degli elementi
  <br/>
  
 **Operatori Logici**

| Operatore | Descrizione |Esempio |
| --- | --- | --- |
| $or | Operatore logico "or" | {"$or": [ { "field" : "value"},{"field" : "value" } ] } |
| $and | Operatore logico "and" |{"$and": [ {"field" : "value"},{"field" : "value"} ] } |
| $in  | abbina elementi con una determinata caratteristia  | {"field" : { "$in" : [value]}} |
| $nin |  abbina elementi che non hanno una determinata caratteristia |{"field" : { "$nin" : [value]}} |
| $not | operatore logico "not" | {"field" : {"$not" : value}} |

**Operatori Condizionali**

| Operatore | Descrizione | Esempio |
| --- | --- | ---|
| $bt | >=value <= | {"field" : {"$bt" : [value1, value2]}} |
| $eq | == | {"field" : {"$eq" : value}} |
| $gt | > | {"field" : {"$gt" : value}} |
| $gte | >= | {"field" : {"$gte" : value}} |
| $lt | < | {"field" : {"$lt" : value}} |
| $lte | <= | {"field" : {"$lte" : value}} |

  
  
## Schema delle richieste GET
| **Link** | **Descrizione** |
| --- | --- |
| http://localhost:8080/metadata | Restituzione metadati |
| http://localhost:8080/ | Restituzione dati |
| http://localhost:8080/stats/number?field=NOME_CAMPO | Restituzione statistiche di tipo numerico specificando il nome del campo sul quale effettuarle|
| http://localhost:8080/stats/string?field=NOME_CAMPO&value=VALORE | Restituzione statistiche sulle stringhe <br/> specificando il nome del campo e il dato che si vuole studiare |

## Inserimento di filtri nelle richieste di tipo POST

L'inserimento di filtri viene effettuato mediante una richiesta di tipo POST.
E' possibile inserire filtri su massimo due campi del dataset (legati da un operatore logico) indicando su di essi l'operatore condizionale e il valore/i di filtraggio ; è possibile concatenare operatori logici in modo da ottenere fino ad un massimo di due livelli di filtraggio.

Gli esempi di seguito riportati sono stati realizzati attraverso l'API Test Environment Postman </br>
***localhost:8080/filter***


| **body** | **descrizione** |
| --- | --- | 
|{ "$and" : <br/> [ { "id" : { "$gte" : 10000 } } , { "percentage" : { "$gt" : 50 } }]} | restituzione degli elementi che verificano la condizione di avere sia un *id* maggiore uguale di 10000 sia una *percentuale* superiore al 50%|
|{ "$or" : </br> [ { "id" : { "$lt" : 10000 } } , { "answers" : { "$lt" : 20 } }] } | restituzione degli elementi che verificano la condizione di avere o un *id* minore uguale di  10000 o un *answers* minore di 20 |
|{ "$and" : [</br> { "$or" : [ { "countryCode" : { "$in" : [AL]} },  { "countryCode" : { "$in" : [CH]} } ] }, </br>	{ "percentage" : { "$bt" : [30, 50]} }] }| OR logico tra elementi con countryCod di valore AL e CH prendendo, fra quest'ultimi ,  quelli con percentuale compresa tra 30 e 50|



<br>


## Diagrammi UML

### Diagramma delle classi 

![CLASS_OBJECT](https://user-images.githubusercontent.com/55144535/65542937-72341f00-df10-11e9-8837-d2a4e65e80af.png)

### Diagramma dei casi d'uso

![USE_CASE](https://user-images.githubusercontent.com/55144535/65542196-000f0a80-df0f-11e9-906e-c72c30ee475e.jpeg)


### Diagramma delle sequenze

![SEQUENCE_DIAGRAM](https://user-images.githubusercontent.com/55144535/65542875-5466ba00-df10-11e9-8fa9-36eb2cdb93f4.jpeg)


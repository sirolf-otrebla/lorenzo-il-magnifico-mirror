# Lorenzo il Magnifico
 
### Gruppo PS05 (Falaschini, Floris, Miotto)

##### Parti svolte:
* Risorse ed effetti
* Board
* Parser e file
* * File board personalizzabile
* * File carte di gico
* Azioni (in parte)

##### Parti da svolgere:
* Controller
* GUI
* Server
* Comunicazione client con server


#### Risorse ed Effetti
Per gestire le risorse e gli effetti sono state create due interfacce: _Resource_ e _ActionResult_. La prima interfaccia, **Resource**, serve per trattare le risorse in quanto tali, cioè per soddisfare i requisiti delle carte, mentre l'interfaccia **ActionResult** serve per trattare le risorse, i bonus e le azioni come risultati degli effetti delle carte. Una singola risorsa, prendiamo come esempio l'oro, implementerà tutte e due queste interfacce in quanto il suo comportamento è duplice. Al contrario se consideriamo un bonus di +2 alla torre gialla questo è semplicemente un effetto di una carta e quindi la sua classe corrispondente implementa solo la seconda interfaccia.

#### Parser
Il parser è colui che si occupa di prendere i dati dai file e trasformabili in oggetti usabili per l'esecuzione del programma. La linea base del parser è zero _switch_, pochi _if_ e molta automatizzazione. Per fare questo in tutti i file ogni risorsa, bonus o effetto ha lo stesso nome della che identifica. Il metodo più usato per questo processo è:
'''java
 Class.forName("_nome della classe da istanziare_").newIstance();
'''
Per quanto riguarda le carte dopo aver istanziato la classe dell'azione, del bonus o della risorsa si esegue un metodo comune a tutte le classi che implementano la classe **ActionResult**, il quale permette di settare il valore del bonus/risorsa effettivo.
Per la **Board** il procedimento è molto simile, anziché trovare risorse e/o carte, nel file troviamo i vari spazi in cui è possibile piazzare un familiare e quindi eseguire un'azione.
#### Board
La classe _Board_ si occupa di tenere tutti i riferimenti a torri, carte in esse, spazi azione generici, il percorso dei punti militare e dei punti fede.



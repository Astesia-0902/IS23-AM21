# Prova Finale Ingegneria del Software 2023
## IS23-AM21
![Display_1](https://github.com/Astesia-0902/IS23-AM21/assets/126017235/1f510880-3d28-471b-8d72-9ab52f193b75)

Implementazione del gioco da tavolo [My Shelfie](https://www.craniocreations.it/prodotto/my-shelfie).
Il progetto è stato sviluppato per il corso di Ingegneria del Software 2022/2023 presso il Politecnico di Milano.
Il progetto consiste in un'applicazione Java che permette di giocare a My Shelfie in modalità Multiplayer (2-4) e Online.
Il rete è stata implementata sia tramite Socket che tramite RMI.

## Componenti del gruppo
- ###   10708445 Jialiang Ding([@Astesia](https://github.com/Astesia-0902))<br>jialiang.ding@mail.polimi.it
- ###   10752734 Simona Cai ([@SimonaCai](https://github.com/SimonaCai))<br>simona.cai@mail.polimi.it
- ###   10699404 Ken Chen [@KenChen](https://github.com/KenChen00) <br>ken.chen@mail.polimi.it
- ###   10705881 Yang Hao Mao ([@Yang Hao Mao](https://github.com/Leomyh))<br>yanghao.mao@mail.polimi.it

|        Functionality         |     State      |
|:----------------------------:|:--------------:|
|         Basic rules          | :green_circle: |
|            Socket            | :green_circle: |
|             GUI              | :green_circle: |
|             TUI              | :green_circle: |
|        Multiple games        | :green_circle: |
|         Persistence          |  :red_circle:  |
| Resilience to disconnections |  :red_circle:  |
|             Chat             | :green_circle: |

## Test Coverage

| Element        |  Class %  | Method %  | Line %  |
| :------------: | :-------: | :-------: | :-----: |
| Bag            |     100%  |     100%  |    85%  |
| Board          |     100%  |     90%   |    88.7%|
| Shelf          |     100%  |     100%  |    96.2%|
| Chat           |     100%  |     90%   |    89.1%|
| CommonGoal     |     100%  |     100%  |    96.8%|
| PersonalGoal   |     100%  |     72.7% |    80.5%|
| Match          |     100%  |     87.1% |    88.8%|
| Game           |     100%  |     81.8% |    50.6%|
| Player         |     100%  |     80.5% |    77.6%|
| VirtualView    |     100%  |     94.2% |    97.4%|
|ServerVirtualView|    100%  |     80%   |    80%  |

## Documentazione
### UML
I diagrammi UML  sono disponibili al seguente link: [UML](https://github.com/Astesia-0902/IS23-AM21/tree/main/deliverables/UML)

### Javadoc
La documentazione Javadoc è disponibile al seguente link: [javaDoc](https://github.com/Astesia-0902/IS23-AM21/tree/main/deliverables/javadoc)
### Jars
I jar eseguibili sono disponibili al seguente link: [Jars](https://polimi365-my.sharepoint.com/:f:/g/personal/10699404_polimi_it/EneFfyTCf6JNllp0owLGmegB_xFKl3W9J9oxApHMLnnz1A?e=7VcqMX)
### TUI
Per avviare il gioco in modalità TUI è necessario eseguire il seguente comando da terminale:
```tui```
### GUI
Per avviare il gioco in modalità GUI è necessario eseguire il seguente comando da terminale:
```gui```

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- TUI
- GUI
- RMI
- Socket
- 2 FA (Funzionalità Avanzate):
    - __Partite Multiple:__ il server deve poter gestire più partite contemporaneamente.
    - __Chat:__ i giocatori possono comunicare tra loro tramite una chat testuale.

## Utilized Software

| Libreria/Plugin  | Descrizione |
| -------------    | ------------- |
| [Maven](https://maven.apache.org/)   | Dependency management. |
| [IntelliJ](https://www.jetbrains.com/idea/)   | Main IDE for project development.                           |
| [JSON](https://www.json.org/json-en.html)    | JSON parsing library (used for loading game resources such as cards,leaders.. and for network protocol).                        |
| [Swing](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html)  | Graphic interface library.
| [Draw.io - Diagrams.net](https://app.diagrams.net/)  | UML and sequence diagrams.
| [JUnit](https://junit.org/junit5/)  | Unit testing framework.

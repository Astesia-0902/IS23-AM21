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
| Client         |     0%    |     0%    |    0%   |
| Communication  |     0%    |     0%    |    0%   |
| Model          |     0%    |     0%    |    0%   |
| Server         |     0%    |     0%    |    0%   |

## Documentazione
### UML
I diagrammi UML  sono disponibili al seguente link: [UML](https://github.com/Astesia-0902/IS23-AM21/tree/main/_deliveries/UML)

### Javadoc
La documentazione Javadoc è disponibile al seguente link:
### Jars
I Jar del progetto possono essere scaricati al seguente link:

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
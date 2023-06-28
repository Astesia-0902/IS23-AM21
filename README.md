# Prova Finale Ingegneria del Software 2023
## IS23-AM21
![Display_1](https://github.com/Astesia-0902/IS23-AM21/assets/126017235/1f510880-3d28-471b-8d72-9ab52f193b75)

Implementazione del gioco da tavolo [My Shelfie](https://www.craniocreations.it/prodotto/my-shelfie).
Il progetto è stato sviluppato per il corso di Ingegneria del Software 2022/2023 presso il Politecnico di Milano.
Il progetto consiste in un'applicazione Java che permette di giocare a My Shelfie in modalità Single Player, Multiplayer e Online.
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
| Resilience to disconnections | :green_circle: |
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
I diagrammi UML sono disponibili al seguente link:

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
- 3 FA (Funzionalità Avanzate):
    - __Partite Multiple:__ il server deve poter gestire più partite contemporaneamente.
    - __Chat:__ i giocatori possono comunicare tra loro tramite una chat testuale.
    - __Disconnessione:__ se un giocatore si disconnette durante una partita, il server deve gestire la sua riconnessione e il suo eventuale abbandono.
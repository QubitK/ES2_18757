# Implementação dos padrões por módulos de desenvolvimento:

## M1 - SINGLETON  
* [LogConfig]: garante uma instância única com as configurações globais do sistema de logs (nível mínimo, destino e formato de mensagem).
* Recorre a *[LogLevel]** (enum auxiliar) para tipificar o nível mínimo de log configurável.


## M2 - FACTORY METHOD
*Hierarquia de Produto*
* [LogRecordInterface] — Interface que define o contrato para todos os tipos de registo (`getLevel`,  `getMessage`, `getTimestamp`, `format`, `outputLog`).

* [LogRecord] — Classe abstrata intermédia que implementa [LogRecordInterface], centralizando o comportamento comum a todos os registos: constructor com `level`, `message` e `timestamp`, e implementação de `format()` e `outputLog()`. As subclasses herdam este comportamento sem o repetir.

* [DebugLog], [InfoLog], [WarningLog], [ErrorLog] — Objetos Log concretos, subclasses de [LogRecord]. Cada uma invoca o construtor do pai passando o `LogLevel` que a identifica, sendo essa a única distinção entre elas.

*Hierarquia de Criador*
* [LogCreator] — Criador abstrato que declara o factory method `createLog(String message)`, devolvendo  [LogRecordInterface]. Define o contrato de criação que todas as subclasses são obrigadas a implementar.

* [DebugLogCreator], [InfoLogCreator], [WarningLogCreator], [ErrorLogCreator] — Criadores concretos, subclasses de [LogCreator]. Cada um implementa `createLog()`  Instanciando o produto concreto correspondente, mantendo o cliente desacoplado das classes concretas.

## M3 - BRIDGE PATTERN
*Abstraction Side*
[Logger] - > agrega HashMap<String, LogDestinationInterface>
*Implementation Side*
[LogDestinationInterface] -> implementa subclasses de tipo de destino: [LogDestination] + [ConsoleDestination], [FileDestination], .... *[]*

> Os registos [LogRecordInterface] são passados ao [Logger] - permanecendo criados pelo Factory Method


## M4 - COMPOSITE PATTERN
*Component*: 
* [LogComponent] - interface uniforme para composites e leafs
                 - outputTo funciona em folha([LogEntry]) e composite([LogCategory])
*Composite*: 
* [LogCategory] - agrega LogComponents por categoria (auth, db, etc.)
                - delega outputTo recursivament
*Leafs*: 
* [LogEntry] - encapsula um LogRecordInterface individual 
             - aplica filtro de LogConfig
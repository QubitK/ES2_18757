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
* [DebugLogCreator], [InfoLogCreator], [WarningLogCreator], [ErrorLogCreator] — Criadores concretos, subclasses de [LogCreator]. Cada um implementa `createLog()` instanciando o produto concreto correspondente, mantendo o cliente desacoplado das classes concretas.

## M3 - BRIDGE PATTERN
*Abstraction Side*
[Logger] - > agrega HashMap<String, LogDestinationInterface>
*Implementation Side*
[LogDestinationInterface] -> implementa subclasses de tipo de destino: [LogDestination] + [ConsoleDestination], [FileDestination], .... *[]*
> Os registos [LogRecordInterface] são passados ao [Logger] - permanecendo criados pelo Factory Method

## M4 - COMPOSITE PATTERN
*Component*
* [LogComponent] — Classe abstrata que define o contrato uniforme para folha e composto: `outputTo(LogDestinationInterface destination)`. É o único tipo que o cliente manipula.
*Leaf*
* [LogEntry] — Encapsula um [LogRecordInterface] individual. Implementa `outputTo()` verificando o nível mínimo via [LogConfig] (M1) antes de delegar a escrita ao destino recebido.
*Composite*
* [LogCategory] — Agrega uma `List<LogComponent>` e expõe `add()`, `remove()`, `getChildren()` e `getName()`. Implementa `outputTo()` delegando recursivamente aos filhos, sem distinguir se são [LogEntry] ou outra [LogCategory].
> O cliente selecciona o nó a partir do qual invoca `outputTo()` — essa escolha é a filtragem por categoria.

## M5 - OBJECT POOL
* [DestinationPool] — Singleton que gere duas listas: `available` e `inUse`, ambas de [LogDestinationInterface]. Inicializado com uma `List<LogCategory>` (M4) — por cada categoria cria uma [FileDestination] dedicada ao ficheiro `<nomeCategoria>Log.txt`. Expõe `acquire()` e `release()`, ambos `synchronized`.
* [PoolExhaustedException] — lançada por `acquire()` quando não há instâncias disponíveis.
* [ObjectNotFoundException] — lançada por `release()` quando a instância devolvida não pertence ao pool.
* [FileDestination] (alteração face a M3) — `FileWriter` mantido aberto persistentemente; `flush()` explícito após cada `write()`; expõe `close()` para libertação do recurso.
> [ConsoleDestination] não é poolizada — não mantém recursos persistentes.
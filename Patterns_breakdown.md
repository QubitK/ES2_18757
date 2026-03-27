## Funcionalidade & Padrões Implementados

---------------------------------------------------------------------------------

### M1 — Singleton
**Objetivo:** garantir um único ponto de acesso às configurações globais do sistema.

`LogLevel` — enum auxiliar que tipifica os níveis de severidade possíveis: `DEBUG`, `INFO`, `WARNING`, `ERROR`. Usado como tipo em `LogConfig` e em todos os registos.

`LogConfig` — enum Singleton com instância única `INSTANCE`. Armazena três configurações globais partilhadas por todo o sistema: `logMinLevel` (nível mínimo de filtragem), `logDestination` (destino textual) e `logMsgFormat` (template de formatação). A JVM garante que `INSTANCE` é criada uma única vez, sendo thread-safe sem código adicional.

------------------------------------------------------------------------------

### M2 — Factory Method
**Objetivo:** encapsular a criação de registos de log, permitindo adicionar novos tipos sem alterar código existente.

*Hierarquia de Produto:*

`LogRecordInterface` — interface que define o contrato de qualquer registo: `getLevel()`, `getMessage()`, `getTimestamp()`, `format()`. É o tipo devolvido pelos criadores e recebido pelo `Logger`.

`LogRecord` — classe abstrata que implementa `LogRecordInterface`, centralizando o comportamento comum: construtor com `level`, `message`, `timestamp` (gerado automaticamente via `LocalDateTime.now()`), e implementação de `format()` que aplica o template de `LogConfig.INSTANCE`, substituindo os placeholders `{level}`, `{timestamp}` e `{message}`.

`DebugLog`, `InfoLog`, `WarningLog`, `ErrorLog` — produtos concretos, subclasses de `LogRecord`. A única distinção entre elas é o `LogLevel` passado ao construtor do pai.

*Hierarquia de Criador:*

`LogCreator` — criador abstrato que declara o factory method `createLog(String message)`, devolvendo `LogRecordInterface`. Define o contrato de criação sem conhecer os produtos concretos.

`DebugLogCreator`, `InfoLogCreator`, `WarningLogCreator`, `ErrorLogCreator` — criadores concretos que implementam `createLog()`, instanciando o produto correspondente. O cliente depende apenas de `LogCreator` e `LogRecordInterface`, nunca das classes concretas.

------------------------------------------------------------------------------

### M3 — Bridge
**Objetivo:** desacoplar o sistema de escrita dos destinos concretos, permitindo adicionar novos destinos sem modificar o restante sistema.

*Abstraction Side:*
`Logger` — classe que agrega um `HashMap<String, LogDestinationInterface>`, onde a chave é um id gerado sequencialmente. Gere dinamicamente os destinos via `addDestination()` (devolve o id atribuído) e `removeDestination()`. Expõe dois métodos de escrita: `logAllDestinations()` itera sobre todos os destinos registados, `logDestination()` escreve para um destino específico por id. Ambos consultam `LogConfig.INSTANCE` via `meetsMinLevel()` antes de qualquer escrita, comparando o ordinal do nível do registo com o nível mínimo configurado.

*Implementation Side:*
`LogDestinationInterface` — interface que declara `write(LogRecordInterface log)`, sendo o único contrato que os destinos concretos têm de cumprir.
`ConsoleDestination` — implementação concreta que escreve para `System.out` invocando `log.format()`.
`FileDestination` — implementação concreta que recebe o caminho do ficheiro no construtor e escreve em modo append via `FileWriter`, tratando `IOException` localmente.

Filtragem 
Logger.logAllDestinations(record)
    └── meetsMinLevel() 
            └── destination.write(record)

------------------------------------------------------------------------------

### M4 — Composite
**Objetivo:** organizar registos de log em categorias hierárquicas (autenticação, base de dados, rede, etc.), tratando grupos e entradas individuais de forma uniforme através de uma interface comum.

`LogComponent` — classe abstrata Component que define o contrato uniforme para folha e composto: `outputTo(LogDestinationInterface destination)`. É o único tipo que o cliente manipula, independentemente de estar a lidar com uma entrada individual ou uma árvore de categorias.

`LogEntry` — Leaf que encapsula um `LogRecordInterface` individual. Implementa `outputTo()` consultando `LogConfig.INSTANCE` para verificar o nível mínimo antes de delegar a escrita ao destino recebido — comportamento consistente com o `Logger` do M3.

`LogCategory` — Composite que agrega uma `List<LogComponent>` e expõe `add()`, `remove()`, `getChildren()` e `getName()`. Implementa `outputTo()` iterando sobre os filhos e delegando recursivamente, sem conhecer se cada filho é um `LogEntry` ou outra `LogCategory`. Permite aninhar categorias arbitrariamente.

------------------------------------------------------------------------------

### M5 — Object Pool
**Objetivo:** reutilizar instâncias de `FileDestination` cujo `FileWriter` é mantido aberto persistentemente, evitando o custo de reabrir o ficheiro a cada operação de escrita. Isto é feito inicializando uma lista de `LogCategory` (M4) — por cada categoria cria uma `FileDestination` com o ficheiro `<nomeCategoria>Log.txt`, mantendo o `FileWriter` aberto.

`DestinationPool` — Singleton que gere duas listas: `available` (instâncias prontas a ser adquiridas) e `inUse` (instâncias actualmente em uso).  Expõe `acquire()`, que move uma instância de `available` para `inUse` e a devolve ao chamador, e `release()`, que a devolve a `available` para reutilização. Ambos os métodos são `synchronized` (thread-safe). `ConsoleDestination` não é poolizada — não mantém recursos persistentes.

`PoolExhaustedException` — excepção lançada por `acquire()` quando `available` está vazia.

`ObjectNotFoundException` — excepção lançada por `release()` quando a instância devolvida não pertence ao pool.

`FileDestination` (alteração face a M3) — o `FileWriter` passa a ser aberto no construtor e mantido persistentemente, com `flush()` após cada `write()`. Expõe `close()` para libertação explícita do recurso.

------------------------------------------------------------------------------

### Ligação entre os cinco padrões
```
LogConfig.INSTANCE (M1)  →  consultado em LogRecord.format(), Logger.meetsMinLevel() e LogEntry.outputTo()
LogCreator (M2)          →  cria LogRecordInterface passado ao Logger (M3) ou encapsulado em LogEntry (M4)
Logger (M3)              →  recebe LogRecordInterface de M2 e distribui pelos destinos
LogCategory (M4)         →  outputTo() recebe LogDestinationInterface de M3; delega aos filhos
LogEntry (M4)            →  encapsula LogRecordInterface de M2; filtra via M1; escreve via M3
DestinationPool (M5)     →  inicializado com List<LogCategory> de M4; gere FileDestination de M3
```
M2 e M3 permanecem independentes entre si. M4 integra-se com os três módulos anteriores exclusivamente através do `LogEntry`. M5 liga-se a M4 na inicialização (nomes das categorias determinam os ficheiros) e a M3 na operação (as instâncias poolizadas implementam `LogDestinationInterface`).
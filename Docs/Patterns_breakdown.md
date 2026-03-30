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
**Objetivo:** organizar registos de log em categorias hierárquicas (autenticação, base de dados, rede, etc.), tratando folhas (entradas individuais) e compostos (categorias) de forma uniforme através de uma interface comum.

`LogComponent` — COMPONENT: Classe abstrata que define o contrato comum: `outputTo(LogDestinationInterface destination)` e `getLevel(): LogLevel`. É o tipo base que o cliente manipula.

`LogEntry` — LEAF: que encapsula um `LogRecordInterface` individual. Implementa `outputTo()` consultando `LogConfig.INSTANCE` para verificar o nível mínimo antes de delegar a escrita ao destino recebido — comportamento consistente com o `Logger` do M3. Implementa `getLevel()` devolvendo o nível do registo.

`LogCategory` — COMPOSITE: Agrega uma `List<LogComponent>` e expõe `add()`, `remove()`, `getChildren()` e `getName()`. Implementa `outputTo()` com delegação recursiva aos filhos e `getLevel()` retornando `null` (não tem nível único), sem conhecer se cada filho é um `LogEntry` ou outra `LogCategory`. Permite aninhar categorias arbitrariamente.

------------------------------------------------------------------------------

### M5 — Object Pool
**Objetivo:** reutilizar instâncias de `FileDestination` cujo `FileWriter` é mantido aberto persistentemente, evitando o custo de reabrir o ficheiro a cada operação de escrita. Isto é feito inicializando uma lista de `LogCategory` (M4) — por cada categoria cria uma `FileDestination` com o ficheiro `<nomeCategoria>Log.txt`, mantendo o `FileWriter` aberto.

`DestinationPool` — Singleton que gere duas listas: `available` (instâncias prontas a ser adquiridas) e `inUse` (instâncias actualmente em uso).  Expõe `acquire()`, que move uma instância de `available` para `inUse` e a devolve ao chamador, e `release()`, que a devolve a `available` para reutilização. Ambos os métodos são `synchronized` (thread-safe). `ConsoleDestination` não é poolizada — não mantém recursos persistentes.

`PoolExhaustedException` — excepção lançada por `acquire()` quando `available` está vazia.

`ObjectNotFoundException` — excepção lançada por `release()` quando a instância devolvida não pertence ao pool.
`FileDestination` — abre `FileWriter` em modo append a cada escrita (não persistente).

------------------------------------------------------------------------------


### M6 — Memento
**Objetivo:** permitir guardar e restaurar o estado completo do sistema de logging sem violar encapsulamento.

`LogSystemMemento` — objeto imutável que representa um snapshot contendo:
- `LogLevel minLevel`
- `HashMap<String, String>` → mapping id → filePath dos destinos
- `List<LogCategory>` → estrutura de categorias (sem `LogEntry`)

`LogSystemOriginator` — entidade central que cria e restaura snapshots:
- `backup()`:
  - lê `LogConfig.INSTANCE`
  - extrai destinos do `Logger`
  - copia profundamente categorias (ignorando `LogEntry`)
- `restore()`:
  - repõe nível mínimo
  - reconstrói destinos
  - substitui categorias
  - reinicializa `DestinationPool`

`LogSystemCaretaker` — gere snapshots:
- armazena lista de mementos
- `takeSnapshot()` guarda estado
- `restoreSnapshot(index)` restaura estado anterior

**Nota importante:** apenas a estrutura (configuração + categorias + destinos) é preservada — os `LogEntry` são ignorados por não representarem estado estrutural.

------------------------------------------------------------------------------

### M7 — Decorator
**Objetivo:** Adicionar responsabilidades dinâmicas (categoria e monitorização) aos componentes de log sem alterar as classes existentes (`LogEntry` e `LogCategory`).

* `LogDecorator` — Classe abstrata base do Decorator. Estende `LogComponent` e mantém uma referência (`wrapped`) ao componente decorado. Delega `outputTo()` e `getLevel()` por defeito.
* `CategoryDecorator` — Adiciona contexto de categoria ao log. Guarda o nome da categoria e permite que seja recuperado ao longo da cadeia através de `getCategoryName()`.
* `MonitoringDecorator` — Adiciona monitorização: conta logs por nível (`debugLogCounter`, `infoLogCounter`, etc.), verifica threshold e emite alertas quando excedido. Expõe `getSummary()` e getters para contagens.

**Dinâmica:** Os decorators formam uma cadeia linear aplicada sobre nós da árvore Composite. O cliente chama `outputTo()` no decorator mais externo, que adiciona comportamento antes de delegar para o próximo.

**Integração com Composite:** Qualquer `LogComponent` (LogEntry ou LogCategory) pode ser embrulhado. `MonitoringDecorator` e `CategoryDecorator` mantêm compatibilidade total com o tipo `LogComponent`.


------------------------------------------------------------------------------
### Ligação entre os cinco padrões

LogConfig.INSTANCE (M1)  →  consultado em LogRecord.format(), Logger.meetsMinLevel() e LogEntry.outputTo()
LogCreator (M2)          →  cria LogRecordInterface passado ao Logger (M3) ou encapsulado em LogEntry (M4)
Logger (M3)              →  recebe LogRecordInterface de M2 e distribui pelos destinos
LogCategory (M4)         →  outputTo() recebe LogDestinationInterface de M3; delega aos filhos
LogEntry (M4)            →  encapsula LogRecordInterface de M2; filtra via M1; escreve via M3
DestinationPool (M5)     →  inicializado com List<LogCategory> de M4; gere FileDestination de M3
LogSystemMemento / Caretaker (M6) → faz backup/restore da estrutura de M4 (categorias),
configurações de M1 e destinos de M3
LogDecorator (M7)         →  embrulha qualquer LogComponent (LogEntry ou LogCategory);
adiciona comportamento dinâmico (categoria + monitorização)

**Integrações principais:**

- M2 e M3 permanecem independentes entre si.
- M4 (Composite) serve de base para integração com M1, M2 e M3, principalmente através do `LogEntry`. Foi também estendido com o método `getLevel()` para suportar o M7.
- M5 (Object Pool) liga-se a M4 na inicialização e a M3 na operação.
- M6 (Memento) preserva o estado estrutural de M1, M3 e M4.
- M7 (Decorator) integra-se diretamente com M4, permitindo decorar qualquer `LogComponent` de forma dinâmica e transparente sem alterar classes existentes.

Esta arquitetura mantém os padrões bem desacoplados, com `LogComponent` como ponto central de ligação entre Composite e Decorator.
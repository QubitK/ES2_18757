# Padrões Utilizados (M1 e M2)

## SINGLETON — M1

* [LogConfig]: garante uma instância única com as configurações globais do sistema de logs (nível mínimo, destino e formato de mensagem).
* Recorre a *[LogLevel]** (enum auxiliar) para tipificar o nível mínimo de log configurável.

## FACTORY METHOD — M2

*Hierarquia de Produto*
* [LogRecordInterface] — Interface que define o contrato para todos os tipos de registo (`getLevel`,  getMessage`, `getTimestamp`, `format`, `outputLog`).
* [LogRecord] — Classe abstrata intermédia que implementa [LogRecordInterface], centralizando o comportamento comum a todos os registos: constructor com `level`, `message` e `timestamp`, e implementação de `format()` e `outputLog()`. As subclasses herdam este comportamento sem o repetir.

* [DebugLog], [InfoLog], [WarningLog], [ErrorLog] — Objetos Log concretos, subclasses de [LogRecord]. Cada uma invoca o construtor do pai passando o `LogLevel` que a identifica, sendo essa a única distinção entre elas.

*Hierarquia de Criador*
* [LogCreator] — Criador abstrato que declara o factory method `createLog(String message)`, devolvendo  [LogRecordInterface]. Define o contrato de criação que todas as subclasses são obrigadas a implementar.

* [DebugLogCreator], [InfoLogCreator], [WarningLogCreator], [ErrorLogCreator] — Criadores concretos, subclasses de [LogCreator]. Cada um implementa `createLog()`  nstanciando o produto concreto correspondente, mantendo o cliente desacoplado das classes concretas.
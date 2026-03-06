package com.es2.bridge;
/*
  Papéis no padrão Bridge

  ┌─────────────────────────────┬─────────────────────┬──────────────────────────────────────────────────────────────────┐
  │           Classe            │        Papel        │                         Responsabilidade                         │
  ├─────────────────────────────┼─────────────────────┼──────────────────────────────────────────────────────────────────┤
  │ APIServiceInterface         │ Implementor         │ Interface com store(), get(), getAll()                           │
  ├─────────────────────────────┼─────────────────────┼──────────────────────────────────────────────────────────────────┤
  │ APIMoodle                   │ ConcreteImplementor │ Armazena conteúdo num LinkedHashMap com IDs sequenciais          │
  ├─────────────────────────────┼─────────────────────┼──────────────────────────────────────────────────────────────────┤
  │ APIRequest                  │ Abstraction         │ Gere serviços dinamicamente; delega operações ao implementor     │
  ├─────────────────────────────┼─────────────────────┼──────────────────────────────────────────────────────────────────┤
  │ APIRequestContentAggregator │ RefinedAbstraction  │ Sobrepõe getContent() para concatenar todo o conteúdo do serviço │
  └─────────────────────────────┴─────────────────────┴──────────────────────────────────────────────────────────────────┘

  Comportamento da API (derivado dos testes)

  - addService(service) → regista um serviço dinamicamente, retorna o ID
  - setContent(serviceId, value) → guarda conteúdo no serviço, retorna ID do conteúdo
  - getContent(serviceId, contentId) → retorna o valor ou null se não existir; lança ServiceNotFoundException se o serviço não existir
  - APIRequestContentAggregator.getContent(...) → ignora contentId e concatena todos os valores em ordem de inserção (LinkedHashMap garante a ordem)
 */
public class Main {

    public static void main(String[] args) throws ServiceNotFoundException {

        // Concrete implementor instantiated dynamically at runtime
        APIRequest request = new APIRequest();
        String moodleId = request.addService(new APIMoodle());

        request.setContent(moodleId, "Introduction to Software Engineering");
        request.setContent(moodleId, "Design Patterns");
        request.setContent(moodleId, "Distributed Systems");

        System.out.println("course 0: " + request.getContent(moodleId, "0"));
        System.out.println("course 1: " + request.getContent(moodleId, "1"));

        // Refined abstraction: aggregates all content from the service
        APIRequestContentAggregator aggregator = new APIRequestContentAggregator();
        String serviceId = aggregator.addService(new APIMoodle());
        aggregator.setContent(serviceId, "Eu vou");
        aggregator.setContent(serviceId, " a Viseu");
        aggregator.setContent(serviceId, " estudar");

        System.out.println("\nAggregated: " + aggregator.getContent(serviceId, "0"));

        // ServiceNotFoundException demo
        try {
            request.getContent("invalid", "0");
        } catch (ServiceNotFoundException e) {
            System.out.println("\nException: " + e.getMessage());
        }
    }
}

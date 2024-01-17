# language: pt
Funcionalidade: API - Produção

  Cenário: Registrar novo pedido à fila de produção
    Quando Adicionar pedido à fila de produção
    Então o pedido é adicionado com sucesso

  Cenário: Atualizar pedido existente na fila de produção
    Dado que um pedido já registrado
    Quando requisitar a atualização do pedido
    Então o pedido é atualizado com sucesso

  Cenário: Listar pedidos existentes na fila de produção
    Dado que pedidos estejam na fila de produção
    Quando requisitar a lista de pedidos na fila
    Então os pedidos são exibidos com sucesso

  Cenário: Consultar status do pedido na fila de produção
    Dado que um pedido esteja na fila de produção
    Quando requisitar a consulta do pedido na fila de produção
    Então o pedido seja exibido com sucesso

  Cenário: Consultar histórico de um pedido na fila de produção
    Dado que um pedido esteja na fila de produção
    Quando requisitar a consulta do histórico do pedido na fila de produção
    Então a lista de histórico do pedido seja exibida com sucesso
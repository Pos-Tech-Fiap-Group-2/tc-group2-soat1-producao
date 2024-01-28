# Documentação - Tech Challenge - Grupo 2 SOAT1 - PosTech - Arquitetura de Software - FIAP
Repositório para o microsserviço de produção do desafio do Tech Challenge da Pós-gradução em Software Architecture pela FIAP.

## Introdução
Uma lanchonete de bairro que está expandido sua operação devido seu grande sucesso. Porém, com a expansão e sem um sistema de controle de pedidos, o atendimento aos clientes pode ser caótico e confuso.
Para solucionar o problema, a lanchonete irá investir em um sistema de autoatendimento de fast food, que é composto por uma série de dispositivos e interfaces que permitem aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente.

## Membros do Grupo 2
- [Danilo Monteiro](https://github.com/dmonteirosouza)
- [Marcel Cozono](https://github.com/macozono)
- [Viviane Scarlatti](https://github.com/viviane-scarlatti)
- [Vinicius Furin](https://github.com/VFurin)
- [Vitor Walcker](https://github.com/VitorWalcker)


# Compilação e geração de artefato
Antes de iniciar, certifique-se de que sua máquina atenda aos seguintes requisitos:<br/><br/>

**JDK 17 instalado:**<br/>
Certifique-se de ter o JDK 17 instalado em sua máquina.<br/><br/>

**Maven >= 3 instalado:**<br/>
Verifique se você tem o Maven instalado em sua máquina. Para verificar a versão do Maven instalada, execute o seguinte comando no terminal: **mvn -version**. Se o Maven não estiver instalado ou estiver em uma versão inferior à 3, faça o download e siga as instruções de instalação do site oficial do Maven ou de outra fonte confiável.<br/><br/>

**Clonando o projeto:**<br/>
Clone o repositório do projeto em seu ambiente local.<br/><br/>

**Compilando o projeto:**<br/>
Navegue até o diretório raiz do projeto no terminal e execute o seguinte comando para compilar o projeto usando o Maven:<br/><br/>

```sh
mvn clean install
```

Isso irá baixar as dependências do projeto, compilar o código-fonte e criar o artefato no diretório target com o nome **tc-group2-soat1-producao.jar**.<br/>
Esse artefato será copiado para a imagem do container em momento de build durante a execução do docker-compose.

**Executando o projeto:**<br/>
Após a conclusão da etapa anterior, você pode executar o projeto seguindo as instruções específicas do projeto.

# Build da imagem do projeto
Caso seja necessária a geração de uma nova imagem, executar o comando no diretório raíz do projeto:
```sh
docker build --build-arg "JAR_FILE=tech-challenge-group2-soat1-producao.jar" -t <usuario>/<imagem_nome>:<tag> .
```

## Documentação Swagger da API
A documentação em padrão Swagger está disponível em http://localhost:8080/api/swagger-ui.html.

## Execução do projeto via Postman
Basta clicar no link [diretório postman](src/main/resources/postman) onde está disponível o arquivo JSON contendo todos os endpoints configurados basta importá-lo via Postman e executar o passo-a-passo abaixo.

<a name="ancora"></a>
1. [Adicionar Pedido à Fila de Produção](#ancora1)
2. [Atualizar Status de Pedido em Produção](#ancora2)
3. [Consultar a Fila de Pedidos](#ancora3)
4. [Consultar Status de Pedido em Produção](#ancora4)
5. [Consultar Histórico de Produção de um Pedido](#ancora5)

# Endpoints disponíveis por recurso
Abaixo, segue a lista de endpoints disponíveis por recurso e exemplos de requisição.

<a id="ancora1"></a>
##### Adicionar Pedido à Fila de Produção
```sh
POST http://localhost:8080/api/producao/{id}/adicionar
```
<a id="ancora2"></a>
##### Atualizar Status de Pedido em Produção
```sh
PUT http://localhost:8080/api/producao/{id}/status -H "Content-Type: application/json" -d 
Request body
{
  "status": "Em Preparação"
}
```
<a id="ancora3"></a>
##### Consultar a Fila de Pedidos
```sh
GET http://localhost:8080/api/producao/fila
```
<a id="ancora4"></a>
##### Consultar Status de Pedido em Produção
```sh
GET http://localhost:8080/api/producao/{id}/status
```
<a id="ancora5"></a>
##### Consultar Histórico de Produção de um Pedido
```sh
GET http://localhost:8080/api/producao/{id}/historico
```
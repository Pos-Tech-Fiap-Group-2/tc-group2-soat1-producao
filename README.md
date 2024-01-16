

# Documentação - Tech Challenge - Grupo 2 SOAT1 - PosTech - Arquitetura de Software - FIAP
Repositório para o desafio do Tech Challenge da Pós-gradução em Software Architecture pela FIAP.

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

Isso irá baixar as dependências do projeto, compilar o código-fonte e criar o artefato no diretório target com o nome **tech-challenge-group2-soat1-pedido.jar**.<br/>
Esse artefato será copiado para a imagem do container em momento de build durante a execução do docker-compose.

**Executando o projeto:**<br/>
Após a conclusão da etapa anterior, você pode executar o projeto seguindo as instruções específicas do projeto.

# Build da imagem do projeto
Caso seja necessária a geração de uma nova imagem, executar o comando no diretório raíz do projeto:
```sh
docker build --build-arg "JAR_FILE=tech-challenge-group2-soat1-pedido.jar" -t <usuario>/<imagem_nome>:<tag> .
```

Após geração da imagem, alterar o arquivo **09-deployment.yaml** indicando o novo tagueamento da imagem.

# Recursos provisionados no k8s
Lista de arquivos YAML com recursos do k8s:
- **00-secrets.yaml:** Armazenamento das secrets de banco de dados e access_token para a API do MP;
- **01-persistent-volume-db.yaml:** Mapeamento da PV para os arquivos de banco de dados;
- **02-persistent-volume-claim.yaml:** Mapeamento da PVC com configuração de claims para volumes do banco de dados;
- **03-configmap.yaml:** ConfigMap com chaves relacionadas a integração do microserviço;
- **04-configmap-db.yaml:** ConfigMap com chaves relacionadas a integração do banco de dados;
- **05-service-db.yaml:** Mapeamento das portas para acesso ao service de banco de dados;
- **06-service-lb.yaml:** Mapeamento das portas para acesso ao service LoadBalancer do microserviço;
- **07-service-np.yaml:** Mapeamento das portar para acesso ao service NodePort do microserviço;
- **08-deployment-db.yaml:** Deployment para disponibilização do banco de dados;
- **09-deployment.yaml:** Deployment para disponibilização do microserviço;
- **10-autoscale.yaml:** HPA com parametrização de quantidade de réplicas e indicador para escalabilidade.

**Importante!**
- Esse comando é necessário ser executado apenas no primeiro provisionamento dos recursos.
- Após a primeira inicialização, os volumes relacionados aos dados do MongoDB estarão persistidos.

# Endpoints disponíveis por recurso
Abaixo, segue a lista de endpoints disponíveis por recurso e exemplos de requisição.

##### Adicionar Pedido à Fila de Produção
```sh
curl -X POST http://localhost:8080/api/producao/{id}/adicionar
```

##### Atualizar Status de Pedido em Produção
```sh
curl -X PUT http://localhost:8080/api/producao/{id}/status -H "Content-Type: application/json" -d '{"status": "Em Preparação"}'
```

##### Consultar a Fila de Pedidos
```sh
curl -X GET http://localhost:8080/api/producao/fila
```

##### Consultar Status de Pedido em Produção
```sh
curl -X GET http://localhost:8080/api/producao/{id}/status
```

##### Consultar Histórico de Produção de um Pedido
```sh
curl -X GET http://localhost:8080/api/producao/{id}/historico
```

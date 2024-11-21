# DAPE-API (Declaração de Apostas Esportivas)

Essa API REST lida com transações realizadas simulando casa de apostas.
Ela lida com informações como tipo da aposta, status da aposta, valor apostado,
informações do cliente, entre outros, registrando essas informações  em um 
banco de dados. O tipo da aposta pode ser definido como 'simples' ou 'múltipla',
enquanto o status da aposta é definido como 'em andamento'(pending), 'vencida'(green)
ou 'perdida'(red).

## Sumário
- [Arquitetura](#arquitetura)
- [Modelagem do banco de dados](#modelagem-do-banco-de-dados)
- [Máquina de estados](#máquina-de-estados-do-status-de-uma-aposta-ou-bilhete)
- [Ambiente de desenvolvimento](#ambiente-de-desenvolvimento)
  - [Tecnologias Utilizadas](#tecnologias-utilizadas)
  - [Configurando ambiente de desenvolvimento](#configurando-ambiente-de-desenvolvimento)
  - [Conexão com o banco de dados](#conexão-com-o-banco-de-dados)
  - [Migração e versionamento com Flyway](#migração-e-versionamento-com-flyway)
- [Utilização](#utilização)
  - [Cadastro de uma nova aposta](#cadastro-de-uma-nova-aposta)
  - [Atualização de uma aposta](#atualização-de-uma-aposta)
  - [Atualização do status de uma aposta](#atualização-do-status-de-uma-aposta)
  - [Visualização de apostas cadastradas](#visualização-de-apostas-cadastradas)
  - [Exclusão de uma aposta cadastrada](#exclusão-de-uma-aposta-cadastrada)


## Arquitetura
![arquitetura](/src/main/resources/images/arquitetura.png)

## Modelagem do banco de dados
![modelagem](/src/main/resources/images/modelagem.png)

## Máquina de estados do status de uma aposta ou bilhete
![status](/src/main/resources/images/status.png)

Toda aposta e bilhetes cadastrados recebem o status inicial PENDING(aposta pendente). Posteriormente esse
status poderá ser alterado para GREEN(aposta vencida) ou RED(aposta perdida) e não poderá ter seu status alterado novamente.

## Ambiente de desenvolvimento
O ambiente de desenvolvimento é configurado utilizando o Docker para criar e gerenciar
uma instância do banco de dados Oracle, simplificando o processo de configuração do ambiente.

### Tecnologias Utilizadas
- Framework: Spring Boot
- Versão do Java: 17
- Banco de Dados: Oracle
- Gerenciamento de Dependências: Gradle
- Containers: Docker

### Configurando ambiente de desenvolvimento

- [Baixar e Instalar Docker](https://docs.docker.com/get-docker/)
- [Instalar Docker Compose](https://docs.docker.com/compose/install/)
- Para criar uma instância do banco de dados Oracle execute o
  comando `docker-compose up`
- Ao aparecer a mensagem `DATABASE IS READY TO USE!` significa que
  a instância do Oracle está em execução e pronta para ser utilizada

### Conexão com o banco de dados
Caso deseje acessar o banco de dados, utilize um software de gestão de banco
de dados de sua escolha e realize a conexão com as seguintes propriedades:
- URL: jdbc:oracle:thin:@//localhost:1521/XEPDB1
- Host: localhost
- Port: 1521
- Service: XEPDB1
- User: DAPE_ADM
- Password: pesippar24

### Migração e versionamento com Flyway
Flyway é uma ferramenta de versionamento e migração de banco de dados, que auxilia
na manutenção da organização dos scripts e garante a integridade do do banco.

Os scripts estão presentes no diretório 'src/main/resources/db/migration' e seguem
o padrão de nomenclatura que pode ser encontrado no link a seguir: [Naming Patterns](https://documentation.red-gate.com/fd/migrations-184127470.html)

Em sequência há uma breve explicação sobre o significado de cada parte:
- Prefixo: inicia-se com V em maiúsculo para migrações que ocorram uma única vez
- Versão: número da versão, deve ser incremental, separada por . como 1.1, 1.2, 2, 3.1
- Separador: __ (2 underscores)
- Descrição: breve descrição dos comandos a serem realizados, com palavras separadas por _ (underscore)

Ao executar a aplicação pela primeira vez, o Flyway será responsável por criar as tabelas, com o arquivo presente em [V1.1__CREATING_TABLES_RG_8483.sql](src/main/resources/db/migration/V1.1__CREATING_TABLES_RG_8483.sql) e em seguida
popular o banco de dados usando o arquivo [V1.2__INSERTING_DATA_RG_8483.sql](src/main/resources/db/migration/V1.2__INSERTING_DATA_RG_8483.sql). Posteriormente foi 
adicionado o arquivo [V1.3__ALTERING_TICKET_TABLE_RG_8484.sql](src/main/resources/db/migration/V1.3__ALTERING_TICKET_TABLE_RG_8484.sql) para adicionar uma coluna
para a odd final do bilhete de apostas.

## Utilização

O projeto possui um documento de contrato de endpoints disponibilizado ([dape-api-contract.yml](src/main/resources/static/dape-api-contract.yml)), no qual
são definidas as regras de requisições e respostas para cada endpoint.

### Cadastro de uma nova aposta
Para cadastrar uma nova aposta no banco de dados rode o comando a seguir no terminal:
```sh
curl -X POST \
     --location 'localhost:8080/dape/bet' \
     -H 'Content-Type: application/json' \
     -d '{
           "num_odd": 2.19,
           "des_bet": "Vitória do Botafogo"
         }' 
```

A resposta deverá ser como a seguinte:
- Status Code: 201 - CREATED
```json
{
  "idt_bet": 4,
  "des_bet": "Vitória do Botafogo",
  "num_odd": 2.19,
  "dat_created": "2025-07-21T15:16:10.877543705",
  "dat_updated": "2025-07-21T15:16:10.877543705",
  "bet_status": "PENDING",
  "flg_selected": 0
}
```

### Atualização de uma aposta
Para atualizar uma aposta no banco de dados rode o comando a seguir no terminal:
```sh
curl -X PATCH \
     --location 'localhost:8080/dape/bet/1' \
     -H 'Content-Type: application/json' \
     -d '{
           "num_odd": 2.45,
           "des_bet": "Vitória do Bahia"
         }' 
```

A resposta deverá ser como a seguinte:
- Status Code: 200 - OK
```json
{
  "idt_bet": 1,
  "des_bet": "Vitória do Bahia",
  "num_odd": 2.45,
  "dat_created": "2025-07-21T15:16:10.877543705",
  "dat_updated": "2025-07-21T15:24:23.877543705",
  "bet_status": "PENDING",
  "flg_selected": 0
}
```

### Atualização do status de uma aposta
Para atualizar o status de uma aposta no banco de dados rode o comando a seguir no terminal:
```sh
curl -X PATCH \
     --location 'localhost:8080/dape/bet/1/status' \
     -H 'Content-Type: application/json' \
     -d '{
           "bet_status": "GREEN"
         }' 
```

A resposta deverá ser como a seguinte:
- Status Code: 200 - OK
```json
{
  "idt_bet": 1,
  "des_bet": "Vitória do São Paulo",
  "num_odd": 2.13,
  "dat_created": "2025-07-21T15:16:10.877543705",
  "dat_updated": "2025-07-21T15:26:23.877543705",
  "bet_status": "GREEN",
  "flg_selected": 0
}
```

### Visualização de apostas cadastradas
Para visualizar as apostas cadastradas no banco de dados rode o seguinte comando no terminal:
```sh
curl -X 'GET' \
  'http://localhost:8080/dape/bet' \
  -H 'accept: application/json'
```

A resposta deverá ser como a seguinte:
- Status Code: 200 - OK
```json
{
  "page": 0,
  "size": 10,
  "totalPages": 1,
  "totalElements": 3,
  "betResponseList": [
    {
      "idt_bet": 1,
      "des_bet": "Vitória do São Paulo",
      "num_odd": 2.13,
      "dat_created": "2024-10-29T15:01:34.928002",
      "dat_updated": "2024-10-31T14:47:58.561104",
      "bet_status_enum": "GREEN",
      "flg_selected": 0
    },
    {
      "idt_bet": 2,
      "des_bet": "Vitória do Flamengo",
      "num_odd": 1.32,
      "dat_created": "2024-10-29T15:01:34.929608",
      "dat_updated": "2024-10-29T15:01:34.929608",
      "bet_status_enum": "PENDING",
      "flg_selected": 1
    },
    {
      "idt_bet": 3,
      "des_bet": "Vitória do Palmeiras",
      "num_odd": 1.45,
      "dat_created": "2024-10-29T15:01:34.931167",
      "dat_updated": "2024-10-29T15:01:34.931167",
      "bet_status_enum": "RED",
      "flg_selected": 0
    }
  ]
}
```

É possível realizar a consulta filtrando as apostas pela data de criação e atualização e pelo status. Um exemplo de consulta
com filtro de status e data de criação seria:
```sh
curl -X 'GET' \
  'http://localhost:8080/dape/bet?bet_status=PENDING&dat_created=2024-10-29' \
  -H 'accept: application/json'
```

A resposta deverá ser como a seguinte:
- Status Code: 200 - OK
```json
{
  "page": 0,
  "size": 10,
  "totalPages": 1,
  "totalElements": 1,
  "betResponseList": [
    {
      "idt_bet": 2,
      "des_bet": "Vitória do Flamengo",
      "num_odd": 1.32,
      "dat_created": "2024-10-29T15:01:34.929608",
      "dat_updated": "2024-10-29T15:01:34.929608",
      "bet_status_enum": "PENDING",
      "flg_selected": 1
    }
  ]
}
```
### Exclusão de uma aposta cadastrada
Para excluir uma aposta cadastrada no banco de dados rode o seguinte comando no terminal:
```sh
curl -X 'DELETE' \
  'http://localhost:8080/dape/bet/1' \
  -H 'accept: */*'
```

A resposta deverá ser como a seguinte:
- Status Code: 200 - OK
Aposta excluída




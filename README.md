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
- [Ambiente de desenvolvimento](#ambiente-de-desenvolvimento)
  - [Tecnologias Utilizadas](#tecnologias-utilizadas)
  - [Configurando ambiente de desenvolvimento](#configurando-ambiente-de-desenvolvimento)
  - [Conexão com o banco de dados](#conexão-com-o-banco-de-dados)
  - [Migração e versionamento com Flyway](#migração-e-versionamento-com-flyway)


## Arquitetura
![arquitetura](/src/main/resources/images/arquitetura.png)

## Modelagem do banco de dados
![modelagem](/src/main/resources/images/modelagem.png)

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
popular o banco de dados usando o arquivo [V1.2__INSERTING_DATA_RG_8483.sql](src/main/resources/db/migration/V1.2__INSERTING_DATA_RG_8483.sql)



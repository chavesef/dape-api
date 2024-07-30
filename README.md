# DAPE-API (Declaração de Apostas Esportivas)

Essa API REST lida com transações realizadas simulando casa de apostas.
Ela lida com informações como tipo da aposta, status da aposta, valor apostado,
informações do cliente, entre outros, registrando essas informações  em um 
banco de dados. O tipo da aposta pode ser definido como 'simples' ou 'múltipla',
enquanto o status da aposta é definido como 'em andamento'(pending), 'vencida'(green)
ou 'perdida'(red).



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
- User: DAPE
- Password: pesippar24

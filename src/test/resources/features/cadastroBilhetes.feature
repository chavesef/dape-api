#language: pt

@CadastroBilhete
Funcionalidade: Cadastro de bilhetes de aposta no banco de dados

  Contexto:
    Dado que existam as seguintes apostas cadastradas no banco de dados
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 1        | 2.13    | 0            | 2024-08-15  | 2024-08-15  | Vitória do São Paulo        | PENDING    |
      | 2        | 1.32    | 0            | 2024-08-15  | 2024-08-15  | Vitória do Flamengo         | PENDING    |
      | 3        | 1.45    | 0            | 2024-08-15  | 2024-08-15  | Vitória do Palmeiras        | PENDING    |
      | 4        | 4.12    | 1            | 2024-08-16  | 2024-08-21  | Vitória do Corinthians      | GREEN      |
      | 5        | 1.19    | 0            | 2024-08-17  | 2024-08-17  | Vitória do Real Madrid      | RED        |
      | 6        | 1.56    | 1            | 2024-08-17  | 2024-08-18  | Vitória do Botafogo         | GREEN      |
      | 7        | 2.67    | 0            | 2024-08-18  | 2024-08-18  | Vitória do Bahia            | RED        |
      | 8        | 4.90    | 1            | 2024-08-18  | 2024-08-19  | Vitória do Vitória          | PENDING    |
      | 9        | 2.91    | 0            | 2024-08-20  | 2024-08-21  | Vitória do Grêmio           | PENDING    |
      | 10       | 3.02    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Internacional    | GREEN      |
      | 11       | 2.15    | 0            | 2024-08-20  | 2024-08-22  | Vitória do Cruzeiro         | RED        |
      | 12       | 2.20    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Atlético Mineiro | RED        |

    E os seguintes clientes cadastrados no banco de dados
      | idt_client | nam_client     | num_document   | num_balance | des_email              | num_password | dat_created | dat_updated |
      | 1          | Ted Lasso      | 123.456.789-00 | 200.00      | tlasso@richmond.com    | teste1       | 2024-08-14  | 2024-08-14  |
      | 2          | Rebecca Welton | 123.454.321-00 | 1000.00     | rwelton@richmond.com   | teste2       | 2024-08-14  | 2024-08-14  |
      | 3          | Roy Kent       | 987.654.321-00 | 400.00      | rkent@richmond.com     | teste3       | 2024-08-14  | 2024-08-14  |
      | 4          | Coach Beard    | 555.666.777-88 | 400.00      | cbeard@richmond.com    | teste4       | 2024-08-14  | 2024-08-14  |
      | 5          | Keeley Jones   | 111.222.333-44 | 900.00      | kjones@richmond.com    | teste5       | 2024-08-14  | 2024-08-14  |
      | 6          | Jamie Tartt    | 999.888.777-66 | 800.00      | jtartt@richmond.com    | teste6       | 2024-08-14  | 2024-08-14  |
      | 7          | Nathan Shelley | 121.343.565.77 | 300.00      | nshelley@richmond.com  | teste7       | 2024-08-14  | 2024-08-14  |
      | 8          | Leslie Higgins | 989.767.545-33 | 150.00      | lhiggins@richmond.com  | teste8       | 2024-08-14  | 2024-08-14  |
      | 9          | Sam Obisanya   | 333.222.111-00 | 450.00      | sobisanya@richmond.com | teste9       | 2024-08-14  | 2024-08-14  |
      | 10         | Dani Rojas     | 989.898.989-11 | 500.00      | drojas@richmond.com    | teste10      | 2024-08-14  | 2024-08-14  |

  @CadastroBilheteMultiploSucesso
  Cenario: Cadastrar um novo bilhete múltiplo com sucesso
    Quando uma requisição de criação de bilhetes for realizada com num_ammount 100.01 e idt_client 1 e idt_bet [1, 2, 3]
    Entao o serviço de cadastro de bilhetes deve retornar o status code 201 - "Created"
    E o seguinte bilhete deve ser cadastrado no banco de dados
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 1          | 100.01      | 2024-08-21  | 2024-08-21  | MULTIPLE    | PENDING           | 1          | 4.08          |

  @CadastroBilheteSimplesSucesso
  Cenario: Cadastrar um novo bilhete simples com sucesso
    Quando uma requisição de criação de bilhetes for realizada com num_ammount 99.99 e idt_client 3 e idt_bet [4]
    Entao o serviço de cadastro de bilhetes deve retornar o status code 201 - "Created"
    E o seguinte bilhete deve ser cadastrado no banco de dados
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 1          | 99.99       | 2024-08-21  | 2024-08-21  | SIMPLE      | PENDING           | 3          | 4.12          |

  @CadastroBilheteValorApostaInvalida
  Cenario: Cadastrar um novo bilhete com num_ammount inválido
    Quando uma requisição de criação de bilhetes for realizada com num_ammount -99.99 e idt_client 3 e idt_bet [4]
    Entao o serviço de cadastro de bilhetes deve retornar o status code 400 - "Bad Request"
    E o bilhete não deve ser cadastrado

  @CadastroBilheteClienteInexistente
  Cenario: Cadastrar um novo bilhete com idt_client inexistente
    Quando uma requisição de criação de bilhetes for realizada com num_ammount 99.99 e idt_client 15 e idt_bet [4]
    Entao o serviço de cadastro de bilhetes deve retornar o status code 400 - "Bad Request"
    E o bilhete não deve ser cadastrado

  @CadastroBilheteApostaInexistente
  Cenario: Cadastrar um novo bilhete com idt_bet inexistente
    Quando uma requisição de criação de bilhetes for realizada com num_ammount 99.99 e idt_client 1 e idt_bet [4, 2024]
    Entao o serviço de cadastro de bilhetes deve retornar o status code 400 - "Bad Request"
    E o bilhete não deve ser cadastrado

  @CadastroBilheteServicoIndisponivel
  Cenário: Cadastrar um novo bilhete com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de criação de bilhetes for realizada com num_ammount 99.99 e idt_client 3 e idt_bet [4]
    Então o serviço de cadastro de bilhetes deve retornar o status code 500 - "Internal Server Error"
    E o bilhete não deve ser cadastrado
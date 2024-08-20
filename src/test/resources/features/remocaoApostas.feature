#language: pt

@RemocaoAposta
Funcionalidade: Deletar apostas disponíveis no banco de dados

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

  @RemocaoApostaSucesso
  Cenario: Remoção de uma aposta no banco de dados com sucesso
    Quando uma requisição de remoção de aposta for realizada com o parâmetro "idt_bet" valor 5
    Entao o serviço de remoção deve retornar o status code 200
    E o seguinte dado deve ser deletado da tabela
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 5        | 1.19    | 0            | 2024-08-17  | 2024-08-17  | Vitória do Real Madrid      | RED        |

  @RemocaoApostaInexistente
  Cenario: Remoção de uma aposta no banco de dados com idt_bet inexistente
    Quando uma requisição de remoção de aposta for realizada com o parâmetro "idt_bet" valor 2024
    Entao o serviço de remoção deve retornar o status code 404

  @RemocaoApostaServicoIndisponivel
  Cenário: Remoção de uma aposta com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de remoção de aposta for realizada com o parâmetro "idt_bet" valor 5
    Então o serviço de remoção deve retornar o status code 500

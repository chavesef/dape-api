#language: pt

@AtualizacaoAposta
Funcionalidade: Realizar atualização de apostas disponíveis no banco de dados

  Contexto:
    Dado que existam as seguintes apostas cadastradas no banco de dados
      | idt_bet | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 1       | 2.13    | 0            | 2024-08-15  | 2024-08-15  | Vitória do São Paulo        | PENDING    |
      | 2       | 1.32    | 0            | 2024-08-15  | 2024-08-15  | Vitória do Flamengo         | PENDING    |
      | 3       | 1.45    | 0            | 2024-08-15  | 2024-08-15  | Vitória do Palmeiras        | PENDING    |
      | 4       | 4.12    | 1            | 2024-08-16  | 2024-08-21  | Vitória do Corinthians      | GREEN      |
      | 5       | 1.19    | 0            | 2024-08-17  | 2024-08-17  | Vitória do Real Madrid      | RED        |
      | 6       | 1.56    | 1            | 2024-08-17  | 2024-08-18  | Vitória do Botafogo         | GREEN      |
      | 7       | 2.67    | 0            | 2024-08-18  | 2024-08-18  | Vitória do Bahia            | RED        |
      | 8       | 4.90    | 1            | 2024-08-18  | 2024-08-19  | Vitória do Vitória          | PENDING    |
      | 9       | 2.91    | 0            | 2024-08-20  | 2024-08-21  | Vitória do Grêmio           | PENDING    |
      | 10      | 3.02    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Internacional    | GREEN      |
      | 11      | 2.15    | 0            | 2024-08-20  | 2024-08-22  | Vitória do Cruzeiro         | RED        |
      | 12      | 2.20    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Atlético Mineiro | RED        |

  @AtualizacaoApostaSucesso
  Cenario: Atualização de aposta com sucesso
    Quando uma requisição de atualização de aposta for realizada com odd 3.20 e descrição "Vitória do Juventude" e idt_bet 2
    Entao o serviço de atualização deve retornar o status code 200
    E o seguinte dado deve ser atualizado na tabela
      | idt_bet | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 2       | 3.20    | 0            | 2024-08-15  | 2024-08-16  | Vitória do Juventude        | PENDING    |

  @AtualizacaoApostaInexistente
  Cenario: Atualização de aposta inexistente
    Quando uma requisição de atualização de aposta for realizada com odd 2.20 e descrição "Vitória do Vitória" e idt_bet 2024
    Entao o serviço de atualização deve retornar o status code 404

  @AtualizacaoApostaOddInvalida
  Cenario: Atualização de aposta com odd inválida
    Quando uma requisição de atualização de aposta for realizada com odd -2.20 e descrição "Vitória do Vitória" e idt_bet 2
    Entao o serviço de atualização deve retornar o status code 400

  @AtualizacaoApostaDescricaoInvalida
  Cenario: Atualização de aposta com descricao inválida
    Quando uma requisição de atualização de aposta for realizada com odd 2.20 e descrição "" e idt_bet 2
    Entao o serviço de atualização deve retornar o status code 400

  @AtualizacaoStatusApostaSucesso
  Cenario: Atualização de status de aposta com sucesso
    Quando uma requisição de atualização de aposta for realizada com status "GREEN" e idt_bet 1
    Entao o serviço de atualização deve retornar o status code 200
    E o seguinte dado deve ser atualizado na tabela
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 1        | 2.13    | 0            | 2024-08-15  | 2024-08-15  | Vitória do São Paulo        | GREEN      |

  @AtualizacaoStatusInvalidoAposta
  Cenario: Atualização de status de aposta com status inválido
    Quando uma requisição de atualização de aposta for realizada com status "VENCIDA" e idt_bet 2
    Entao o serviço de cadastro deve retornar o status code 400

  @AtualizacaoStatusApostaInexistente
  Cenario: Atualização de status de aposta inexistente
    Quando uma requisição de atualização de aposta for realizada com status "GREEN" e idt_bet 2024
    Entao o serviço de cadastro deve retornar o status code 404

  @AtualizacaoApostaServicoIndisponivel
  Cenário: Atualização de aposta com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de atualização de aposta for realizada com odd 2.20 e descrição "Vitória do Vitória" e idt_bet 2
    Então o serviço de listagem deve retornar o status code 500

  @AtualizacaoStatusApostaServicoIndisponivel
  Cenário: Atualização de status de aposta com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de atualização de aposta for realizada com status "GREEN" e idt_bet 1
    Então o serviço de atualização deve retornar o status code 500
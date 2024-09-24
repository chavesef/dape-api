#language: pt

@CadastroAposta
Funcionalidade: Realizar o cadastro de novas apostas no banco de dados

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

  @CadastroApostaSucesso
  Cenario: Cadastrar uma nova aposta com sucesso
    Quando uma requisição de criação de aposta for realizada com odd 2,19 e descrição "Vitória do Bahia"
    Entao o serviço de cadastro de apostas deve retornar o status code 201 - "Created"
    E os seguintes dados devem ser cadastrados no banco de dados
      | idt_bet | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 13      | 2.19    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Bahia            | PENDING    |

  @CadastroApostaMesmaDescricaoSucesso
  Cenario: Cadastrar uma nova aposta com descrição já existente para vitória de um time em partidas diferentes com sucesso
    Quando uma requisição de criação de aposta for realizada com odd 1,87 e descrição "Vitória do Botafogo"
    Entao o serviço de cadastro de apostas deve retornar o status code 201 - "Created"
    E os seguintes dados devem ser cadastrados no banco de dados
      | idt_bet | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 14      | 1.87    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Botafogo         | PENDING    |

  @CadastroApostaOddInvalida
  Cenario: Cadastrar uma nova aposta com odd inválida
    Quando uma requisição de criação de aposta for realizada com odd -2,19 e descrição "Vitória do Bahia"
    Entao o serviço de cadastro de apostas deve retornar o status code 400 - "Bad Request"
    E o banco de dados não deve ser modificado
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
      | 13      | 2.19    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Bahia            | PENDING    |
      | 14      | 1.87    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Botafogo         | PENDING    |

  @CadastroApostaDescricaoInvalida
  Cenario: Cadastrar uma nova aposta com descricao invalida
    Quando uma requisição de criação de aposta for realizada com odd 2,19 e descrição ""
    Entao o serviço de cadastro de apostas deve retornar o status code 400 - "Bad Request"
    E o banco de dados não deve ser modificado
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
      | 13      | 2.19    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Bahia            | PENDING    |
      | 14      | 1.87    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Botafogo         | PENDING    |

  @CadastroApostaServicoIndisponivel
  Cenário: Cadastrar uma nova aposta com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de criação de aposta for realizada com odd 2,19 e descrição "Vitória do Bahia"
    Então o serviço de cadastro de apostas deve retornar o status code 500 - "Internal Server Error"
    E o banco de dados não deve ser modificado
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
      | 13      | 2.19    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Bahia            | PENDING    |
      | 14      | 1.87    | 0            | 2024-08-20  | 2024-08-20  | Vitória do Botafogo         | PENDING    |
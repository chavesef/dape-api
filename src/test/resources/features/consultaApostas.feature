#language: pt

@ConsultaAposta
Funcionalidade: Realizar consulta de apostas disponíveis no banco de dados

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

  Esquema do Cenario: Validação de paginação e ordenação de registros
    Dado que uma requisição de consulta com os parametros page <page> seja realizada
    Então devem ser retornados <size> registros para a página <page>
    E o tamanho total da lista deve ser <total_size>
    E o total de páginas deve ser <total_pages>

    Exemplos:
      | page | size | total_size | total_pages |
      | 1    | 10   | 12         | 2           |
      | 2    | 2    | 12         | 2           |
      | 3    | 0    | 12         | 2           |

  @ConsultaApostaSucesso
  Cenario: Consulta de apostas com sucesso
    Quando uma requisição de consulta de apostas for realizada
    Entao o serviço de listagem deve retornar o status code 200
    E os seguintes dados devem ser retornados para a página 1
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

    E os seguintes dados devem ser retornados para a página 2
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 11       | 2.15    | 0            | 2024-08-20  | 2024-08-22  | Vitória do Cruzeiro         | RED        |
      | 12       | 2.20    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Atlético Mineiro | RED        |

  @ConsultaApostaPaginaSucesso
  Cenario: Consulta de apostas por página
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "page" valor "1"
    Entao o serviço de listagem deve retornar o status code 200
    E os seguintes dados devem ser retornados para a página 1
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

  @ConsultaApostaStatusSucesso
  Cenario: Consulta de apostas pelo status
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "bet_status" valor "GREEN"
    Entao o serviço de listagem deve retornar o status code 200
    E os seguintes dados devem ser retornados para a página 1
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 4        | 4.12    | 1            | 2024-08-16  | 2024-08-21  | Vitória do Corinthians      | GREEN      |
      | 6        | 1.56    | 1            | 2024-08-17  | 2024-08-18  | Vitória do Botafogo         | GREEN      |
      | 10       | 3.02    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Internacional    | GREEN      |

  @ConsultaApostaDataCriacaoSucesso
  Cenario: Consulta de apostas pelo dat_created
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "dat_created" valor "2024-08-20"
    Entao o serviço de listagem deve retornar o status code 200
    E os seguintes dados devem ser retornados para a página 1
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 9        | 2.91    | 0            | 2024-08-20  | 2024-08-21  | Vitória do Grêmio           | PENDING    |
      | 10       | 3.02    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Internacional    | GREEN      |
      | 11       | 2.15    | 0            | 2024-08-20  | 2024-08-22  | Vitória do Cruzeiro         | RED        |
      | 12       | 2.20    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Atlético Mineiro | RED        |

  @ConsultaApostaDataAtualizacaoSucesso
  Cenario: Consulta de apostas pelo dat_updated
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "dat_updated" valor "2024-08-18"
    Entao o serviço de listagem deve retornar o status code 200
    E os seguintes dados devem ser retornados para a página 1
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 6        | 1.56    | 1            | 2024-08-17  | 2024-08-18  | Vitória do Botafogo         | GREEN      |
      | 7        | 2.67    | 0            | 2024-08-18  | 2024-08-18  | Vitória do Bahia            | RED        |

  @ConsultaApostaMultiplosParametrosSucesso
  Cenario: Consulta de apostas pelo status e dat_created
    Quando uma requisição de consulta de apostas for realizada com os parâmetros "dat_created" valor "2024-08-20" e "bet_status" valor "RED"
    Entao o serviço de listagem deve retornar o status code 200
    E os seguintes dados devem ser retornados para a página 1
      | idt_bet  | num_odd | flg_selected | dat_created | dat_updated | des_bet                     | bet_status |
      | 11       | 2.15    | 0            | 2024-08-20  | 2024-08-22  | Vitória do Cruzeiro         | RED        |
      | 12       | 2.20    | 1            | 2024-08-20  | 2024-08-20  | Vitória do Atlético Mineiro | RED        |

  @ConsultaApostaDadosInexistentes
  Cenario: Consulta de apostas com dados inexistentes
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "dat_updated" valor "2023-07-20"
    Entao o serviço de listagem deve retornar o status code 200
    E o conteúdo de resposta deve ser vazio

  @ConsultaApostaDadosInvalidos
  Cenario: Consulta de apostas com dados inválidos
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "bet_status" valor "VENCIDA"
    Entao o serviço de listagem deve retornar o status code 400

  @ConsultaApostaServicoIndisponivel
  Cenário: Consulta de apostas com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de consulta de apostas for realizada com os parâmetros "dat_created" valor "2024-08-20" e "bet_status" valor "RED"
    Então o serviço de listagem deve retornar o status code 500
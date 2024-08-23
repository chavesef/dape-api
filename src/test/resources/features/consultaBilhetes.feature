#language: pt

@ConsultaBilhete
Funcionalidade: Realizar consulta de bilhetes cadastrados no banco de dados

  Contexto:
    Dado que existam os seguintes bilhetes cadastrados no banco de dados
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 1          | 100.01      | 2024-08-21  | 2024-08-21  | MULTIPLE    | PENDING           | 1          | 4.08          |
      | 2          | 99.99       | 2024-08-21  | 2024-08-21  | SIMPLE      | PENDING           | 3          | 4.12          |
      | 3          | 12.87       | 2024-08-22  | 2024-08-22  | SIMPLE      | PENDING           | 1          | 3.12          |
      | 4          | 992.99      | 2024-08-23  | 2024-08-23  | SIMPLE      | PENDING           | 4          | 1.35          |
      | 5          | 101.11      | 2024-08-24  | 2024-08-24  | SIMPLE      | GREEN             | 7          | 2.20          |
      | 6          | 50.00       | 2024-08-25  | 2024-08-25  | SIMPLE      | RED               | 9          | 2.19          |
      | 7          | 22.22       | 2024-08-26  | 2024-08-26  | MULTIPLE    | RED               | 10         | 16.79         |
      | 8          | 99.98       | 2024-08-26  | 2024-08-26  | MULTIPLE    | GREEN             | 1          | 4.67          |
      | 9          | 100.02      | 2024-08-26  | 2024-08-26  | MULTIPLE    | GREEN             | 1          | 7.65          |
      | 10         | 7.54        | 2024-08-27  | 2024-08-27  | SIMPLE      | PENDING           | 8          | 4.12          |
      | 11         | 112.12      | 2024-08-28  | 2024-08-28  | MULTIPLE    | PENDING           | 2          | 4.08          |
      | 12         | 12.12       | 2024-08-28  | 2024-08-28  | SIMPLE      | RED               | 6          | 3.32          |

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

  @ConsultaBilheteSucesso
  Cenario: Consultar bilhetes com sucesso
    Quando uma requisição de consulta de bilhetes for realizada
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 1          | 100.01      | 2024-08-21  | 2024-08-21  | MULTIPLE    | PENDING           | 1          | 4.08          |
      | 2          | 99.99       | 2024-08-21  | 2024-08-21  | SIMPLE      | PENDING           | 3          | 4.12          |
      | 3          | 12.87       | 2024-08-22  | 2024-08-22  | SIMPLE      | PENDING           | 1          | 3.12          |
      | 4          | 992.99      | 2024-08-23  | 2024-08-23  | SIMPLE      | PENDING           | 4          | 1.35          |
      | 5          | 101.11      | 2024-08-24  | 2024-08-24  | SIMPLE      | GREEN             | 7          | 2.20          |
      | 6          | 50.00       | 2024-08-25  | 2024-08-25  | SIMPLE      | RED               | 9          | 2.19          |
      | 7          | 22.22       | 2024-08-26  | 2024-08-26  | MULTIPLE    | RED               | 10         | 16.79         |
      | 8          | 99.98       | 2024-08-26  | 2024-08-26  | MULTIPLE    | GREEN             | 1          | 4.67          |
      | 9          | 100.02      | 2024-08-26  | 2024-08-26  | MULTIPLE    | GREEN             | 1          | 7.65          |
      | 10         | 7.54        | 2024-08-27  | 2024-08-27  | SIMPLE      | PENDING           | 8          | 4.12          |

    E os seguintes dados devem ser retornados para a página 2
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 11         | 112.12      | 2024-08-28  | 2024-08-28  | MULTIPLE    | PENDING           | 2          | 4.08          |
      | 12         | 12.12       | 2024-08-28  | 2024-08-28  | SIMPLE      | RED               | 6          | 3.32          |

  @ConsultaBilhetePaginaSucesso
  Cenario: Consultar bilhetes por página
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "page" valor "1"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 1          | 100.01      | 2024-08-21  | 2024-08-21  | MULTIPLE    | PENDING           | 1          | 4.08          |
      | 2          | 99.99       | 2024-08-21  | 2024-08-21  | SIMPLE      | PENDING           | 3          | 4.12          |
      | 3          | 12.87       | 2024-08-22  | 2024-08-22  | SIMPLE      | PENDING           | 1          | 3.12          |
      | 4          | 992.99      | 2024-08-23  | 2024-08-23  | SIMPLE      | PENDING           | 4          | 1.35          |
      | 5          | 101.11      | 2024-08-24  | 2024-08-24  | SIMPLE      | GREEN             | 7          | 2.20          |
      | 6          | 50.00       | 2024-08-25  | 2024-08-25  | SIMPLE      | RED               | 9          | 2.19          |
      | 7          | 22.22       | 2024-08-26  | 2024-08-26  | MULTIPLE    | RED               | 10         | 16.79         |
      | 8          | 99.98       | 2024-08-26  | 2024-08-26  | MULTIPLE    | GREEN             | 1          | 4.67          |
      | 9          | 100.02      | 2024-08-26  | 2024-08-26  | MULTIPLE    | GREEN             | 1          | 7.65          |
      | 10         | 7.54        | 2024-08-27  | 2024-08-27  | SIMPLE      | PENDING           | 8          | 4.12          |

  @ConsultaBilheteStatusSucesso
  Cenario: Consultar bilhetes pelo status
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "ticket_status" valor "RED"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 6          | 50.00       | 2024-08-25  | 2024-08-25  | SIMPLE      | RED               | 9          | 2.19          |
      | 7          | 22.22       | 2024-08-26  | 2024-08-26  | MULTIPLE    | RED               | 10         | 16.79         |
      | 12         | 12.12       | 2024-08-28  | 2024-08-28  | SIMPLE      | RED               | 6          | 3.32          |

  @ConsultaBilheteTipoSucesso
  Cenario: Consultar bilhetes pelo tipo
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "ticket_type" valor "SIMPLE"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 2          | 99.99       | 2024-08-21  | 2024-08-21  | SIMPLE      | PENDING           | 3          | 4.12          |
      | 3          | 12.87       | 2024-08-22  | 2024-08-22  | SIMPLE      | PENDING           | 1          | 3.12          |
      | 4          | 992.99      | 2024-08-23  | 2024-08-23  | SIMPLE      | PENDING           | 4          | 1.35          |
      | 5          | 101.11      | 2024-08-24  | 2024-08-24  | SIMPLE      | GREEN             | 7          | 2.20          |
      | 6          | 50.00       | 2024-08-25  | 2024-08-25  | SIMPLE      | RED               | 9          | 2.19          |
      | 10         | 7.54        | 2024-08-27  | 2024-08-27  | SIMPLE      | PENDING           | 8          | 4.12          |
      | 12         | 12.12       | 2024-08-28  | 2024-08-28  | SIMPLE      | RED               | 6          | 3.32          |

  @ConsultaBilheteClienteSucesso
  Cenario: Consultar bilhetes pelo idt_client
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "idt_client" valor "10"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 7          | 22.22       | 2024-08-26  | 2024-08-26  | MULTIPLE    | RED               | 10         | 16.79         |

  @ConsultaBilheteDataCriacaoSucesso
  Cenario: Consultar bilhetes pelo dat_created
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "dat_created" valor "2024-08-22"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 3          | 12.87       | 2024-08-22  | 2024-08-22  | SIMPLE      | PENDING           | 1          | 3.12          |

  @ConsultaBilheteDataAtualizacaoSucesso
  Cenario: Consultar bilhetes pelo dat_updated
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "dat_updated" valor "2024-08-28"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 11         | 112.12      | 2024-08-28  | 2024-08-28  | MULTIPLE    | PENDING           | 2          | 4.08          |
      | 12         | 12.12       | 2024-08-28  | 2024-08-28  | SIMPLE      | RED               | 6          | 3.32          |

  @ConsultaBilheteMultiplosParametrosSucesso
  Cenario: Consultar bilhetes pelo status e tipo
    Quando uma requisição de consulta de apostas for realizada com os parâmetros "ticket_type" valor "MULTIPLE" e "ticket_status" valor "PENDING"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E os seguintes dados devem ser retornados para a página 1
      | idt_ticket | num_ammount | dat_created | dat_updated | ticket_type | cod_ticket_status | idt_client | num_final_odd |
      | 1          | 100.01      | 2024-08-21  | 2024-08-21  | MULTIPLE    | PENDING           | 1          | 4.08          |
      | 11         | 112.12      | 2024-08-28  | 2024-08-28  | MULTIPLE    | PENDING           | 2          | 4.08          |

  @ConsultaBilheteDadosInexistentes
  Cenario: Consultar bilhetes com dados inexistentes
    Quando uma requisição de consulta de bilhetes for realizada com o parâmetro "idt_client" valor "2024"
    Entao o serviço de listagem deve retornar o status code 200 - "OK"
    E o conteúdo de resposta deve ser vazio

  @ConsultaBilheteDadosInvalidos
  Cenario: Consultar bilhetes com dados inválidos
    Quando uma requisição de consulta de apostas for realizada com o parâmetro "bet_status" valor "VENCIDA"
    Entao o serviço de listagem deve retornar o status code 400 - "Bad Request"

  @ConsultaBilheteServicoIndisponivel
  Cenário: Consultar bilhetes com serviço indisponível
    Dado que o serviço esteja indisponível
    Quando uma requisição de consulta de apostas for realizada com os parâmetros "ticket_type" valor "MULTIPLE" e "ticket_status" valor "PENDING"
    Então o serviço de listagem deve retornar o status code 500 - "Internal Server Error"
proper: (valores elegiveis do no)
proposta: valor dentro do proper q cada no tem
decisao: decisao e decidida pelo valor de proposta do no com maior relogio logico
decisao final: round finalizado, pega o cnocenso
corum: total de propostas recebidas

ENUM proposta {
  CIMA, ESQUERDA, DIREIRA, BAIXO
}

Map mapaDecisao = {
  direita:  2
  esquerda: 1
  cima: 3
}

Map<Valores, NoPropostas>
corum: MAIOR valor de NoPropostas
proper: List de valores do Map
decisao: valor da Entrada no corum

[1, 2, 3, 4]
nodeID  proposta  decisao  corum
1       1         3        []
2       2         3        []
3       3         3        []
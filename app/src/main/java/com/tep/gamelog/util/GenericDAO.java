package com.tep.gamelog.util;

import java.util.List;

// Interface para implemetação de um DAO (Objeto de acesso a dados - Data Access Object)
public interface GenericDAO<O> {
    long inserir(O objeto);
    int excluir(String id);
    List<O> lista();
}

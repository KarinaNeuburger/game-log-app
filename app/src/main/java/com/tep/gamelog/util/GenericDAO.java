package com.tep.gamelog.util;

import java.util.List;


public interface GenericDAO<O> {
    long inserir(O objeto);
    int alterar(O objeto);
    int excluir(String id);
    //O buscar(int id);
    List<O> lista();
}

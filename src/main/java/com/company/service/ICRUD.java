package com.company.service;

import java.util.List;
public interface ICRUD <T, ID> {

    List<T> buscarTodos();

    T buscarPorId(ID id);

    T registrar(T obj);

    void eliminar(ID id);
}


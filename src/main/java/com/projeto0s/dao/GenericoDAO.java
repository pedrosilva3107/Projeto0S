package com.projeto0s.dao;

import java.util.List;

public interface GenericoDAO<T> {
    void salvar(T obj);
    T buscarPorId(int id);
    List<T> listar();
    void remover(int id);
}

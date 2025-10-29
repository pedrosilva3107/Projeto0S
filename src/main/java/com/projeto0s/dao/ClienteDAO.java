package com.projeto0s.dao;

import com.projeto0s.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final List<Cliente> clientes = new ArrayList<>();

    public void adicionar(Cliente c) {
        clientes.add(c);
    }

    public List<Cliente> listar() {
        return clientes;
    }
}

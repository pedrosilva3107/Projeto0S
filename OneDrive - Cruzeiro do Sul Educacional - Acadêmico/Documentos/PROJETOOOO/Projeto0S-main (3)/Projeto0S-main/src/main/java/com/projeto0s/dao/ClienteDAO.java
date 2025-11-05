package com.projeto0s.dao;

import com.projeto0s.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final List<Cliente> clientes = new ArrayList<>();
    private static int idCounter = 1;

    public void adicionar(Cliente cliente) {
        cliente.setId(idCounter++);
        clientes.add(cliente);
    }

    // Alias opcional, se o código chamar "salvar"
    public void salvar(Cliente cliente) {
        adicionar(cliente);
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    public List<Cliente> listarClientes() {
        return listar();
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}

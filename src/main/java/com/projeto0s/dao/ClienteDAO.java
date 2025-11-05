package com.projeto0s.dao;

import com.projeto0s.model.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * DAO responsável por gerenciar os clientes.
 * Utiliza List + Map para persistência e acesso rápido.
 */
public class ClienteDAO implements GenericoDAO<Cliente> {


    private List<Cliente> clientes = new ArrayList<>();
    private Map<Integer, Cliente> clienteMap = new HashMap<>();

    private static final String FILE_NAME = "clientes.json";
    private static int nextId = 1;

    public ClienteDAO() {
        carregar();
    }

    // 🔸 Salvar um novo cliente
    public void salvar(Cliente c) {
        if (c.getId() == 0) {
            c.setId(nextId++);
        }
        clientes.add(c);
        clienteMap.put(c.getId(), c);
        salvarArquivo();
    }

    // 🔸 Compatibilidade com código antigo (usa salvar internamente)
    public void adicionar(Cliente c) {
        salvar(c);
    }

    // 🔸 Listar todos os clientes
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    // 🔸 Método compatível com ListarOSPanel
    public List<Cliente> listarClientes() {
        return listar();
    }

    // 🔸 Buscar por ID usando o Map
    public Cliente buscarPorId(int id) {
        return clienteMap.get(id);
    }

    // 🔸 Remover cliente
    public void remover(int id) {
        Cliente c = clienteMap.remove(id);
        if (c != null) {
            clientes.remove(c);
            salvarArquivo();
        }
    }

    // 🔸 Gravação no JSON
    private void salvarArquivo() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            new Gson().toJson(clientes, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    // 🔸 Leitura do JSON
    private void carregar() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<Cliente>>() {}.getType();
            clientes = new Gson().fromJson(reader, listType);
            if (clientes == null) clientes = new ArrayList<>();

            for (Cliente c : clientes) {
                clienteMap.put(c.getId(), c);
                if (c.getId() >= nextId) nextId = c.getId() + 1;
            }
        } catch (IOException e) {
            System.out.println("Nenhum arquivo de clientes encontrado, iniciando vazio.");
        }
    }
}

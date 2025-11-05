package com.projeto0s.dao;

import com.projeto0s.model.OS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * DAO responsável por gerenciar as Ordens de Serviço (OS).
 * Usa List + Map para melhor desempenho e persistência em JSON.
 */
public class OSDAO implements GenericoDAO<OS> {

    private List<OS> osList = new ArrayList<>();
    private Map<Integer, OS> osMap = new HashMap<>();

    private static final String FILE_NAME = "os.json";
    private static int nextId = 1;

    public OSDAO() {
        carregar();
    }

    // 🔸 Salvar ou atualizar uma OS
    public void salvar(OS os) {
        if (os.getId() == 0) {
            os.setId(nextId++);
        }
        osList.add(os);
        osMap.put(os.getId(), os);
        salvarArquivo();
    }

    // 🔸 Compatibilidade com código antigo
    public void adicionar(OS os) {
        salvar(os);
    }

    // 🔸 Listar todas as OS
    public List<OS> listar() {
        return new ArrayList<>(osList);
    }

    // 🔸 Método compatível com ListarOSPanel
    public List<OS> listarOS() {
        return listar();
    }

    // 🔸 Buscar por ID
    public OS buscarPorId(int id) {
        return osMap.get(id);
    }

    // 🔸 Remover
    public void remover(int id) {
        OS os = osMap.remove(id);
        if (os != null) {
            osList.remove(os);
            salvarArquivo();
        }
    }

    // 🔸 Atualizar status
    public void atualizarStatus(int id, String novoStatus) {
        OS os = osMap.get(id);
        if (os != null) {
            os.setStatus(novoStatus);
            salvarArquivo();
        }
    }

    // 🔸 Listar por status
    public List<OS> listarPorStatus(String status) {
        List<OS> filtradas = new ArrayList<>();
        for (OS os : osList) {
            if (os.getStatus() != null && os.getStatus().equalsIgnoreCase(status)) {
                filtradas.add(os);
            }
        }
        return filtradas;
    }

    // 🔸 Salvar no arquivo JSON
    private void salvarArquivo() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            new Gson().toJson(osList, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar OS: " + e.getMessage());
        }
    }

    // 🔸 Carregar do arquivo JSON
    private void carregar() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<OS>>() {}.getType();
            osList = new Gson().fromJson(reader, listType);
            if (osList == null) osList = new ArrayList<>();

            for (OS os : osList) {
                osMap.put(os.getId(), os);
                if (os.getId() >= nextId) nextId = os.getId() + 1;
            }
        } catch (IOException e) {
            System.out.println("Nenhum arquivo de OS encontrado, iniciando vazio.");
        }
    }
}

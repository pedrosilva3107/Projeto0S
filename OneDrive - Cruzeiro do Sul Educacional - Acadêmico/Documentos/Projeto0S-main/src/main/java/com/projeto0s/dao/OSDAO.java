package com.projeto0s.dao;

import com.projeto0s.model.Cliente;
import com.projeto0s.model.OS;
import java.util.ArrayList;
import java.util.List;

public class OSDAO {

    private static final List<OS> listaOS = new ArrayList<>();
    private static int idCounter = 1;

    // Adiciona uma nova OS
    public void adicionar(OS os) {
        osDAOSetId(os);
        listaOS.add(os);
    }

    // Alias opcional (para compatibilidade com nomes diferentes)
    public void salvar(OS os) {
        adicionar(os);
    }

    // Retorna todas as OS
    public List<OS> listar() {
        return new ArrayList<>(listaOS);
    }

    // Alias usado por alguns painéis
    public List<OS> listarOS() {
        return listar();
    }

    // Lista apenas OS com um determinado status
    public List<OS> listarPorStatus(String status) {
        List<OS> filtradas = new ArrayList<>();
        for (OS os : listaOS) {
            if (os.getStatus().equalsIgnoreCase(status)) {
                filtradas.add(os);
            }
        }
        return filtradas;
    }

    // Atualiza status de uma OS por ID
    public void atualizarStatus(int id, String novoStatus) {
        for (OS os : listaOS) {
            if (os.getId() == id) {
                os.setStatus(novoStatus);
                break;
            }
        }
    }

    // Busca uma OS por ID
    public OS buscarPorId(int id) {
        for (OS os : listaOS) {
            if (os.getId() == id) return os;
        }
        return null;
    }

    // --- método auxiliar interno ---
    private void osDAOSetId(OS os) {
        try {
            var field = OS.class.getDeclaredField("id");
            field.setAccessible(true);
            field.setInt(os, idCounter++);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao definir ID da OS", e);
        }
    }
}

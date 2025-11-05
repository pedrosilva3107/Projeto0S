package com.projeto0s.test;

import com.projeto0s.dao.ClienteDAO;
import com.projeto0s.model.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDAOTest {

    @Test
    void testSalvarEListarCliente() {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = new Cliente("Teste", "12345678900", "Rua A");
        dao.salvar(c);

        assertTrue(dao.listar().size() > 0);
    }

    @Test
    void testBuscarPorId() {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = new Cliente("João", "98765432100", "Rua B");
        dao.salvar(c);

        Cliente encontrado = dao.buscarPorId(c.getId());
        assertNotNull(encontrado);
        assertEquals("João", encontrado.getNome());
    }

    @Test
    void testRemoverCliente() {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = new Cliente("Maria", "55555555555", "Rua C");
        dao.salvar(c);

        dao.remover(c.getId());
        assertNull(dao.buscarPorId(c.getId()));
    }
}

package com.projeto0s.dao;

import com.projeto0s.model.OS;
import java.util.ArrayList;
import java.util.List;

public class OSDAO {
    private final List<OS> ordens = new ArrayList<>();

    public void adicionar(OS os) { ordens.add(os); }
    public List<OS> listar() { return ordens; }
}

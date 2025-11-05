package com.projeto0s.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T lerArquivo(String caminho, Class<T> clazz) {
        try {
            File f = new File(caminho);
            if (!f.exists()) return null;
            try (FileReader reader = new FileReader(f)) {
                return gson.fromJson(reader, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void salvarArquivo(String caminho, Object objeto) {
        try {
            File f = new File(caminho);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(f)) {
                gson.toJson(objeto, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

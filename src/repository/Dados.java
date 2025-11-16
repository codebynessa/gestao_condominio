/**
 * Autor: Vanessa de Freitas Ferreira
 * Data: 15/11/2025
 * Projeto: SeuProjetoAqui
 * Descrição:
 */
package repository;

import model.SistemaCondominio;

import java.io.*;

public class Dados {
    private static final String FILE_NAME = "sistema.dat";

    public static void salvar(SistemaCondominio sistema) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(sistema);
            System.out.println(" Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println(" Erro ao salvar: " + e.getMessage());
        }
    }

    public static SistemaCondominio carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SistemaCondominio sistema = (SistemaCondominio) ois.readObject();
            System.out.println(" Dados carregados com sucesso!");
            return sistema;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("️ Erro ao carregar: " + e.getMessage());
            return new SistemaCondominio();
        }
    }
}

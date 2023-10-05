package dev.diego;

import dev.diego.controllers.ProcesadorCsv;
import dev.diego.models.Funkos;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ProcesadorCsv pCsv=ProcesadorCsv.getInstancia();
        ExecutorService ex= Executors.newFixedThreadPool(5);

        try {
            Future<List<Funkos>> lista=ex.submit(pCsv);
            lista.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        ex.shutdown();
    }
}
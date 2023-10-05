package dev.diego.controllers;

import dev.diego.enunms.Modelo;
import dev.diego.models.Funkos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcesadorCsv implements Callable<List<Funkos>> {
    /**
     * instancia Procesador
     */
    private static ProcesadorCsv instancia;

    /**
     * path a la carpeta foco del procesador
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    /**
     * nombre del archivo csv creado
     */
    private static final String csvFile="funkos.csv";

    /**
     * devuelve la instancia del procesador, si esta es null la inicia
     * @return ProcesadorCsv
     */
    public static ProcesadorCsv getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorCsv();
        }
        return instancia;
    }
    @Override
    public List<Funkos> call() throws Exception {
        String archivoCsv=dataPath+File.separator+csvFile;
        File f=new File(archivoCsv);

        List<Funkos> res=new ArrayList<>();

        ExecutorService executorService= Executors.newFixedThreadPool(5);

        if (f.exists())
            System.out.println("Leyendo el archivo csv");

        try(BufferedReader fr= new BufferedReader(new FileReader(f))){
            fr.readLine();//leer linea encabezados
            while(fr.ready()){
                String[] procesar =fr.readLine().split(",");
                UUID cod;
                try{
                    cod=UUID.fromString(procesar[0]);
                }catch (IllegalArgumentException e){
                    cod=UUID.randomUUID();
                }
                String name=procesar[1];//si diera NullPointerException la lanza y la procesaremos despues
                Modelo modelo=Modelo.valueOf(procesar[2]);
                double precio=Double.parseDouble(procesar[3]);
                LocalDate fechaLanzamiento=LocalDate.parse(procesar[4], DateTimeFormatter.ISO_LOCAL_DATE);
                res.add(new Funkos(cod,name,modelo,precio,fechaLanzamiento));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return res;
    }
}

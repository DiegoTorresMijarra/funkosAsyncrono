package dev.diego.models;

import dev.diego.enunms.Modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record Funkos(UUID cod, String nombre, Modelo modelo, Double precio, LocalDate FechaLanzamiento) {
    @Override
    public String toString() {
        return "Funkos{" +
                "cod=" + cod +
                ", nombre='" + nombre + '\'' +
                ", modelo=" + modelo +
                ", precio=" + precio +
                ", FechaLanzamiento=" + FechaLanzamiento +
                '}';
    }
}

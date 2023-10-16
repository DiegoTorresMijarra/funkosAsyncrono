package dev.diego.models;

import dev.diego.enunms.Modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase Funkos que representa los funkos que se van a procesar.
 * @param cod El código UUID del Funko.
 * @param nombre El nombre del Funko.
 * @param modelo El modelo al que pertenece el Funko (e.g., MARVEL, DISNEY, ANIME, OTROS).
 * @param precio El precio del Funko en euros con dos decimales.
 * @param FechaLanzamiento La fecha de lanzamiento del Funko en formato ISO-8601 (YYYY-MM-DD).
 */
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

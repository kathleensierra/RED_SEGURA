package modelo;

import java.util.Objects;

/**
 * Representa una conexion (arista o arco) en el grafo.
 */
public class Conexion {
    private final String id;
    private final String origen;
    private final String destino;
    private final boolean dirigida;
    private final int peso;
    private final String etiqueta;

    public Conexion(String id, String origen, String destino, boolean dirigida, int peso, String etiqueta) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la conexion no puede ser nulo o vacio");
        }
        if (origen == null || origen.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen de la conexion no puede ser nulo o vacio");
        }
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino de la conexion no puede ser nulo o vacio");
        }
        if (peso < 0) {
            throw new IllegalArgumentException("El peso de la conexion no puede ser negativo");
        }
        if (etiqueta == null || etiqueta.trim().isEmpty()) {
            throw new IllegalArgumentException("La etiqueta de la conexion no puede ser nula o vacia");
        }

        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.dirigida = dirigida;
        this.peso = peso;
        this.etiqueta = etiqueta;
    }

    public String getId() {
        return id;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public boolean esDirigida() {
        return dirigida;
    }

    public int getPeso() {
        return peso;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conexion conexion = (Conexion) o;
        return Objects.equals(id, conexion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String tipo = dirigida ? "Dirigida" : "No dirigida";
        return id + ": " + origen + " -> " + destino + " (" + peso + " min) [" + tipo + "] - " + etiqueta;
    }
}

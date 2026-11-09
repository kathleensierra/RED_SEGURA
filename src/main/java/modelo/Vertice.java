package modelo;
import java.util.Objects;

/**
 * Representa un vertice en el grafo de RedSegura.
 */
public class Vertice implements Comparable<Vertice> {
    private final String id;
    private final String etiqueta;
    private final String tipo;

    public Vertice(String id, String etiqueta, String tipo) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del vertice no puede ser nulo o vacio");
        }
        if (etiqueta == null || etiqueta.trim().isEmpty()) {
            throw new IllegalArgumentException("La etiqueta del vertice no puede ser nula o vacia");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo del vertice no puede ser nulo o vacio");
        }

        this.id = id;
        this.etiqueta = etiqueta;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(id, vertice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Vertice otro) {
        return this.id.compareTo(otro.id);
    }

    @Override
    public String toString() {
        return id + " - " + etiqueta + " (" + tipo + ")";
    }
}
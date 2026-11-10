package grafos;

import java.util.*;
import modelo.Conexion;
import modelo.Vertice;

/**
 * Representa un grafo mixto (con aristas dirigidas y no dirigidas).
 * Permite agregar y eliminar vertices y conexiones dinamicamente.
 */
public class GrafoMixto {
    private final Map<String, Vertice> vertices;
    private final Map<String, Conexion> conexiones;
    private final Map<String, List<Conexion>> listaAdyacencia;

    public GrafoMixto() {
        this.vertices = new LinkedHashMap<>();
        this.conexiones = new LinkedHashMap<>();
        this.listaAdyacencia = new LinkedHashMap<>();
    }

    /**
     * Agrega un vertice al grafo.
     */
    public void agregarVertice(Vertice vertice) {
        if (vertice == null) {
            throw new IllegalArgumentException("El vertice no puede ser nulo");
        }
        if (vertices.containsKey(vertice.getId())) {
            throw new IllegalArgumentException("Ya existe un vertice con ID: " + vertice.getId());
        }
        vertices.put(vertice.getId(), vertice);
        listaAdyacencia.put(vertice.getId(), new LinkedList<>());
    }

    /**
     * Elimina un vertice y todas sus conexiones asociadas.
     */
    public void eliminarVertice(String id) {
        if (!vertices.containsKey(id)) {
            throw new IllegalArgumentException("El vertice no existe: " + id);
        }

        List<String> conexionesAEliminar = new LinkedList<>();
        for (Conexion conexion : conexiones.values()) {
            if (conexion.getOrigen().equals(id) || conexion.getDestino().equals(id)) {
                conexionesAEliminar.add(conexion.getId());
            }
        }

        for (String idConexion : conexionesAEliminar) {
            eliminarConexion(idConexion);
        }

        vertices.remove(id);
        listaAdyacencia.remove(id);
    }

    /**
     * Agrega una conexion al grafo.
     */
    public void agregarConexion(Conexion conexion) {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexion no puede ser nula");
        }
        if (conexiones.containsKey(conexion.getId())) {
            throw new IllegalArgumentException("Ya existe una conexion con ID: " + conexion.getId());
        }
        if (!vertices.containsKey(conexion.getOrigen())) {
            throw new IllegalArgumentException("El vertice origen no existe: " + conexion.getOrigen());
        }
        if (!vertices.containsKey(conexion.getDestino())) {
            throw new IllegalArgumentException("El vertice destino no existe: " + conexion.getDestino());
        }
        if (conexion.getPeso() < 0) {
            throw new IllegalArgumentException("El peso de la conexion no puede ser negativo: " + conexion.getPeso());
        }

        conexiones.put(conexion.getId(), conexion);
        listaAdyacencia.get(conexion.getOrigen()).add(conexion);

        if (!conexion.esDirigida()) {
            Conexion inversa = new Conexion(
                conexion.getId() + "_inv",
                conexion.getDestino(),
                conexion.getOrigen(),
                false,
                conexion.getPeso(),
                conexion.getEtiqueta()
            );
            listaAdyacencia.get(conexion.getDestino()).add(inversa);
        }
    }

    /**
     * Elimina una conexion del grafo.
     */
    public void eliminarConexion(String id) {
        if (!conexiones.containsKey(id)) {
            throw new IllegalArgumentException("La conexion no existe: " + id);
        }
        Conexion conexion = conexiones.remove(id);

        listaAdyacencia.get(conexion.getOrigen()).removeIf(c -> c.getId().equals(id));

        if (!conexion.esDirigida()) {
            listaAdyacencia.get(conexion.getDestino()).removeIf(c -> c.getId().equals(id + "_inv"));
        }
    }

    /**
     * Bloquea una conexion (alias de eliminar).
     */
    public void bloquearConexion(String id) {
        eliminarConexion(id);
    }

    public Vertice obtenerVertice(String id) {
        return vertices.get(id);
    }

    public Collection<Vertice> obtenerVertices() {
        return new LinkedList<>(vertices.values());
    }

    public Collection<Conexion> obtenerConexiones() {
        return new LinkedList<>(conexiones.values());
    }

    public List<Conexion> obtenerVecinos(String idVertice) {
        if (!vertices.containsKey(idVertice)) {
            throw new IllegalArgumentException("El vertice no existe: " + idVertice);
        }
        return new LinkedList<>(listaAdyacencia.get(idVertice));
    }

    public boolean existeVertice(String id) {
        return vertices.containsKey(id);
    }

    public boolean existeConexion(String id) {
        return conexiones.containsKey(id);
    }

    public int getNumeroVertices() {
        return vertices.size();
    }

    public int getNumeroConexiones() {
        return conexiones.size();
    }
}
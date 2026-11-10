package algoritmos;


import grafos.GrafoMixto;
import java.util.*;
import modelo.Conexion;

/**
 * Implementacion de Busqueda en Anchura (BFS).
 */
public class BusquedaAnchura {
    private final GrafoMixto grafo;

    public BusquedaAnchura(GrafoMixto grafo) {
        this.grafo = grafo;
    }

    /**
     * Realiza BFS desde un vertice origen.
     */
    public List<String> recorrer(String origen) {
        if (!grafo.existeVertice(origen)) {
            throw new IllegalArgumentException("El vertice origen no existe: " + origen);
        }

        List<String> resultado = new LinkedList<>();
        Set<String> visitados = new LinkedHashSet<>();
        Queue<String> cola = new LinkedList<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            resultado.add(actual);

            for (Conexion conexion : grafo.obtenerVecinos(actual)) {
                String vecino = conexion.getDestino();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return resultado;
    }

    /**
     * Construye el arbol BFS desde un origen.
     */
    public Map<String, String> construirArbolBFS(String origen) {
        if (!grafo.existeVertice(origen)) {
            throw new IllegalArgumentException("El vertice origen no existe: " + origen);
        }

        Map<String, String> arbol = new LinkedHashMap<>();
        Set<String> visitados = new LinkedHashSet<>();
        Queue<String> cola = new LinkedList<>();

        cola.add(origen);
        visitados.add(origen);
        arbol.put(origen, null);

        while (!cola.isEmpty()) {
            String actual = cola.poll();

            for (Conexion conexion : grafo.obtenerVecinos(actual)) {
                String vecino = conexion.getDestino();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                    arbol.put(vecino, actual);
                }
            }
        }

        return arbol;
    }
}

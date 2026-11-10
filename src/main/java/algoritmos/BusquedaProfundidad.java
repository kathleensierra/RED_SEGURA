package algoritmos;



import grafos.GrafoMixto;
import java.util.*;
import modelo.Conexion;

/**
 * Implementacion de Busqueda en Profundidad (DFS).
 */
public class BusquedaProfundidad {
    private final GrafoMixto grafo;

    public BusquedaProfundidad(GrafoMixto grafo) {
        this.grafo = grafo;
    }

    /**
     * Realiza DFS recursivo desde un vertice origen.
     */
    public List<String> recorrer(String origen) {
        if (!grafo.existeVertice(origen)) {
            throw new IllegalArgumentException("El vertice origen no existe: " + origen);
        }

        List<String> resultado = new LinkedList<>();
        Set<String> visitados = new LinkedHashSet<>();
        dfsRecursivo(origen, visitados, resultado);
        return resultado;
    }

    private void dfsRecursivo(String actual, Set<String> visitados, List<String> resultado) {
        visitados.add(actual);
        resultado.add(actual);

        for (Conexion conexion : grafo.obtenerVecinos(actual)) {
            String vecino = conexion.getDestino();
            if (!visitados.contains(vecino)) {
                dfsRecursivo(vecino, visitados, resultado);
            }
        }
    }

    /**
     * Detecta si existe un ciclo en el grafo.
     */
    public boolean detectarCiclos() {
        Set<String> visitados = new LinkedHashSet<>();
        Set<String> enPila = new LinkedHashSet<>();

        for (String vertice : obtenerTodosVertices()) {
            if (!visitados.contains(vertice)) {
                if (detectarCicloDFS(vertice, visitados, enPila)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean detectarCicloDFS(String actual, Set<String> visitados, Set<String> enPila) {
        visitados.add(actual);
        enPila.add(actual);

        for (Conexion conexion : grafo.obtenerVecinos(actual)) {
            String vecino = conexion.getDestino();
            if (!visitados.contains(vecino)) {
                if (detectarCicloDFS(vecino, visitados, enPila)) {
                    return true;
                }
            } else if (enPila.contains(vecino)) {
                return true;
            }
        }

        enPila.remove(actual);
        return false;
    }

    /**
     * Encuentra componentes debiles del grafo.
     */
    public List<Set<String>> encontrarComponentesDebiles() {
        List<Set<String>> componentes = new LinkedList<>();
        Set<String> visitados = new LinkedHashSet<>();

        for (String vertice : obtenerTodosVertices()) {
            if (!visitados.contains(vertice)) {
                Set<String> componente = new LinkedHashSet<>();
                dfsComponente(vertice, visitados, componente);
                componentes.add(componente);
            }
        }

        return componentes;
    }

    private void dfsComponente(String actual, Set<String> visitados, Set<String> componente) {
        visitados.add(actual);
        componente.add(actual);

        for (Conexion conexion : grafo.obtenerVecinos(actual)) {
            String vecino = conexion.getDestino();
            if (!visitados.contains(vecino)) {
                dfsComponente(vecino, visitados, componente);
            }
        }
    }

    private List<String> obtenerTodosVertices() {
        List<String> vertices = new LinkedList<>();
        for (var v : grafo.obtenerVertices()) {
            vertices.add(v.getId());
        }
        return vertices;
    }
}
package grafos;

import java.util.*;
import modelo.Conexion;
import modelo.Vertice;

/**
 * Proporciona diferentes representaciones del grafo.
 */
public class RepresentacionesGrafo {
    private final GrafoMixto grafo;

    public RepresentacionesGrafo(GrafoMixto grafo) {
        this.grafo = grafo;
    }

    /**
     * Genera la lista de adyacencia.
     */
    public Map<String, List<String>> obtenerListaAdyacencia() {
        Map<String, List<String>> lista = new LinkedHashMap<>();

        for (Vertice vertice : grafo.obtenerVertices()) {
            List<String> vecinos = new LinkedList<>();
            for (Conexion conexion : grafo.obtenerVecinos(vertice.getId())) {
                vecinos.add(conexion.getDestino());
            }
            lista.put(vertice.getId(), vecinos);
        }

        return lista;
    }

    /**
     * Genera la matriz de adyacencia ponderada.
     */
    public int[][] obtenerMatrizAdyacencia() {
        List<Vertice> vertices = new LinkedList<>(grafo.obtenerVertices());
        int n = vertices.size();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(matriz[i], -1);
            matriz[i][i] = 0;
        }

        Map<String, Integer> indiceMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indiceMap.put(vertices.get(i).getId(), i);
        }

        for (Conexion conexion : grafo.obtenerConexiones()) {
            int origen = indiceMap.get(conexion.getOrigen());
            int destino = indiceMap.get(conexion.getDestino());
            matriz[origen][destino] = conexion.getPeso();

            if (!conexion.esDirigida()) {
                matriz[destino][origen] = conexion.getPeso();
            }
        }

        return matriz;
    }

    /**
     * Genera la matriz de incidencia.
     */
    public int[][] obtenerMatrizIncidencia() {
        List<Vertice> vertices = new LinkedList<>(grafo.obtenerVertices());
        List<Conexion> conexiones = new LinkedList<>(grafo.obtenerConexiones());

        int numVertices = vertices.size();
        int numConexiones = conexiones.size();
        int[][] matriz = new int[numVertices][numConexiones];

        Map<String, Integer> verticeIndice = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            verticeIndice.put(vertices.get(i).getId(), i);
        }

        for (int j = 0; j < numConexiones; j++) {
            Conexion conexion = conexiones.get(j);
            int origen = verticeIndice.get(conexion.getOrigen());
            int destino = verticeIndice.get(conexion.getDestino());

            if (conexion.esDirigida()) {
                matriz[origen][j] = 1;
                matriz[destino][j] = -1;
            } else {
                matriz[origen][j] = 1;
                matriz[destino][j] = 1;
            }
        }

        return matriz;
    }

    public void imprimirListaAdyacencia() {
        System.out.println("=== LISTA DE ADYACENCIA ===");
        Map<String, List<String>> lista = obtenerListaAdyacencia();
        for (Map.Entry<String, List<String>> entrada : lista.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
        System.out.println();
    }

    public void imprimirMatrizAdyacencia() {
        System.out.println("=== MATRIZ DE ADYACENCIA ===");
        int[][] matriz = obtenerMatrizAdyacencia();
        List<Vertice> vertices = new LinkedList<>(grafo.obtenerVertices());

        System.out.print("    ");
        for (Vertice v : vertices) {
            System.out.printf("%4s", v.getId());
        }
        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%4s", vertices.get(i).getId());
            for (int j = 0; j < vertices.size(); j++) {
                System.out.printf("%4d", matriz[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void imprimirMatrizIncidencia() {
        System.out.println("=== MATRIZ DE INCIDENCIA ===");
        int[][] matriz = obtenerMatrizIncidencia();
        List<Vertice> vertices = new LinkedList<>(grafo.obtenerVertices());
        List<Conexion> conexiones = new LinkedList<>(grafo.obtenerConexiones());

        System.out.print("    ");
        for (Conexion c : conexiones) {
            System.out.printf("%4s", c.getId());
        }
        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%4s", vertices.get(i).getId());
            for (int j = 0; j < conexiones.size(); j++) {
                System.out.printf("%4d", matriz[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
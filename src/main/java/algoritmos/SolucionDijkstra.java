package algoritmos;


import grafos.GrafoMixto;
import java.util.*;
import modelo.Conexion;

/**
 * Implementacion del algoritmo de Dijkstra para encontrar rutas minimas.
 */
public class SolucionDijkstra {
    private final GrafoMixto grafo;

    public SolucionDijkstra(GrafoMixto grafo) {
        this.grafo = grafo;
    }

    /**
     * Encuentra la ruta minima desde origen hasta todos los vertices alcanzables.
     */
    public Map<String, Integer> calcularDistancias(String origen) {
        if (!grafo.existeVertice(origen)) {
            throw new IllegalArgumentException("El vertice origen no existe: " + origen);
        }

        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        PriorityQueue<NodoDijkstra> cola = new PriorityQueue<>();

        for (var vertice : grafo.obtenerVertices()) {
            distancias.put(vertice.getId(), Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);

        cola.add(new NodoDijkstra(origen, 0));

        while (!cola.isEmpty()) {
            NodoDijkstra actual = cola.poll();
            String verticeActual = actual.vertice;
            int distanciaActual = actual.distancia;

            if (distanciaActual > distancias.get(verticeActual)) {
                continue;
            }

            for (Conexion conexion : grafo.obtenerVecinos(verticeActual)) {
                String vecino = conexion.getDestino();
                int nuevaDistancia = distanciaActual + conexion.getPeso();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, verticeActual);
                    cola.add(new NodoDijkstra(vecino, nuevaDistancia));
                }
            }
        }

        return distancias;
    }

    /**
     * Encuentra la ruta minima entre dos vertices y reconstruye el camino.
     */
    public ResultadoDijkstra encontrarRutaMinima(String origen, String destino) {
        if (!grafo.existeVertice(origen)) {
            throw new IllegalArgumentException("El vertice origen no existe: " + origen);
        }
        if (!grafo.existeVertice(destino)) {
            throw new IllegalArgumentException("El vertice destino no existe: " + destino);
        }

        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        PriorityQueue<NodoDijkstra> cola = new PriorityQueue<>();

        for (var vertice : grafo.obtenerVertices()) {
            distancias.put(vertice.getId(), Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);

        cola.add(new NodoDijkstra(origen, 0));

        while (!cola.isEmpty()) {
            NodoDijkstra actual = cola.poll();
            String verticeActual = actual.vertice;

            if (verticeActual.equals(destino)) {
                break;
            }

            if (actual.distancia > distancias.get(verticeActual)) {
                continue;
            }

            for (Conexion conexion : grafo.obtenerVecinos(verticeActual)) {
                String vecino = conexion.getDestino();
                int nuevaDistancia = actual.distancia + conexion.getPeso();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, verticeActual);
                    cola.add(new NodoDijkstra(vecino, nuevaDistancia));
                }
            }
        }

        List<String> camino = new LinkedList<>();
        if (distancias.get(destino) == Integer.MAX_VALUE) {
            return new ResultadoDijkstra(camino, -1);
        }

        String actual = destino;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }

        return new ResultadoDijkstra(camino, distancias.get(destino));
    }

    private static class NodoDijkstra implements Comparable<NodoDijkstra> {
        String vertice;
        int distancia;

        NodoDijkstra(String vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(NodoDijkstra otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }

    public static class ResultadoDijkstra {
        private final List<String> camino;
        private final int distanciaTotal;

        public ResultadoDijkstra(List<String> camino, int distanciaTotal) {
            this.camino = camino;
            this.distanciaTotal = distanciaTotal;
        }

        public List<String> getCamino() {
            return camino;
        }

        public int getDistanciaTotal() {
            return distanciaTotal;
        }

        public boolean tieneCamino() {
            return !camino.isEmpty() && distanciaTotal >= 0;
        }

        @Override
        public String toString() {
            if (!tieneCamino()) {
                return "No existe camino";
            }
            return String.join(" -> ", camino) + " [" + distanciaTotal + " min]";
        }
    }
}
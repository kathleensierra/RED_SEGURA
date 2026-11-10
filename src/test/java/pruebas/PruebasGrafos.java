package pruebas;

import algoritmos.BusquedaAnchura;
import algoritmos.BusquedaProfundidad;
import grafos.RepresentacionesGrafo;
import algoritmos.SolucionDijkstra;
import aplicacion.FabricaDatos;
import grafos.GrafoMixto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import modelo.Conexion;
import modelo.Vertice;

import static org.junit.jupiter.api.Assertions.*;

class PruebasGrafos {
    private GrafoMixto grafo;

    @BeforeEach
    void setUp() {
        grafo = FabricaDatos.crearGrafoRedSegura();
    }

    // --- CASOS NORMALES ---
    @Test
    void testAgregarVertice() {
        assertEquals(12, grafo.getNumeroVertices());
        assertTrue(grafo.existeVertice("S01"));
    }

    @Test
    void testAgregarConexion() {
        assertEquals(18, grafo.getNumeroConexiones());
        assertTrue(grafo.existeConexion("R01"));
    }

    @Test
    void testListaAdyacencia() {
        RepresentacionesGrafo rep = new RepresentacionesGrafo(grafo);
        Map<String, List<String>> lista = rep.obtenerListaAdyacencia();
        assertFalse(lista.isEmpty());
        assertTrue(lista.containsKey("S01"));
    }

    @Test
    void testMatrizAdyacencia() {
        RepresentacionesGrafo rep = new RepresentacionesGrafo(grafo);
        int[][] matriz = rep.obtenerMatrizAdyacencia();
        assertEquals(12, matriz.length);
    }

    @Test
    void testMatrizIncidencia() {
        RepresentacionesGrafo rep = new RepresentacionesGrafo(grafo);
        int[][] matriz = rep.obtenerMatrizIncidencia();
        assertEquals(12, matriz.length);
        assertEquals(18, matriz[0].length);
    }

    @Test
    void testBFS() {
        BusquedaAnchura bfs = new BusquedaAnchura(grafo);
        List<String> resultado = bfs.recorrer("S02");
        assertFalse(resultado.isEmpty());
        assertEquals("S02", resultado.get(0));
    }

    @Test
    void testDFS() {
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);
        List<String> resultado = dfs.recorrer("S02");
        assertFalse(resultado.isEmpty());
        assertEquals("S02", resultado.get(0));
    }

    @Test
    void testDijkstraRutaMinima() {
        SolucionDijkstra dijkstra = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra resultado = dijkstra.encontrarRutaMinima("S02", "S08");
        assertTrue(resultado.tieneCamino());
        assertEquals(24, resultado.getDistanciaTotal());
    }

    // --- VERTICE INEXISTENTE ---
    @Test
    void testVerticeInexistenteBFS() {
        BusquedaAnchura bfs = new BusquedaAnchura(grafo);
        assertThrows(IllegalArgumentException.class, () -> bfs.recorrer("S99"));
    }

    @Test
    void testVerticeInexistenteDFS() {
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);
        assertThrows(IllegalArgumentException.class, () -> dfs.recorrer("S99"));
    }

    @Test
    void testVerticeInexistenteDijkstra() {
        SolucionDijkstra d = new SolucionDijkstra(grafo);
        assertThrows(IllegalArgumentException.class, () -> d.encontrarRutaMinima("S99", "S08"));
    }

    @Test
    void testEliminarVerticeInexistente() {
        assertThrows(IllegalArgumentException.class, () -> grafo.eliminarVertice("S99"));
    }

    @Test
    void testEliminarConexionInexistente() {
        assertThrows(IllegalArgumentException.class, () -> grafo.eliminarConexion("R99"));
    }

    // --- GRAFO DESCONECTADO ---
    @Test
    void testDijkstraGrafoDesconectado() {
        GrafoMixto g = new GrafoMixto();
        g.agregarVertice(new Vertice("A", "A", "Test"));
        g.agregarVertice(new Vertice("B", "B", "Test"));

        SolucionDijkstra d = new SolucionDijkstra(g);
        SolucionDijkstra.ResultadoDijkstra resultado = d.encontrarRutaMinima("A", "B");
        assertFalse(resultado.tieneCamino());
        assertEquals(-1, resultado.getDistanciaTotal());
    }

    @Test
    void testBFSGrafoDesconectado() {
        GrafoMixto g = new GrafoMixto();
        g.agregarVertice(new Vertice("A", "A", "Test"));
        g.agregarVertice(new Vertice("B", "B", "Test"));

        BusquedaAnchura bfs = new BusquedaAnchura(g);
        List<String> resultado = bfs.recorrer("A");
        assertEquals(1, resultado.size());
    }

    // --- PESOS INVALIDOS ---
    @Test
    void testPesoNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
            new Conexion("R99", "S01", "S02", true, -5, "Invalida")
        );
    }

    @Test
    void testAgregarConexionConPesoNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
            grafo.agregarConexion(new Conexion("R99", "S01", "S02", true, -1, "Negativa"))
        );
    }

    // --- INSERCION Y ELIMINACION DINAMICA ---
    @Test
    void testInsertarYEliminarVertice() {
        int antes = grafo.getNumeroVertices();
        Vertice nuevo = new Vertice("S13", "Nuevo Lugar", "Test");
        grafo.agregarVertice(nuevo);
        assertEquals(antes + 1, grafo.getNumeroVertices());

        grafo.eliminarVertice("S13");
        assertEquals(antes, grafo.getNumeroVertices());
        assertFalse(grafo.existeVertice("S13"));
    }

    @Test
    void testInsertarYEliminarConexion() {
        int antes = grafo.getNumeroConexiones();
        Conexion nueva = new Conexion("R19", "S01", "S02", true, 5, "Nueva");
        grafo.agregarConexion(nueva);
        assertEquals(antes + 1, grafo.getNumeroConexiones());

        grafo.eliminarConexion("R19");
        assertEquals(antes, grafo.getNumeroConexiones());
        assertFalse(grafo.existeConexion("R19"));
    }

    @Test
    void testEliminarVerticeEliminaConexionesAsociadas() {
        grafo.eliminarVertice("S01");
        assertFalse(grafo.existeVertice("S01"));
        for (Conexion c : grafo.obtenerConexiones()) {
            assertNotEquals("S01", c.getOrigen());
            assertNotEquals("S01", c.getDestino());
        }
    }

    @Test
    void testVerticeDuplicado() {
        assertThrows(IllegalArgumentException.class, () ->
            grafo.agregarVertice(new Vertice("S01", "Dup", "Test"))
        );
    }

    @Test
    void testConexionDuplicada() {
        assertThrows(IllegalArgumentException.class, () ->
            grafo.agregarConexion(new Conexion("R01", "S01", "S02", true, 5, "Dup"))
        );
    }

    @Test
    void testConexionConOrigenInexistente() {
        assertThrows(IllegalArgumentException.class, () ->
            grafo.agregarConexion(new Conexion("R99", "S99", "S01", true, 5, "Inv"))
        );
    }

    @Test
    void testBloquearConexionCambiaRuta() {
        SolucionDijkstra d1 = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra antes = d1.encontrarRutaMinima("S02", "S08");

        grafo.bloquearConexion("R16");

        SolucionDijkstra d2 = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra despues = d2.encontrarRutaMinima("S02", "S08");

        assertTrue(despues.tieneCamino());
        assertTrue(despues.getDistanciaTotal() >= antes.getDistanciaTotal());
    }
}

package aplicacion;


import algoritmos.BusquedaAnchura;
import algoritmos.BusquedaProfundidad;
import algoritmos.SolucionDijkstra;
import arboles.ArbolAVL;
import arboles.ArbolBinarioBusqueda;
import grafos.GrafoMixto;
import grafos.RepresentacionesGrafo;
import java.util.List;
import java.util.Scanner;
import modelo.Conexion;
import modelo.Vertice;

/**
 * Aplicacion principal de RedSegura con menu interactivo completo.
 */
public class AplicacionRedSegura {
    private GrafoMixto grafo;
    private ArbolBinarioBusqueda<Vertice> abb;
    private ArbolAVL<Vertice> avl;
    private final Scanner scanner;

    public AplicacionRedSegura() {
        this.scanner = new Scanner(System.in);
        inicializar();
    }

    private void inicializar() {
        grafo = FabricaDatos.crearGrafoRedSegura();

        abb = new ArbolBinarioBusqueda<>((v1, v2) -> v1.getId().compareTo(v2.getId()));
        for (Vertice v : grafo.obtenerVertices()) {
            abb.insertar(v);
        }

        avl = new ArbolAVL<>((v1, v2) -> v1.getId().compareTo(v2.getId()));
        for (Vertice v : grafo.obtenerVertices()) {
            avl.insertar(v);
        }
    }

    public void ejecutar() {
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> menuGrafo();
                    case "2" -> menuArboles();
                    case "3" -> menuRepresentaciones();
                    case "4" -> menuRecorridos();
                    case "5" -> menuDijkstra();
                    case "6" -> consultasObligatorias();
                    case "7" -> continuar = false;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("      REDSEGURA - MENU PRINCIPAL");
        System.out.println("==========================================");
        System.out.println("1. Gestion del Grafo");
        System.out.println("2. Gestion de Arboles (ABB y AVL)");
        System.out.println("3. Representaciones del Grafo");
        System.out.println("4. Recorridos (BFS y DFS)");
        System.out.println("5. Ruta Minima (Dijkstra)");
        System.out.println("6. Consultas Obligatorias (Q1-Q7)");
        System.out.println("7. Salir");
        System.out.println("==========================================");
        System.out.print("Opcion: ");
    }

    // ============================================================
    // MENU DEL GRAFO
    // ============================================================
    private void menuGrafo() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DEL GRAFO ---");
            System.out.println("1. Listar vertices");
            System.out.println("2. Listar conexiones");
            System.out.println("3. Insertar vertice");
            System.out.println("4. Eliminar vertice");
            System.out.println("5. Insertar conexion");
            System.out.println("6. Eliminar conexion");
            System.out.println("7. Consultar vecinos de un vertice");
            System.out.println("8. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> listarVertices();
                    case "2" -> listarConexiones();
                    case "3" -> insertarVertice();
                    case "4" -> eliminarVertice();
                    case "5" -> insertarConexion();
                    case "6" -> eliminarConexion();
                    case "7" -> consultarVecinos();
                    case "8" -> volver = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void listarVertices() {
        System.out.println("\n=== VERTICES DEL GRAFO ===");
        for (Vertice v : grafo.obtenerVertices()) {
            System.out.println("  " + v);
        }
        System.out.println("Total: " + grafo.getNumeroVertices());
    }

    private void listarConexiones() {
        System.out.println("\n=== CONEXIONES DEL GRAFO ===");
        for (Conexion c : grafo.obtenerConexiones()) {
            System.out.println("  " + c);
        }
        System.out.println("Total: " + grafo.getNumeroConexiones());
    }

    private void insertarVertice() {
        System.out.println("\n--- INSERTAR VERTICE ---");
        System.out.print("ID (ej. S13): ");
        String id = scanner.nextLine().trim();
        System.out.print("Etiqueta: ");
        String etiqueta = scanner.nextLine().trim();
        System.out.print("Tipo: ");
        String tipo = scanner.nextLine().trim();

        Vertice nuevo = new Vertice(id, etiqueta, tipo);
        grafo.agregarVertice(nuevo);
        abb.insertar(nuevo);
        avl.insertar(nuevo);

        System.out.println("Vertice '" + id + "' insertado correctamente");
    }

    private void eliminarVertice() {
        System.out.println("\n--- ELIMINAR VERTICE ---");
        System.out.print("ID del vertice a eliminar: ");
        String id = scanner.nextLine().trim();

        grafo.eliminarVertice(id);

        try {
            Vertice temp = new Vertice(id, "", "");
            abb.eliminar(temp);
        } catch (Exception ignored) {}
        try {
            Vertice temp = new Vertice(id, "", "");
            avl.eliminar(temp);
        } catch (Exception ignored) {}

        System.out.println("Vertice '" + id + "' eliminado correctamente");
    }

    private void insertarConexion() {
        System.out.println("\n--- INSERTAR CONEXION ---");
        System.out.print("ID (ej. R19): ");
        String id = scanner.nextLine().trim();
        System.out.print("Origen: ");
        String origen = scanner.nextLine().trim();
        System.out.print("Destino: ");
        String destino = scanner.nextLine().trim();
        System.out.print("Dirigida? (s/n): ");
        boolean dirigida = scanner.nextLine().trim().equalsIgnoreCase("s");
        System.out.print("Peso (minutos): ");
        int peso = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Etiqueta: ");
        String etiqueta = scanner.nextLine().trim();

        Conexion nueva = new Conexion(id, origen, destino, dirigida, peso, etiqueta);
        grafo.agregarConexion(nueva);

        System.out.println("Conexion '" + id + "' insertada correctamente");
    }

    private void eliminarConexion() {
        System.out.println("\n--- ELIMINAR CONEXION ---");
        System.out.print("ID de la conexion a eliminar: ");
        String id = scanner.nextLine().trim();

        grafo.eliminarConexion(id);
        System.out.println("Conexion '" + id + "' eliminada correctamente");
    }

    private void consultarVecinos() {
        System.out.println("\n--- VECINOS DE UN VERTICE ---");
        System.out.print("ID del vertice: ");
        String id = scanner.nextLine().trim();

        List<Conexion> vecinos = grafo.obtenerVecinos(id);
        System.out.println("Conexiones salientes de " + id + ":");
        for (Conexion c : vecinos) {
            System.out.println("  -> " + c.getDestino() + " (" + c.getPeso() + " min) [" + c.getEtiqueta() + "]");
        }
    }

    // ============================================================
    // MENU DE ARBOLES
    // ============================================================
    private void menuArboles() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE ARBOLES ---");
            System.out.println("1. Ver ABB (recorridos)");
            System.out.println("2. Ver AVL (recorridos)");
            System.out.println("3. Insertar en ABB");
            System.out.println("4. Insertar en AVL");
            System.out.println("5. Eliminar del ABB");
            System.out.println("6. Eliminar del AVL");
            System.out.println("7. Buscar en ABB");
            System.out.println("8. Buscar en AVL");
            System.out.println("9. Comparar alturas ABB vs AVL");
            System.out.println("10. Demostrar rotaciones AVL");
            System.out.println("11. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> verABB();
                    case "2" -> verAVL();
                    case "3" -> insertarEnABB();
                    case "4" -> insertarEnAVL();
                    case "5" -> eliminarDeABB();
                    case "6" -> eliminarDeAVL();
                    case "7" -> buscarEnABB();
                    case "8" -> buscarEnAVL();
                    case "9" -> compararAlturas();
                    case "10" -> demostrarRotaciones();
                    case "11" -> volver = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void verABB() {
        System.out.println("\n=== ARBOL BINARIO DE BUSQUEDA ===");
        System.out.println("InOrder  : " + abb.inOrder());
        System.out.println("PreOrder : " + abb.preOrder());
        System.out.println("PostOrder: " + abb.postOrder());
        System.out.println("Altura   : " + abb.altura());
        System.out.println("Tamano   : " + abb.getTamanio());
    }

    private void verAVL() {
        System.out.println("\n=== ARBOL AVL ===");
        System.out.println("InOrder  : " + avl.inOrder());
        System.out.println("PreOrder : " + avl.preOrder());
        System.out.println("PostOrder: " + avl.postOrder());
        System.out.println("Altura   : " + avl.altura());
        System.out.println("Tamano   : " + avl.getTamanio());
    }

    private void insertarEnABB() {
        System.out.print("ID del vertice a insertar en ABB: ");
        String id = scanner.nextLine().trim();
        Vertice v = grafo.obtenerVertice(id);
        if (v == null) {
            System.out.println("El vertice no existe en el grafo. Primero insertelo en el grafo.");
            return;
        }
        abb.insertar(v);
        System.out.println("Insertado en ABB");
    }

    private void insertarEnAVL() {
        System.out.print("ID del vertice a insertar en AVL: ");
        String id = scanner.nextLine().trim();
        Vertice v = grafo.obtenerVertice(id);
        if (v == null) {
            System.out.println("El vertice no existe en el grafo. Primero insertelo en el grafo.");
            return;
        }
        avl.insertar(v);
        System.out.println("Insertado en AVL");
    }

    private void eliminarDeABB() {
        System.out.print("ID del vertice a eliminar del ABB: ");
        String id = scanner.nextLine().trim();
        abb.eliminar(new Vertice(id, "", ""));
        System.out.println("Eliminado del ABB");
    }

    private void eliminarDeAVL() {
        System.out.print("ID del vertice a eliminar del AVL: ");
        String id = scanner.nextLine().trim();
        avl.eliminar(new Vertice(id, "", ""));
        System.out.println("Eliminado del AVL");
    }

    private void buscarEnABB() {
        System.out.print("ID a buscar en ABB: ");
        String id = scanner.nextLine().trim();
        boolean encontrado = abb.buscar(new Vertice(id, "", ""));
        System.out.println("Encontrado: " + encontrado + " | Pasos: " + abb.getPasosBusqueda());
    }

    private void buscarEnAVL() {
        System.out.print("ID a buscar en AVL: ");
        String id = scanner.nextLine().trim();
        boolean encontrado = avl.buscar(new Vertice(id, "", ""));
        System.out.println("Encontrado: " + encontrado + " | Pasos: " + avl.getPasosBusqueda());
    }

    private void compararAlturas() {
        System.out.println("\n=== COMPARACION ABB vs AVL ===");
        System.out.println("Altura ABB: " + abb.altura());
        System.out.println("Altura AVL: " + avl.altura());
        System.out.println("Tamano ABB: " + abb.getTamanio());
        System.out.println("Tamano AVL: " + avl.getTamanio());
        System.out.println("El AVL garantiza O(log n) en busqueda gracias al balanceo.");
    }

    private void demostrarRotaciones() {
        System.out.println("\n=== DEMOSTRACION DE ROTACIONES AVL ===");

        System.out.println("\n-- Rotacion LL (izquierda-izquierda) --");
        ArbolAVL<Integer> avlLL = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avlLL.insertar(30);
        avlLL.insertar(20);
        avlLL.insertar(10);
        System.out.println("Secuencia: 30, 20, 10");
        System.out.println("InOrder: " + avlLL.inOrder() + " | Altura: " + avlLL.altura());

        System.out.println("\n-- Rotacion RR (derecha-derecha) --");
        ArbolAVL<Integer> avlRR = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avlRR.insertar(10);
        avlRR.insertar(20);
        avlRR.insertar(30);
        System.out.println("Secuencia: 10, 20, 30");
        System.out.println("InOrder: " + avlRR.inOrder() + " | Altura: " + avlRR.altura());

        System.out.println("\n-- Rotacion LR (izquierda-derecha) --");
        ArbolAVL<Integer> avlLR = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avlLR.insertar(30);
        avlLR.insertar(10);
        avlLR.insertar(20);
        System.out.println("Secuencia: 30, 10, 20");
        System.out.println("InOrder: " + avlLR.inOrder() + " | Altura: " + avlLR.altura());

        System.out.println("\n-- Rotacion RL (derecha-izquierda) --");
        ArbolAVL<Integer> avlRL = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avlRL.insertar(10);
        avlRL.insertar(30);
        avlRL.insertar(20);
        System.out.println("Secuencia: 10, 30, 20");
        System.out.println("InOrder: " + avlRL.inOrder() + " | Altura: " + avlRL.altura());
    }

    // ============================================================
    // MENU DE REPRESENTACIONES
    // ============================================================
    private void menuRepresentaciones() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- REPRESENTACIONES DEL GRAFO ---");
            System.out.println("1. Lista de adyacencia");
            System.out.println("2. Matriz de adyacencia ponderada");
            System.out.println("3. Matriz de incidencia");
            System.out.println("4. Mostrar todas");
            System.out.println("5. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine().trim();

            RepresentacionesGrafo rep = new RepresentacionesGrafo(grafo);
            switch (opcion) {
                case "1" -> rep.imprimirListaAdyacencia();
                case "2" -> rep.imprimirMatrizAdyacencia();
                case "3" -> rep.imprimirMatrizIncidencia();
                case "4" -> {
                    rep.imprimirListaAdyacencia();
                    rep.imprimirMatrizAdyacencia();
                    rep.imprimirMatrizIncidencia();
                }
                case "5" -> volver = true;
                default -> System.out.println("Opcion no valida");
            }
        }
    }

    // ============================================================
    // MENU DE RECORRIDOS
    // ============================================================
    private void menuRecorridos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- RECORRIDOS DEL GRAFO ---");
            System.out.println("1. BFS desde un vertice");
            System.out.println("2. DFS desde un vertice");
            System.out.println("3. Comparar BFS vs DFS");
            System.out.println("4. Detectar ciclos (DFS)");
            System.out.println("5. Componentes debiles");
            System.out.println("6. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> ejecutarBFS();
                    case "2" -> ejecutarDFS();
                    case "3" -> compararBFSDFS();
                    case "4" -> detectarCiclos();
                    case "5" -> componentesDebiles();
                    case "6" -> volver = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void ejecutarBFS() {
        System.out.print("Vertice origen: ");
        String origen = scanner.nextLine().trim();
        BusquedaAnchura bfs = new BusquedaAnchura(grafo);
        List<String> resultado = bfs.recorrer(origen);
        System.out.println("BFS: " + resultado);
    }

    private void ejecutarDFS() {
        System.out.print("Vertice origen: ");
        String origen = scanner.nextLine().trim();
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);
        List<String> resultado = dfs.recorrer(origen);
        System.out.println("DFS: " + resultado);
    }

    private void compararBFSDFS() {
        System.out.print("Vertice origen: ");
        String origen = scanner.nextLine().trim();

        BusquedaAnchura bfs = new BusquedaAnchura(grafo);
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);

        List<String> rBFS = bfs.recorrer(origen);
        List<String> rDFS = dfs.recorrer(origen);

        System.out.println("BFS: " + rBFS);
        System.out.println("DFS: " + rDFS);
        System.out.println("\nExplicacion:");
        System.out.println("- BFS usa cola (FIFO) y explora por niveles.");
        System.out.println("- DFS usa recursion/pila (LIFO) y explora en profundidad.");
        System.out.println("- Ambos controlan visitados para evitar ciclos.");
    }

    private void detectarCiclos() {
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);
        boolean hayCiclos = dfs.detectarCiclos();
        System.out.println("Existen ciclos dirigidos? " + (hayCiclos ? "SI" : "NO"));
    }

    private void componentesDebiles() {
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);
        List<java.util.Set<String>> componentes = dfs.encontrarComponentesDebiles();
        System.out.println("Componentes debiles: " + componentes.size());
        for (int i = 0; i < componentes.size(); i++) {
            System.out.println("  Componente " + (i + 1) + ": " + componentes.get(i));
        }
    }

    // ============================================================
    // MENU DE DIJKSTRA
    // ============================================================
    private void menuDijkstra() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- RUTA MINIMA (DIJKSTRA) ---");
            System.out.println("1. Calcular ruta entre dos vertices");
            System.out.println("2. Ruta S02 -> S08 (control)");
            System.out.println("3. Bloquear conexion y recalcular");
            System.out.println("4. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> calcularRuta();
                    case "2" -> rutaControl();
                    case "3" -> bloquearYRecalcular();
                    case "4" -> volver = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void calcularRuta() {
        System.out.print("Origen: ");
        String origen = scanner.nextLine().trim();
        System.out.print("Destino: ");
        String destino = scanner.nextLine().trim();

        SolucionDijkstra dijkstra = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra resultado = dijkstra.encontrarRutaMinima(origen, destino);

        if (resultado.tieneCamino()) {
            System.out.println("Ruta: " + String.join(" -> ", resultado.getCamino()));
            System.out.println("Costo: " + resultado.getDistanciaTotal() + " minutos");
        } else {
            System.out.println("No existe ruta entre " + origen + " y " + destino);
        }
    }

    private void rutaControl() {
        SolucionDijkstra dijkstra = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra resultado = dijkstra.encontrarRutaMinima("S02", "S08");
        System.out.println("Ruta S02 -> S08:");
        System.out.println("  Camino: " + String.join(" -> ", resultado.getCamino()));
        System.out.println("  Costo: " + resultado.getDistanciaTotal() + " minutos");
        System.out.println(resultado.getDistanciaTotal() == 24 ? "Resultado de control correcto" : "Resultado incorrecto");
    }

    private void bloquearYRecalcular() {
        System.out.print("ID de la conexion a bloquear: ");
        String id = scanner.nextLine().trim();

        SolucionDijkstra dijkstraAntes = new SolucionDijkstra(grafo);
        System.out.print("Origen para recalcular: ");
        String origen = scanner.nextLine().trim();
        System.out.print("Destino para recalcular: ");
        String destino = scanner.nextLine().trim();

        SolucionDijkstra.ResultadoDijkstra antes = dijkstraAntes.encontrarRutaMinima(origen, destino);
        System.out.println("Antes de bloquear: " + String.join(" -> ", antes.getCamino()) + " [" + antes.getDistanciaTotal() + " min]");

        grafo.bloquearConexion(id);
        System.out.println("Conexion " + id + " bloqueada");

        SolucionDijkstra dijkstraDespues = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra despues = dijkstraDespues.encontrarRutaMinima(origen, destino);

        if (despues.tieneCamino()) {
            System.out.println("Despues de bloquear: " + String.join(" -> ", despues.getCamino()) + " [" + despues.getDistanciaTotal() + " min]");
        } else {
            System.out.println("Despues de bloquear: No existe ruta alternativa");
        }
    }

    // ============================================================
    // CONSULTAS OBLIGATORIAS (Q1-Q7)
    // ============================================================
    private void consultasObligatorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- CONSULTAS OBLIGATORIAS ---");
            System.out.println("Q1. Buscar Hospital Universitario en ABB y AVL");
            System.out.println("Q2. Mostrar las 4 rotaciones AVL");
            System.out.println("Q3. Imprimir lista, matriz adyacencia e incidencia");
            System.out.println("Q4. BFS y DFS desde S02");
            System.out.println("Q5. Dijkstra S02 -> S08 (debe ser 24 min)");
            System.out.println("Q6. Bloquear R16 y recalcular");
            System.out.println("Q7. Consultar vertice inexistente");
            System.out.println("8. Volver");
            System.out.print("Opcion: ");
            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "Q1", "1" -> ejecutarQ1();
                    case "Q2", "2" -> demostrarRotaciones();
                    case "Q3", "3" -> ejecutarQ3();
                    case "Q4", "4" -> ejecutarQ4();
                    case "Q5", "5" -> rutaControl();
                    case "Q6", "6" -> ejecutarQ6();
                    case "Q7", "7" -> ejecutarQ7();
                    case "8" -> volver = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void ejecutarQ1() {
        System.out.println("\n=== Q1: Busqueda Hospital Universitario (S05) ===");
        Vertice hospital = new Vertice("S05", "Hospital Universitario", "Salud");

        boolean encABB = abb.buscar(hospital);
        int pasosABB = abb.getPasosBusqueda();

        boolean encAVL = avl.buscar(hospital);
        int pasosAVL = avl.getPasosBusqueda();

        System.out.println("ABB - Encontrado: " + encABB + " | Pasos: " + pasosABB + " | Altura arbol: " + abb.altura());
        System.out.println("AVL - Encontrado: " + encAVL + " | Pasos: " + pasosAVL + " | Altura arbol: " + avl.altura());
        System.out.println("El AVL tiene menor altura gracias al balanceo -> busquedas mas eficientes.");
    }

    private void ejecutarQ3() {
        RepresentacionesGrafo rep = new RepresentacionesGrafo(grafo);
        rep.imprimirListaAdyacencia();
        rep.imprimirMatrizAdyacencia();
        rep.imprimirMatrizIncidencia();
    }

    private void ejecutarQ4() {
        System.out.println("\n=== Q4: BFS y DFS desde S02 ===");
        BusquedaAnchura bfs = new BusquedaAnchura(grafo);
        BusquedaProfundidad dfs = new BusquedaProfundidad(grafo);

        System.out.println("BFS: " + bfs.recorrer("S02"));
        System.out.println("DFS: " + dfs.recorrer("S02"));
        System.out.println("\nLos ordenes difieren porque:");
        System.out.println("- BFS explora nivel por nivel (cola FIFO).");
        System.out.println("- DFS explora en profundidad (pila/recursion LIFO).");
    }

    private void ejecutarQ6() {
        System.out.println("\n=== Q6: Bloquear R16 y recalcular ===");
        SolucionDijkstra d1 = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra antes = d1.encontrarRutaMinima("S02", "S08");
        System.out.println("Antes: " + String.join(" -> ", antes.getCamino()) + " [" + antes.getDistanciaTotal() + " min]");

        grafo.bloquearConexion("R16");
        System.out.println("R16 bloqueada");

        SolucionDijkstra d2 = new SolucionDijkstra(grafo);
        SolucionDijkstra.ResultadoDijkstra despues = d2.encontrarRutaMinima("S02", "S08");
        System.out.println("Despues: " + String.join(" -> ", despues.getCamino()) + " [" + despues.getDistanciaTotal() + " min]");
        System.out.println("El cambio ocurre porque Dijkstra recalcula la ruta optima evitando la arista bloqueada.");
    }

    private void ejecutarQ7() {
        System.out.println("\n=== Q7: Consultar vertice inexistente ===");
        System.out.print("ID a buscar: ");
        String id = scanner.nextLine().trim();

        boolean encontrado = abb.buscar(new Vertice(id, "", ""));
        System.out.println("Resultado: " + (encontrado ? "Encontrado" : "No encontrado"));
        System.out.println("El programa continua sin terminar (manejo de excepciones).");
    }

    public static void main(String[] args) {
        AplicacionRedSegura app = new AplicacionRedSegura();
        app.ejecutar();
    }
}

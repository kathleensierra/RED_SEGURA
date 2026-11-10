package aplicacion;

import grafos.GrafoMixto;
import modelo.Conexion;
import modelo.Vertice;


/**
 * Fabrica para crear el grafo de RedSegura con datos didacticos.
 */
public class FabricaDatos {

    public static GrafoMixto crearGrafoRedSegura() {
        GrafoMixto grafo = new GrafoMixto();

        grafo.agregarVertice(new Vertice("S01", "UAJS Sede C", "Universidad"));
        grafo.agregarVertice(new Vertice("S02", "UAJS Sede E", "Universidad"));
        grafo.agregarVertice(new Vertice("S03", "Universidad de Sucre", "Universidad"));
        grafo.agregarVertice(new Vertice("S04", "CECAR", "Universidad"));
        grafo.agregarVertice(new Vertice("S05", "Hospital Universitario", "Salud"));
        grafo.agregarVertice(new Vertice("S06", "Clinica Las Americas", "Salud"));
        grafo.agregarVertice(new Vertice("S07", "Estacion Central SETP", "Transporte"));
        grafo.agregarVertice(new Vertice("S08", "Terminal de Transporte", "Transporte"));
        grafo.agregarVertice(new Vertice("S09", "Parque Santander", "Punto de encuentro"));
        grafo.agregarVertice(new Vertice("S10", "Mercado de Sincelejo", "Comercio"));
        grafo.agregarVertice(new Vertice("S11", "PEP Argelia", "Paradero"));
        grafo.agregarVertice(new Vertice("S12", "Glorieta Sincelejito", "Movilidad"));

        grafo.agregarConexion(new Conexion("R01", "S01", "S07", true, 4, "Corredor universitario"));
        grafo.agregarConexion(new Conexion("R02", "S07", "S01", true, 6, "Retorno por via alterna"));
        grafo.agregarConexion(new Conexion("R03", "S01", "S09", false, 5, "Corredor peatonal"));
        grafo.agregarConexion(new Conexion("R04", "S02", "S01", false, 8, "Conexion entre sedes"));
        grafo.agregarConexion(new Conexion("R05", "S02", "S06", true, 10, "Ruta de atencion"));
        grafo.agregarConexion(new Conexion("R06", "S06", "S05", true, 4, "Corredor clinico"));
        grafo.agregarConexion(new Conexion("R07", "S05", "S09", false, 6, "Via hospitalaria"));
        grafo.agregarConexion(new Conexion("R08", "S09", "S10", false, 3, "Centro historico"));
        grafo.agregarConexion(new Conexion("R09", "S10", "S08", true, 8, "Salida al terminal"));
        grafo.agregarConexion(new Conexion("R10", "S08", "S10", true, 9, "Ingreso desde terminal"));
        grafo.agregarConexion(new Conexion("R11", "S03", "S12", false, 4, "Corredor Puerta Roja"));
        grafo.agregarConexion(new Conexion("R12", "S12", "S09", false, 7, "Troncal al centro"));
        grafo.agregarConexion(new Conexion("R13", "S04", "S11", false, 6, "Conexion universitaria"));
        grafo.agregarConexion(new Conexion("R14", "S11", "S07", true, 8, "Ruta SETP al centro"));
        grafo.agregarConexion(new Conexion("R15", "S07", "S09", false, 3, "Transferencia central"));
        grafo.agregarConexion(new Conexion("R16", "S07", "S08", true, 12, "Ruta SETP al terminal"));
        grafo.agregarConexion(new Conexion("R17", "S09", "S03", true, 7, "Ruta hacia universidad"));
        grafo.agregarConexion(new Conexion("R18", "S12", "S04", true, 9, "Ruta hacia CECAR"));

        return grafo;
    }
}
package arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Implementacion de Arbol Binario de Busqueda (ABB).
 * @param <T> Tipo de dato almacenado
 */
public class ArbolBinarioBusqueda<T> {
    protected NodoArbol<T> raiz;
    protected int tamanio;
    protected BiFunction<T, T, Integer> comparador;
    protected int pasosBusqueda;

    public ArbolBinarioBusqueda(BiFunction<T, T, Integer> comparador) {
        this.raiz = null;
        this.tamanio = 0;
        this.comparador = comparador;
        this.pasosBusqueda = 0;
    }

    /**
     * Inserta un dato en el arbol.
     * @throws IllegalArgumentException si el dato es nulo o ya existe
     */
    public void insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede insertar un dato nulo");
        }
        if (buscar(dato)) {
            throw new IllegalArgumentException("El dato ya existe en el arbol");
        }
        raiz = insertarRec(raiz, dato);
        tamanio++;
    }

    private NodoArbol<T> insertarRec(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return new NodoArbol<>(dato);
        }

        int comparacion = comparador.apply(dato, nodo.getDato());

        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), dato));
        }

        return nodo;
    }

    /**
     * Busca un dato en el arbol y retorna el numero de pasos.
     */
    public boolean buscar(T dato) {
        pasosBusqueda = 0;
        return buscarRec(raiz, dato);
    }

    private boolean buscarRec(NodoArbol<T> nodo, T dato) {
        pasosBusqueda++;

        if (nodo == null) {
            return false;
        }

        int comparacion = comparador.apply(dato, nodo.getDato());

        if (comparacion == 0) {
            return true;
        } else if (comparacion < 0) {
            return buscarRec(nodo.getIzquierdo(), dato);
        } else {
            return buscarRec(nodo.getDerecho(), dato);
        }
    }

    /**
     * Retorna el numero de pasos de la ultima busqueda.
     */
    public int getPasosBusqueda() {
        return pasosBusqueda;
    }

    /**
     * Elimina un dato del arbol.
     * @throws IllegalArgumentException si el dato no existe
     */
    public void eliminar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede eliminar un dato nulo");
        }
        if (!buscar(dato)) {
            throw new IllegalArgumentException("El dato no existe en el arbol");
        }
        raiz = eliminarRec(raiz, dato);
        tamanio--;
    }

    private NodoArbol<T> eliminarRec(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = comparador.apply(dato, nodo.getDato());

        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarRec(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), dato));
        } else {
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            } else if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }

            T sucesor = obtenerMinimo(nodo.getDerecho());
            nodo.setDato(sucesor);
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), sucesor));
        }

        return nodo;
    }

    private T obtenerMinimo(NodoArbol<T> nodo) {
        T minimo = nodo.getDato();
        while (nodo.getIzquierdo() != null) {
            minimo = nodo.getIzquierdo().getDato();
            nodo = nodo.getIzquierdo();
        }
        return minimo;
    }

    /**
     * Recorrido inorder.
     */
    public List<T> inOrder() {
        List<T> resultado = new LinkedList<>();
        inOrderRec(raiz, resultado);
        return resultado;
    }

    private void inOrderRec(NodoArbol<T> nodo, List<T> resultado) {
        if (nodo != null) {
            inOrderRec(nodo.getIzquierdo(), resultado);
            resultado.add(nodo.getDato());
            inOrderRec(nodo.getDerecho(), resultado);
        }
    }

    /**
     * Recorrido preorder.
     */
    public List<T> preOrder() {
        List<T> resultado = new LinkedList<>();
        preOrderRec(raiz, resultado);
        return resultado;
    }

    private void preOrderRec(NodoArbol<T> nodo, List<T> resultado) {
        if (nodo != null) {
            resultado.add(nodo.getDato());
            preOrderRec(nodo.getIzquierdo(), resultado);
            preOrderRec(nodo.getDerecho(), resultado);
        }
    }

    /**
     * Recorrido postorder.
     */
    public List<T> postOrder() {
        List<T> resultado = new LinkedList<>();
        postOrderRec(raiz, resultado);
        return resultado;
    }

    private void postOrderRec(NodoArbol<T> nodo, List<T> resultado) {
        if (nodo != null) {
            postOrderRec(nodo.getIzquierdo(), resultado);
            postOrderRec(nodo.getDerecho(), resultado);
            resultado.add(nodo.getDato());
        }
    }

    /**
     * Calcula la altura del arbol.
     */
    public int altura() {
        return calcularAltura(raiz);
    }

    protected int calcularAltura(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(calcularAltura(nodo.getIzquierdo()), calcularAltura(nodo.getDerecho()));
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return tamanio == 0;
    }
}
package arboles;


import java.util.function.BiFunction;

/**
 * Implementacion de Arbol AVL con balanceo automatico.
 */
public class ArbolAVL<T> extends ArbolBinarioBusqueda<T> {

    public ArbolAVL(BiFunction<T, T, Integer> comparador) {
        super(comparador);
    }

    @Override
    public void insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede insertar un dato nulo");
        }
        if (buscar(dato)) {
            throw new IllegalArgumentException("El dato ya existe en el arbol");
        }
        raiz = insertarAVL(raiz, dato);
        tamanio++;
    }

    private NodoArbol<T> insertarAVL(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return new NodoArbol<>(dato);
        }

        int comparacion = comparador.apply(dato, nodo.getDato());

        if (comparacion < 0) {
            nodo.setIzquierdo(insertarAVL(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarAVL(nodo.getDerecho(), dato));
        } else {
            return nodo;
        }

        nodo.setAltura(1 + Math.max(obtenerAltura(nodo.getIzquierdo()),
                                     obtenerAltura(nodo.getDerecho())));

        int balance = obtenerBalance(nodo);

        // Caso LL
        if (balance > 1 && comparador.apply(dato, nodo.getIzquierdo().getDato()) < 0) {
            return rotarDerecha(nodo);
        }

        // Caso RR
        if (balance < -1 && comparador.apply(dato, nodo.getDerecho().getDato()) > 0) {
            return rotarIzquierda(nodo);
        }

        // Caso LR
        if (balance > 1 && comparador.apply(dato, nodo.getIzquierdo().getDato()) > 0) {
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }

        // Caso RL
        if (balance < -1 && comparador.apply(dato, nodo.getDerecho().getDato()) < 0) {
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    @Override
    public void eliminar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede eliminar un dato nulo");
        }
        if (!buscar(dato)) {
            throw new IllegalArgumentException("El dato no existe en el arbol");
        }
        raiz = eliminarAVL(raiz, dato);
        tamanio--;
    }

    private NodoArbol<T> eliminarAVL(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = comparador.apply(dato, nodo.getDato());

        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarAVL(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarAVL(nodo.getDerecho(), dato));
        } else {
            if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
                NodoArbol<T> temp = nodo.getIzquierdo() != null ?
                                    nodo.getIzquierdo() : nodo.getDerecho();
                if (temp == null) {
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                NodoArbol<T> temp = obtenerNodoMinimo(nodo.getDerecho());
                nodo.setDato(temp.getDato());
                nodo.setDerecho(eliminarAVL(nodo.getDerecho(), temp.getDato()));
            }
        }

        if (nodo == null) {
            return null;
        }

        nodo.setAltura(1 + Math.max(obtenerAltura(nodo.getIzquierdo()),
                                     obtenerAltura(nodo.getDerecho())));

        int balance = obtenerBalance(nodo);

        // Caso LL
        if (balance > 1 && obtenerBalance(nodo.getIzquierdo()) >= 0) {
            return rotarDerecha(nodo);
        }

        // Caso LR
        if (balance > 1 && obtenerBalance(nodo.getIzquierdo()) < 0) {
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }

        // Caso RR
        if (balance < -1 && obtenerBalance(nodo.getDerecho()) <= 0) {
            return rotarIzquierda(nodo);
        }

        // Caso RL
        if (balance < -1 && obtenerBalance(nodo.getDerecho()) > 0) {
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoArbol<T> obtenerNodoMinimo(NodoArbol<T> nodo) {
        NodoArbol<T> actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;
    }

    private NodoArbol<T> rotarDerecha(NodoArbol<T> y) {
        NodoArbol<T> x = y.getIzquierdo();
        NodoArbol<T> T2 = x.getDerecho();

        x.setDerecho(y);
        y.setIzquierdo(T2);

        y.setAltura(Math.max(obtenerAltura(y.getIzquierdo()),
                             obtenerAltura(y.getDerecho())) + 1);
        x.setAltura(Math.max(obtenerAltura(x.getIzquierdo()),
                             obtenerAltura(x.getDerecho())) + 1);

        return x;
    }

    private NodoArbol<T> rotarIzquierda(NodoArbol<T> x) {
        NodoArbol<T> y = x.getDerecho();
        NodoArbol<T> T2 = y.getIzquierdo();

        y.setIzquierdo(x);
        x.setDerecho(T2);

        x.setAltura(Math.max(obtenerAltura(x.getIzquierdo()),
                             obtenerAltura(x.getDerecho())) + 1);
        y.setAltura(Math.max(obtenerAltura(y.getIzquierdo()),
                             obtenerAltura(y.getDerecho())) + 1);

        return y;
    }

    private int obtenerAltura(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.getAltura();
    }

    private int obtenerBalance(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return obtenerAltura(nodo.getIzquierdo()) - obtenerAltura(nodo.getDerecho());
    }

    @Override
    protected int calcularAltura(NodoArbol<T> nodo) {
        return obtenerAltura(nodo);
    }
}

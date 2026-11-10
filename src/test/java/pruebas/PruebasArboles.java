
package pruebas;

import arboles.ArbolAVL;
import arboles.ArbolBinarioBusqueda;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PruebasArboles {

    @Test
    void testABBInsercionYBusqueda() {
        ArbolBinarioBusqueda<Integer> abb = new ArbolBinarioBusqueda<>((a, b) -> a.compareTo(b));
        abb.insertar(50);
        abb.insertar(30);
        abb.insertar(70);
        abb.insertar(20);
        abb.insertar(40);

        assertTrue(abb.buscar(50));
        assertTrue(abb.buscar(30));
        assertFalse(abb.buscar(100));
        assertEquals(5, abb.getTamanio());
    }

    @Test
    void testABBDuplicado() {
        ArbolBinarioBusqueda<Integer> abb = new ArbolBinarioBusqueda<>((a, b) -> a.compareTo(b));
        abb.insertar(50);
        assertThrows(IllegalArgumentException.class, () -> abb.insertar(50));
    }

    @Test
    void testABBEliminarExistente() {
        ArbolBinarioBusqueda<Integer> abb = new ArbolBinarioBusqueda<>((a, b) -> a.compareTo(b));
        abb.insertar(50);
        abb.insertar(30);
        abb.insertar(70);

        abb.eliminar(30);
        assertFalse(abb.buscar(30));
        assertEquals(2, abb.getTamanio());
    }

    @Test
    void testABBEliminarNoExistente() {
        ArbolBinarioBusqueda<Integer> abb = new ArbolBinarioBusqueda<>((a, b) -> a.compareTo(b));
        abb.insertar(50);
        assertThrows(IllegalArgumentException.class, () -> abb.eliminar(99));
    }

    @Test
    void testABBRecorridos() {
        ArbolBinarioBusqueda<Integer> abb = new ArbolBinarioBusqueda<>((a, b) -> a.compareTo(b));
        abb.insertar(50);
        abb.insertar(30);
        abb.insertar(70);
        abb.insertar(20);
        abb.insertar(40);

        assertEquals("[20, 30, 40, 50, 70]", abb.inOrder().toString());
        assertEquals("[50, 30, 20, 40, 70]", abb.preOrder().toString());
        assertEquals("[20, 40, 30, 70, 50]", abb.postOrder().toString());
    }

    @Test
    void testAVLBalanceoLL() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(30);
        avl.insertar(20);
        avl.insertar(10);
        assertEquals(2, avl.altura());
    }

    @Test
    void testAVLBalanceoRR() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(10);
        avl.insertar(20);
        avl.insertar(30);
        assertEquals(2, avl.altura());
    }

    @Test
    void testAVLBalanceoLR() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(30);
        avl.insertar(10);
        avl.insertar(20);
        assertEquals(2, avl.altura());
    }

    @Test
    void testAVLBalanceoRL() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(10);
        avl.insertar(30);
        avl.insertar(20);
        assertEquals(2, avl.altura());
    }

    @Test
    void testAVLEliminar() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(50);
        avl.insertar(30);
        avl.insertar(70);
        avl.insertar(20);
        avl.insertar(40);

        avl.eliminar(30);
        assertFalse(avl.buscar(30));
        assertEquals(4, avl.getTamanio());
    }

    @Test
    void testAVLEliminarNoExistente() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(50);
        assertThrows(IllegalArgumentException.class, () -> avl.eliminar(99));
    }

    @Test
    void testAVLDuplicado() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        avl.insertar(50);
        assertThrows(IllegalArgumentException.class, () -> avl.insertar(50));
    }

    @Test
    void testAVLMantieneBalanceTrasVariasInserciones() {
        ArbolAVL<Integer> avl = new ArbolAVL<>((a, b) -> a.compareTo(b));
        for (int i = 1; i <= 15; i++) {
            avl.insertar(i);
        }
        assertTrue(avl.altura() <= 4);
        assertEquals(15, avl.getTamanio());
    }
}
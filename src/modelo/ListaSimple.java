
package modelo;

public class ListaSimple<T> {
    private NodoSimple<T> cabeza;

    public ListaSimple() { this.cabeza = null; }

    public boolean estaVacia() { return cabeza == null; }

    public void insertarInicio(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
    }

    public void insertarFinal(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);
        if (estaVacia()) { cabeza = nuevo; return; }
        NodoSimple<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.setSiguiente(nuevo);
    }

    public boolean eliminarPorValor(T dato) {
        if (estaVacia()) return false;
        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            return true;
        }
        NodoSimple<T> anterior = cabeza;
        NodoSimple<T> actual = cabeza.getSiguiente();
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                anterior.setSiguiente(actual.getSiguiente());
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public boolean buscar(T dato) {
        if (estaVacia()) return false;
        NodoSimple<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public String toString() {
        if (estaVacia()) return "La lista está vacía.";
        StringBuilder sb = new StringBuilder();
        NodoSimple<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) sb.append(" -> ");
            actual = actual.getSiguiente();
        }
        sb.append(" -> null");
        return sb.toString();
    }
}
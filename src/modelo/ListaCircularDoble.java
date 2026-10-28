
package modelo;

public class ListaCircularDoble<T> {
    private NodoDoble<T> cabeza;
    private int tamanio;

    public ListaCircularDoble() { this.cabeza = null; this.tamanio = 0; }

    public boolean estaVacia() { return cabeza == null; }
    public int getTamanio() { return tamanio; }
    public NodoDoble<T> getCabeza() { return cabeza; }

    public void insertar(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
            cabeza = nuevo;
        } else {
            NodoDoble<T> ultimo = cabeza.getAnterior();
            nuevo.setSiguiente(cabeza);
            nuevo.setAnterior(ultimo);
            ultimo.setSiguiente(nuevo);
            cabeza.setAnterior(nuevo);
        }
        tamanio++;
    }

    public boolean eliminar(T dato) {
        if (estaVacia()) return false;
        NodoDoble<T> actual = cabeza;
        do {
            if (actual.getDato().equals(dato)) {
                if (tamanio == 1) {
                    cabeza = null;
                } else {
                    NodoDoble<T> ant = actual.getAnterior();
                    NodoDoble<T> sig = actual.getSiguiente();
                    ant.setSiguiente(sig);
                    sig.setAnterior(ant);
                    if (actual == cabeza) cabeza = sig;
                }
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        return false;
    }
}

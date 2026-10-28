package modelo;

public class Reproductor {
    private ListaCircularDoble<Cancion> lista;
    private NodoDoble<Cancion> actual;

    public Reproductor() {
        lista = new ListaCircularDoble<>();
        actual = null;
    }

    public void agregarCancion(Cancion c) {
        lista.insertar(c);
        if (actual == null) actual = lista.getCabeza();
    }

    public void reproducirActual() {
        if (actual == null) return;
        // La vista muestra el estado a través de getActual()
    }

    public void siguienteCancion() {
        if (actual == null) return;
        actual = actual.getSiguiente();
    }

    public void cancionAnterior() {
        if (actual == null) return;
        actual = actual.getAnterior();
    }

    public boolean eliminarDeLista(int id) {
        if (actual == null) return false;
        Cancion aBorrar = null;
        NodoDoble<Cancion> aux = lista.getCabeza();
        do {
            if (aux.getDato().getId() == id) {
                aBorrar = aux.getDato();
                break;
            }
            aux = aux.getSiguiente();
        } while (aux != lista.getCabeza());

        if (aBorrar == null) return false;

        if (actual.getDato().equals(aBorrar)) {
            if (lista.getTamanio() > 1) actual = actual.getSiguiente();
            else actual = null;
        }
        return lista.eliminar(aBorrar);
    }

    public Cancion getActual() {
        return actual == null ? null : actual.getDato();
    }

    public ListaCircularDoble<Cancion> getLista() { return lista; }
}
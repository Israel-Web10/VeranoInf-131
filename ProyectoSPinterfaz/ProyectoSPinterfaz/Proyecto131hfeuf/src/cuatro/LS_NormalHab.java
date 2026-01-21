package cuatro;

public class LS_NormalHab extends ListaSimpleHab {

    public void adiPrincipio(Habitacion h) {
        NodoHabitacion nuevo = new NodoHabitacion(h);
        if (P == null) {
            P = nuevo;
        } else {
            nuevo.setSig(P);
            P = nuevo;
        }
    }

    public void adiFinal(Habitacion h) {
        NodoHabitacion nuevo = new NodoHabitacion(h);
        if (P == null) {
            P = nuevo;
        } else {
            NodoHabitacion aux = P;
            while (aux.getSig() != null) {
                aux = aux.getSig();
            }
            aux.setSig(nuevo);
        }
    }
    public void eliFinal() {
        if (P != null) {
            if (P.getSig() == null) {
                P = null;
            } else {
                NodoHabitacion aux = P;
                while (aux.getSig().getSig() != null) {
                    aux = aux.getSig();
                }
                aux.setSig(null);
            }
        }
    }
     public void esVacia() {
        if (P == null) {
            System.out.println("La lista está vacía.");
        } else {
            System.out.println("La lista no está vacía.");
        }
    }
    public void nroNodos() {
        int contador = 0;
        NodoHabitacion aux = P;
        while (aux != null) {
            contador++;
            aux = aux.getSig();
        }
        System.out.println("Número de nodos en la lista: " + contador);
    }
    public void mostrar() {
        NodoHabitacion aux = P;
        while (aux != null) {
            aux.mostrar();
            aux = aux.getSig();
        }
    }

}

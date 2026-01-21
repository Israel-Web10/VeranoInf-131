package cuatro;

public class PilaReservas {
	private int tope, max = 50;
    private Reserva[] v = new Reserva[max];

    public PilaReservas() { tope = -1; }

    public boolean esVacia() { return tope == -1; }
    public boolean esLlena() { return tope == max-1; }

    public void adi(Reserva r) {
        if(!esLlena()) v[++tope] = r;
        else System.out.println("Pila llena!");
    }

    public Reserva eli() {
        if(!esVacia()) return v[tope--];
        else return null;
    }

    public void mostrar() {
        PilaReservas aux = new PilaReservas();
        while(!esVacia()) {
            Reserva r = eli();
            r.mostrar();
            aux.adi(r);
        }
        vaciar(aux);
    }

    public void vaciar(PilaReservas p) {
        while(!p.esVacia()) adi(p.eli());
    }
}

// ----------- COLA CIRCULAR DE CLIENTES -----------
class ColaCircularClientes {
    private int ini, fin, max = 50;
    private Cliente[] v = new Cliente[max];

    public ColaCircularClientes() { ini = fin = -1; }

    public boolean esVacia() { return ini == -1; }
    public boolean esLlena() { return (fin+1)%max == ini; }

    public void adi(Cliente c) {
        if(!esLlena()) {
            if(esVacia()) ini = fin = 0;
            else fin = (fin+1)%max;
            v[fin] = c;
        } else System.out.println("Cola llena!");
    }

    public Cliente eli() {
        if(esVacia()) return null;
        Cliente c = v[ini];
        if(ini == fin) ini = fin = -1;
        else ini = (ini+1)%max;
        return c;
    }

    public void mostrar() {
        if(esVacia()) { System.out.println("(Cola vacía)"); return; }
        int i = ini;
        while(true) {
            System.out.println("Cliente: " + v[i].getNombre());
            if(i==fin) break;
            i = (i+1)%max;
        }
    }

    public int nroElem() {
        if(esVacia()) return 0;
        if(fin >= ini) return fin - ini + 1;
        else return max - ini + fin + 1;
    }

}

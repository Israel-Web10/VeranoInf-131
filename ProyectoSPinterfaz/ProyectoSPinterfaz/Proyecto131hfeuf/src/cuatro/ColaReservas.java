package cuatro;

public class ColaReservas {
	private int ini, fin, max = 50;
    private Reserva[] v = new Reserva[max];

    public ColaReservas() { ini = fin = -1; }

    public boolean esVacia() { return ini==-1; }
    public boolean esLlena() { return (fin+1)%max == ini; }

    public void adi(Reserva r) {
        if(!esLlena()) {
            if(esVacia()) ini = fin = 0;
            else fin = (fin+1)%max;
            v[fin] = r;
        } else System.out.println("Cola de reservas llena!");
    }

    public Reserva eli() {
        if(esVacia()) return null;
        Reserva r = v[ini];
        if(ini==fin) ini=fin=-1;
        else ini=(ini+1)%max;
        return r;
    }

    public void mostrar() {
        if(esVacia()) { System.out.println("(Cola vacía)"); return; }
        int i = ini;
        while(true) {
            v[i].mostrar();
            if(i==fin) break;
            i=(i+1)%max;
        }
    }

    public void vaciar(ColaReservas c) {
        while(!c.esVacia()) adi(c.eli());
    }

    public int nroElem() {
        if(esVacia()) return 0;
        if(fin >= ini) return fin-ini+1;
        else return max-ini+fin+1;
    }

}

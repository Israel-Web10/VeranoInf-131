package cuatro;

public class MulticolaReservas {
	private int n;
    private ColaReservas[] c;

    public MulticolaReservas(int n) {
        this.n = n;
        c = new ColaReservas[n];
        for(int i=0;i<n;i++) c[i] = new ColaReservas();
    }

    public void adicionar(int i, Reserva r) { c[i].adi(r); }
    public Reserva eliminar(int i) { return c[i].eli(); }
    public void mostrar() { for(int i=0;i<n;i++) { System.out.println("Cola tipo "+i+":"); c[i].mostrar(); } }
    public boolean esVacia(int i) { return c[i].esVacia(); }
    public boolean esLlena(int i) { return c[i].esLlena(); }
    public int getN() { return n; }
    public ColaReservas getCola(int i) { return c[i]; }

}

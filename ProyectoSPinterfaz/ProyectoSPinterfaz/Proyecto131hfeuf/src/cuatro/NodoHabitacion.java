package cuatro;

public class NodoHabitacion {
    private Habitacion hab;
    private NodoHabitacion sig;
    private NodoHabitacion ant;

    public NodoHabitacion(Habitacion hab) {
        this.hab = hab;
        this.sig = null;
        this.ant = null;
    }
    public Habitacion getHab() {
        return hab;
    }
    public void setHab(Habitacion hab) {
        this.hab = hab;
    }
    public NodoHabitacion getSig() {
        return sig;
    }
    public void setSig(NodoHabitacion sig) {
        this.sig = sig;
    }
    public NodoHabitacion getAnt() {
        return ant;
    }
    public void setAnt(NodoHabitacion ant) {
        this.ant = ant;
    }
    public void mostrar() {
        hab.mostrar();
    }
    

    
}
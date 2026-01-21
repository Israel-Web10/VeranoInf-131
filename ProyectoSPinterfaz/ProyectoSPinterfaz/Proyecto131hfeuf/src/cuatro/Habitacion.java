package cuatro;

public class Habitacion {
    private int nro;
    private String tipoHabitacion;
    private boolean estado;
    private double precio;
    
    public Habitacion(int nro, String tipoHabitacion, boolean estado, double precio) {
        this.nro = nro;
        this.tipoHabitacion = tipoHabitacion;
        this.estado = estado;
        this.precio = precio;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public void mostrar() {
        System.out.println("Habitacion Nro: " + nro + " | Tipo: " + tipoHabitacion + " | Estado: " + (estado ? "Ocupada" : "Disponible") + " | Precio: " + precio);
    }
    

}

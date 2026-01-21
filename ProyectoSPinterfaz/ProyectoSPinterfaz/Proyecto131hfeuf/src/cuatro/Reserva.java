package cuatro;

public class Reserva {
	private Cliente cliente;
    private String tipoHabitacion;
    private String fecha;

    public Reserva(Cliente cliente, String tipoHabitacion, String fecha) {
        this.cliente = cliente;
        this.tipoHabitacion = tipoHabitacion;
        this.fecha = fecha;
    }

    public String getTipoHabitacion() { return tipoHabitacion; }
    
    public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public String getFecha() { return fecha; }
    public Cliente getCliente() { return cliente; }

    public void mostrar() {
        System.out.println("Cliente: " + cliente.getNombre() + " | Tipo: " + tipoHabitacion + " | Fecha: " + fecha);
    }

}

package cuatro;

public class Cliente {
	private String nombre;
    private String ci;

    public Cliente(String nombre, String ci) {
        this.nombre = nombre;
        this.ci = ci;
    }

    public String getNombre() { return nombre; }
    
    public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void mostrar() {
        System.out.println("Cliente: " + nombre + " | CI: " + ci);
    }

}

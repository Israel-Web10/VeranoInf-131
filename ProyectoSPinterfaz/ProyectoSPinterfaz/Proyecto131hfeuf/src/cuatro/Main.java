package cuatro;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
     // --- LISTA DE HABITACIONES ---
        LS_NormalHab listaHab = new LS_NormalHab();

        listaHab.adiFinal(new Habitacion(101, "Simple", false, 150));
        listaHab.adiFinal(new Habitacion(102, "Doble", true, 250));
        listaHab.adiFinal(new Habitacion(103, "Suite", false, 400));
        listaHab.adiFinal(new Habitacion(104, "Simple", true, 150));
        listaHab.adiFinal(new Habitacion(105, "Doble", false, 250));
        mostrarHabitacionesDisponibles(listaHab);


        // --- CLIENTES ---
        Cliente c1 = new Cliente("Ana","123");
        Cliente c2 = new Cliente("Luis","456");
        Cliente c3 = new Cliente("Marta","789");

        // --- COLA CIRCULAR ---
        ColaCircularClientes colaClientes = new ColaCircularClientes();
        colaClientes.adi(c1); colaClientes.adi(c2); colaClientes.adi(c3);

        System.out.println("=== COLA DE CLIENTES ===");
        colaClientes.mostrar();

        // --- PILA DE RESERVAS ---
        PilaReservas pilaReservas = new PilaReservas();

        // --- MULTICOLA DE RESERVAS ---
        MulticolaReservas mc = new MulticolaReservas(3); // 0=Simple,1=Doble,2=Suite

        // --- RESERVAS ---
        Reserva r1 = new Reserva(c1,"Simple","2025-11-02");
        Reserva r2 = new Reserva(c2,"Doble","2025-11-03");
        Reserva r3 = new Reserva(c3,"Suite","2025-11-05");
        Reserva r4 = new Reserva(c1,"Doble","2025-11-06");

        // --- AGREGAR A MULTICOLA Y PILA ---
        agregarReserva(mc,pilaReservas,0,r1);
        agregarReserva(mc,pilaReservas,1,r2);
        agregarReserva(mc,pilaReservas,2,r3);
        agregarReserva(mc,pilaReservas,1,r4);

        System.out.println("\n=== MULTICOLA DE RESERVAS ===");
        mc.mostrar();

        System.out.println("\n=== PILA DE RESERVAS RECIENTES ===");
        pilaReservas.mostrar();

        // --- DESHACER ULTIMA RESERVA ---
        System.out.println("\n=== DESHACER ÚLTIMA RESERVA ===");
        deshacerUltimaReserva(pilaReservas);
        System.out.println("\n=== PILA DE RESERVAS ACTUALIZADA ===");
        pilaReservas.mostrar();

        // --- ATENDER CLIENTES ---
        System.out.println("\n=== ATENDER CLIENTES ===");
        atenderClientes(colaClientes);

        // --- BUSCAR RESERVA DE CLIENTE ---
        System.out.println("\n=== BUSCAR RESERVA CLIENTE 'Ana' ===");
        buscarReservaCliente(mc,"Ana","123");

        // --- CONTAR RESERVAS DE CLIENTE ---
        int cant = contarReservasCliente(mc,"Ana");
        System.out.println("Ana tiene "+cant+" reservas.");

        // --- RESERVAS POR FECHA ---
        System.out.println("\n=== RESERVAS PARA 2025-11-03 ===");
        reservasPorFecha(mc,"2025-11-03");

        // --- CANCELAR RESERVA ---
        System.out.println("\n=== CANCELAR RESERVA DE 'Luis' TIPO Doble ===");
        cancelarReserva(mc,pilaReservas,"Luis","Doble");
        mc.mostrar();

        System.out.println("\n=== MODIFICAR RESERVA (POR CONSOLA) ===");
        modificarReservaConsola(mc, pilaReservas, sc);

        System.out.println("\n=== MULTICOLA ACTUALIZADA ===");
        mc.mostrar();

        System.out.println("\n=== PILA ACTUALIZADA ===");
        pilaReservas.mostrar();

        sc.close();


    }
    

    // ---------------- PROBLEMAS ----------------
    static void agregarReserva(MulticolaReservas mc, PilaReservas pila,int tipo,Reserva r) {
        mc.adicionar(tipo,r);
        pila.adi(r);
    }

    static void deshacerUltimaReserva(PilaReservas pila) {
        if(!pila.esVacia()) {
            Reserva r = pila.eli();
            System.out.println("Se deshizo la reserva de: "+r.getCliente().getNombre()+" | Tipo: "+r.getTipoHabitacion());
        } else System.out.println("No hay reservas para deshacer.");
    }

    static void atenderClientes(ColaCircularClientes cola) {
        int total = cola.nroElem();
        for(int i=0;i<total;i++) {
            Cliente c = cola.eli();
            if(c!=null) {
                System.out.println("Atendiendo a: "+c.getNombre());
                cola.adi(c); // flujo circular
            }
        }
    }

    static void buscarReservaCliente(MulticolaReservas mc, String nombre, String ci) {
        boolean encontrado = false;

        for (int i = 0; i < mc.getN(); i++) {
            ColaReservas aux = mc.getCola(i);
            ColaReservas temp = new ColaReservas();

            while (!aux.esVacia()) {
                Reserva r = aux.eli();

                boolean coincide =
                        r.getCliente().getNombre().equalsIgnoreCase(nombre)
                        || r.getCliente().getCi() == ci;

                if (coincide) {
                    System.out.println("Reserva encontrada - Tipo: " +
                            r.getTipoHabitacion() + " | Fecha: " + r.getFecha());
                    encontrado = true;
                }

                temp.adi(r);
            }
            aux.vaciar(temp);
        }

        if (!encontrado)
            System.out.println("No se encontraron reservas para este cliente.");
    }

    static int contarReservasCliente(MulticolaReservas mc,String nombre) {
        int contador=0;
        for(int i=0;i<mc.getN();i++) {
            ColaReservas aux = mc.getCola(i);
            ColaReservas temp = new ColaReservas();
            while(!aux.esVacia()) {
                Reserva r = aux.eli();
                if(r.getCliente().getNombre().equalsIgnoreCase(nombre)) contador++;
                temp.adi(r);
            }
            aux.vaciar(temp);
        }
        return contador;
    }

    static void reservasPorFecha(MulticolaReservas mc,String fecha) {
        System.out.println("Reservas para la fecha: "+fecha);
        for(int i=0;i<mc.getN();i++) {
            ColaReservas aux = mc.getCola(i);
            ColaReservas temp = new ColaReservas();
            while(!aux.esVacia()) {
                Reserva r = aux.eli();
                if(r.getFecha().equals(fecha)) System.out.println("Cliente: "+r.getCliente().getNombre()+" | Tipo: "+r.getTipoHabitacion());
                temp.adi(r);
            }
            aux.vaciar(temp);
        }
    }

    static void cancelarReserva(MulticolaReservas mc,PilaReservas pila,String nombre,String tipoHab) {
        for(int i=0;i<mc.getN();i++) {
            ColaReservas aux = mc.getCola(i);
            ColaReservas temp = new ColaReservas();
            while(!aux.esVacia()) {
                Reserva r = aux.eli();
                if(r.getCliente().getNombre().equalsIgnoreCase(nombre) && r.getTipoHabitacion().equalsIgnoreCase(tipoHab)) {
                    System.out.println("Reserva cancelada: "+nombre+" | Tipo: "+tipoHab);
                    // Quitar de pila si es la última
                    if(!pila.esVacia() && pila.eli()==r) {
                        System.out.println("Se eliminó de la pila de reservas recientes.");
                    }
                } else temp.adi(r);
            }
            aux.vaciar(temp);
        }
    }
    static void mostrarHabitacionesDisponibles(LS_NormalHab listaHab) {
        NodoHabitacion aux = listaHab.getP();

        System.out.println("\n=== HABITACIONES DISPONIBLES ===");

        boolean hay = false;

        while(aux != null) {
            Habitacion h = aux.getHab();
            if(!h.isEstado()) { // false = Disponible
                System.out.println("Nro: " + h.getNro() +
                                   " | Tipo: " + h.getTipoHabitacion() +
                                   " | Precio: " + h.getPrecio());
                hay = true;
            }
            aux = aux.getSig();
        }

        if(!hay) {
            System.out.println("No hay habitaciones disponibles.");
        }
    }
    static void modificarReserva(MulticolaReservas mc, PilaReservas pila,
            String nombreCliente, String ci,
            String tipoHabActual, String nuevoTipoHab) {

			boolean modificado = false;
			
			for (int i = 0; i < mc.getN(); i++) {
			ColaReservas aux = mc.getCola(i);
			ColaReservas temp = new ColaReservas();
			
			while (!aux.esVacia()) {
			    Reserva r = aux.eli();
			    boolean coincide =(r.getCliente().getNombre().equalsIgnoreCase(nombreCliente)|| r.getCliente().getCi() == ci)&&r.getTipoHabitacion().equalsIgnoreCase(tipoHabActual);
			    if (coincide) {
			        System.out.println("Reserva modificada de: "+ nombreCliente + " | CI: " + ci +" | Tipo: " + tipoHabActual +" -> " + nuevoTipoHab);
		            r.setTipoHabitacion(nuevoTipoHab);
			        modificado = true;
			// actualizar pila
			if (!pila.esVacia()) {
			   PilaReservas auxPila = new PilaReservas();
			   while (!pila.esVacia()) {
			       Reserva rp = pila.eli();
			       if (rp == r) {
			           rp.setTipoHabitacion(nuevoTipoHab);
			       }
			       auxPila.adi(rp);
			   }
			   while (!auxPila.esVacia()) pila.adi(auxPila.eli());
			}
			}
			
			temp.adi(r);
			}
			aux.vaciar(temp);
			}

		if (!modificado)
		System.out.println("No se encontró una reserva que coincida con los datos proporcionados.");
	}

    static void modificarReservaConsola(MulticolaReservas mc, PilaReservas pila, Scanner sc) {
        sc.nextLine();

        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();

        System.out.print("CI del cliente: ");
        String ci = sc.nextLine();
        sc.nextLine();

        System.out.print("Tipo de habitación actual: ");
        String tipoActual = sc.nextLine();

        System.out.print("Nuevo tipo de habitación: ");
        String nuevoTipo = sc.nextLine();

        modificarReserva(mc, pila, nombre, ci, tipoActual, nuevoTipo);
    }



}


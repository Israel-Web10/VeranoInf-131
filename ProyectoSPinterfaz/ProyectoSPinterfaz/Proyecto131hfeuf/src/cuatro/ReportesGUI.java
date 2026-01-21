package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReportesGUI extends JFrame {
    private MulticolaReservas multicolaReservas;
    private LS_NormalHab listaHab;
    private ColaCircularClientes colaClientes;
    private JTextArea reporteArea;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color WARNING = new Color(243, 156, 18);
    private final Color PURPLE = new Color(142, 68, 173);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public ReportesGUI(MulticolaReservas mc, LS_NormalHab lh, ColaCircularClientes cc) {
        this.multicolaReservas = mc;
        this.listaHab = lh;
        this.colaClientes = cc;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Reportes y Estadísticas");
        setSize(850, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" REPORTES Y ESTADÍSTICAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Área de reporte
        reporteArea = new JTextArea();
        reporteArea.setEditable(false);
        reporteArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reporteArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(reporteArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBackground(LIGHT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50));
        
        JButton btnGeneral = createButton(" Reporte General", SUCCESS);
        btnGeneral.addActionListener(e -> generarReporteGeneral());
        
        JButton btnHabitaciones = createButton(" Habitaciones", PRIMARY);
        btnHabitaciones.addActionListener(e -> reporteHabitaciones());
        
        JButton btnReservas = createButton(" Reservas", WARNING);
        btnReservas.addActionListener(e -> reporteReservas());
        
        JButton btnClientes = createButton(" Clientes", PURPLE);
        btnClientes.addActionListener(e -> reporteClientes());
        
        JButton btnLimpiar = createButton(" Limpiar", PRIMARY);
        btnLimpiar.addActionListener(e -> reporteArea.setText(""));
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnGeneral);
        buttonPanel.add(btnHabitaciones);
        buttonPanel.add(btnReservas);
        buttonPanel.add(btnClientes);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        generarReporteGeneral();
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            Color original = color;
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(brighten(original, 30));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(original);
            }
        });
        
        return btn;
    }
    
    private Color brighten(Color color, int amount) {
        int r = Math.min(255, color.getRed() + amount);
        int g = Math.min(255, color.getGreen() + amount);
        int b = Math.min(255, color.getBlue() + amount);
        return new Color(r, g, b);
    }
    
    private void generarReporteGeneral() {
        reporteArea.setText("");
        reporteArea.append("╔══════════════════════════════════════════════════════════════╗\n");
        reporteArea.append("║                  REPORTE GENERAL DEL HOTEL                   ║\n");
        reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
        reporteArea.append("║                                                              ║\n");
        
        // Contar habitaciones
        int totalHab = 0, ocupadas = 0, disponibles = 0;
        NodoHabitacion aux = listaHab.getP();
        while(aux != null) {
            totalHab++;
            if(aux.getHab().isEstado()) ocupadas++;
            else disponibles++;
            aux = aux.getSig();
        }
        
        reporteArea.append(String.format("║   HABITACIONES                                             ║\n"));
        reporteArea.append(String.format("║     Total: %d habitaciones                                   ║\n", totalHab));
        reporteArea.append(String.format("║      Ocupadas: %d (%.1f%%)                                 ║\n", 
            ocupadas, totalHab > 0 ? (ocupadas*100.0/totalHab) : 0));
        reporteArea.append(String.format("║      Disponibles: %d (%.1f%%)                              ║\n", 
            disponibles, totalHab > 0 ? (disponibles*100.0/totalHab) : 0));
        reporteArea.append("║                                                              ║\n");
        
        // Contar reservas
        int totalReservas = 0;
        int[] reservasPorTipo = new int[3];
        for(int i = 0; i < 3; i++) {
            reservasPorTipo[i] = multicolaReservas.getCola(i).nroElem();
            totalReservas += reservasPorTipo[i];
        }
        
        reporteArea.append("║  RESERVAS                                                 ║\n");
        reporteArea.append(String.format("║     Total de reservas: %d                                    ║\n", totalReservas));
        reporteArea.append(String.format("║       Simple: %d reservas                                  ║\n", reservasPorTipo[0]));
        reporteArea.append(String.format("║      Doble: %d reservas                                  ║\n", reservasPorTipo[1]));
        reporteArea.append(String.format("║      Suite: %d reservas                                    ║\n", reservasPorTipo[2]));
        reporteArea.append("║                                                              ║\n");
        
        // Clientes
        int totalClientes = colaClientes.nroElem();
        reporteArea.append("║   CLIENTES                                                 ║\n");
        reporteArea.append(String.format("║     Clientes en cola: %d                                     ║\n", totalClientes));
        reporteArea.append("║                                                              ║\n");
        
        reporteArea.append("╚══════════════════════════════════════════════════════════════╝\n");
    }
    
    private void reporteHabitaciones() {
        reporteArea.setText("");
        reporteArea.append("╔══════════════════════════════════════════════════════════════╗\n");
        reporteArea.append("║              REPORTE DETALLADO DE HABITACIONES               ║\n");
        reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        NodoHabitacion aux = listaHab.getP();
        int contador = 0;
        
        while(aux != null) {
            contador++;
            Habitacion h = aux.getHab();
            String estado = h.isEstado() ? "Ocupada" : " Disponible";
            
            reporteArea.append(String.format("║ %d. Habitación #%-3d                                         ║\n", 
                contador, h.getNro()));
            reporteArea.append(String.format("║    Tipo: %-20s Estado: %-15s║\n", 
                h.getTipoHabitacion(), estado));
            reporteArea.append(String.format("║    Precio: Bs. %-10.2f                                  ║\n", 
                h.getPrecio()));
            reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
            
            aux = aux.getSig();
        }
        
        reporteArea.append(String.format("║              Total: %d habitaciones                          ║\n", contador));
        reporteArea.append("╚══════════════════════════════════════════════════════════════╝\n");
    }
    
    private void reporteReservas() {
        reporteArea.setText("");
        reporteArea.append("╔══════════════════════════════════════════════════════════════╗\n");
        reporteArea.append("║               REPORTE DETALLADO DE RESERVAS                  ║\n");
        reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        String[] tipos = {" SIMPLE", " DOBLE", " SUITE"};
        int totalGeneral = 0;
        
        for(int i = 0; i < 3; i++) {
            reporteArea.append(String.format("║  %s                                                    ║\n", tipos[i]));
            reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
            
            ColaReservas cola = multicolaReservas.getCola(i);
            ColaReservas temp = new ColaReservas();
            int count = 0;
            
            while(!cola.esVacia()) {
                Reserva r = cola.eli();
                count++;
                totalGeneral++;
                
                reporteArea.append(String.format("║ %d. %-25s                          ║\n", 
                    count, r.getCliente().getNombre()));
                reporteArea.append(String.format("║    CI: %-15s  Fecha: %-15s      ║\n", 
                    r.getCliente().getCi(), r.getFecha()));
                reporteArea.append("║                                                              ║\n");
                
                temp.adi(r);
            }
            
            if(count == 0) {
                reporteArea.append("║    No hay reservas de este tipo                              ║\n");
                reporteArea.append("║                                                              ║\n");
            }
            
            cola.vaciar(temp);
            
            if(i < 2) {
                reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
            }
        }
        
        reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
        reporteArea.append(String.format("║           TOTAL DE RESERVAS: %-4d                          ║\n", totalGeneral));
        reporteArea.append("╚══════════════════════════════════════════════════════════════╝\n");
    }
    
    private void reporteClientes() {
        reporteArea.setText("");
        reporteArea.append("╔══════════════════════════════════════════════════════════════╗\n");
        reporteArea.append("║                 REPORTE DE CLIENTES EN COLA                  ║\n");
        reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        if(colaClientes.esVacia()) {
            reporteArea.append("║                                                              ║\n");
            reporteArea.append("║                No hay clientes en cola                     ║\n");
            reporteArea.append("║                                                              ║\n");
        } else {
            ColaCircularClientes temp = new ColaCircularClientes();
            int pos = 1;
            
            while(!colaClientes.esVacia()) {
                Cliente c = colaClientes.eli();
                reporteArea.append(String.format("║ %d.  %-30s                        ║\n", 
                    pos++, c.getNombre()));
                reporteArea.append(String.format("║     CI: %-48s║\n", c.getCi()));
                reporteArea.append("╠══════════════════════════════════════════════════════════════╣\n");
                temp.adi(c);
            }
            
            while(!temp.esVacia()) {
                colaClientes.adi(temp.eli());
            }
        }
        
        reporteArea.append(String.format("║           Total de clientes en cola: %-4d                  ║\n", 
            colaClientes.nroElem()));
        reporteArea.append("╚══════════════════════════════════════════════════════════════╝\n");
    }
}
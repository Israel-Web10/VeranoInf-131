package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VerReservasGUI extends JFrame {
    private MulticolaReservas multicolaReservas;
    private JTabbedPane tabbedPane;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public VerReservasGUI(MulticolaReservas mc) {
        this.multicolaReservas = mc;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Ver Reservas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" TODAS LAS RESERVAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Pestaña Simple
        JTextArea simpleArea = createTextArea();
        tabbedPane.addTab(" Simple", new JScrollPane(simpleArea));
        
        // Pestaña Doble
        JTextArea dobleArea = createTextArea();
        tabbedPane.addTab(" Doble", new JScrollPane(dobleArea));
        
        // Pestaña Suite
        JTextArea suiteArea = createTextArea();
        tabbedPane.addTab(" Suite", new JScrollPane(suiteArea));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnActualizar = createButton(" Actualizar", PRIMARY);
        btnActualizar.addActionListener(e -> {
            cargarReservas(simpleArea, 0);
            cargarReservas(dobleArea, 1);
            cargarReservas(suiteArea, 2);
        });
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Cargar datos iniciales
        cargarReservas(simpleArea, 0);
        cargarReservas(dobleArea, 1);
        cargarReservas(suiteArea, 2);
    }
    
    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return area;
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
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 45));
        
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
    
    private void cargarReservas(JTextArea area, int tipo) {
        area.setText("");
        String[] tipos = {"SIMPLE", "DOBLE", "SUITE"};
        
        area.append("╔══════════════════════════════════════════════════════╗\n");
        area.append(String.format("║         RESERVAS TIPO %s%-20s║\n", tipos[tipo], ""));
        area.append("╠══════════════════════════════════════════════════════╣\n");
        
        ColaReservas cola = multicolaReservas.getCola(tipo);
        ColaReservas temp = new ColaReservas();
        
        int count = 0;
        while(!cola.esVacia()) {
            Reserva r = cola.eli();
            count++;
            area.append(String.format("║ %d. Cliente: %-25s ║\n", count, r.getCliente().getNombre()));
            area.append(String.format("║    CI: %-40s ║\n", r.getCliente().getCi()));
            area.append(String.format("║    Fecha: %-37s ║\n", r.getFecha()));
            area.append("╠══════════════════════════════════════════════════════╣\n");
            temp.adi(r);
        }
        
        if(count == 0) {
            area.append("║                No hay reservas                     ║\n");
            area.append("╚══════════════════════════════════════════════════════╝\n");
        } else {
            area.append(String.format("║              Total: %d reservas%-19s║\n", count, ""));
            area.append("╚══════════════════════════════════════════════════════╝\n");
        }
        
        // Restaurar cola
        cola.vaciar(temp);
    }
}
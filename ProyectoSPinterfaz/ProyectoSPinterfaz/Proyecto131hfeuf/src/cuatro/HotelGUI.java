package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HotelGUI extends JFrame {
    private LS_NormalHab listaHab;
    private ColaCircularClientes colaClientes;
    private PilaReservas pilaReservas;
    private MulticolaReservas multicolaReservas;
    
    // Colores vibrantes y profesionales
    private final Color BG_DARK = new Color(44, 62, 80);
    private final Color CARD_BG = new Color(52, 73, 94);
    
    public HotelGUI() {
        initData();
        initComponents();
    }
    
    private void initData() {
        // Inicializar estructuras de datos
        listaHab = new LS_NormalHab();
        listaHab.adiFinal(new Habitacion(101, "Simple", false, 150));
        listaHab.adiFinal(new Habitacion(102, "Doble", true, 250));
        listaHab.adiFinal(new Habitacion(103, "Suite", false, 400));
        listaHab.adiFinal(new Habitacion(104, "Simple", true, 150));
        listaHab.adiFinal(new Habitacion(105, "Doble", false, 250));
        
        colaClientes = new ColaCircularClientes();
        colaClientes.adi(new Cliente("Ana", "123"));
        colaClientes.adi(new Cliente("Luis", "456"));
        colaClientes.adi(new Cliente("Marta", "789"));
        
        pilaReservas = new PilaReservas();
        multicolaReservas = new MulticolaReservas(3);
        
        // Agregar reservas iniciales
        Reserva r1 = new Reserva(new Cliente("Ana","123"),"Simple","2025-11-02");
        Reserva r2 = new Reserva(new Cliente("Luis","456"),"Doble","2025-11-03");
        multicolaReservas.adicionar(0, r1);
        multicolaReservas.adicionar(1, r2);
        pilaReservas.adi(r1);
        pilaReservas.adi(r2);
    }
    
    private void initComponents() {
        setTitle(" Sistema Inteligente de Reservas");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal con fondo oscuro
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(BG_DARK);
        
        // Header moderno
        JPanel headerPanel = createHeader();
        
        // Panel de contenido con scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_DARK);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Sección 1: Gestión Principal
        contentPanel.add(createSectionTitle(" GESTIÓN PRINCIPAL"));
        contentPanel.add(Box.createVerticalStrut(20));
        
        JPanel row1 = new JPanel(new GridLayout(1, 3, 25, 0));
        row1.setOpaque(false);
        row1.setMaximumSize(new Dimension(1100, 160));
        
        row1.add(createCard("", "HABITACIONES", "Administrar habitaciones del hotel", 
            new Color(26, 188, 156), e -> new HabitacionesGUI(listaHab).setVisible(true)));
        row1.add(createCard("", "CLIENTES", "Gestionar cola de clientes", 
            new Color(52, 152, 219), e -> new ClientesGUI(colaClientes).setVisible(true)));
        row1.add(createCard("", "REPORTES", "Ver estadísticas y reportes", 
            new Color(155, 89, 182), e -> new ReportesGUI(multicolaReservas, listaHab, colaClientes).setVisible(true)));
        
        contentPanel.add(row1);
        contentPanel.add(Box.createVerticalStrut(40));
        
        // Sección 2: Reservas
        contentPanel.add(createSectionTitle(" GESTIÓN DE RESERVAS"));
        contentPanel.add(Box.createVerticalStrut(20));
        
        JPanel row2 = new JPanel(new GridLayout(1, 3, 25, 0));
        row2.setOpaque(false);
        row2.setMaximumSize(new Dimension(1100, 140));
        
        row2.add(createCard("", "NUEVA RESERVA", "Crear una nueva reserva", 
            new Color(46, 204, 113), e -> new NuevaReservaGUI(multicolaReservas, pilaReservas, listaHab, colaClientes).setVisible(true)));
        row2.add(createCard("", "VER RESERVAS", "Ver todas las reservas", 
            new Color(241, 196, 15), e -> new VerReservasGUI(multicolaReservas).setVisible(true)));
        row2.add(createCard("", "BUSCAR RESERVA", "Buscar por cliente", 
            new Color(52, 152, 219), e -> new BuscarReservaGUI(multicolaReservas).setVisible(true)));
        
        contentPanel.add(row2);
        contentPanel.add(Box.createVerticalStrut(30));
        
        JPanel row3 = new JPanel(new GridLayout(1, 3, 25, 0));
        row3.setOpaque(false);
        row3.setMaximumSize(new Dimension(1100, 140));
        
        row3.add(createCard("", "MODIFICAR", "Modificar una reserva", 
            new Color(230, 126, 34), e -> new ModificarReservaGUI(multicolaReservas, pilaReservas).setVisible(true)));
        row3.add(createCard("", "CANCELAR", "Cancelar una reserva", 
            new Color(231, 76, 60), e -> new CancelarReservaGUI(multicolaReservas, pilaReservas).setVisible(true)));
        row3.add(createCard("", "DESHACER", "Deshacer última reserva", 
            new Color(149, 165, 166), e -> deshacerUltimaReserva()));
        
        contentPanel.add(row3);
        contentPanel.add(Box.createVerticalStrut(20));
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(41, 128, 185));
        header.setBorder(BorderFactory.createEmptyBorder(35, 30, 35, 30));
        
        JLabel icon = new JLabel("", SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.PLAIN, 60));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Sistema Inteligente de Reservas");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Sistema Profesional de Gestión de Habitaciones");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(new Color(236, 240, 241));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        header.add(icon);
        header.add(Box.createVerticalStrut(10));
        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);
        
        return header;
    }
    
    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(new Color(236, 240, 241));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel createCard(String icon, String title, String description, Color color, ActionListener action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        
        card.setLayout(new BorderLayout(15, 15));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setOpaque(false);
        
        // Panel izquierdo con icono
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setForeground(color);
        leftPanel.add(iconLabel, BorderLayout.CENTER);
        
        // Panel derecho con texto
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        descLabel.setForeground(new Color(189, 195, 199));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        rightPanel.add(titleLabel);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(descLabel);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.CENTER);
        
        // Barra de color en la parte inferior
        JPanel colorBar = new JPanel();
        colorBar.setBackground(color);
        colorBar.setPreferredSize(new Dimension(0, 5));
        card.add(colorBar, BorderLayout.SOUTH);
        
        // Efectos hover
        card.addMouseListener(new MouseAdapter() {
            Color originalBg = CARD_BG;
            Color hoverBg = new Color(69, 90, 100);
            
            public void mouseEntered(MouseEvent e) {
                card.setBackground(hoverBg);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2),
                    BorderFactory.createEmptyBorder(23, 18, 23, 18)
                ));
                card.repaint();
            }
            
            public void mouseExited(MouseEvent e) {
                card.setBackground(originalBg);
                card.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
                card.repaint();
            }
            
            public void mousePressed(MouseEvent e) {
                card.setBackground(new Color(58, 78, 89));
                card.repaint();
            }
            
            public void mouseReleased(MouseEvent e) {
                card.setBackground(hoverBg);
                card.repaint();
            }
            
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }
        });
        
        return card;
    }
    
    private void deshacerUltimaReserva() {
        if(!pilaReservas.esVacia()) {
            Reserva r = pilaReservas.eli();
            JOptionPane.showMessageDialog(this, 
                " Reserva deshecha:\nCliente: " + r.getCliente().getNombre() + 
                "\nTipo: " + r.getTipoHabitacion(),
                "Deshacer Reserva", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                " No hay reservas para deshacer.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new HotelGUI().setVisible(true);
        });
    }
}
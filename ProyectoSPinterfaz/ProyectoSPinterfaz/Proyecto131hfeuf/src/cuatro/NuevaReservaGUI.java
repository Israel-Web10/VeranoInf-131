package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NuevaReservaGUI extends JFrame {
    private MulticolaReservas multicolaReservas;
    private PilaReservas pilaReservas;
    private LS_NormalHab listaHab;
    private ColaCircularClientes colaClientes;
    
    private JTextField nombreField, ciField, fechaField;
    private JComboBox<String> tipoCombo;
    private JTextArea habitacionesArea;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public NuevaReservaGUI(MulticolaReservas mc, PilaReservas pr, LS_NormalHab lh, ColaCircularClientes cc) {
        this.multicolaReservas = mc;
        this.pilaReservas = pr;
        this.listaHab = lh;
        this.colaClientes = cc;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("📅 Nueva Reserva");
        setSize(800, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" CREAR NUEVA RESERVA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel(" Nombre del Cliente:"), gbc);
        
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nombreField, gbc);
        
        // CI
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel(" CI:"), gbc);
        
        gbc.gridx = 1;
        ciField = new JTextField(20);
        ciField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(ciField, gbc);
        
        // Tipo de Habitación
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel(" Tipo de Habitación:"), gbc);
        
        gbc.gridx = 1;
        tipoCombo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"});
        tipoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tipoCombo, gbc);
        
        // Fecha
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel(" Fecha (AAAA-MM-DD):"), gbc);
        
        gbc.gridx = 1;
        fechaField = new JTextField("2025-12-01", 20);
        fechaField.setFont(new Font("Arial", Font.PLAIN, 14));
        fechaField.setToolTipText("Ingrese la fecha en formato: AAAA-MM-DD (ejemplo: 2025-12-25)");
        formPanel.add(fechaField, gbc);
        
        // Habitaciones disponibles
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JLabel habLabel = createLabel(" Habitaciones Disponibles:");
        formPanel.add(habLabel, gbc);
        
        gbc.gridy = 5;
        habitacionesArea = new JTextArea(6, 30);
        habitacionesArea.setEditable(false);
        habitacionesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(habitacionesArea);
        formPanel.add(scrollPane, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnCrear = createButton(" Crear Reserva", SUCCESS);
        btnCrear.addActionListener(e -> crearReserva());
        
        JButton btnActualizar = createButton(" Actualizar Habitaciones", PRIMARY);
        btnActualizar.addActionListener(e -> mostrarHabitacionesDisponibles());
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCrear);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        mostrarHabitacionesDisponibles();
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(PRIMARY);
        return label;
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
        btn.setPreferredSize(new Dimension(220, 45));
        
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
    
    private void mostrarHabitacionesDisponibles() {
        habitacionesArea.setText("");
        habitacionesArea.append("╔══════════════════════════════════════════╗\n");
        habitacionesArea.append("║    HABITACIONES DISPONIBLES              ║\n");
        habitacionesArea.append("╠══════════════════════════════════════════╣\n");
        
        NodoHabitacion aux = listaHab.getP();
        boolean hayDisponibles = false;
        
        while(aux != null) {
            Habitacion h = aux.getHab();
            if(!h.isEstado()) {
                habitacionesArea.append(String.format("║ Nro: %-4d | %-8s | Bs. %-7.2f ║\n", 
                    h.getNro(), h.getTipoHabitacion(), h.getPrecio()));
                hayDisponibles = true;
            }
            aux = aux.getSig();
        }
        
        if(!hayDisponibles) {
            habitacionesArea.append("║    No hay habitaciones disponibles    ║\n");
        }
        
        habitacionesArea.append("╚══════════════════════════════════════════╝\n");
    }
    
    private void crearReserva() {
        String nombre = nombreField.getText().trim();
        String ci = ciField.getText().trim();
        String tipo = (String) tipoCombo.getSelectedItem();
        String fecha = fechaField.getText().trim();
        
        if(nombre.isEmpty() || ci.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                " Por favor complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar formato de fecha básico
        if(!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, 
                " Formato de fecha incorrecto\n\nUse el formato: AAAA-MM-DD\nEjemplo: 2025-12-25", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear cliente y reserva
        Cliente cliente = new Cliente(nombre, ci);
        Reserva reserva = new Reserva(cliente, tipo, fecha);
        
        // Agregar a multicola según tipo
        int tipoIndex = tipo.equals("Simple") ? 0 : tipo.equals("Doble") ? 1 : 2;
        multicolaReservas.adicionar(tipoIndex, reserva);
        pilaReservas.adi(reserva);
        
        JOptionPane.showMessageDialog(this, 
            " Reserva creada exitosamente!\n\n" +
            "Cliente: " + nombre + "\n" +
            "Tipo: " + tipo + "\n" +
            "Fecha: " + fecha,
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar campos
        nombreField.setText("");
        ciField.setText("");
        tipoCombo.setSelectedIndex(0);
        fechaField.setText("2025-12-01");
    }
}
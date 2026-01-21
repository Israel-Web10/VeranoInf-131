package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModificarReservaGUI extends JFrame {
    private MulticolaReservas multicolaReservas;
    private PilaReservas pilaReservas;
    
    private JTextField nombreField, ciField;
    private JComboBox<String> tipoActualCombo, nuevoTipoCombo;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color WARNING = new Color(230, 126, 34);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public ModificarReservaGUI(MulticolaReservas mc, PilaReservas pr) {
        this.multicolaReservas = mc;
        this.pilaReservas = pr;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Modificar Reserva");
        setSize(650, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" MODIFICAR RESERVA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 12, 12, 12);
        
        // Sección: Buscar Reserva
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel seccion1 = new JLabel(" Buscar Reserva");
        seccion1.setFont(new Font("Arial", Font.BOLD, 16));
        seccion1.setForeground(PRIMARY);
        formPanel.add(seccion1, gbc);
        
        gbc.gridwidth = 1;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel(" Nombre:"), gbc);
        
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nombreField, gbc);
        
        // CI
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel(" CI:"), gbc);
        
        gbc.gridx = 1;
        ciField = new JTextField(20);
        ciField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(ciField, gbc);
        
        // Tipo Actual
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel(" Tipo Actual:"), gbc);
        
        gbc.gridx = 1;
        tipoActualCombo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"});
        tipoActualCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tipoActualCombo, gbc);
        
        // Separador
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JSeparator sep = new JSeparator();
        formPanel.add(sep, gbc);
        
        // Sección: Nuevo Tipo
        gbc.gridy = 5;
        JLabel seccion2 = new JLabel(" Nuevo Tipo de Habitación");
        seccion2.setFont(new Font("Arial", Font.BOLD, 16));
        seccion2.setForeground(WARNING);
        formPanel.add(seccion2, gbc);
        
        gbc.gridwidth = 1;
        
        // Nuevo Tipo
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabel(" Nuevo Tipo:"), gbc);
        
        gbc.gridx = 1;
        nuevoTipoCombo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"});
        nuevoTipoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nuevoTipoCombo, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnModificar = createButton(" Modificar", WARNING);
        btnModificar.addActionListener(e -> modificarReserva());
        
        JButton btnLimpiar = createButton(" Limpiar", PRIMARY);
        btnLimpiar.addActionListener(e -> limpiar());
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
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
        btn.setPreferredSize(new Dimension(160, 45));
        
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
    
    private void modificarReserva() {
        String nombre = nombreField.getText().trim();
        String ci = ciField.getText().trim();
        String tipoActual = (String) tipoActualCombo.getSelectedItem();
        String nuevoTipo = (String) nuevoTipoCombo.getSelectedItem();
        
        if(nombre.isEmpty() || ci.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Complete el nombre y CI del cliente", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(tipoActual.equals(nuevoTipo)) {
            JOptionPane.showMessageDialog(this, 
                "El tipo nuevo debe ser diferente al actual", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean modificado = false;
        
        for(int i = 0; i < multicolaReservas.getN(); i++) {
            ColaReservas cola = multicolaReservas.getCola(i);
            ColaReservas temp = new ColaReservas();
            
            while(!cola.esVacia()) {
                Reserva r = cola.eli();
                
                boolean coincide = (r.getCliente().getNombre().equalsIgnoreCase(nombre) || 
                                   r.getCliente().getCi().equals(ci)) &&
                                   r.getTipoHabitacion().equalsIgnoreCase(tipoActual);
                
                if(coincide) {
                    r.setTipoHabitacion(nuevoTipo);
                    modificado = true;
                    
                    // Actualizar en pila
                    PilaReservas auxPila = new PilaReservas();
                    while(!pilaReservas.esVacia()) {
                        Reserva rp = pilaReservas.eli();
                        if(rp == r) {
                            rp.setTipoHabitacion(nuevoTipo);
                        }
                        auxPila.adi(rp);
                    }
                    while(!auxPila.esVacia()) {
                        pilaReservas.adi(auxPila.eli());
                    }
                }
                
                temp.adi(r);
            }
            
            cola.vaciar(temp);
        }
        
        if(modificado) {
            JOptionPane.showMessageDialog(this, 
                " Reserva modificada exitosamente!\n\n" +
                "Cliente: " + nombre + "\n" +
                tipoActual + " → " + nuevoTipo,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, 
                " No se encontró la reserva especificada ni modo :)", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiar() {
        nombreField.setText("");
        ciField.setText("");
        tipoActualCombo.setSelectedIndex(0);
        nuevoTipoCombo.setSelectedIndex(0);
    }
}
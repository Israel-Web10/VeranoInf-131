package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CancelarReservaGUI extends JFrame {
    private MulticolaReservas multicolaReservas;
    private PilaReservas pilaReservas;
    
    private JTextField nombreField;
    private JComboBox<String> tipoCombo;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public CancelarReservaGUI(MulticolaReservas mc, PilaReservas pr) {
        this.multicolaReservas = mc;
        this.pilaReservas = pr;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Cancelar Reserva");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header con advertencia
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(LIGHT);
        
        JLabel titleLabel = new JLabel(" CANCELAR RESERVA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(DANGER);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel warningLabel = new JLabel(" Esta acción no se puede deshacer");
        warningLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        warningLabel.setForeground(new Color(230, 126, 34));
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(warningLabel, BorderLayout.SOUTH);
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DANGER, 2),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nombreLabel = new JLabel(" Nombre del Cliente:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nombreLabel.setForeground(PRIMARY);
        formPanel.add(nombreLabel, gbc);
        
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nombreField, gbc);
        
        // Tipo
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel tipoLabel = new JLabel(" Tipo de Habitación:");
        tipoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tipoLabel.setForeground(PRIMARY);
        formPanel.add(tipoLabel, gbc);
        
        gbc.gridx = 1;
        tipoCombo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"});
        tipoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tipoCombo, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnCancelar = createButton(" Cancelar Reserva", DANGER);
        btnCancelar.addActionListener(e -> cancelarReserva());
        
        JButton btnLimpiar = createButton(" Limpiar", PRIMARY);
        btnLimpiar.addActionListener(e -> limpiar());
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
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
        btn.setPreferredSize(new Dimension(190, 45));
        
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
    
    private void cancelarReserva() {
        String nombre = nombreField.getText().trim();
        String tipo = (String) tipoCombo.getSelectedItem();
        
        if(nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                " Ingrese el nombre del cliente", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmar cancelación
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cancelar la reserva de:\n\n" +
            "Cliente: " + nombre + "\n" +
            "Tipo: " + tipo + "\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Cancelación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if(confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        boolean cancelado = false;
        
        for(int i = 0; i < multicolaReservas.getN(); i++) {
            ColaReservas cola = multicolaReservas.getCola(i);
            ColaReservas temp = new ColaReservas();
            
            while(!cola.esVacia()) {
                Reserva r = cola.eli();
                
                if(r.getCliente().getNombre().equalsIgnoreCase(nombre) && 
                   r.getTipoHabitacion().equalsIgnoreCase(tipo)) {
                    cancelado = true;
                    // No agregar a temp = eliminar
                } else {
                    temp.adi(r);
                }
            }
            
            cola.vaciar(temp);
        }
        
        if(cancelado) {
            JOptionPane.showMessageDialog(this, 
                " Reserva cancelada exitosamente\n\n" +
                "Cliente: " + nombre + "\n" +
                "Tipo: " + tipo,
                "Cancelación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, 
                " No se encontró la reserva especificada", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiar() {
        nombreField.setText("");
        tipoCombo.setSelectedIndex(0);
    }
}
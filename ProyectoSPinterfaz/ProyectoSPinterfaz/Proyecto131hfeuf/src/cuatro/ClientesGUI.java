package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientesGUI extends JFrame {
    private ColaCircularClientes colaClientes;
    private JTextArea textArea;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public ClientesGUI(ColaCircularClientes colaClientes) {
        this.colaClientes = colaClientes;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Gestión de Clientes");
        setSize(700, 550);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" COLA DE CLIENTES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // TextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnAgregar = createButton(" Agregar Cliente", SUCCESS);
        btnAgregar.addActionListener(e -> agregarCliente());
        
        JButton btnAtender = createButton(" Atender Cliente", PRIMARY);
        btnAtender.addActionListener(e -> atenderCliente());
        
        JButton btnActualizar = createButton(" Actualizar", PRIMARY);
        btnActualizar.addActionListener(e -> mostrarClientes());
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnAtender);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        mostrarClientes();
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
    
    private void mostrarClientes() {
        textArea.setText("");
        textArea.append("═══════════════════════════════════════\n");
        textArea.append("         CLIENTES EN COLA\n");
        textArea.append("═══════════════════════════════════════\n\n");
        
        if(colaClientes.esVacia()) {
            textArea.append("     No hay clientes en la cola\n");
            return;
        }
        
        // Copiar cola para mostrar sin destruir
        ColaCircularClientes temp = new ColaCircularClientes();
        int pos = 1;
        
        while(!colaClientes.esVacia()) {
            Cliente c = colaClientes.eli();
            textArea.append(String.format("  %d. 👤 %s (CI: %s)\n", pos++, c.getNombre(), c.getCi()));
            temp.adi(c);
        }
        
        // Restaurar cola
        while(!temp.esVacia()) {
            colaClientes.adi(temp.eli());
        }
        
        textArea.append("\n═══════════════════════════════════════\n");
        textArea.append(String.format("   Total: %d clientes\n", colaClientes.nroElem()));
        textArea.append("═══════════════════════════════════════\n");
    }
    
    private void agregarCliente() {
        JTextField nombreField = new JTextField(15);
        JTextField ciField = new JTextField(15);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("CI:"));
        panel.add(ciField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            " Agregar Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String ci = ciField.getText().trim();
            
            if(!nombre.isEmpty() && !ci.isEmpty()) {
                colaClientes.adi(new Cliente(nombre, ci));
                mostrarClientes();
                JOptionPane.showMessageDialog(this, " Cliente agregado a la cola!");
            } else {
                JOptionPane.showMessageDialog(this, " Complete todos los campos no sea imbecil ", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void atenderCliente() {
        if(!colaClientes.esVacia()) {
            Cliente c = colaClientes.eli();
            JOptionPane.showMessageDialog(this, 
                " Atendiendo a:\n\n " + c.getNombre() + "\n CI: " + c.getCi(),
                "Cliente Atendido", JOptionPane.INFORMATION_MESSAGE);
            mostrarClientes();
        } else {
            JOptionPane.showMessageDialog(this, " No hay clientes en la cola", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}
package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BuscarReservaGUI extends JFrame {
    private MulticolaReservas multicolaReservas;
    private JTextField nombreField, ciField;
    private JTextArea resultadoArea;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color TURQUOISE = new Color(26, 188, 156);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public BuscarReservaGUI(MulticolaReservas mc) {
        this.multicolaReservas = mc;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Buscar Reserva");
        setSize(700, 550);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" BUSCAR RESERVA POR CLIENTE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nombreLabel = new JLabel(" Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(nombreLabel, gbc);
        
        gbc.gridx = 1;
        nombreField = new JTextField(20);
        nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(nombreField, gbc);
        
        // CI
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel ciLabel = new JLabel(" CI:");
        ciLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(ciLabel, gbc);
        
        gbc.gridx = 1;
        ciField = new JTextField(20);
        ciField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(ciField, gbc);
        
        // Botón buscar
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnBuscar = createButton(" Buscar Reservas", SUCCESS);
        btnBuscar.addActionListener(e -> buscarReservas());
        searchPanel.add(btnBuscar, gbc);
        
        // Área de resultados
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultadoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY, 2), 
            "Resultados",
            0, 0, new Font("Arial", Font.BOLD, 14), PRIMARY
        ));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnLimpiar = createButton(" Limpiar", PRIMARY);
        btnLimpiar.addActionListener(e -> limpiar());
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnCerrar);
        
        // Layout
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(LIGHT);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
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
    
    private void buscarReservas() {
        String nombre = nombreField.getText().trim();
        String ci = ciField.getText().trim();
        
        if(nombre.isEmpty() && ci.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                " Ingrese al menos un criterio de búsqueda", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        resultadoArea.setText("");
        resultadoArea.append("═══════════════════════════════════════════════════\n");
        resultadoArea.append("           RESULTADOS DE BÚSQUEDA\n");
        resultadoArea.append("═══════════════════════════════════════════════════\n\n");
        
        boolean encontrado = false;
        int totalReservas = 0;
        
        for(int i = 0; i < multicolaReservas.getN(); i++) {
            ColaReservas cola = multicolaReservas.getCola(i);
            ColaReservas temp = new ColaReservas();
            
            while(!cola.esVacia()) {
                Reserva r = cola.eli();
                
                boolean coincide = false;
                if(!nombre.isEmpty() && r.getCliente().getNombre().equalsIgnoreCase(nombre)) {
                    coincide = true;
                }
                if(!ci.isEmpty() && r.getCliente().getCi().equals(ci)) {
                    coincide = true;
                }
                
                if(coincide) {
                    encontrado = true;
                    totalReservas++;
                    resultadoArea.append("✓ RESERVA ENCONTRADA\n");
                    resultadoArea.append("  Cliente: " + r.getCliente().getNombre() + "\n");
                    resultadoArea.append("  CI: " + r.getCliente().getCi() + "\n");
                    resultadoArea.append("  Tipo: " + r.getTipoHabitacion() + "\n");
                    resultadoArea.append("  Fecha: " + r.getFecha() + "\n");
                    resultadoArea.append("---------------------------------------------------\n");
                }
                
                temp.adi(r);
            }
            
            cola.vaciar(temp);
        }
        
        if(!encontrado) {
            resultadoArea.append("    No se encontraron reservas con los\n");
            resultadoArea.append("      criterios especificados.\n");
        } else {
            resultadoArea.append("\n═══════════════════════════════════════════════════\n");
            resultadoArea.append(String.format("  Total de reservas encontradas: %d\n", totalReservas));
            resultadoArea.append("═══════════════════════════════════════════════════\n");
        }
    }
    
    private void limpiar() {
        nombreField.setText("");
        ciField.setText("");
        resultadoArea.setText("");
    }
}
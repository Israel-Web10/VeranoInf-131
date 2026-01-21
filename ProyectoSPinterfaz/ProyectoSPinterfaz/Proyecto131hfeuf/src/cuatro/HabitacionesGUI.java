package cuatro;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HabitacionesGUI extends JFrame {
    private LS_NormalHab listaHab;
    private JTable table;
    private DefaultTableModel model;
    
    private final Color PRIMARY = new Color(52, 152, 219);
    private final Color SUCCESS = new Color(39, 174, 96);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color GRAY = new Color(127, 140, 141);
    private final Color LIGHT = new Color(236, 240, 241);
    
    public HabitacionesGUI(LS_NormalHab listaHab) {
        this.listaHab = listaHab;
        initComponents();
    }
    
    private void initComponents() {
        setTitle(" Gestión de Habitaciones");
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(LIGHT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel(" HABITACIONES DEL HOTEL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Tabla
        String[] columns = {"Número", "Tipo", "Estado", "Precio (Bs.)"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Configurar el header de la tabla
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(41, 128, 185));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setReorderingAllowed(false);
        
        // Hacer que el header sea visible
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setOpaque(true);
        header.setBackground(new Color(41, 128, 185));
        header.setForeground(Color.WHITE);
        
        // Renderer para el header
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(41, 128, 185));
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 16));
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setGridColor(new Color(200, 200, 200));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(LIGHT);
        
        JButton btnAgregar = createButton(" Agregar", SUCCESS);
        btnAgregar.addActionListener(e -> agregarHabitacion());
        
        JButton btnEliminar = createButton(" Eliminar", DANGER);
        btnEliminar.addActionListener(e -> eliminarHabitacion());
        
        JButton btnActualizar = createButton(" Actualizar", PRIMARY);
        btnActualizar.addActionListener(e -> cargarDatos());
        
        JButton btnCerrar = createButton(" Cerrar", GRAY);
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnCerrar);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        cargarDatos();
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
    
    private void cargarDatos() {
        model.setRowCount(0);
        NodoHabitacion aux = listaHab.getP();
        
        while(aux != null) {
            Habitacion h = aux.getHab();
            Object[] row = {
                h.getNro(),
                h.getTipoHabitacion(),
                h.isEstado() ? " Ocupada" : " Disponible",
                String.format("%.2f", h.getPrecio())
            };
            model.addRow(row);
            aux = aux.getSig();
        }
    }
    
    private void agregarHabitacion() {
        JTextField nroField = new JTextField(10);
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"});
        JTextField precioField = new JTextField(10);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Número:"));
        panel.add(nroField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoCombo);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "➕ Agregar Nueva Habitación", JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION) {
            try {
                int nro = Integer.parseInt(nroField.getText());
                String tipo = (String) tipoCombo.getSelectedItem();
                double precio = Double.parseDouble(precioField.getText());
                
                listaHab.adiFinal(new Habitacion(nro, tipo, false, precio));
                cargarDatos();
                JOptionPane.showMessageDialog(this, "✅ Habitación agregada exitosamente!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: Datos inválidos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarHabitacion() {
        listaHab.eliFinal();
        cargarDatos();
        JOptionPane.showMessageDialog(this, "✅ Última habitación eliminada!");
    }
}
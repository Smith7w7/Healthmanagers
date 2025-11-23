package com.healthmanager.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class VentanaPrincipal extends JFrame {
    private JPanel panelMenu;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;

    public VentanaPrincipal() {
        setTitle("HealthManager - Sistema de Gestión Hospitalaria");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Color de fondo principal
        Color colorFondo = new Color(240, 245, 250);
        
        // Panel principal con BorderLayout
        JPanel panelPrincipalContenedor = new JPanel(new BorderLayout());
        panelPrincipalContenedor.setBackground(colorFondo);

        // PANEL SUPERIOR CON LOGO Y TÍTULO
        JPanel panelSuperior = crearPanelSuperior();
        panelPrincipalContenedor.add(panelSuperior, BorderLayout.NORTH);

        // PANEL LATERAL IZQUIERDO CON MENÚ
        panelMenu = crearPanelMenu();
        panelPrincipalContenedor.add(panelMenu, BorderLayout.WEST);

        // PANEL CENTRAL CON CONTENIDO
        panelPrincipal = crearPanelCentral();
        panelPrincipalContenedor.add(panelPrincipal, BorderLayout.CENTER);

        // PANEL INFERIOR CON INFORMACIÓN
        JPanel panelInferior = crearPanelInferior();
        panelPrincipalContenedor.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipalContenedor);
        setVisible(true);
    }

    // ======== PANEL SUPERIOR ========
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 46, 84)); // Azul profesional
        panel.setPreferredSize(new Dimension(0, 80));

        // Logo/Icono
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setPreferredSize(new Dimension(100, 80));
        
        // Cargar el logo desde recursos
        try {
            // Intentar diferentes rutas posibles
            URL logoUrl = getClass().getResource("/Resources/logo.png");
            if (logoUrl == null) {
                logoUrl = getClass().getClassLoader().getResource("Resources/logo.png");
            }
            if (logoUrl == null) {
                logoUrl = getClass().getClassLoader().getResource("/Resources/logo.png");
            }
            
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                // Escalar la imagen si es necesario (mantener proporción)
                Image img = logoIcon.getImage();
                int ancho = 80;
                int alto = 80;
                Image imgEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                ImageIcon iconoEscalado = new ImageIcon(imgEscalada);
                lblLogo.setIcon(iconoEscalado);
            } else {
                // Si no se encuentra el logo, usar emoji como respaldo
                lblLogo.setText("🏥");
                lblLogo.setFont(new Font("Arial", Font.BOLD, 48));
                lblLogo.setForeground(Color.WHITE);
                System.out.println("Logo no encontrado, usando emoji como respaldo");
            }
        } catch (Exception e) {
            // Si hay error al cargar, usar emoji como respaldo
            lblLogo.setText("🏥");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 48));
            lblLogo.setForeground(Color.WHITE);
            System.err.println("Error al cargar el logo: " + e.getMessage());
        }

        // Título y subtítulo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(0, 46, 84));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblTitulo = new JLabel("HEALTHMANAGER");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);

        lblSubtitulo = new JLabel("Sistema de Gestión Hospitalaria Integral");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(200, 220, 255));

        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblSubtitulo);

        panel.add(lblLogo, BorderLayout.WEST);
        panel.add(panelTitulo, BorderLayout.CENTER);

        return panel;
    }

    // ======== PANEL LATERAL (MENÚ) ========
    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(35, 45, 65)); // Gris azulado oscuro
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        // Menú Pacientes
        panel.add(crearBotonMenuConIcono("PACIENTES", new Color(76, 175, 80), "pacientesicon.png", e -> {
            mostrarMenuPacientes();
        }));
        panel.add(Box.createVerticalStrut(10));
        
        // Menú Pacientes
        panel.add(crearBotonMenuConIcono("MEDICOS", new Color(33, 150, 243), "medicoicon.png", e -> {
            mostrarMenuMedicos();
        }));
        panel.add(Box.createVerticalStrut(10));

        // Menú Citas
        panel.add(crearBotonMenuConIcono("CITAS", new Color(255, 152, 0), "citasicon.png", e -> {
            mostrarMenuCitas();
        }));
        panel.add(Box.createVerticalStrut(10));
        
        // Menú Reportes
        panel.add(crearBotonMenuConIcono("REPORTES", new Color(156, 39, 176), "reportesicon.png", e -> {
            mostrarMenuReportes();
        }));
        panel.add(Box.createVerticalStrut(10));

      

        // Menú Configuración
        panel.add(crearBotonMenuConIcono ("CONFIGURACIÓN", new Color(120, 120, 120), "configurationicon.png", e -> {
            mostrarMenuConfiguracion();
        }));

        panel.add(Box.createVerticalGlue());

        // Botón Salir
        JButton btnSalir = crearBotonSalirConIcono("SALIR", new Color(244, 67, 54), "salidaicon.png", e -> System.exit(0));
        panel.add(btnSalir);

        return panel;
    }

    // ======== CREAR BOTÓN DE MENÚ ========
    private JButton crearBotonMenu(String texto, Color color, ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(220, 50));
        btn.setPreferredSize(new Dimension(220, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(accion);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    // ======== CREAR BOTÓN DE MENÚ CON ICONO ========
    private JButton crearBotonMenuConIcono(String texto, Color color, String nombreIcono, ActionListener accion) {
        JButton btn = new JButton();
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(220, 50));
        btn.setPreferredSize(new Dimension(220, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setLayout(new BorderLayout());
        btn.addActionListener(accion);
        
        // Cargar el icono
        try {
            URL iconUrl = getClass().getResource("/Resources/" + nombreIcono);
            if (iconUrl == null) {
                iconUrl = getClass().getClassLoader().getResource("Resources/" + nombreIcono);
            }
            if (iconUrl == null) {
                iconUrl = getClass().getClassLoader().getResource("/Resources/" + nombreIcono);
            }
            
            if (iconUrl != null) {
                ImageIcon iconoOriginal = new ImageIcon(iconUrl);
                // Escalar la imagen a un tamaño apropiado para el botón (30x30)
                Image img = iconoOriginal.getImage();
                int ancho = 30;
                int alto = 30;
                Image imgEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                ImageIcon iconoEscalado = new ImageIcon(imgEscalada);
                
                // Crear un panel interno para el icono y texto
                JPanel panelInterno = new JPanel(new BorderLayout(10, 0));
                panelInterno.setOpaque(false);
                panelInterno.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                
                JLabel lblIcono = new JLabel(iconoEscalado);
                JLabel lblTexto = new JLabel(texto);
                lblTexto.setForeground(Color.WHITE);
                lblTexto.setFont(new Font("Arial", Font.BOLD, 13));
                
                panelInterno.add(lblIcono, BorderLayout.WEST);
                panelInterno.add(lblTexto, BorderLayout.CENTER);
                btn.add(panelInterno, BorderLayout.CENTER);
            } else {
                // Si no se encuentra el icono, usar texto con emoji como respaldo
                btn.setText("👥 " + texto);
            }
        } catch (Exception e) {
            // Si hay error al cargar, usar texto con emoji como respaldo
            btn.setText("👥 " + texto);
            System.err.println("Error al cargar el icono " + nombreIcono + ": " + e.getMessage());
        }
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }

    // ======== CREAR BOTÓN SALIR CON ICONO ========
    private JButton crearBotonSalirConIcono(String texto, Color color, String nombreIcono, ActionListener accion) {
        JButton btn = new JButton();
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setMaximumSize(new Dimension(220, 45));
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setLayout(new BorderLayout());
        btn.addActionListener(accion);
        
        // Cargar el icono
        try {
            URL iconUrl = getClass().getResource("/Resources/" + nombreIcono);
            if (iconUrl == null) {
                iconUrl = getClass().getClassLoader().getResource("Resources/" + nombreIcono);
            }
            if (iconUrl == null) {
                iconUrl = getClass().getClassLoader().getResource("/Resources/" + nombreIcono);
            }
            
            if (iconUrl != null) {
                ImageIcon iconoOriginal = new ImageIcon(iconUrl);
                // Escalar la imagen a un tamaño apropiado para el botón (25x25)
                Image img = iconoOriginal.getImage();
                int ancho = 25;
                int alto = 25;
                Image imgEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                ImageIcon iconoEscalado = new ImageIcon(imgEscalada);
                
                // Crear un panel interno para el icono y texto
                JPanel panelInterno = new JPanel(new BorderLayout(10, 0));
                panelInterno.setOpaque(false);
                panelInterno.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                
                JLabel lblIcono = new JLabel(iconoEscalado);
                JLabel lblTexto = new JLabel(texto);
                lblTexto.setForeground(Color.WHITE);
                lblTexto.setFont(new Font("Arial", Font.BOLD, 12));
                
                panelInterno.add(lblIcono, BorderLayout.WEST);
                panelInterno.add(lblTexto, BorderLayout.CENTER);
                btn.add(panelInterno, BorderLayout.CENTER);
            } else {
                // Si no se encuentra el icono, usar texto con emoji como respaldo
                btn.setText("🚪 " + texto);
            }
        } catch (Exception e) {
            // Si hay error al cargar, usar texto con emoji como respaldo
            btn.setText("🚪 " + texto);
            System.err.println("Error al cargar el icono " + nombreIcono + ": " + e.getMessage());
        }
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(229, 57, 53));
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }

    // ======== PANEL CENTRAL ========
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Panel bienvenida
        JPanel panelBienvenida = new JPanel(new BorderLayout());
        panelBienvenida.setBackground(Color.WHITE);
        panelBienvenida.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel lblBienvenida = new JLabel("Bienvenido a HealthManager", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(25, 118, 210));
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        JLabel lblDescripcion = new JLabel(
            "<html><center>Sistema integral de gestión hospitalaria<br>" +
            "Seleccione una opción del menú para comenzar<br><br>" +
            "✓ Gestión de Pacientes<br>" +
            "✓ Gestión de Médicos y Especialidades<br>" +
            "✓ Control de Citas Médicas<br>" +
            "✓ Registro de Tratamientos<br>" +
            "✓ Generación de Reportes</center></html>",
            SwingConstants.CENTER
        );
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescripcion.setForeground(new Color(80, 80, 80));
        lblDescripcion.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        panelBienvenida.add(lblBienvenida, BorderLayout.NORTH);
        panelBienvenida.add(lblDescripcion, BorderLayout.CENTER);

        panel.add(panelBienvenida, BorderLayout.CENTER);

        return panel;
    }

    // ======== PANEL INFERIOR ========
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setPreferredSize(new Dimension(0, 40));

        JLabel lblEstado = new JLabel("✓ Sistema funcionando correctamente");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEstado.setForeground(new Color(76, 175, 80));

        JLabel lblVersion = new JLabel("HealthManager v1.0 | 2025");
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 11));
        lblVersion.setForeground(new Color(120, 120, 120));

        panel.add(lblEstado, BorderLayout.WEST);
        panel.add(lblVersion, BorderLayout.EAST);

        return panel;
    }

    // ======== MÉTODOS DE MENÚ ========
    private void mostrarMenuPacientes() {
        new VentanaMenuPacientes(this);
    }

    private void mostrarMenuMedicos() {
        new VentanaMenuMedicos(this);
    }

    private void mostrarMenuCitas() {
        new VentanaMenuCitas(this);
    }

    private void mostrarMenuReportes() {
        JOptionPane.showMessageDialog(this, "Módulo de Reportes en desarrollo", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMenuConfiguracion() {
        JOptionPane.showMessageDialog(this, "Configuración en desarrollo", 
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
package com.mycompany.proyectosupermecado;

import com.mycompany.proyectosupermecado.componentes.ClienteGestionCliente;
import com.mycompany.proyectosupermecado.modelo.Cliente;
import com.mycompany.proyectosupermecado.servicio.ClienteServicio;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Ventana para modificar y gestionar clientes
 * ACTUALIZADA CON PATRÓN DAO
 * @author MEDAC
 */
public class VentanaModificarClientes extends javax.swing.JFrame {
    
    String nombre;
    private ClienteServicio clienteServicio;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaModificarClientes.class.getName());

    /**
     * Carga todos los clientes activos y los muestra en el panel
     */
    public void cargarClientes() {
        try {
            // Usar el servicio en lugar de conexión directa
            List<Cliente> clientes = clienteServicio.listarClientesActivos();

            // Limpiar panel
            Pnlmenucito.removeAll();
            Pnlmenucito.setLayout(new java.awt.GridLayout(0, 1, 5, 5)); // dinámico

            // Crear un panel por cada cliente
            for (Cliente cliente : clientes) {
                ClienteGestionCliente panel = new ClienteGestionCliente();

                // Configurar el panel con los datos del cliente
                panel.setDni(cliente.getDni());
                panel.setNombre(cliente.getNombre());
                panel.setApellido(cliente.getApellidos());
                
                // Asociar los eventos de los botones
                configurarEventosPanel(panel, cliente);

                Pnlmenucito.add(panel);
            }

            Pnlmenucito.revalidate();
            Pnlmenucito.repaint();

            System.out.println("Clientes cargados: " + clientes.size());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error cargando clientes: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura los eventos de los botones del panel de cliente
     */
    private void configurarEventosPanel(ClienteGestionCliente panel, Cliente cliente) {
            panel.btnInsertar.addActionListener(e -> {
        VentanaAñadirCliente ventana = new VentanaAñadirCliente(this.nombre);
        ventana.setVisible(true);
        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent ev) {
                cargarClientes();
                }
            });
        });
        // Botón Consultar - Muestra información del cliente
        panel.btnConsultar.addActionListener(e -> {
            String mensaje = String.format(
                "Información del Cliente:\n\n" +
                "DNI: %s\n" +
                "Nombre: %s %s\n" +
                "Teléfono: %s\n" +
                "Email: %s\n" +
                "Dirección: %s\n" +
                "Puntos Acumulados: %d\n" +
                "Fecha Registro: %s\n" +
                "Estado: %s",
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getTelefono() != null ? cliente.getTelefono() : "No registrado",
                cliente.getEmail() != null ? cliente.getEmail() : "No registrado",
                cliente.getDireccion() != null ? cliente.getDireccion() : "No registrada",
                cliente.getPuntosAcumulados(),
                cliente.getFechaRegistro() != null ? cliente.getFechaRegistro().toString() : "No disponible",
                cliente.isActivo() ? "Activo" : "Inactivo"
            );
            
            JOptionPane.showMessageDialog(this, mensaje, 
                "Información del Cliente", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Botón Editar - Abre ventana para editar
        panel.btnEditar.addActionListener(e -> {
            abrirVentanaEdicion(cliente);
        });

        // Botón Eliminar - Da de baja al cliente
        panel.btnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de dar de baja al cliente:\n" + 
                cliente.getNombre() + " " + cliente.getApellidos() + "?",
                "Confirmar Baja",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (clienteServicio.darDeBajaCliente(cliente.getIdCliente())) {
                    cargarClientes(); // Recargar la lista
                }
            }
        });
    }

    /**
     * Abre una ventana de diálogo para editar el cliente
     */
    private void abrirVentanaEdicion(Cliente cliente) {
        // Crear campos para editar
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(cliente.getNombre());
        javax.swing.JTextField txtApellidos = new javax.swing.JTextField(cliente.getApellidos());
        javax.swing.JTextField txtTelefono = new javax.swing.JTextField(cliente.getTelefono() != null ? cliente.getTelefono() : "");
        javax.swing.JTextField txtEmail = new javax.swing.JTextField(cliente.getEmail() != null ? cliente.getEmail() : "");
        javax.swing.JTextField txtDireccion = new javax.swing.JTextField(cliente.getDireccion() != null ? cliente.getDireccion() : "");

        Object[] campos = {
            "Nombre:", txtNombre,
            "Apellidos:", txtApellidos,
            "Teléfono:", txtTelefono,
            "Email:", txtEmail,
            "Dirección:", txtDireccion
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, 
            "Editar Cliente - " + cliente.getDni(), 
            JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            // Actualizar el objeto cliente
            cliente.setNombre(txtNombre.getText().trim());
            cliente.setApellidos(txtApellidos.getText().trim());
            cliente.setTelefono(txtTelefono.getText().trim());
            cliente.setEmail(txtEmail.getText().trim());
            cliente.setDireccion(txtDireccion.getText().trim());

            // Guardar cambios
            if (clienteServicio.modificarCliente(cliente)) {
                cargarClientes(); // Recargar la lista
            }
        }
    }

    /**
     * Creates new form VentanaModificarClientes
     */
    public VentanaModificarClientes() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        
        // Inicializar servicio
        this.clienteServicio = new ClienteServicio();
        
        // Cargar clientes al iniciar
        cargarClientes();
    }

    public VentanaModificarClientes(String nombre) {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        this.nombre = nombre;
        lblNombre.setText(nombre);
        fotoPerfil.setIcon(new ImageIcon(getClass().getResource("/bicho_lidel.png")));
        
        // Inicializar servicio
        this.clienteServicio = new ClienteServicio();
        
        // Cargar clientes al iniciar
        cargarClientes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        clienteGestionCliente1 = new com.mycompany.proyectosupermecado.componentes.ClienteGestionCliente();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        fotoPerfil = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        Pnlmenucito = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setText("Gestión de Clientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel24, gridBagConstraints);

        fotoPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perfil.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        jPanel3.add(fotoPerfil, gridBagConstraints);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fotocostamarketLogo.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jButton2, gridBagConstraints);

        jButton3.setForeground(new java.awt.Color(255, 102, 102));
        jButton3.setText("Cerrar sesion");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jButton3, gridBagConstraints);

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombre y apellidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        jPanel3.add(lblNombre, gridBagConstraints);

        Pnlmenucito.setForeground(new java.awt.Color(51, 51, 51));
        Pnlmenucito.setLayout(new java.awt.GridLayout(20, 1, 5, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Pnlmenucito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 262, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 263, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Pnlmenucito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 55, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 54, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        VentanaPrincipalEmpleado principal = new VentanaPrincipalEmpleado(nombre);
        principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        VentanaLogin vuelta_inicio = new VentanaLogin();
        vuelta_inicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaModificarClientes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pnlmenucito;
    private com.mycompany.proyectosupermecado.componentes.ClienteGestionCliente clienteGestionCliente1;
    private javax.swing.JLabel fotoPerfil;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}

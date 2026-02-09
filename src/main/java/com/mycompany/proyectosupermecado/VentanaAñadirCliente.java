package com.mycompany.proyectosupermecado;

import com.mycompany.proyectosupermecado.servicio.ClienteServicio;
import javax.swing.JOptionPane;

/**
 * Ventana para añadir nuevos clientes al sistema
 * @author MEDAC
 */
public class VentanaAñadirCliente extends javax.swing.JFrame {

    private ClienteServicio clienteServicio;
    private String nombreEmpleado;

    /**
     * Creates new form VentanaAñadirCliente
     */
    public VentanaAñadirCliente() {
        initComponents();
        this.clienteServicio = new ClienteServicio();
        setLocationRelativeTo(null); // Centrar ventana
    }

    public VentanaAñadirCliente(String nombreEmpleado) {
        initComponents();
        this.clienteServicio = new ClienteServicio();
        this.nombreEmpleado = nombreEmpleado;
        setLocationRelativeTo(null);
    }

    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtDni.requestFocus(); // Poner foco en DNI
    }

    /**
     * Valida que los campos obligatorios estén llenos
     */
    private boolean validarCampos() {
        if (txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El DNI es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Los apellidos son obligatorios", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Añadir Nuevo Cliente");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE NUEVO CLIENTE");

        jLabel2.setText("DNI: *");

        jLabel3.setText("Nombre: *");

        jLabel4.setText("Apellidos: *");

        jLabel5.setText("Teléfono:");

        jLabel6.setText("Email:");

        jLabel7.setText("Dirección:");

        btnGuardar.setBackground(new java.awt.Color(0, 153, 0));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar Cliente");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar Campos");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 102, 102));
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDni)
                            .addComponent(txtNombre)
                            .addComponent(txtApellidos)
                            .addComponent(txtTelefono)
                            .addComponent(txtEmail)
                            .addComponent(txtDireccion)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Obtener valores de los campos
        String dni = txtDni.getText().trim().toUpperCase();
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        // Usar el servicio para registrar el cliente
        boolean exito = clienteServicio.registrarCliente(
            dni, nombre, apellidos, 
            telefono.isEmpty() ? null : telefono,
            email.isEmpty() ? null : email,
            direccion.isEmpty() ? null : direccion
        );

        if (exito) {
            // Limpiar campos para nuevo registro
            limpiarCampos();
            
            // Preguntar si quiere añadir otro cliente
            int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea añadir otro cliente?",
                "Cliente Registrado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.NO_OPTION) {
                this.dispose(); // Cerrar ventana
            }
        }
    }                                          

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        limpiarCampos();
    }                                          

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de cancelar? Se perderán los datos no guardados.",
            "Confirmar Cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaAñadirCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaAñadirCliente().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration                   
}

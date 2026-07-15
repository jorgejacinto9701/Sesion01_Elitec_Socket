package ejemploJList;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import util.MySqlDBConexion;

public class ExploradorTablas extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> lstTablas;
    private JButton btnEnviar;

    public ExploradorTablas() {

        setTitle("Explorador de Archivos");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lstTablas = new JList<String>();
        lstTablas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        btnEnviar = new JButton();
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(this);
        add(new JScrollPane(lstTablas), BorderLayout.CENTER);
        add(btnEnviar, BorderLayout.SOUTH);
        
        cargarTablasJList();
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEnviar) {
			do_brnEnviar_actionPerformed(e);
		}
	}
	
	public void cargarTablasJList() {
		 DefaultListModel<String> modelo = new DefaultListModel<>();
	     lstTablas.setModel(modelo);
	        
		Connection conn = null;
		try {
			conn = MySqlDBConexion.getConexion();
			DatabaseMetaData meta = conn.getMetaData();

			ResultSet rs = meta.getTables(conn.getCatalog(),null,"%",new String[]{"TABLE"});
			while (rs.next()) {
				String tabla = rs.getString("TABLE_NAME");
				modelo.addElement(tabla);
			}
	        rs.close();
	        conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {  new ExploradorTablas().setVisible(true); });
    }
    
    protected void do_brnEnviar_actionPerformed(ActionEvent e) {
    	String[] formatos = {"PDF", "JSON", "EXCEL", "XML"};

    	JComboBox<String> cboFormato = new JComboBox<>(formatos);

    	int opcion = JOptionPane.showConfirmDialog(
    	        null,
    	        cboFormato,
    	        "Seleccione el formato de exportación",
    	        JOptionPane.OK_CANCEL_OPTION,
    	        JOptionPane.QUESTION_MESSAGE);

    	if (opcion == JOptionPane.OK_OPTION) {

    	    String formato = (String) cboFormato.getSelectedItem();

    	    JOptionPane.showMessageDialog(null,
    	            "Formato seleccionado: " + formato);

    	    switch (formato) {
    	        case "PDF":
    	            // Exportar a PDF
    	            break;

    	        case "JSON":
    	            // Exportar a JSON
    	            break;

    	        case "EXCEL":
    	            // Exportar a Excel
    	            break;

    	        case "XML":
    	            // Exportar a XML
    	            break;
    	    }

    	} else {
    	    JOptionPane.showMessageDialog(null, "Operación cancelada.");
    	}
	}
}


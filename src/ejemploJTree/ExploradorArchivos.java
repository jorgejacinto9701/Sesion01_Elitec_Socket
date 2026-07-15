package ejemploJTree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

public class ExploradorArchivos extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
    private JButton btnEnviar;

    public ExploradorArchivos() {

        setTitle("Explorador de Archivos");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Nodo raíz
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Equipo");

        String ruta = "D:\\cliente";
        File unidad = new File(ruta);
        DefaultMutableTreeNode nodoUnidad = new DefaultMutableTreeNode(ruta);
       	cargarArchivos(unidad, nodoUnidad);
       	raiz.add(nodoUnidad);

        
        tree = new JTree(raiz);
        // Expandir todos los nodos
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
        
        btnEnviar = new JButton();
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(this);
        add(new JScrollPane(tree), BorderLayout.CENTER);
        add(btnEnviar, BorderLayout.SOUTH);
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEnviar) {
			do_brnEnviar_actionPerformed(e);
		}
	}
	
    // Método recursivo
    private void cargarArchivos(File carpeta, DefaultMutableTreeNode nodo) {

        File[] archivos = carpeta.listFiles();

        if (archivos == null)
            return;

        for (File archivo : archivos) {

            DefaultMutableTreeNode hijo =
                    new DefaultMutableTreeNode(archivo.getName());

            nodo.add(hijo);

            // Solo recorrer carpetas
            if (archivo.isDirectory()) {
                cargarArchivos(archivo, hijo);
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ExploradorArchivos().setVisible(true);
        });

    }
    
    protected void do_brnEnviar_actionPerformed(ActionEvent e) {
		
	}
}


package ejemploJTreeSocket;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class ExploradorArchivos extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JTree tree;
    private JButton btnEnviar;

    public ExploradorArchivos() {

        setTitle("Explorador de Archivos");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Equipo");
        File unidad = new File("C:\\cliente");
        DefaultMutableTreeNode nodoUnidad = new DefaultMutableTreeNode(unidad);
        cargarArchivos(unidad, nodoUnidad);

        raiz.add(nodoUnidad);

        tree = new JTree(raiz);

        tree.getSelectionModel().setSelectionMode( TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(this);

        add(new JScrollPane(tree), BorderLayout.CENTER);
        add(btnEnviar, BorderLayout.SOUTH);
    }

    private void cargarArchivos(File carpeta, DefaultMutableTreeNode nodo) {
        File[] archivos = carpeta.listFiles();
        if (archivos == null)
            return;

        for (File archivo : archivos) {
            DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(archivo);
            nodo.add(hijo);

            if (archivo.isDirectory()) {
                cargarArchivos(archivo, hijo);
            }
        }
    }

    private File[] obtenerArchivosSeleccionados() {
        javax.swing.tree.TreePath[] rutas = tree.getSelectionPaths();

        if (rutas == null)
            return null;
        
        List<File> lista = new ArrayList<>();

        for (javax.swing.tree.TreePath ruta : rutas) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();
            Object obj = nodo.getUserObject();

            if (obj instanceof File) {
                File archivo = (File) obj;
                if (archivo.isFile()) {
                    lista.add(archivo);
                }
            }
        }

        return lista.toArray(new File[0]);
    }

    private File crearZip(File[] archivos) {
        File zip = new File("servidor.zip");

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {

            for (File archivo : archivos) {
                FileInputStream fis =new FileInputStream(archivo);

                zos.putNextEntry(new ZipEntry(archivo.getName()));
                byte[] buffer = new byte[4096];
                int bytes;
                while ((bytes = fis.read(buffer)) != -1) {zos.write(buffer, 0, bytes);}

                zos.closeEntry();
                fis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return zip;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnEnviar) {
            File[] archivos = obtenerArchivosSeleccionados();
            if (archivos == null || archivos.length == 0) {
                System.out.println("Seleccione archivos");
                return;
            }

            File zip = crearZip(archivos);
            Cliente cliente = new Cliente();
            cliente.enviarZip(zip);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {new ExploradorArchivos().setVisible(true);
        });
    }
}
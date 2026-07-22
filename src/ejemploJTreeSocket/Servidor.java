package ejemploJTreeSocket;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static final int PUERTO = 5000;

    public Servidor() {
        System.out.println("1 ==> Servidor iniciado...");
        
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {

            while (true) {
                System.out.println("2 ==> Esperando cliente...");
                try (Socket cliente = servidor.accept();
                     DataInputStream entrada =
                         new DataInputStream(cliente.getInputStream())) {

                    System.out.println("3 ==> Cliente conectado");

                    // Recibir nombre y tamaño del archivo
                    String nombreArchivo = entrada.readUTF();
                    long tamañoArchivo = entrada.readLong();

                    System.out.println("Nombre archivo: " + nombreArchivo);
                    System.out.println("Tamaño archivo: " + tamañoArchivo + " bytes");

                    // Crear carpeta servidor
                    File carpeta = new File("C:\\servidor");
                    if (!carpeta.exists()) {
                        carpeta.mkdir(); }

                    File archivo = new File(carpeta, nombreArchivo);

                    try (FileOutputStream fos = new FileOutputStream(archivo)) {

                        byte[] buffer = new byte[4096];
                        long bytesRestantes = tamañoArchivo;
                        while (bytesRestantes > 0) {
                            int bytesLeidos = entrada.read( buffer,0,(int) Math.min( buffer.length, bytesRestantes));
                            if (bytesLeidos == -1) {
                                break;
                            }
                            fos.write(buffer, 0, bytesLeidos);
                            bytesRestantes -= bytesLeidos;
                        }
                        fos.flush();
                    }

                    System.out.println("4 ==> ZIP recibido correctamente");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("5 ==> Conexion cerrada\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Servidor();

    }
}
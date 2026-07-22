package ejemploJTreeSocket;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Cliente {

    public static final String HOST = "localhost";
    public static final int PUERTO = 5000;

    public void enviarZip(File zip) {

        try (Socket cliente = new Socket(HOST, PUERTO);
             FileInputStream fis = new FileInputStream(zip);
             DataOutputStream salida = new DataOutputStream(cliente.getOutputStream())) {

            System.out.println("1 ==> Cliente conectado al servidor "+ HOST + " en el puerto " + PUERTO);

            // Enviar nombre y tamaño del ZIP
            salida.writeUTF(zip.getName());
            salida.writeLong(zip.length());

            // Enviar bytes del archivo
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                salida.write(buffer, 0, bytesRead);
            }
            salida.flush();

            System.out.println("2 ==> ZIP enviado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
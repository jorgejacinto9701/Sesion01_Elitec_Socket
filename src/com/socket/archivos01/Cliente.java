package com.socket.archivos01;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Cliente {

	
	public static final String HOST = "localhost";
	public static final int PUERTO = 5000;
	
	public Cliente() {
		try {
			//Creamos el socket del cliente y nos conectamos al servidor
			Socket cliente = new Socket(HOST, PUERTO);
			System.out.println("1 ==> Cliente conectado al servidor " + HOST + " en el puerto " + PUERTO);

			//Archivo que vamos a enviar al servidor
			File archivoPDF = new File("D:/cliente/alumnos.json");
			
			//flujos de la comunicación para enviar un archivo PDF
			FileInputStream fis = new FileInputStream(archivoPDF);
			DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
						
			
			//Enviar el achivo PDF al servidor
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				salida.write(buffer, 0, bytesRead);
			}
		
			salida.flush();
					
			fis.close();
			salida.close();
			
			System.out.println("2 ==> Se ha cerrado la conexión con el servidor");
			//Cerramos la conexión con el servidor
			cliente.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new Cliente();
	}
}

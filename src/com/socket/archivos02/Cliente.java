package com.socket.archivos02;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Cliente {

	public static final String HOST = "localhost";
	public static final int PUERTO = 5000;

	public Cliente() {

		// Archivo que vamos a enviar al servidor
		File fileSend = new File("D:/cliente/alumnos.xml");

		try (Socket cliente = new Socket(HOST, PUERTO);
			 FileInputStream fis = new FileInputStream(fileSend);
			 DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());) {

			System.out.println("1 ==> Cliente conectado al servidor " + HOST + " en el puerto " + PUERTO);

			//Enviamos el Nombre y el tamaño del archivo al servidor
			salida.writeUTF(fileSend.getName());
			salida.writeLong(fileSend.length());
			    
			// Enviamos los bytes del archivo
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				salida.write(buffer, 0, bytesRead);
			}
			salida.flush();

			System.out.println("2 ==> Se ha cerrado la conexión con el servidor");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Cliente();
	}
}

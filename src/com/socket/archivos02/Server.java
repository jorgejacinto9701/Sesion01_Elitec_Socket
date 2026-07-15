package com.socket.archivos02;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PUERTO = 5000;

	public Server() {
		System.out.println("1 => Servidor iniciado ....");
		try (ServerSocket servidor = new ServerSocket(PUERTO)) {

			while (true) {
				System.out.println("2 => Esperando cliente ....");
				// Esperando a que un cliente se conecte(Se detiene el programa hasta que un
				// cliente se conecte)

				System.out.println("3 => Se inicia la atención al cliente : ");

				try (Socket cliente = servidor.accept();
					 DataInputStream entrada = new DataInputStream(cliente.getInputStream());) {

					// Recibir el nombre y el tamaño del archivo
					String nombreArchivo = entrada.readUTF();
					long tamanioArchivo = entrada.readLong();

					System.out.println("Nombre del archivo : " + nombreArchivo);
					System.out.println("Tamaño del archivo : " + tamanioArchivo + " bytes");

					File archivo = new File("D:/servidor/" + nombreArchivo);

					try (FileOutputStream fos = new FileOutputStream(archivo)) {
					
						byte[] buffer = new byte[4096];
						long bytesRestantes = tamanioArchivo;
						while (bytesRestantes > 0) {
							int bytesLeidos = entrada.read(buffer, 0, (int) Math.min(buffer.length, bytesRestantes));
							if (bytesLeidos == -1) {
								break;
							}
							fos.write(buffer, 0, bytesLeidos);
							bytesRestantes -= bytesLeidos;
						}
						fos.flush();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("5 => Se ha cerrado la conexión con el cliente");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}

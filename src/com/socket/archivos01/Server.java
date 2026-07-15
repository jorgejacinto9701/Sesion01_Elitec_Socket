package com.socket.archivos01;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PUERTO = 5000;
	
	public Server() {
		System.out.println("1 => Servidor iniciado ....");
		try (ServerSocket servidor = new ServerSocket(PUERTO)){
			Socket cliente = null;
			while(true) {
				System.out.println("2 => Esperando cliente ....");
				//Esperando a que un cliente se conecte(Se detiene el programa hasta que un cliente se conecte)
				cliente = servidor.accept();
				System.out.println("3 => Se inicia la atención al cliente : " );
				System.out.println("4 => Cliente conectado: " + cliente.getInetAddress().getHostAddress());
				
				//El archivo se va a generar con los bytes que se reciban del cliente
				File fileLlegada = new File("D:/servidor/alummos_recibidos.json");
				
				FileOutputStream fos = new FileOutputStream(fileLlegada);
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				
				//comunicacion con el cliente
				byte[] buffer = new byte[4096];
				int bytesLeidos;
				while ((bytesLeidos = entrada.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesLeidos);
				} 
				
				fos.close();
				entrada.close();
				
				System.out.println("5 => Se ha cerrado la conexión con el cliente");
				cliente.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
}

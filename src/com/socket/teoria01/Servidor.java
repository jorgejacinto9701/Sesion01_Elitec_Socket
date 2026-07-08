package com.socket.teoria01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static final int PUERTO = 13;
		
	public Servidor() {
		System.out.println("1 => Servidor iniciado ....");
		int contador = 0; 
		try (ServerSocket servidor = new ServerSocket(PUERTO)){
			Socket cliente = null;
			while(true) {
				System.out.println("2 => Esperando cliente ....");
				//Esperando a que un cliente se conecte(Se detiene el programa hasta que un cliente se conecte)
				cliente =servidor.accept();
				System.out.println("3 => Se inicia la atención al cliente : " + (++contador) );
				System.out.println("4 => Cliente conectado: " + cliente.getInetAddress().getHostAddress());
				
				//flujos de la comunicación
				BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
				
				
				//comunicacion con el cliente
				String mensaje = entrada.readLine();
				System.out.println("==> " + mensaje);
				
				//respuesta al cliente
				salida.println("Hola cliente, soy el servidor");
				
				
				
				System.out.println("5 => Se ha cerrado la conexión con el cliente");
				cliente.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Servidor finaliado ....");
	}
	
	public static void main(String[] args) {
		new Servidor();
	}
	
}

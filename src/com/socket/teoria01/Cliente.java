package com.socket.teoria01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

	//Los datos del servidor al que nos vamos a conectar
	private final String HOST = "localhost";
	private final int PUERTO = 13;
	
	public Cliente() {
		try {
			//Creamos el socket del cliente y nos conectamos al servidor
			Socket cliente = new Socket(HOST, PUERTO);
			System.out.println("1 ==> Cliente conectado al servidor " + HOST + " en el puerto " + PUERTO);
			
			//flujos de la comunicación
			BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
			
			//comunicacion con el servidor
			salida.println("Hola servidor, cliente VIP");
			
			//respuesta del servidor
			String respuesta = entrada.readLine();
			System.out.println("Respuesta del servidor ==> " + respuesta);
			
			
			
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

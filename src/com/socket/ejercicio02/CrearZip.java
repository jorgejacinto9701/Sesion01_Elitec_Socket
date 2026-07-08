package com.socket.ejercicio02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CrearZip {

        public static void main(String[] args) {
            try {

                String[] archivos = { "C:/cliente/2024f1n2.pdf", "C:/cliente/2024f1n1.pdf","C:/cliente/ARTIGOS.pdf"};
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("C:/cliente/comprimido.zip"));
                for (String ruta : archivos) {
                    System.out.println("Archivo: " + ruta);
    
                    // Crear un archivo zip
                    File archivoZip = new File(ruta);
                    FileInputStream fis = new FileInputStream(archivoZip);    

                    // Crear un archivo zip
                    ZipEntry zipEntry = new ZipEntry(archivoZip.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    
                    // agregar los bytes al archivo zip
                    int byteLeidos = 0;
                    while ((byteLeidos = fis.read()) != -1) {
                        zipOutputStream.write(byteLeidos);
                    }
                   
                    fis.close();
                    zipOutputStream.closeEntry();
                }
                zipOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
          
        }
    
}

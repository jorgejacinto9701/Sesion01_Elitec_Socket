package com.socket.ejercicio07;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CrearPDF {

    public static void main(String[] args) {

        Cliente cliente1 = new Cliente(1, "Juan", "Perez", "12345678");
        Cliente cliente2 = new Cliente(2, "Maria", "Gomez", "87654321");
        Cliente cliente3 = new Cliente(3, "Carlos", "Lopez", "11223344");

        List<Cliente> lista = new ArrayList<>();
        lista.add(cliente1);
        lista.add(cliente2);
        lista.add(cliente3);

        try {

            // Crear carpeta si no existe
            File carpeta = new File("C:/cliente");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File file = new File("C:/cliente/clientes.pdf");

            // Crear documento
            Document document = new Document();

            // Asociar el documento con el archivo PDF
            PdfWriter.getInstance(document, new FileOutputStream(file));

            // Abrir documento
            document.open();

            // Agregar contenido
            for (Cliente cliente : lista) {

                document.add(new Paragraph("ID: " + cliente.getIdCliente()));
                document.add(new Paragraph("Nombre: " + cliente.getNombre()));
                document.add(new Paragraph("Apellido: " + cliente.getApellido()));
                document.add(new Paragraph("DNI: " + cliente.getDni()));
                document.add(new Paragraph("--------------------------------------------"));
            }

            // Cerrar documento
            document.close();

            System.out.println("PDF generado correctamente.");
            System.out.println(file.getAbsolutePath());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
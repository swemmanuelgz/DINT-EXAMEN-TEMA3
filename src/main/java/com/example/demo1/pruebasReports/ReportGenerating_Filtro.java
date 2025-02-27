package com.example.demo1.pruebasReports;

import com.example.demo1.utils.Constantes;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase que genera un report con la libreria iText, para obtener
 * los datos de un usuario concreto en la tabla USER
 *
 */
public class ReportGenerating_Filtro {
    public void generateReport(Connection conn, String nombre) {
        try {
            // Crea un documento PDF y un PdfDocument asociado
            PdfWriter writer = new PdfWriter(new FileOutputStream("REPORTS/reporte_" + nombre + ".pdf"));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Título del reporte
            Paragraph title = new Paragraph("REPORTE DEL USUARIO (Nombre = '" + nombre + "')")
                    .setFontSize(16);
            title.setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));  // Salto de línea

            // Crear la tabla con 2 columnas
            Table table = new Table(2); // 2 columnas

            // Establecer el ancho de la tabla al 100% del espacio disponible
            table.setWidth(UnitValue.createPercentValue(100));

            // Agregar los encabezados de la tabla
            table.addCell(new Cell().add(new Paragraph("NOMBRE")).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("PWD")).setTextAlignment(TextAlignment.CENTER));

            // Consulta SQL filtrada para obtener solo los usuarios con el nombre proporcionado
            Statement stmt = conn.createStatement();
            String sql = "SELECT NOMBRE, PWD FROM USER WHERE NOMBRE = '" + nombre + "'"; // Filtrar por el nombre variable
            ResultSet rs = stmt.executeQuery(sql);

            // Agregar los datos de la base de datos a la tabla
            while (rs.next()) {
                table.addCell(new Cell().add(new Paragraph(rs.getString("NOMBRE"))).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell().add(new Paragraph(rs.getString("PWD"))).setTextAlignment(TextAlignment.LEFT));
            }

            // Añadir la tabla al documento PDF
            document.add(table);

            // Cerrar el documento
            document.close();

            System.out.println("Reporte generado exitosamente para " + nombre);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para establecer la conexión con la base de datos SQLite
    public static Connection connect() {
        try {
            // Asegúrate de tener el driver JDBC de SQLite en tu proyecto
            Connection conn = DriverManager.getConnection(Constantes.RUTA_BBDD.getDescripcion());
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Conectar a la base de datos y generar el reporte para un nombre específico
        String nombre = "maria";  // Cambia el valor aquí para filtrar por diferentes nombres
        Connection conn = connect();
        if (conn != null) {
            ReportGenerating_Filtro generator = new ReportGenerating_Filtro();
            generator.generateReport(conn, nombre);
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}
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
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase que genera un report con la libreria iText, para obtener
 * un listado de usuarios en la tabla USER
 *
 */
public class ReportGenerating {
    public void generateReport(Connection conn) {
        try {
            // Crea un documento PDF y un PdfDocument asociado
            PdfWriter writer = new PdfWriter(new FileOutputStream("REPORTS/reporte_usuarios.pdf"));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Título del reporte
            Paragraph title = new Paragraph("REPORTE DE USUARIOS, ACTIVIDADES Y CALORIAS")
                    .setFontSize(16);
            title.setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));  // Salto de línea

            // Crear la tabla con 3 columnas
            Table table = new Table(3); // 3 columnas

            // Establecer los anchos de las columnas en porcentaje
            table.setWidth(UnitValue.createPercentValue(100)); // Hace que la tabla ocupe el 100% del ancho disponible

            // Agregar los encabezados de la tabla
            table.addCell(new Cell().add(new Paragraph("USUARIO")).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("ACTIVIDAD")).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("CALORIAS")).setTextAlignment(TextAlignment.CENTER));

            // Obtener los datos de la base de datos
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, actividad, calorias FROM USUARIO");

            // Agregar los datos de la base de datos a la tabla
            while (rs.next()) {
                table.addCell(new Cell().add(new Paragraph(rs.getString("nombre"))).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell().add(new Paragraph(rs.getString("actividad"))).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell().add(new Paragraph(rs.getString("calorias"))).setTextAlignment(TextAlignment.LEFT));
            }

            // Añadir la tabla al documento PDF
            document.add(table);

            // Cerrar el documento
            document.close();

            System.out.println("Reporte generado exitosamente.");
            //mostramos la alerta
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reporte generado");
            alert.setHeaderText("Reporte generado exitosamente.");
            alert.setContentText("El reporte se ha generado con éxito en la carpeta REPORTS.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para establecer la conexión con la base de datos SQLite
    public static Connection connect() {
        try {
            // Asegúrate de tener el driver JDBC de SQLite en tu proyecto maven
            Connection conn = DriverManager.getConnection(Constantes.RUTA_BBDD_CALORIAS.getDescripcion());
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Conectar a la base de datos y generar el reporte
        Connection conn = connect();
        if (conn != null) {
            ReportGenerating generator = new ReportGenerating();
            generator.generateReport(conn);
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}

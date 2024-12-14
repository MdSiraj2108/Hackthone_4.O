import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class AttendanceServlet extends HttpServlet {
    private static final String FILE_PATH = "C:/attendance.xlsx";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Parse JSON input
        BufferedReader reader = request.getReader();
        StringBuilder jsonInput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }
        String studentId = jsonInput.toString().replaceAll("[^\\dA-Za-z]", ""); // Extract Student ID

        // Record attendance in the Excel file
        try {
            recordAttendance(studentId);
            response.getWriter().write("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false}");
        }
    }

    private void recordAttendance(String studentId) throws IOException {
        File file = new File(FILE_PATH);
        Workbook workbook;
        Sheet sheet;

        // Load existing workbook or create a new one
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Attendance");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Student ID");
            headerRow.createCell(1).setCellValue("Timestamp");
        }

        // Append new attendance record
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum + 1);
        row.createCell(0).setCellValue(studentId);
        row.createCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Save the workbook
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
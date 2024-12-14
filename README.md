# Hackthone_4.O
The `AttendanceServlet.java` file is a Java servlet that facilitates the recording of attendance data into an Excel file using the Apache POI library. Below is a detailed description of its functionality:

### Features:
1. **Handles POST Requests:**
   - The servlet processes incoming HTTP POST requests to record attendance data.
   - The request contains a JSON input, from which a `Student ID` is extracted.

2. **Data Parsing:**
   - Reads JSON input from the request body and extracts the student identifier by sanitizing the input.

3. **Excel File Handling:**
   - Utilizes the Apache POI library to work with `.xlsx` files.
   - If the `attendance.xlsx` file exists, it is opened and updated. Otherwise, a new file is created with appropriate headers (`Student ID` and `Timestamp`).

4. **Attendance Recording:**
   - Records the `Student ID` and a timestamp of when the attendance was marked.

5. **Error Handling:**
   - Returns a JSON response indicating the success or failure of the operation.

### Key Components:
- **Servlet Setup:**
  - Extends `HttpServlet` and overrides the `doPost` method.
  - Uses `HttpServletRequest` to handle input and `HttpServletResponse` to send responses.

- **Excel Manipulation:**
  - The attendance data is stored in an Excel file (`attendance.xlsx`) located at a specified path (`C:/attendance.xlsx`).
  - Apache POI is used to read and write Excel files.

- **Date and Time Formatting:**
  - Uses `SimpleDateFormat` to format the current timestamp in the `yyyy-MM-dd HH:mm:ss` format.

### Application Use Case:
This servlet is designed for applications that require tracking attendance, such as educational institutions or corporate environments. It ensures:
- Persistent storage of attendance records in an Excel sheet.
- Real-time recording through HTTP POST requests.
- Compatibility with existing tools that can read Excel files.

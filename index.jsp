<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Attendance Tracker</title>
  <script src="https://cdn.jsdelivr.net/npm/jsqr/dist/jsQR.js"></script>
  <style>
    video, canvas {
      width: 400px;
      height: 300px;
      border: 1px solid black;
    }
  </style>
</head>
<body>
  <h1>Scan QR Code for Attendance</h1>
  <video id="video" autoplay></video>
  <canvas id="canvas"></canvas>
  <p id="message"></p>

  <script>
    const video = document.getElementById("video");
    const canvas = document.getElementById("canvas");
    const context = canvas.getContext("2d");
    const message = document.getElementById("message");

    // Initialize webcam
    navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } })
      .then((stream) => {
        video.srcObject = stream;
      })
      .catch((err) => {
        console.error("Error accessing camera:", err);
      });

    function scanQRCode() {
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
      const code = jsQR(imageData.data, canvas.width, canvas.height);

      if (code) {
        console.log("QR Code Detected:", code.data);
        message.innerText = QR Code: ${code.data};
        sendAttendance(code.data);
      }
    }

    // Send QR code data to the server
    function sendAttendance(studentId) {
      fetch("AttendanceServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ studentId }),
      })
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            alert("Attendance recorded successfully!");
          } else {
            alert("Failed to record attendance.");
          }
        })
        .catch(err => console.error("Error sending data:", err));
    }

    setInterval(scanQRCode, 500); // Scan every 500ms
  </script>
</body>
</html>
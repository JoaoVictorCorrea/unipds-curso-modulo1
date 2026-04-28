import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

void main() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(8000)){
        System.out.println("Book Server is running on port 8000...");

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                InputStream clientIS = clientSocket.getInputStream();

                StringBuilder requestBuilder = new StringBuilder();
                int data;

                do {
                    data = clientIS.read();
                    requestBuilder.append((char) data);
                } while (clientIS.available() > 0);

                String request = requestBuilder.toString();
                System.out.println(request);

                Path path = Path.of("src/books.json");
                System.out.println("Reading file: " + path.toAbsolutePath());
                String json = Files.readString(path);

                OutputStream clientOS = clientSocket.getOutputStream();
                PrintStream clientOut = new PrintStream(clientOS);

                clientOut.println("HTTP/1.1 200 OK");
                clientOut.println("Content-Type: application/json; charset=UTF-8");
                clientOut.println();
                clientOut.println(json);
            }
        }
    }
}

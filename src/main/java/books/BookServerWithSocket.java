package books;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookServerWithSocket {
    private static final Logger logger = Logger.getLogger(BookServerWithSocket.class.getName());
    public static final Database database = new InMemoryDatabase();

    void main() throws IOException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {
            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                logger.info("Book Server is running on port 8000...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    executorService.execute(() -> processRequest(clientSocket));
                }
            }
        }
    }

    private static void processRequest(Socket clientSocket) {
        try (clientSocket) {
            InputStream clientIS = clientSocket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();
            int data;

            do {
                data = clientIS.read();
                requestBuilder.append((char) data);
            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            logger.finest(request);

            Thread.sleep(250);

            String[] requestChunks = request.split("\r\n\r\n");
            String requestLineAndHeaders = requestChunks[0];

            String[] requestLineAndHeadersChunks = requestLineAndHeaders.split("\r\n");
            String requestLine = requestLineAndHeadersChunks[0];

            String[] requestLineChunks = requestLine.split(" ");
            String method = requestLineChunks[0];
            String requestURI = requestLineChunks[1];

            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS);

            try {

                if (method.equals("GET") && requestURI.equals("/books/books.json")) {
                    Path path = Path.of("src/main/java/books/books.json");
                    logger.fine("Reading file: " + path.toAbsolutePath());
                    String json = Files.readString(path);

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println();
                    clientOut.println(json);
                } else if (method.equals("GET") && requestURI.equals("/books")) {
                    List<Book> library = database.getLibrary();

                    Gson gson = new Gson();
                    String json = gson.toJson(library);

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println();
                    clientOut.println(json);
                } else if (method.equals("GET") && requestURI.equals("/books/total")) {
                    List<Book> library = database.getLibrary();
                    int total = library.size();

                    clientOut.println("HTTP/1.1 200 OK");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println();
                    clientOut.println(total);
                } else if (method.equals("POST") && requestURI.equals("/books")) {
                    if (requestChunks.length == 1) {
                        clientOut.println("HTTP/1.1 400 Bad Request");
                        return;
                    }

                    String body = requestChunks[1];
                    Gson gson = new Gson();
                    Book newBook = gson.fromJson(body, Book.class);
                    database.addBook(newBook);

                    clientOut.println("HTTP/1.1 201 Created");
                    clientOut.println("Content-Type: application/json; charset=UTF-8");
                    clientOut.println();
                    clientOut.println();
                } else {
                    logger.warning(() -> "URI não encontrada: " + requestURI);
                    clientOut.println("HTTP/1.1 404 Not Found");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e, () -> "Erro ao tratar " + method + " " + requestURI);
                clientOut.println("HTTP/1.1 500 Internal Server Error");
                clientOut.println();
                clientOut.println(e.getMessage());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro no servidor", e);
            throw new RuntimeException(e);
        }
    }
}
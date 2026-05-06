import books.Book;
import books.Database;
import com.google.gson.Gson;

public static final Database database = new Database();

void main() throws IOException {
    try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Book Server is running on port 8000...");

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
        System.out.println(request);

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

        if (method.equals("GET") && requestURI.equals("/books/books.json")) {
            Path path = Path.of("src/books.json");
            System.out.println("Reading file: " + path.toAbsolutePath());
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
            if(requestChunks.length == 1) {
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
            clientOut.println("HTTP/1.1 404 Not Found");
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

void main() throws IOException {
    try (Socket socket = new Socket("localhost", 8000)) {
        OutputStream clientOS = socket.getOutputStream();
        PrintStream clientOut = new PrintStream(clientOS);
        clientOut.println("GET /books.json HTTP/1.1");
        clientOut.println();

        InputStream clientIS = socket.getInputStream();
        Scanner scanner = new Scanner(clientIS);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }
}

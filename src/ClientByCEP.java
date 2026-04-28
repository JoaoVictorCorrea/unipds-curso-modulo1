import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() throws IOException, InterruptedException {
    //Java 1.0
    URL url = new URL("https://viacep.com.br/ws/01001000/json/");
    try (Scanner scanner = new Scanner(url.openStream())) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }

    //Java 11
    URI uri = URI.create("https://viacep.com.br/ws/01001000/json/");
    try (HttpClient client = HttpClient.newHttpClient()){
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String body = response.body();
        System.out.println(statusCode);
        System.out.println(body);
    }
}
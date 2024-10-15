import java.io.IOException;

import com.google.gson.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Scanner;


public class TranslatorText {
    private static String key = "ac57f571e01d44ecbfab379caef73a92";

    // Localização/Região
    // Onde achar: ' Azure portal: Chaves e Ponto de extremidade '
    private static String location = "westus2";

    // Inicialização do OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // Realização do Post
    public String Post(String toTranslate) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestString = "[{\"Text\": \"" + toTranslate + "\"}]";
        RequestBody body = RequestBody.create(requestString, mediaType);
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=ja")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                // Localização para o caso de usar um recurso multi-service ou regional (não global). 
                .addHeader("Ocp-Apim-Subscription-Region", location) 
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // "Embelezar" a resposta json
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.print("Type the string to be translated: ");
        
        String toTranslate = entrada.nextLine();

        // Traduzir de inglês para japonês
        try {
            TranslatorText translateRequest = new TranslatorText();
            String response = translateRequest.Post(toTranslate);
            System.out.println(prettify(response));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

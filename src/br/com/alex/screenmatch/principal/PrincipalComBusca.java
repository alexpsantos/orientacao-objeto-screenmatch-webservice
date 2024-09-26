package br.com.alex.screenmatch.principal;

import br.com.alex.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alex.screenmatch.modelos.Titulo;
import br.com.alex.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {

    public static void main(String[] args) throws IOException, InterruptedException {

        var busca = "";
        List<Titulo> titulos = new ArrayList<>();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create();

        while(!busca.equalsIgnoreCase("sair")) {


            Scanner leitura = new Scanner(System.in);
            System.out.println("Digite um filme para busca: ");
            busca = leitura.nextLine();

            if(busca.equalsIgnoreCase("sair")) {
                break;
            }

            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=356698d1";
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

                String json = response.body();
                System.out.println(json);



                TituloOmdb tituloOmdb = gson.fromJson(json, TituloOmdb.class);

                System.out.println("Titulo: " + tituloOmdb);

                //try {

                Titulo meuTitulo = new Titulo(tituloOmdb);
                System.out.println("Titulo: " + meuTitulo);

                 titulos.add(meuTitulo);


            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento, verifique a url " + e.getMessage());
            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println("Ocorreu um erro, nao sei qual: " + e.getMessage());
            } finally {
                System.out.println("Finalizou!");
            }
        }
        FileWriter escrita = new FileWriter("titulos.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();
        System.out.println(titulos);

    }
}



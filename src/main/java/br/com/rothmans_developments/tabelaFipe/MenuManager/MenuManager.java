package br.com.rothmans_developments.tabelaFipe.MenuManager;

import br.com.rothmans_developments.tabelaFipe.model.Dados;
import br.com.rothmans_developments.tabelaFipe.model.Modelos;
import br.com.rothmans_developments.tabelaFipe.service.ConsumoApi;
import br.com.rothmans_developments.tabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

public class MenuManager {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String  URL_BASE = "https://parallelum.com.br/fipe/api/v1/";


    public void exibeMenu() {
        var menu = """
                *** OPÇÔES ***
         
                |----------------------------|
                |Carro                       |
                |Moto                        |
                |Caminhão                    |
                |----------------------------|
          
          
                Digite uma das opções para consulta
                """;

        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        }else {
            endereco = URL_BASE + "canhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas. stream().
                sorted(Comparator.comparing(Dados::codigo)).
                forEach(System.out::println);

        System.out.println("Informe o codigo da marca para consulta");
        var codigoMarca = leitura.nextLine();


        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modelosLista = conversor.obterDados(json, Modelos.class);


        System.out.println("\n Modelos dessa marca: ");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
    }
}

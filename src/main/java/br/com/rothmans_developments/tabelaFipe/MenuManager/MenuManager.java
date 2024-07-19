package br.com.rothmans_developments.tabelaFipe.MenuManager;

import br.com.rothmans_developments.tabelaFipe.model.Dados;
import br.com.rothmans_developments.tabelaFipe.model.Modelos;
import br.com.rothmans_developments.tabelaFipe.model.Veiculo;
import br.com.rothmans_developments.tabelaFipe.service.ConsumoApi;
import br.com.rothmans_developments.tabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        marcas. stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca para consulta");
        var codigoMarca = leitura.nextLine();


        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modelosLista = conversor.obterDados(json, Modelos.class);


        System.out.println("\n Modelos dessa marca: ");
        modelosLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome a ser buscado");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modelosLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

    System.out.println("\nModelos filtrados: ");
    modelosFiltrados.forEach(System.out::println);


    System.out.println("Digite por favor o código do modelo para buscar valores avaliados");
    var codigoModelo = leitura.nextLine();


    endereco = endereco + "/" + codigoModelo + "/anos";
    json = consumo.obterDados(endereco);
    List<Dados> anos = conversor.obterLista(json, Dados.class);
    List<Veiculo> veiculos = new ArrayList<>();

    for (int i =0; i <  anos.size(); i++) {

        var enderecoAnos = endereco + "/" + anos.get(i).codigo();
        json = consumo.obterDados(enderecoAnos);
        Veiculo veiculo =(conversor.obterDados(json, Veiculo.class));
        veiculos.add(veiculo);
    }
    System.out.println("Todos os veiculos filtrados com avalições por ano: ");
    veiculos.forEach(System.out::println);







    }
}

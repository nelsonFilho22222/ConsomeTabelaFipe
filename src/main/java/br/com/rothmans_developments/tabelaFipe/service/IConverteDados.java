package br.com.rothmans_developments.tabelaFipe.service;

import java.util.List;

public interface IConverteDados {

    <T> T obterDados (String json, Class<T> classe);

    <T> List<T> obterLista(String jsonl, Class<T> classe);
}

package br.com.rothmans_developments.tabelaFipe.service;

public interface IConverteDados {

    <T> T obterDados (String json, Class<T> classe);
}

package com.thiagomdo.ba.challenge.msorders.client.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressByCep {
    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private String cep;

    @Override
    public String toString() {
        return "AddressByCep{" +
        "logradouro='" + logradouro + '\'' +
        ", complemento='" + complemento + '\'' +
        ", bairro='" + bairro + '\'' +
        ", localidade='" + localidade + '\'' +
        ", uf='" + uf + '\'' +
        ", cep='" + cep + '\'' +
        '}';
    }
}

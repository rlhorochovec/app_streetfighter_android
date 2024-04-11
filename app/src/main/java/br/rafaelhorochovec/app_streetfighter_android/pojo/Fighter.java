package br.rafaelhorochovec.app_streetfighter_android.pojo;

import com.google.gson.annotations.SerializedName;

public class Fighter {

    @SerializedName("id")
    public Integer id;

    @SerializedName("nome")
    public String nome;

    @SerializedName("nacionalidade")
    public String nacionalidade;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("updatedAt")
    public String updatedAt;

    public Fighter(Integer id, String nome, String nacionalidade, String createdAt, String updatedAt) {
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
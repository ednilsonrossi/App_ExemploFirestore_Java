package br.edu.ifsp.dmo.exemplofirestore;

public class Disciplina {
    private String sigla;
    private String nome;
    private int aulas;

    public Disciplina() {    }

    public Disciplina(String sigla, String nome, int aulas) {
        this.sigla = sigla;
        this.nome = nome;
        this.aulas = aulas;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAulas() {
        return aulas;
    }

    public void setAulas(int aulas) {
        this.aulas = aulas;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "sigla='" + sigla + '\'' +
                ", nome='" + nome + '\'' +
                ", aulas=" + aulas +
                '}';
    }
}

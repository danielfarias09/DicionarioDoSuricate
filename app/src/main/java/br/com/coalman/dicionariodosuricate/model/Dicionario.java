package br.com.coalman.dicionariodosuricate.model;


    public class Dicionario {
        long id;
        String palavra;
        String significado;


        public long getId() {
            return id;
        }



        public void setId(long id) {
            this.id = id;
        }



        public String getPalavra() {
            return palavra;
        }

        public void setPalavra(String palavra) {
            this.palavra = palavra;
        }

        public String getSignificado() {
            return significado;
        }

        public void setSignificado(String significado) {
            this.significado = significado;
        }
}

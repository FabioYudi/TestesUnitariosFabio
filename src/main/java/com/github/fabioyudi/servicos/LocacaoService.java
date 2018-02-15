package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.exception.FilmeSemEstoqueException;
import com.github.fabioyudi.exception.LocadoraException;

import java.util.Date;
import java.util.List;

import static com.github.fabioyudi.utils.DataUtils.adicionarDias;

public class LocacaoService {
    public String vPublico;
    protected String vProtegido;
    private String vPrivado;
    String vDefault;
    private double valorLocacao = 0;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {



        if(usuario == null){
            throw  new LocadoraException("Usuario vazio");
        }

        if(filmes == null || filmes.isEmpty()){
            throw  new LocadoraException("Filme Vazio");
        }

        for (Filme f : filmes) {
            System.out.println(f.getNome());
            System.out.println(f.getEstoque());
            System.out.println(f.getPrecoLocacao());
            if (f.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }

            valorLocacao += f.getPrecoLocacao();
        }


        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());




        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(valorLocacao);

        //Salvando a locacao...
        //TODO adicionar método para salvar


        return locacao;
    }

}
package com.github.fabioyudi.Servicos;

import com.github.fabioyudi.Entidades.Filme;
import com.github.fabioyudi.Entidades.Locacao;
import com.github.fabioyudi.Entidades.Usuario;

import java.util.Date;

import static com.github.fabioyudi.Utils.DataUtils.adicionarDias;

public class LocacaoService {

    public String vPublico;
    protected String vProtegido;
    private String vPrivado;
    String vDefault;

    public Locacao alugarFilme(Usuario usuario, Filme filme) {

        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }

}

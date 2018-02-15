package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.fabioyudi.utils.DataUtils.isMesmaData;
import static com.github.fabioyudi.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class LocacaoServiceAssertTest {
    private List<Filme> filmes;


    @Before
    public void setup() {
        filmes = new ArrayList<>();
    }



    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testeLocacaoAssert() {


        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 4, 5.0);


        Locacao locacao;
        try {
            locacao = service.alugarFilme(usuario, filmes);
            assertThat(locacao.getValor(), is(equalTo(5.0)));
            assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
            assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Erro");
        }


    }
}

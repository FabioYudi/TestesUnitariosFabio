package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.exception.FilmeSemEstoqueException;
import com.github.fabioyudi.exception.LocadoraException;
import com.github.fabioyudi.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.*;

import static com.github.fabioyudi.utils.DataUtils.isMesmaData;
import static com.github.fabioyudi.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocacaoServiceTest {
    private LocacaoService service;
    private static int contador = 0;
    private List<Filme> filmes;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setup() {

        service = new LocacaoService();
        filmes = new ArrayList<>();
    }


    @Test
    public void testeLocacao() throws Exception {
        //Cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 4, 5.0);
        Filme filme2 = new Filme("Filme 2", 2, 5.0);

        filmes.add(filme);
        filmes.add(filme2);


        //acao
        Locacao locacao;


        locacao = service.alugarFilme(usuario, filmes);


        //verificacao
        error.checkThat(locacao.getValor(), is(equalTo(10.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));


    }


    @Test(expected = FilmeSemEstoqueException.class)
    public void testeFilme() throws Exception {
        //Cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);
        Filme filme2 = new Filme("Filme 2", 0, 5.0);

        filmes.add(filme);
        filmes.add(filme2);

        //acao
        service.alugarFilme(usuario, filmes);
    }


    @Test
    public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
        //cenario
        Filme filme = new Filme("Filme 2", 1, 4.0);
        Usuario usuario = new Usuario("Usuario 1");
        filmes.add(filme);

        //Ação

        try {
            service.alugarFilme(null, filmes);
            Assert.fail("Deveria ter dado exceção");
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuario vazio"));
        }

        System.out.println("Forma robusta");

    }


    @Test
    public void testeLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        //  Filme filme = new Filme("Filme 2", 1, 4.0);
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme Vazio");
        //Ação
        service.alugarFilme(usuario, null);


        System.out.println("Forma Nova");

    }


    @Test
    public void comprarComDesconto() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");

        filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0),
                new Filme("Filme 5", 2, 4.0),
                new Filme("Filme 6", 2, 4.0),
                new Filme("Filme 6", 2, 4.0),
                new Filme("Filme 6", 2, 4.0));


        Locacao resultado = service.alugarFilme(usuario, filmes);


        assertThat(resultado.getValor(), is(22.0));
    }


    @Test
    public void DevolverDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        Usuario usuario = new Usuario("Usuario 1");

        filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0));


        Locacao retorno = service.alugarFilme(usuario, filmes);


        boolean segunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(segunda);

    }


}

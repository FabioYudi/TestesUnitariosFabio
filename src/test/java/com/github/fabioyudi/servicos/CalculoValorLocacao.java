package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.exception.FilmeSemEstoqueException;
import com.github.fabioyudi.exception.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)

public class CalculoValorLocacao {
    private LocacaoService service;
    private List<Filme> filmes;
    private Double valorLocacao;


    @Before
    public void setup() {
        service = new LocacaoService();
    }

    private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
    private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
    private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
    private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
    private static Filme filme6 = new Filme("Filme 5", 2, 4.0);


    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2, filme3), 11.0},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 17.0},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 18.0}
        });
    }


    @Test
    public void CalcularValorLocacao() throws FilmeSemEstoqueException, LocadoraException {
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


        assertThat(resultado.getValor(), is(valorLocacao));
    }
}

package com.github.fabioyudi.servicos;

import com.github.fabioyudi.daos.LocacaoDao;
import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.exception.FilmeSemEstoqueException;
import com.github.fabioyudi.exception.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.github.fabioyudi.builders.FilmeBuilder.umFilme;
import static com.github.fabioyudi.builders.UsuarioBuilder.umUsuarioBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)

public class CalculoValorLocacaoTest {
   @InjectMocks
   private LocacaoService service;
    @Parameterized.Parameter
    public  List<Filme> filmes;
    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String teste;

    @Mock
    LocacaoDao dao;


    @Before
    public void setup() {
        service = new LocacaoService();
        MockitoAnnotations.initMocks(this);
    }

    private static Filme filme1 = umFilme().agora();
    private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
    private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
    private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
    private static Filme filme6 = new Filme("Filme 5", 2, 4.0);

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes"}
        });
    }


    @Test
    public void CalcularValorLocacao() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = umUsuarioBuilder().agora();




        Locacao resultado = service.alugarFilme(usuario, filmes);


        assertThat(resultado.getValor(), is(valorLocacao));
    }
}

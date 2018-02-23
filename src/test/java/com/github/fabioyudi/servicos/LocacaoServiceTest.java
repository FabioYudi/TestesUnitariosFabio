package com.github.fabioyudi.servicos;

import com.github.fabioyudi.daos.LocacaoDao;
import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.exception.FilmeSemEstoqueException;
import com.github.fabioyudi.exception.LocadoraException;
import com.github.fabioyudi.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.github.fabioyudi.builders.FilmeBuilder.umFilme;
import static com.github.fabioyudi.builders.LocacaoBuilder.umLocacao;
import static com.github.fabioyudi.builders.UsuarioBuilder.umUsuarioBuilder;
import static com.github.fabioyudi.matchers.MatchersProprios.ehHoje;
import static com.github.fabioyudi.matchers.MatchersProprios.ehHojeComDiferenca;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LocacaoServiceTest {


    @InjectMocks
    private LocacaoService service;

    private static int contador = 0;
    private List<Filme> filmes;

    @Mock
    private SPCService spc;
    @Mock
    private LocacaoDao dao;
    @Mock
    private EmailService email;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setup() {
        service = new LocacaoService();
        MockitoAnnotations.initMocks(this);

        filmes = new ArrayList<>();


    }


    @Test
    public void testeLocacao() throws Exception {
        //Cenario
        Usuario usuario = umUsuarioBuilder().agora();
        filmes = asList(umFilme().agora());


        //acao
        Locacao locacao;


        locacao = service.alugarFilme(usuario, filmes);


        //verificacao
        error.checkThat(locacao.getValor(), is(equalTo(4.0)));
//      error.checkThat(isMesmaData(locacao.getDataLocacao(), MatchersProprios.ehHoje())));
//        error.checkThat(isMesmaData(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferenca(1)));


    }


    @Test(expected = FilmeSemEstoqueException.class)
    public void testeFilme() throws Exception {
        //Cenario
        Usuario usuario = umUsuarioBuilder().agora();
        filmes = asList(umFilme().semEstoque().agora());


        //acao
        service.alugarFilme(usuario, filmes);
    }


    @Test
    public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
        //cenario
        filmes = asList(umFilme().agora());
        Usuario usuario = umUsuarioBuilder().agora();

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
        Usuario usuario = umUsuarioBuilder().agora();
        filmes = asList(umFilme().semEstoque().agora());
        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme Vazio");
        //Ação
        service.alugarFilme(usuario, null);


        System.out.println("Forma Nova");

    }


    @Test
    public void comprarComDesconto() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");

        filmes = asList(new Filme("Filme 1", 2, 4.0),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0),
                new Filme("Filme 5", 2, 4.0));


        Locacao resultado = service.alugarFilme(usuario, filmes);


        assertThat(resultado.getValor(), is(14.0));
    }


    @Test
    public void DevolverDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        Usuario usuario = new Usuario("Usuario 1");

        filmes = asList(umFilme().agora(),
                new Filme("Filme 2", 2, 4.0),
                new Filme("Filme 3", 2, 4.0),
                new Filme("Filme 4", 2, 4.0));


        Locacao retorno = service.alugarFilme(usuario, filmes);


        boolean segunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(segunda);
//        assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
//        assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.SUNDAY));

    }

    @Test
    public void naoDeveAlugarFilmeParaNegativado() throws FilmeSemEstoqueException {
        //Cenario
        Usuario usuario = umUsuarioBuilder().agora();
        filmes = asList(umFilme().agora());

        when(spc.possuiNegativacao(any(Usuario.class))).thenReturn(true);


        //acao
        try {
            service.alugarFilme(usuario, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario negativado"));
        }


        verify(spc).possuiNegativacao(any(Usuario.class));


    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas() {
        //Cenario
        Usuario usuario = umUsuarioBuilder().agora();
        Usuario usuario2 = umUsuarioBuilder().comNome("Usuario em dia").agora();
        Usuario usuario3 = umUsuarioBuilder().comNome("Outro atrasado").agora();

        List<Locacao> locacoes =
                asList(umLocacao().atrasado().comUsuario(usuario).agora(),
                        umLocacao().comUsuario(usuario2).agora(),
                        umLocacao().atrasado().comUsuario(usuario3).agora(),
                        umLocacao().atrasado().comUsuario(usuario3).agora());

        when(dao.obterLocacacoesAtrasadas()).thenReturn(locacoes);
        //acao
        service.notificarAtrasos();
        verify(email, times(3)).notificarAtraso(any(Usuario.class));
        verify(email).notificarAtraso(usuario);
        verify(email, never()).notificarAtraso(usuario2);
        verifyNoMoreInteractions(email);

    }


    @Ignore
    public void deveTratarErroSPC() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = umUsuarioBuilder().agora();
        filmes = asList(umFilme().agora());

        when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha"));

        //acao
        service.alugarFilme(usuario, filmes);
        //verificação
    }


    @Test
    public void deveProrrogarUmaLocacao() throws FilmeSemEstoqueException, LocadoraException {

        Locacao locacao = umLocacao().agora();
        service.prorrogarLocacao(locacao, 3);

        ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
        Mockito.verify(dao).salvar(argCapt.capture());
        Locacao locacaoRetorno = argCapt.getValue();

        error.checkThat(locacaoRetorno.getValor(), is(12.0));
        error.checkThat(locacaoRetorno.getDataLocacao(), ehHoje());
        error.checkThat(locacaoRetorno.getDataRetorno(), ehHojeComDiferenca(3));


    }


}

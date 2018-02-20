package com.github.fabioyudi.servicos;

import buildermaster.BuilderMaster;
import com.github.fabioyudi.daos.LocacaoDao;
import com.github.fabioyudi.entidades.Filme;
import com.github.fabioyudi.entidades.Locacao;
import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.exception.FilmeSemEstoqueException;
import com.github.fabioyudi.exception.LocadoraException;
import com.github.fabioyudi.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.github.fabioyudi.utils.DataUtils.adicionarDias;

public class LocacaoService {
    private LocacaoDao dao;
    private SPCService spcService;
    private EmailService emailService;


    public String vPublico;
    protected String vProtegido;
    private String vPrivado;
    String vDefault;
    private double valorLocacao = 0;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {


        if (usuario == null) {
            throw new LocadoraException("Usuario vazio");
        }

        if (filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("Filme Vazio");
        }

        for (Filme f : filmes) {
            System.out.println(f.getNome());
            System.out.println(f.getEstoque());
            System.out.println(f.getPrecoLocacao());
            if (f.getEstoque() == 0) {

                throw new FilmeSemEstoqueException();

            }

            if (spcService.possuiNegativacao(usuario)) {
                throw new LocadoraException("Usuario negativado");
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
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }

        locacao.setDataRetorno(dataEntrega);

        if (filmes.size() > 2) {
            valorLocacao -= filmes.get(2).getPrecoLocacao() * 0.25;
            if (filmes.size() > 3) {
                valorLocacao -= filmes.get(3).getPrecoLocacao() * 0.50;
                if (filmes.size() > 4) {
                    valorLocacao -= filmes.get(4).getPrecoLocacao() * 0.75;
                    if (filmes.size() > 5) {
                        valorLocacao -= filmes.get(5).getPrecoLocacao();
                    }
                }
            }
        }


        locacao.setValor(valorLocacao);

        //Salvando a locacao...
        dao.salvar(locacao);

        return locacao;
    }

    public void notificarAtrasos(){
        List<Locacao> locacoes = dao.obterLocacacoesAtrasadas();

        locacoes.forEach(locacao2 -> {
            if(locacao2.getDataRetorno().before(new Date())) {
                emailService.notificarAtraso(locacao2.getUsuario());
            }

        });
    }


    public static void main(String[] args) {
        new BuilderMaster().gerarCodigoClasse(Locacao.class);
    }




}

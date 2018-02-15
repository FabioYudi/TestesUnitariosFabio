package com.github.fabioyudi.Servicos;

import com.github.fabioyudi.Entidades.Filme;
import com.github.fabioyudi.Entidades.Locacao;
import com.github.fabioyudi.Entidades.Usuario;
import com.github.fabioyudi.Servicos.LocacaoService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static com.github.fabioyudi.Utils.DataUtils.isMesmaData;
import static com.github.fabioyudi.Utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoServiceTest {
    @Test
    public void teste() {
        //Cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);
        //acaoa
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificacao
        Assert.assertTrue(locacao.getValor() == 5.0);
        Assert.assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));

    }
}

package com.github.fabioyudi.daos;

import com.github.fabioyudi.entidades.Locacao;

import java.util.List;

public interface LocacaoDao {

    public void salvar(Locacao locacao);

    List<Locacao> obterLocacacoesAtrasadas();
}

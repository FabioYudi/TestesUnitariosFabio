package com.github.fabioyudi.daos;

import com.github.fabioyudi.entidades.Locacao;

import java.util.List;

public class LocacaoDaoFake implements LocacaoDao {
    @Override
    public void salvar(Locacao locacao) {

    }

    @Override
    public List<Locacao> obterLocacacoesAtrasadas() {
        return null;
    }
}

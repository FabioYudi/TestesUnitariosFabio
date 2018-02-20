package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Usuario;

public interface EmailService {

    void notificarAtraso(Usuario usuario);
}

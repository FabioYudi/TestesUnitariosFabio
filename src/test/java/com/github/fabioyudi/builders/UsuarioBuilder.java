package com.github.fabioyudi.builders;

import com.github.fabioyudi.entidades.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

   private UsuarioBuilder() {

    }

    public static UsuarioBuilder umUsuarioBuilder(){
       UsuarioBuilder builder = new UsuarioBuilder();
       builder.usuario = new Usuario();
       builder.usuario.setNome("Usuario1");
       return builder;
    }

    public Usuario agora(){
       return usuario;
    }
}

package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Usuario;
import com.github.fabioyudi.matchers.PrimeiroElementoMatcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AssertTest {



    @Test
    public void test(){

        //booleans

        Assert.assertTrue(true);
        Assert.assertFalse(false);
        Assert.assertEquals(1, 1);
        Assert.assertEquals(0.51234, 0.313, 0.102);
        Assert.assertEquals(Math.PI, 3.14, 0.01);
        System.out.println(Math.PI);

        //inteiro objeto e tipo primitivo
        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        //Strings
        Assert.assertEquals("bola", "bola");
        Assert.assertNotEquals("bola", "casa");
        Assert.assertTrue("bola".contains("aa"));
        Assert.assertTrue("bola".startsWith("bo"));



        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;

        Assert.assertEquals(u1, u2);


        // verificar se são da mesma instancia
        Assert.assertSame(u2, u2);
        Assert.assertNotSame(u2,u1);


        // verifica se é nulo
        Assert.assertNull(u3);
        Assert.assertNotNull(u2);

    }


    @Test
    public void trazListaOrdenadaComTodosOsElementos() {
        List<Integer> lista = Arrays.asList(1,2,3,10);


        List<Integer> listaOrdenada = lista.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        listaOrdenada.forEach(System.out::println);

        Assert.assertThat(listaOrdenada, new PrimeiroElementoMatcher());
    }
}

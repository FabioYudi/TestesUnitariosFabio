package com.github.fabioyudi.servicos;

import com.github.fabioyudi.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {
    @Test
    public void test(){

        //booleans

        Assert.assertTrue(true);
        Assert.assertFalse(false);
        Assert.assertEquals(1, 1);
        Assert.assertEquals(0.51234, 0.512, 0.001);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        //inteiro objeto e tipo primitivo
        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        //Strings
        Assert.assertEquals("bola", "bola");
        Assert.assertNotEquals("bola", "casa");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
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
}

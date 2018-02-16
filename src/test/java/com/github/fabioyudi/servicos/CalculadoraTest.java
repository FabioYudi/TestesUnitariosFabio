package com.github.fabioyudi.servicos;

import com.github.fabioyudi.exception.NaoPodeDividirPorZero;
import org.junit.Assert;
import org.junit.Test;

public class CalculadoraTest {


    @Test
    public void SomarDoisValores() {
        //Cenario
        int a = 5;
        int b = 3;
        Calculadora ca = new Calculadora();

        //acao
        int resultado = ca.somar(a, b);


        //Verificacao
        Assert.assertEquals(8, resultado);
    }


    @Test
    public void SubtrairDoisValores(){
        //Cenario
        int a = 8;
        int b = 5;
        Calculadora ca = new Calculadora();




        //Ação

        int resultado = ca.subtrair(a, b);

        //Verificação
        Assert.assertEquals(3, resultado);
    }


    @Test
    public void DividirDoisValores() throws NaoPodeDividirPorZero {
        int a =6;
        int b = 3;
        Calculadora ca = new Calculadora();


        int resultado = ca.dividir(a, b);


        Assert.assertEquals(2, resultado);
    }


    @Test(expected = NaoPodeDividirPorZero.class)
    public void  LancarExcecao() throws NaoPodeDividirPorZero {
        int a = 30;
        int b = 0;
        Calculadora ca = new Calculadora();

        ca.dividir(a,b);

    }

}

package com.github.fabioyudi.servicos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraMockTest {

    @Mock
    private Calculadora calcMock;

    @Spy
    private Calculadora calcSpy;



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void teste(){
        Mockito.when(calcMock.somar(1,2)).thenReturn(8);
        Mockito.when(calcSpy.somar(1,2)).thenReturn(8);

        System.out.println("Mock " + calcMock.somar(1,1));
        System.out.println("Spy " + calcSpy.somar(1,1));


    }
}

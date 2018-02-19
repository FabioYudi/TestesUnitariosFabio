package com.github.fabioyudi.suites;

import com.github.fabioyudi.servicos.CalculadoraTest;
import com.github.fabioyudi.servicos.CalculoValorLocacaoTest;
import com.github.fabioyudi.servicos.LocacaoServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculoValorLocacaoTest.class,
        CalculadoraTest.class,
        LocacaoServiceTest.class

})
public class SuiteExecucao {

    @BeforeClass
    public static void before(){
        System.out.println("Before");
    }

    @AfterClass
    public static void after(){
        System.out.println("After");
    }
}

package com.github.fabioyudi.matchers;

public class MatchersProprios {

    public  static DiaSemanaMatcher ehHojeComDiferenca(Integer qtdDias){
        return new DiaSemanaMatcher(qtdDias);
    }

    public  static DiaSemanaMatcher ehHoje(){return new DiaSemanaMatcher(0);

    }
}

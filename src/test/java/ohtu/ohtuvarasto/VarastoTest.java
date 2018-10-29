package ohtu.ohtuvarasto;

import org.junit.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void konstruktoriKorjaaVirheellisenTilavuuden() {
        Varasto v = new Varasto(-1);
        assertEquals(0, v.getTilavuus(), 0.01);
    }

    @Test
    public void ToinenKonstruktoriLuoTyhjanVaraston() {
        Varasto v = new Varasto(0, 0);
        assertEquals(0, v.getTilavuus(), 0.01);
        assertEquals(0, v.getSaldo(), 0.01);
    }

    @Test
    public void ToinenKonstruktoriSaldoOveflow() {
        Varasto v = new Varasto(10, 100);
        assertEquals(10, v.getSaldo(), 0.01);
    }

    @Test
    public void ToinenKonstruktoriToimiiDefault() {
        Varasto v = new Varasto(100, 10);
        assertEquals(10, v.getSaldo(), 0.01);
    }

    @Test
    public void ToinenKonstruktoriKorjaaVirheellisenSaldon() {
        Varasto v = new Varasto(10, -10);
        assertEquals(0, v.getSaldo(), 0.01);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void LisaysEiToimiNegatiivisella() {
        double saldo = varasto.getSaldo();
        varasto.lisaaVarastoon(-1);
        assertEquals(saldo, varasto.getSaldo(), 0.01);
    }

    @Test
    public void LisaysOverflowToimii() {
        double max = varasto.getTilavuus();
        varasto.lisaaVarastoon(max * 100);
        assertEquals(varasto.getTilavuus(), varasto.getSaldo(), 0.01);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void OttaminenEiOtaNegatiivista() {
        double saldo = varasto.getSaldo();
        double otto = varasto.otaVarastosta(-100);
        assertEquals(1, otto, 0.01);
        assertEquals(saldo, varasto.getSaldo(), 0.01);
    }

    @Test
    public void OttaminenEiUnderFlowaa() {
        varasto.lisaaVarastoon(100);
        double saldo = varasto.getSaldo();
        double otto = varasto.otaVarastosta(saldo + 1);
        assertEquals(0, varasto.getSaldo(), 0.01);
        assertEquals(saldo, otto, 0.01);
    }

    @Test
    public void toStringTesti() {
        String s = "saldo = 0.0, vielä tilaa 10.0";
        assertEquals(s, varasto.toString());

        varasto.lisaaVarastoon(5);
        String s2 = "saldo = 5.0, vielä tilaa 5.0";
        assertEquals(s2, varasto.toString());
    }

}
package ohtu.ohtuvarasto;

import org.junit.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VarastoTest {

    Varasto varasto;
    private static final double vertailuTarkkuus = 0.0001;
    private static final double NEGATIIVINEN_SUURE = -10;
    private static final double TILAVUUS = 10;
    private static final double SALDO = 10;
    private static final double OVERFLOW_MAARA = Double.MAX_VALUE;

    @Before
    public void setUp() {
        varasto = new Varasto(TILAVUUS);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void konstruktoriKorjaaVirheellisenTilavuuden() {
        Varasto v = new Varasto(-1);
        assertEquals(0, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void ToinenKonstruktoriLuoTyhjanVaraston() {
        Varasto v = new Varasto(0, 0);
        assertEquals(0, v.getTilavuus(), vertailuTarkkuus);
        assertEquals(0, v.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void ToinenKonstruktoriSaldoOveflow() {
        Varasto v = new Varasto(TILAVUUS, OVERFLOW_MAARA);
        assertEquals(v.getTilavuus(), v.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void ToinenKonstruktoriToimiiDefault() {
        Varasto v = new Varasto(TILAVUUS, SALDO);
        assertEquals(SALDO, v.getSaldo(), vertailuTarkkuus);
        assertEquals(TILAVUUS, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void ToinenKonstruktoriKorjaaVirheellisenSaldon() {
        Varasto v = new Varasto(TILAVUUS, NEGATIIVINEN_SUURE);
        assertEquals(0, v.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(TILAVUUS, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(SALDO);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(SALDO, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(SALDO);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(varasto.getTilavuus() - SALDO, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void LisaysEiToimiNegatiivisella() {
        double saldo = varasto.getSaldo();
        varasto.lisaaVarastoon(-1);
        assertEquals(saldo, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void LisaysOverflowToimii() {
        double max = varasto.getTilavuus();
        varasto.lisaaVarastoon(OVERFLOW_MAARA);
        assertEquals(varasto.getTilavuus(), varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(SALDO);

        double saatuMaara = varasto.otaVarastosta(SALDO);

        assertEquals(SALDO, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(SALDO);

        varasto.otaVarastosta(SALDO);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(TILAVUUS, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void OttaminenEiOtaNegatiivista() {
        double saldo = varasto.getSaldo();
        double otto = varasto.otaVarastosta(-SALDO);
        assertEquals(0, otto, vertailuTarkkuus);
        assertEquals(saldo, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void OttaminenEiUnderFlowaa() {
        varasto.lisaaVarastoon(SALDO);
        double saldo = varasto.getSaldo();
        double otto = varasto.otaVarastosta(OVERFLOW_MAARA);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(saldo, otto, vertailuTarkkuus);
    }

    @Test
    public void toStringTesti() {
        String s = "saldo = 0.0, vielä tilaa 10.0";
        assertEquals(s, varasto.toString());

        varasto.lisaaVarastoon(SALDO);
        String s2 = "saldo = 10.0, vielä tilaa 0.0";
        assertEquals(s2, varasto.toString());
    }

}
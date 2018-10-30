package ohtuesimerkki;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsTest {
    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp() {
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }

    @Test
    public void hakuToimiiJosOlemassa() {
        String nimi = "Kurri";
        Player p = stats.search(nimi);
        assertNotNull(p);
        assertEquals(nimi, p.getName());
    }

    @Test
    public void hakuToimiiJosEiOlemassa() {
        Player p = stats.search("asdasd");
        assertNull(p);
    }

    @Test
    public void TiimiPalauttaaOikein() {
        List<Player> edm = stats.team("EDM");
        assertEquals(3, edm.size());
    }

    @Test
    public void TopScorersToimii() {
        List<Player> top = stats.topScorers(3);
        assertEquals(3, top.size());
        Player p = top.remove(0);
        assertEquals("Gretzky", p.getName());
    }
}

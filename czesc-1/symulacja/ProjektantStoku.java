package symulacja;

import kadra.mapki.GeneratorMapek;
import kadra.mapki.pliki.WyjatekSystemuPlikow;
import kadra.mapki.styl.GruboscKonturu;
import kadra.mapki.styl.StylKrawedzi;
import kadra.mapki.styl.StylLinii;
import kadra.mapki.styl.StylWezla;
import sportowcy.Sportowiec;
import stok.KrawędźGrafu;
import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Klasa implementująca metody umożliwiające wygenerowanie map, które
 * reperezentują statystyki podsumowujące wykonaną symulację.
 */
public class ProjektantStoku {
    private static final int CZAS_ZAKOŃCZENIA = 16 * 3600;
    private final List<Węzeł> listaWęzłów;
    private final GeneratorMapek generatorMapek;

    public ProjektantStoku(List<Węzeł> listaWęzłów,
                           String ścieżkaKatalogu) throws WyjatekSystemuPlikow {
        this.listaWęzłów = listaWęzłów;
        this.generatorMapek = new GeneratorMapek(ścieżkaKatalogu);
    }

    public void generujMapy(List<Sportowiec> listaSportowców) throws WyjatekSystemuPlikow {
        generujMapeParametrów();
        generujMapęStatystyk();
        generujMapyŚledzonychSportowców(listaSportowców);
    }

    private void generujMapeParametrów() throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();
        this.narysujWęzły();

        for(Węzeł węzełPoczątkowy: listaWęzłów) {
            this.zbierzParametryTras(węzełPoczątkowy);
            this.zbierzParametryWyciągów(węzełPoczątkowy);
        }

        generatorMapek.tworzMapke("parametryStoku.tex");
    }

    private void generujMapęStatystyk() throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();
        this.narysujWęzły();

        for(Węzeł węzełPoczątkowy: listaWęzłów) {
            zbierzStatystykiTras(węzełPoczątkowy);
            zbierzStatystykiWyciągów(węzełPoczątkowy);
        }

        generatorMapek.tworzMapke("StatystykiStoku.tex");
    }

    private void generujMapyŚledzonychSportowców(List<Sportowiec> listaSportowców) throws WyjatekSystemuPlikow {
        for(Sportowiec sportowiec: listaSportowców) {
            if(sportowiec.czyŚledzony()) {
                generujMapęSportowca(sportowiec);
            }
        }
    }

    private void narysujWęzły() {
        for(int i = 0; i < listaWęzłów.size(); i++) {
            Węzeł pobranyWęzeł = listaWęzłów.get(i);
            StylWezla styl;
            if(pobranyWęzeł.czySkomunikowany()) {
                styl = new StylWezla(GruboscKonturu.POGRUBIONY);
            }
            else {
                styl = new StylWezla(GruboscKonturu.ZWYKLY);
            }
            generatorMapek.dodajWezel(i, pobranyWęzeł.getX(), pobranyWęzeł.getY(), styl);
        }
    }

    private void zbierzParametryTras(Węzeł węzełPoczątkowy) {
        List<Trasa> listaTras = węzełPoczątkowy.getListaTras();

        for (Trasa pobranaTrasa: listaTras) {
            StylKrawedzi styl = new StylKrawedzi(StylLinii.CIAGLA);
            Węzeł węzełKońcowy = pobranaTrasa.getKońcowaStacja();

            List<String> listaDanych = new ArrayList<>();
            String liniaPierwsza = String.format("t%d: poziom: %d, czas: %ds",
                    pobranaTrasa.getId(), pobranaTrasa.getPoziomTrudności(), pobranaTrasa.getCzasPrzejazdu());
            String liniaDruga = String.format(Locale.ENGLISH, "odporność: %.2f, %.5f",
                    pobranaTrasa. getBazowaAtrakcyjność(), pobranaTrasa.getOdporność());

            listaDanych.add(liniaPierwsza);
            listaDanych.add(liniaDruga);

            generatorMapek.dodajKrawedz(listaWęzłów.indexOf(węzełPoczątkowy),
                    listaWęzłów.indexOf(węzełKońcowy), styl, listaDanych);
        }
    }

    private void zbierzParametryWyciągów(Węzeł węzełPoczątkowy) {
        List<Wyciąg> listaWyciągów = węzełPoczątkowy.getListaWyciągów();

        for(Wyciąg pobranyWyciąg: listaWyciągów) {
            StylKrawedzi styl = new StylKrawedzi(StylLinii.PRZERYWANA);
            Węzeł węzełKońcowy = pobranyWyciąg.getKońcowaStacja();

            List<String> listaDanych = new ArrayList<>();
            String liniaPierwsza = String.format("w%d: %d os. co %ds", pobranyWyciąg.getId(),
                    pobranyWyciąg.getIleSiedzeń(), pobranyWyciąg.getOdstępCzasowy());
            String liniaDruga = String.format("czas: %ds", pobranyWyciąg.getCzasPrzejazdu());

            listaDanych.add(liniaPierwsza);
            listaDanych.add(liniaDruga);

            generatorMapek.dodajKrawedz(listaWęzłów.indexOf(węzełPoczątkowy),
                    listaWęzłów.indexOf(węzełKońcowy), styl, listaDanych);
        }
    }

    private void zbierzStatystykiTras(Węzeł węzełPoczątkowy) {
        List<Trasa> listaTras = węzełPoczątkowy.getListaTras();

        for(Trasa pobranaTrasa: listaTras) {
            Węzeł węzełKońcowy = pobranaTrasa.getKońcowaStacja();
            StylKrawedzi styl = new StylKrawedzi(StylLinii.CIAGLA);
            double wyrównanie = pobranaTrasa.podajWyrównanie();
            int ilośćZjadów = pobranaTrasa.getIlePrzejazdów();

            List<String> listaDanych = new ArrayList<>();
            String liniaPierwsza = String.format(Locale.ENGLISH, "t%d: śnieg: %.2f",
                    pobranaTrasa.getId(), wyrównanie);
            String liniaDruga = String.format("zjazdy: %d", ilośćZjadów);

            listaDanych.add(liniaPierwsza);
            listaDanych.add(liniaDruga);

            generatorMapek.dodajKrawedz(listaWęzłów.indexOf(węzełPoczątkowy),
                    listaWęzłów.indexOf(węzełKońcowy), styl, listaDanych);
        }
    }

    private void zbierzStatystykiWyciągów(Węzeł węzełPoczątkowy) {
        List<Wyciąg> listaWyciągów = węzełPoczątkowy.getListaWyciągów();

        for(Wyciąg pobranyWyciąg: listaWyciągów) {
            Węzeł węzełKońcowy = pobranyWyciąg.getKońcowaStacja();
            StylKrawedzi styl = new StylKrawedzi(StylLinii.PRZERYWANA);
            int średniaDługość = (int) Math.round(pobranyWyciąg.getŚredniaDługośćKolejki(CZAS_ZAKOŃCZENIA));
            int maksymalnaDługość = pobranyWyciąg.getMaksymalnaDługośćKolejki();
            int ileOsóbJechało = pobranyWyciąg.getIleOsóbJechało();
            int ileOsóbMogłoJechać = pobranyWyciąg.getIleSiedzeń() * pobranyWyciąg.getIleKursów();
            int procentZajętości = pobranyWyciąg.podajProcentZajętości();

            List<String> listaDanych = new ArrayList<>();
            String liniaPierwsza = String.format("w%d: kol: %d(śr), %d(maks)",
                    pobranyWyciąg.getId(), średniaDługość, maksymalnaDługość);
            String liniaDruga = String.format("wjazdy: %d / %d (%d%%)",
                    ileOsóbJechało, ileOsóbMogłoJechać, procentZajętości);

            listaDanych.add(liniaPierwsza);
            listaDanych.add(liniaDruga);

            generatorMapek.dodajKrawedz(listaWęzłów.indexOf(węzełPoczątkowy),
                    listaWęzłów.indexOf(węzełKońcowy),
                    styl,
                    listaDanych);
        }
    }

    private void generujMapęSportowca(Sportowiec sportowiec) throws WyjatekSystemuPlikow {
        generatorMapek.zeruj();
        this.narysujWęzły();

        Map<KrawędźGrafu, List<Integer>> przejazdy = sportowiec.getHistoriaPrzejazdów();

        /* Należy iterować po wszystkich węzłach i krawędziach z ośrodka. */
        for (Węzeł węzeł : listaWęzłów) {
            int indeksStacjiPoczątkowej = listaWęzłów.indexOf(węzeł);

            for (Trasa trasa : węzeł.getListaTras()) {
                narysujKrawędźNaMapie(indeksStacjiPoczątkowej, trasa, przejazdy);
            }

            for (Wyciąg wyciąg : węzeł.getListaWyciągów()) {
                narysujKrawędźNaMapie(indeksStacjiPoczątkowej, wyciąg, przejazdy);
            }
        }
        generatorMapek.tworzMapke("HistoriaSportowcaNr" + sportowiec.getId() + ".tex");
    }

    private void narysujKrawędźNaMapie(int indeksStacjiPoczątkowej, KrawędźGrafu krawędź,
                                       Map<KrawędźGrafu, List<Integer>> przejazdy) {
        int indeksStacjiKońcowej = listaWęzłów.indexOf(krawędź.getKońcowaStacja());

        StylKrawedzi styl = krawędź.getStylKrawędzi();
        String typKrawędzi = krawędź.getOznaczenieTypu();

        List<Integer> numeryPrzejazdów = przejazdy.getOrDefault(krawędź, new ArrayList<>());
        int ilośćPrzejazdów = numeryPrzejazdów.size();

        StringBuilder ciągPrzejazdów = new StringBuilder();
        int wczytane = 0;
        for (Integer numer : numeryPrzejazdów) {
            ciągPrzejazdów.append(numer);
            wczytane++;
            if (wczytane < ilośćPrzejazdów) {
                ciągPrzejazdów.append(",");
            }
        }

        String liniaDanych = String.format("%s%d(%d): %s",
                typKrawędzi, krawędź.getId(), ilośćPrzejazdów, ciągPrzejazdów);

        generatorMapek.dodajKrawedz(indeksStacjiPoczątkowej, indeksStacjiKońcowej,
                styl, liniaDanych);
    }
}



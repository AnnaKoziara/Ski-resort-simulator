# Projekt: symulator ośrodka narciarskiego

Projekt składa się z dwóch niezależnych części. Każda ma własne źródła Java i może zawierać klasy o tych samych nazwach, dlatego należy kompilować je osobno.

## Układ projektu

```text
czesc-1/
  narzedzia/
  sportowcy/
  stok/
  symulacja/
  zdarzenia/
  README.md                 Opis, format danych i instrukcja uruchomienia
czesc-2/
  kadra/mapki/              Generowanie map
  narzedzia/                Narzędzia pomocnicze
  sportowcy/                Modele i strategie sportowców
  stok/                     Trasy, wyciągi i graf stoku
  symulacja/                Uruchomienie i przebieg symulacji
  zdarzenia/                Zdarzenia symulacji
  testy/                    Testy jednostkowe
```

## Część 1

Pierwsza część to symulator ruchu narciarzy, uwzględniający m.in. wybór tras i wyciągów, kolejki, czasy przejazdu oraz zmienną atrakcyjność tras. Szczegółowy opis, wymagania, format danych i polecenia uruchomienia znajdują się w [README części 1](./czesc-1/README.md).

Kod części 1 pochodzi z repozytorium [AnnaKoziara/Ski-resort-simulator](https://github.com/AnnaKoziara/Ski-resort-simulator).

## Część 2

Druga część obejmuje symulację z różnymi strategiami sportowców, obsługę stoku oraz generowanie map. Testy znajdują się w `czesc-2/testy/`.

Aby skompilować źródła produkcyjne w PowerShellu:

```powershell
Set-Location .\czesc-2
New-Item -ItemType Directory -Force out | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java |
    Where-Object { $_.FullName -notmatch '\\testy\\' } |
    ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sources
```

## Kompilowanie

Kompiluj każdą część z osobna, z jej katalogu głównego. Nie kompiluj jednocześnie plików z `czesc-1/` i `czesc-2/`, ponieważ części mają nakładające się nazwy pakietów i klas.

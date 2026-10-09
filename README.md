# Ski Resort Simulator

A **Java** discrete-event simulation of skier traffic at a ski resort. The simulator models lift queues, slope degradation, route-selection strategies, and produces end-of-day statistics. A LaTeX map generator visualizes the resort graph and individual skier routes.

The project is split into two independent parts that share the same domain but differ in complexity:

| | Part 1 (`czesc-1`) | Part 2 (`czesc-2`) |
|---|---|---|
| **Skier model** | Single type - spontaneous or max-attractiveness | 3 strategy subclasses + boredom mechanics |
| **Event queue** | Array-based (linear insertion) | Priority queue (heap) |
| **Graph search** | - | BFS/DFS for lookahead strategies |
| **Map generation** | - | LaTeX/TikZ per-skier route maps |
| **Tests** | - | JUnit 5 |

## How the Simulation Works

The resort is modeled as a **directed graph**:

- **Nodes** (`Węzeł`)  stations with elevation and (x, y) coordinates where skiers make decisions
- **Slopes** (`Trasa`) - downhill edges with difficulty, travel time, and degrading surface
- **Lifts** (`Wyciąg`) - uphill edges with periodic departures, capacity, and FIFO queues

### Simulation Timeline

| Time | Event |
|---|---|
| **09:00** | Simulation starts, first skiers arrive |
| **15:00** | Skiers stop making new route decisions |
| **16:00** | Lifts stop operating |

Rides started before 15:00 may finish after the cutoff. The simulation ends when all events have been processed.

### Skier Strategies (Part 2)

All strategies first check the skier's **spontaneity** factor - with that probability, a random edge is chosen. Otherwise:

| Strategy | Class | Behavior |
|---|---|---|
| **Greedy** | `SportowiecZachłanny` | Picks the edge with the **highest attractiveness** at the current node |
| **Local** | `SportowiecLokalny` | Uses graph search to evaluate routes up to a **configurable depth** ahead |
| **Collector** | `SportowiecKolekcjoner` | Prioritizes **unvisited slopes**; uses graph search to find paths to them. Falls back to greedy when all slopes have been ridden |

Each strategy has **boredom mechanics** (`ParametryZnudzenia`): after visiting the same edge too many times, the skier may switch to random selection.

## Input Format

Input is read from **stdin**. Sections are separated by blank lines, in this order:

### 1. Nodes

```text
number_of_nodes
elevation x y [s]
...
```

The optional `s` marks a node as connected (stored but does not affect routing).

### 2. Lifts

```text
number_of_lifts
start_node end_node departure_interval max_group_size travel_time
...
```

Times are in seconds. Node IDs are zero-based.

### 3. Slopes

```text
number_of_slopes
start_node end_node difficulty travel_time base_attractiveness resilience
...
```

`base_attractiveness` and `resilience` are decimal numbers (e.g. `0.8`, `0.95`).

### 4. Skier Groups

Each group is described by three lines:

```text
group_size skill_level spontaneity [s]
difficulty_weight surface_weight
starting_node HH:MM:SS [arrival_interval]
```

- `[s]` - enables activity logging for this group
- `spontaneity` - probability of choosing a random edge (0.0–1.0)
- `arrival_interval` - seconds between successive skiers in the group (default: 0)

### Example Input

```text
2
1000 0 0
2000 1 0

1
0 1 300 4 600

1
1 0 2 300 0.8 0.95

1
3 3 0.1 s
0.7 0.3
0 09:00:00 60
```

This defines: 2 nodes (bottom at 1000m, top at 2000m), 1 lift (every 5 min, capacity 4, 10 min ride), 1 slope (difficulty 2, 5 min descent, degrades slowly), and a group of 3 skiers arriving 60s apart.

## Output

### Activity Messages

For skiers marked with `s`, timestamped messages are printed for each event:

```
HH:MM:SS Sportowiec X - [action description]
```

### End-of-Day Statistics

**Slopes:**
- Total number of rides
- Surface condition ("wyrównanie") at end of day

**Lifts:**
- Maximum queue length
- Average queue length
- Total passengers transported
- Seat occupancy percentage

### LaTeX Maps (Part 2 only)

Part 2 generates `.tex` files with TikZ graphs:
- **General resort map** - all nodes, slopes, and lifts
- **Per-skier route maps** - highlighting each skier's traversed edges

## Build

### Requirements

- **JDK 17+** - uses `Random.nextDouble(origin, bound)` and other modern APIs
-Compile each part separately, they share class names across packages.

## Project Structure

```
.
├── czesc-1/                          Part 1 - basic simulation
│   ├── narzedzia/                      Utilities (random choice, activity messages)
│   ├── sportowcy/                      Skier model and route selection
│   │   └── Sportowiec.java
│   ├── stok/                           Resort graph model
│   │   ├── KalkulatorAtrakcyjności.java  Attractiveness formula
│   │   ├── KolejkaSportowców.java        FIFO lift queue
│   │   ├── KrawędźGrafu.java             Abstract graph edge
│   │   ├── Trasa.java                    Slope (with degradation)
│   │   ├── Wyciąg.java                   Lift (with statistics)
│   │   └── Węzeł.java                    Graph node
│   ├── symulacja/                      Entry point & simulation runner
│   │   ├── Main.java
│   │   ├── Symulacja.java
│   │   └── CzytnikDanychStoku.java       Input parser
│   └── zdarzenia/                      Event system (array-based queue)
│       ├── Zdarzenie.java                Abstract event
│       ├── TablicaZdarzeń.java           Event array
│       ├── KoniecWjazdu.java             Lift ride end
│       ├── KoniecZjazdu.java             Slope ride end
│       ├── KursWyciągu.java              Lift departure
│       └── PrzybycieDo Węzła.java        Arrival at node
│
├── czesc-2/                          Part 2 - strategies + map generation
│   ├── kadra/mapki/                    LaTeX map generator
│   │   ├── GeneratorMapek.java           Map orchestrator
│   │   ├── graf/                         Graph primitives (points, edges)
│   │   ├── pliki/                        File I/O with safety limits
│   │   ├── rysowanie/                    TikZ code generation & edge bending
│   │   ├── styl/                         Visual styles (line, node, contour)
│   │   └── tekst/                        Character inspection & typesetting
│   ├── narzedzia/                      Shared utilities
│   ├── sportowcy/                      Skier strategies
│   │   ├── SportowiecZeStrategią.java    Abstract strategy base
│   │   ├── SportowiecZachłanny.java      Greedy - best local edge
│   │   ├── SportowiecLokalny.java        Local - lookahead search
│   │   ├── SportowiecKolekcjoner.java    Collector - visit all slopes
│   │   └── ParametryZnudzenia.java       Boredom configuration
│   ├── stok/                           Extended resort model
│   │   ├── PrzeszukiwaczGrafu.java       BFS/DFS graph search
│   │   └── WynikPrzeszukiwania.java      Search result container
│   ├── symulacja/                      Entry point & simulation runner
│   │   ├── Main.java                     Accepts map output path as arg
│   │   ├── Symulacja.java
│   │   └── ProjektantStoku.java          Map generation orchestrator
│   ├── zdarzenia/                      Event system (priority queue)
│   │   └── KolejkaZdarzeń.java           Heap-based event queue
│   └── testy/                          JUnit 5 tests
│       └── stok/
│           ├── PrzeszukiwaczGrafuTest.java
│           └── WyciągTest.java
│
├── .gitignore
└── README.md
```

## Notes

- Random choices are **not seeded**, results may differ between runs
- Input must contain valid numbers and existing node IDs
- Lift departure intervals must be positive
- The program does not provide comprehensive input validation

## Author

**Anna Koziara**  
ak479522@students.mimuw.edu.pl

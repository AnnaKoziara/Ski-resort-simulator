# Ski Resort Simulator

The project consists of two independent parts. Each has its own Java sources and may contain classes with the same names, so they must be compiled separately.

## Project layout

```text
czesc-1/          Part 1 – skier traffic simulation
  narzedzia/        Random choice utilities and activity messages
  sportowcy/        Skier model and route-selection logic
  stok/             Nodes, slopes, lifts, skier queue, attractiveness calculator
  symulacja/        Main entry point, simulation class, and input parsing
  zdarzenia/        Simulation events and the chronological event queue
czesc-2/          Part 2 – simulation with skier strategies and map generation
  kadra/mapki/      LaTeX map generation (graph rendering, styling, text)
  narzedzia/        Shared utilities
  sportowcy/        Skier models and strategies (collector, local, greedy)
  stok/             Slopes, lifts, and graph search
  symulacja/        Main entry point and simulation runner
  zdarzenia/        Simulation events
  testy/            Unit tests (JUnit 5)
```

---

## Part 1 – Skier Traffic Simulation

A Java project simulating skier traffic at a ski resort, including slope and lift selection, lift queues, travel times, and changes in slope attractiveness as slopes are used.

### How the simulation works

The resort is represented as a directed graph:

- **Nodes** represent stations and locations where skiers decide where to go next.
- **Slopes** and **lifts** are edges connecting a starting node to an ending node.
- Skiers arrive at nodes according to a schedule and choose a slope or a lift.
- Lifts run periodically. Skiers wait in FIFO queues, and each departure carries up to the lift's capacity.
- After completing a ride, a skier arrives at the ending node and can choose another edge.

Each skier makes a spontaneous choice with a probability determined by their spontaneity factor. Otherwise, they choose the option with the highest calculated attractiveness. When evaluating a lift, the skier considers the attractiveness of the slopes available at its upper station.

Slope attractiveness takes into account how well its difficulty matches the skier's skill level and the condition of its surface:

```text
attractiveness =
    difficulty_weight × skill_match
  + surface_weight × surface_attractiveness
```

Surface attractiveness is calculated from the slope's base attractiveness, resilience, and the number of rides so far. Random choices are not seeded, so results may differ between runs.

### Simulation hours

- The simulation starts at **09:00:00**.
- Skiers stop making new decisions at **15:00:00**.
- Lifts stop operating at **16:00:00**.

Rides that started earlier may finish after 15:00. Skiers arriving at a node at or after 15:00 do not start another ride.

### Input format

Provide the data in this order: nodes, lifts, slopes, and skier groups. Separate the node, lift, and slope sections with a blank line. Separate fields within a line with spaces. Node IDs are zero-based indices.

#### Nodes

```text
number_of_nodes
elevation x y [s]
```

- `elevation`, `x`, and `y` are integers.
- The optional `s` marks a node as connected. This value is currently stored but does not affect route selection.

#### Lifts

```text
number_of_lifts
start_node end_node departure_interval max_group_size travel_time
```

All parameters other than node IDs are integers. Departure intervals and travel times are measured in seconds.

#### Slopes

```text
number_of_slopes
start_node end_node difficulty travel_time base_attractiveness resilience
```

- `difficulty` and `travel_time` are integers; travel time is measured in seconds.
- `base_attractiveness` and `resilience` are decimal numbers written with a period.

#### Skier groups

Each group is described by three lines:

```text
group_size skill_level spontaneity [s]
difficulty_weight surface_weight
starting_node HH:MM:SS [arrival_interval]
```

- The optional `s` enables activity messages for skiers in the group.
- `spontaneity` is the probability of choosing an edge at random.
- The weights determine the influence of skill matching and surface attractiveness.
- `arrival_interval` is optional and measured in seconds. It specifies the delay between successive skiers in the group; the default is `0`.

#### Example input

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

### Output

For skiers marked with `s`, the program prints messages for each event with timestamps in `HH:MM:SS` format. When the simulation finishes, it prints the total number of rides on each slope and lift.

### Compile and run (Part 1)

In PowerShell, from the repository root:

```powershell
Set-Location .\czesc-1
New-Item -ItemType Directory -Force out | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sources
java -cp out symulacja.Main < data.txt
```

The program reads input from standard input. The command above supplies it from `data.txt`.

---

## Part 2 – Simulation with Skier Strategies and Map Generation

The second part extends the resort simulation with multiple skier strategies (collector, local, greedy), slope graph search, and LaTeX map generation. Unit tests are located in `czesc-2/testy/`.

### Compile (Part 2)

```powershell
Set-Location .\czesc-2
New-Item -ItemType Directory -Force out | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java |
    Where-Object { $_.FullName -notmatch '\\testy\\' } |
    ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sources
```

---

## Requirements

- **JDK 17 or later** — the project uses, among other features, `Random.nextDouble(origin, bound)`.
- Source files are encoded in UTF-8.

## Notes

- Compile each part separately from its own directory. Do not compile files from `czesc-1/` and `czesc-2/` together, because the parts have overlapping package and class names.
- Input data should contain valid numbers, IDs of existing nodes, and positive lift departure intervals. The program does not provide comprehensive input validation.

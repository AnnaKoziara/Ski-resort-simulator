# Ski Resort Simulator

A Java project simulating skier traffic at a ski resort, including slope and lift selection, lift queues, travel times, and changes in slope attractiveness as slopes are used.

## How the simulation works

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

## Simulation hours

- The simulation starts at **09:00:00**.
- Skiers stop making new decisions at **15:00:00**.
- Lifts stop operating at **16:00:00**.

Rides that started earlier may finish after 15:00. Skiers arriving at a node at or after 15:00 do not start another ride.

## Project structure

| Directory | Contents |
| --- | --- |
| `symulacja/` | The `Main` entry point, the main simulation class, and input parsing. |
| `sportowcy/` | The skier model and route-selection logic. |
| `stok/` | Nodes, slopes, lifts, the skier queue, and the attractiveness calculator. |
| `zdarzenia/` | Simulation events and the chronological event queue. |
| `narzedzia/` | Random choice utilities and activity messages for tracked skiers. |

## Requirements

- **JDK 17 or later** — the project uses, among other features, `Random.nextDouble(origin, bound)`.
- Source files are encoded in UTF-8.

## Compile and run

In PowerShell, from the project root:

```powershell
New-Item -ItemType Directory -Force out
$sources = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sources
java -cp out symulacja.Main < data.txt
```

The program reads input from standard input. The command above supplies it from `data.txt`.

## Input format

Provide the data in this order: nodes, lifts, slopes, and skier groups. Separate the node, lift, and slope sections with a blank line. Separate fields within a line with spaces. Node IDs are zero-based indices.

### Nodes

```text
number_of_nodes
elevation x y [s]
```

- `elevation`, `x`, and `y` are integers.
- The optional `s` marks a node as connected. This value is currently stored but does not affect route selection.

### Lifts

```text
number_of_lifts
start_node end_node departure_interval max_group_size travel_time
```

All parameters other than node IDs are integers. Departure intervals and travel times are measured in seconds.

### Slopes

```text
number_of_slopes
start_node end_node difficulty travel_time base_attractiveness resilience
```

- `difficulty` and `travel_time` are integers; travel time is measured in seconds.
- `base_attractiveness` and `resilience` are decimal numbers written with a period.

### Skier groups

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

### Example input

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

## Output

For skiers marked with `s`, the program prints messages for each event with timestamps in `HH:MM:SS` format. When the simulation finishes, it prints the total number of rides on each slope and lift.

## Notes

Input data should contain valid numbers, IDs of existing nodes, and positive lift departure intervals. The program does not provide comprehensive input validation.

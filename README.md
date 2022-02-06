# Surveyor GA
Java implementation of a genetic algorithm that finds optimal paths for the vehicle
routing and travelling salesman problems.

## Usage

**Requirements**: Java 1.8+ locally installed.

1. Clone or download the repository.
2. Modify parameters in [Main.java](src/io/thebitspud/vrp/ui/Main.java) as desired.
3. Run [Main.java](src/io/thebitspud/vrp/ui/Main.java) using an IDE or CLI.

I will most likely add an executable applet at some point, but for now the program
must be run via command line.

## Example Progression

This is an example of what the best route will look like as the genetic algorithm
iteratively optimizes its solutions.


| ![Part 1](assets/n150p2_part1.png) | ![Part 2](assets/n150p2_part2.png) |
|------------------------------------|------------------------------------|
| ![Part 3](assets/n150p2_part3.png) | ![Part 4](assets/n150p2_part4.png) |

## More Examples

Here are some additional examples of vehicle routing problems solved by the algorithm
(along with the approximate number of generations it took to settle on an optimal solution).

| ![100 Nodes 1 Agent](assets/n100p1.png)  | ![128 Nodes 4 Agents](assets/n128p4.png)   |
|------------------------------------------|--------------------------------------------|
| ![250 Nodes 2 Agents](assets/n250p2.png) | ![300 Nodes 3 Agents](assets/n300p3.png)   |
| ![500 Nodes 1 Agent](assets/n500p1.png)  | ![500 Nodes 10 Agents](assets/n500p10.png) |

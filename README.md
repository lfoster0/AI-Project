# AI Project
Project for CPSC 371 Artificial Intelligence at UNBC

### Description
Goal of the project was to train a Neural Network to solve XOR and then to solve
an 8 tiled sliding puzzle. This repos is Phase 3 of the project where a Genetic
Algorithm is used to train the Neural Network. In the previous phase the Neural
Network was trained using Stochastic Back Propagation.

### Genetic Algorithms
Genetic algorithms are used to optimize problems with multiple parameters. They optimize by evolving a population of potential solutions over many generations changing different parameters until an optimal solution is found. Members of the population are called genomes and contain a list of genes which are the values to be optimized. The performance of these genomes are compared and ranked against a fitness function that evaluates their performance. Based off how well genomes perform in the fitness function they may be selected to be elite members of the population. Elite members of the population carry over to the next generation and are used to build the remaining population through mutation and crossover.

<img src="http://i.imgur.com/AhuxNti.jpg?1" width=300>

The percentage of the population that becomes elite is an experimental parameter, as well as the percentage of elite to mutate and the percentage to do crossover with. The percentage to mutate and do crossover on is on the leftover population that are not elite. The example below shows
how many elites, mutated genomes and crossover genomes would result from evolving a population of 100 choosing 20% elite, 70% mutation and 30% crossover.

<img src="http://i.imgur.com/9mttxJ0.jpg?1" width=400>

For our problem of training a Neural Network with a genetic algorithm we use the edges as the values for the genomes. In the XOR problem there are 9 edges, so the genome has 9 values. For Eight Puzzle we are using a network layout of 10 hidden nodes so there will be 414 edges, therefore 414 values in the genome. The fitness function for XOR is mean squared error across all 4 training tuples. The Eight Puzzle uses a relaxed error function where the max value of the output is the interpreted move. If the first output node has the max value then the move is UP, the second output node DOWN, the third LEFT, and the fourth RIGHT.

#### Choosing Elite

The process of choosing elite is more complicated than simply choosing the ones with the best fitness. The first elite is chosen at random based off the probability of selection based off fitness rank (PSFR). After elites are ranked based off fitness PSFR is calculated by the following formula: PSFR = 0.667 * (1-(Sum of all previous PSFR). The higher the PSFR the more likely it is to be selected as the first elite.

| Genome | Raw Fitness | Fitness Rank | PSFR |
| :----: |:-----------:| :-----------:| -----
| 1   | 0.3     | 1      | 0.667 * (1.0-0) = 0.667
| 2   | 1.5     | 2      | 0.667 * (1.0 -0.667) = .222111
| 3   | 5.6     | 3      | 0.667 * (1.0 - (0.667 + .222111) = 0.07396
| 4   | 14.2    | 4      | 0.667 * (1.0 - (0.667 + .222111 + 0.07396) = 0.024631643
| 5   | 15.8    | 5      | 0.667 * (1.0 - (0.667 + .222111 + 0.07396 + 0.024631643) = 0.008202337119

The PSFR for the whole genome should sum to approximately 1.0. Because of this we can select the first elite by picking a random number between 0.0 and 1.0, where it falls on a number line gives the first genome we choose. The image below illustrates this more clearly.

<img src="http://i.imgur.com/iurCyKW.jpg?1" width=500>

After selecting the first elite, for each remaining genome a Diversity Score is calculated, which is the vector distance between the first elite and a the current genome. The remaining population is then sorted by Diversity Score. Now that each remaining genome is ranked by Diversity Score, Combined Rank is calculated which is the Fitness Rank + Diversity Rank. Finally Probability of Selection Based off Combined Rank is calculated exactly like how PSFR was. The next selected elite is chosen based off this similarly to how the first elite was chosen from PSFR. The chosen elite is removed from the population and Diversity Score is calculated again and the process repeats until we have enough elites.

The diagram below shows the process of choosing elites in detail.

<img src="http://i.imgur.com/6AwtPRV.jpg?1" width=400>

#### Mutation
Mutation involves randomly choosing an elite and randomly changing genes by some amount. Every mutation of a genome must produce another valid genome. The actual genes to mutate is chosen at random, but the percentage of genes in a genome to mutate is an experimental value. The amount that we we mutate a gene by is also an experimental value.
The example below shows mutating a population of three genomes. In the example we are mutating one genome, mutating 50% of the genes by ± 50%.

<img src="http://i.imgur.com/qcvFYSi.jpg?1" width=300>

#### Crossover
Crossover is the process of selecting two elites without replacement and creating two new genomes with elements from both. To select which genes to use a mask the size of the genomes is generated with randomly assigned 1’s and 0’s. For the first created genome a 0 corresponds to taking from the first genome and a 1 means we take from the second genome. The second created child is the inverse of this. The example below illustrates this process.

<img src="http://i.imgur.com/QR7jr5T.jpg?1" width=300>

### Screenshots

<img src="http://i.imgur.com/BmWqDTP.png" width=500>

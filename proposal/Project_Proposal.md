School of Computing

CA326 Year 3 Project Proposal Form

SECTION A
Project Title __Plant_Simulation________________________________________

Student 1 Name __Gergley Gellert_______________   ID Number  17379616__

Student 2 Name __Nevan Oman Crowe__________    ID Number 17407926__

Student 3 Name ______________________________    ID Number ___________ 
 (A third team member is exceptional and requires detailed justification.) 
 
Staff Member Consulted ___Annalina Caputo____________________________

Project Description (1-2 pages):

Description: 
We are planning on making a simulation of plant life and growth and the plants competing in a dynamic environment with the goal of reaching maturity, reproducing with another plant and carrying on their genes to the next generation. The basis of the simulation would be a grid of generated soil and air aboved with randomly allocated nutrients (in clusters) and water. The user would specific the size of the board, the amount of plants in the first generation and the abundance of resources. 

The first generation plants are randomly allocated a position in the soil just below the surface and will begin to grow and compete. We decided to model the plants and their growth on a genetic algorithm based on an Initial population,Fitness Function, Selection,Crossover and Mutation.

The initial population of plants would have several factors to start off it growth such as its Growth Rate which would randomly allocated. It’s Resource Consumption Rate. It’s Age and the amount of time it needs to stay alive to reach maturity. The Maturity Period which would be how long the plant would be able to reproduce and also the plants Aggressiveness which would dictate how likely the plant would fight over resources and block off other plants access.

The plant needs a certain amount of resources to stay alive depending on their current size and they’ll also need a certain amount of resources to continue to grow. Some plants would consume resources at a faster rate but grow faster while others could grow slower but consume less resources.The larger a plant grows the more resources it can attempt to gain access to but also the more resources it consumes. How efficiently the plant would be able to manage and to do this coupled with how much resources the plant has access to would be the basis of the plants fitness function.

Selection is how each of the plants chooses its mate, our algorithm works via two individuals that have reached maturity and are within proximity mating. Should there be multiple choices for mates in an area two mates will be randomly chosen similar to real life plants and should a plant fail to mate in the set maturity time due to lack of mate they will die.


Crossover and the mixture of genes of the plants is one of the most important parts of the algorithm. This is how the plants change and get better over time. Should two plants mate their genes will be mixed, the percentage mix of genes would be based change the % off of the parent plants base, the fitness function and the aggressiveness of the plant.

 As each plant will be making their own seeds they have a higher base % of their genes being passed on when genes are mixed. The fitness of the two plants plants would have an effect on the gene distribution giving the fitter plant a higher distribution. The more aggressive of the two plants would have an effect on how much of its genes will be passed onto the next generation, with more aggressive plants having more dominant genes. The ultimate choice of each gene will be random but the higher percentage a plant has in distribution the more likely it has more of an effect on the next generations genes.

When plants mate there is always a small chance of mutation of genes. So during mating there is a small change that any one of the plants variables will be altered in a positive or negative fashion. This is the mutation chance of the plant and would be determined at the beginning and can be affected by future mating.

We would need to take all of these factors together to design a learning genetic algorithm to simulate plant life and it interacting with others in a dynamic environment. Furthermore we would need to build a UI to actually portray the growth of the plants to the user and see their choices of size of the map, abundance of resources and amount of base plants and  see how those choices are affecting the environment. 


Division of Work: We’ll be approaching this project in a pair programming fashion but we’ll accept individual responsibilities for back end and front end modules of this project.

Programming language(s) - Java, Javascript

Programming tool(s) - Java Compiler, DESMO-J library, React.js library

Learning Challenges -
    Learn how to utilize simulation frameworks such as DESMO-J to create the environment
    Modelling plants within constraints of the genetic algorithm
    Modelling the simulation space accurately and in real time
    Learn how to utilize User Interface frameworks such as Javascript to create a suitable UI
    
Hardware / software platform - OpenSuse Linux, Laptop / Lab PC

Special Hardware/Software Requirements: None
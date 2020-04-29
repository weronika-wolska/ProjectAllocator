TEAM PARTIAL:
Weronika Wolska 17301623
Lucie Konecna 17485964

Genetic Algorithms (Assignment 6)

There are currently errors in the application that will be fixed by the next submission.

Logic behind genetic algorithm:
- Terminating condition: all students and projects are unique, which is ensured in isViable function in the Population class in
  GeneticAlgorithm.java file, and every student gets a project that is in their preference list.
  If the condition is not met after 1000 iterations, the best solution found so far is returned
  
- Deciding which Candidate Solutions to mate: The algorithm picks two random solutions that are in the top 20
   algorithms with the highest fitness (found by getNthFittest() function in the population class)
   
- Mutatation process: generate a random number n between 0 and student size. The first 0-n-1 elements are taken from first
   parent anf the other n - student size elements are taken from the second parent
   
Other minor changes since last submission:

- StaffReader modified to add the read staff members to StaffRepository
- StudentReader and ProjectReader which read files containing students and projects, respectively, and add them
   to their corresponding repositories
- CandidateSolution.calculateFitness() fixed
- Null Pointer Exception issue with repositories fixed

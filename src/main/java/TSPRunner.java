import org.cicirello.permutations.Permutation;
import org.cicirello.search.Configurator;
import org.cicirello.search.SolutionCostPair;
import org.cicirello.search.evo.FitnessProportionalSelection;
import org.cicirello.search.evo.GenerationalEvolutionaryAlgorithm;
import org.cicirello.search.evo.InverseCostFitnessFunction;
import org.cicirello.search.operators.permutations.EnhancedEdgeRecombination;
import org.cicirello.search.operators.permutations.PermutationInitializer;
import org.cicirello.search.operators.permutations.ReversalMutation;
import org.cicirello.search.problems.tsp.RandomTSPMatrix;

public final class TSPRunner {

  private TSPRunner() {}

  public static int[] solveTSP(double[][] distanceMatrix) {
    Configurator.configureRandomGenerator(1112);

    int numCities = distanceMatrix.length;

    RandomTSPMatrix.Double problem = new RandomTSPMatrix.Double(distanceMatrix);

    int populationSize = 100;
    int maxGenerations = 500;
    int numElite = 10;
    double crossoverRate = 0.7;
    double mutationRate = 0.2;

    GenerationalEvolutionaryAlgorithm<Permutation> ea =
        new GenerationalEvolutionaryAlgorithm<>(
            populationSize,
            new ReversalMutation(),
            mutationRate,
            new EnhancedEdgeRecombination(),
            crossoverRate,
            new PermutationInitializer(numCities),
            new InverseCostFitnessFunction<>(problem),
            new FitnessProportionalSelection(),
            numElite);

    SolutionCostPair<Permutation> solution = ea.optimize(maxGenerations);
    return solution.getSolution().toArray();
  }
}

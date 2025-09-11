import java.util.*;
import java.util.function.*;

public class GS<T> {

    private final int pSize; // population size
    private final double mRate; // mutation rate
    private final double crossRate; // crossover rate
    private final int eliteCount; // elitism count
    private final int maxGen; // max generations

    private final Supplier<T> createIndiv; 
    private final Function<T, Double> fitFunc; // fitness function
    private final BiFunction<T, T, T> crossFunc; // crossover function
    private final Function<T, T> mFunc; // mutation function
    private final Comparator<T> fitComp; // fitness comparator

    public GS(int pSize, double mRate, double crossRate, int eliteCount, int maxGen, Supplier<T> createIndiv, Function<T, Double> fitFunc, BiFunction<T, T, T> crossFunc, Function<T, T> mFunc) {
        this.pSize = pSize;
        this.mRate = mRate;
        this.crossRate = crossRate;
        this.eliteCount = eliteCount;
        this.maxGen = maxGen;

        this.createIndiv = createIndiv;
        this.fitFunc = fitFunc;
        this.crossFunc = crossFunc;
        this.mFunc = mFunc;
        this.fitComp = Comparator.comparing(fitFunc).reversed();
    }

    public T geneticSearch() {
        List<T> p = new ArrayList<>();

        for (int i = 0; i < pSize; i++) {
            p.add(createIndiv.get());
        }

        for (int generation = 0; gen < maxGen; gen++) {
            List<T> nextGen = new ArrayList<>();
            p.sort(fitComp);

            for (int i = 0; i < eliteCount; i++) {
                nextGen.add(p.get(i));
            }

            while (nextGen.size() < pSize) {
                T parent1 = select(p);
                T parent2 = select(p);
                T offspring = Math.random() < crossRate ? crossFunc.apply(parent1, parent2) : parent1;

                if (Math.random() < mRate) {
                    offspring = mFunc.apply(offspring);
                }
              
                nextGen.add(offspring);
            }
            p = nextGen;
        }
        return p.stream().max(fitComp).orElseThrow();
    }

    // Tournament selection
    private T select(List<T> p) {
        Random rand = new Random();
        T best = p.get(rand.nextInt(pSize));
        for (int i = 1; i < 3; i++) {
            T challenger = p.get(rand.nextInt(pSize));
            if (fitFunc.apply(challenger) > fitFunc.apply(best)) {
                best = challenger;
            }
        }
        return best;
    }
}

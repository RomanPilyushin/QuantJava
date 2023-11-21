package utils;

import java.util.*;
import java.util.stream.Collector;
import java.util.function.*;

public class MovingAverageCollector implements Collector<Double, Queue<Double>, List<Double>> {
    private final int period;

    public MovingAverageCollector(int period) {
        this.period = period;
    }

    @Override
    public Supplier<Queue<Double>> supplier() {
        return LinkedList::new;
    }

    @Override
    public BiConsumer<Queue<Double>, Double> accumulator() {
        return (queue, value) -> {
            queue.add(value);
            if (queue.size() > period) {
                queue.remove();
            }
        };
    }

    @Override
    public BinaryOperator<Queue<Double>> combiner() {
        return (queue1, queue2) -> {
            queue1.addAll(queue2);
            return queue1;
        };
    }

    @Override
    public Function<Queue<Double>, List<Double>> finisher() {
        return queue -> {
            List<Double> movingAverages = new ArrayList<>();
            for (int i = 0; i < queue.size() - period + 1; i++) {
                movingAverages.add(queue.stream().skip(i).limit(period).mapToDouble(d -> d).average().orElse(Double.NaN));
            }
            return movingAverages;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}

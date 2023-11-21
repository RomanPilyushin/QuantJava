package data;

import utils.MovingAverageCollector;

import java.util.List;
import java.util.stream.Collectors;

public class DataPreprocessor {

    // Example method to handle missing data
    public List<Double> handleMissingData(List<Double> data) {
        // Replace missing data (e.g., nulls) with the average
        double average = data.stream()
                .filter(d -> d != null)
                .mapToDouble(d -> d)
                .average()
                .orElse(Double.NaN);

        return data.stream()
                .map(d -> (d == null) ? average : d)
                .collect(Collectors.toList());
    }

    // Example method for min-max normalization
    public List<Double> normalizeMinMax(List<Double> data) {
        double min = data.stream().min(Double::compare).orElse(Double.NaN);
        double max = data.stream().max(Double::compare).orElse(Double.NaN);

        return data.stream()
                .map(d -> (d - min) / (max - min))
                .collect(Collectors.toList());
    }

    // Example method for simple moving average feature
    public List<Double> calculateSimpleMovingAverage(List<Double> data, int period) {
        return data.stream()
                .sequential()
                .collect(new MovingAverageCollector(period));
    }
}

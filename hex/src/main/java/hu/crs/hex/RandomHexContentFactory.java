package hu.crs.hex;

import java.security.SecureRandom;
import java.util.List;

public class RandomHexContentFactory<T> {
    private static final SecureRandom random = new SecureRandom();

    public T randomHexEntity(List<HexProbability<T>> hexProbabilities) {

        var probabilitySum = hexProbabilities.stream()
                .map(HexProbability::probability)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (probabilitySum != 1) {
            throw new IllegalArgumentException("The sum of probabilities must be 1!");
        }
        return cdfSelector(hexProbabilities, random.nextDouble());
    }

    T cdfSelector(List<HexProbability<T>> hexProbabilities, double randomNumber) {
        var cumulativeProbability = 0d;

        for (var hexProbability : hexProbabilities) {
            cumulativeProbability += hexProbability.probability();

            if (cumulativeProbability > randomNumber) {
                return hexProbability.content();
            }
        }
        throw new IllegalArgumentException("Generated random number doesn't belong to a probability!");
    }
}

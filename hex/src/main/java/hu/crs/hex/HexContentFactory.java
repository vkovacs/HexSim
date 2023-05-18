package hu.crs.hex;

import java.security.SecureRandom;
import java.util.List;

public class HexContentFactory<T> {
    private static final SecureRandom random = new SecureRandom();

    public T randomHexEntity(List<HexProbability<T>> hexProbabilities) {

        var probabilitySum = hexProbabilities.stream()
                .map(HexProbability::probability)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (probabilitySum > 1) {
            throw new IllegalArgumentException("Probability sum must not be greater than 1");
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

        if (cumulativeProbability < randomNumber && randomNumber < 1) {
            return hexProbabilities.get(hexProbabilities.size() - 1).content();
        }

        throw new IllegalArgumentException("Illegal generated random number!");
    }
}

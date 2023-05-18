package hu.crs.hex;

import java.security.SecureRandom;
import java.util.List;

public enum HexEntity {
    EMPTY, FOX, RABBIT, GRASS;

    private static final SecureRandom random = new SecureRandom();

    public static HexEntity randomHexEntity(List<HexProbability> hexProbabilities) {

        var probabilitySum = hexProbabilities.stream()
                .map(HexProbability::probability)
                .mapToDouble(Double::doubleValue)
                .sum();

        if (probabilitySum > 1) {
            throw new IllegalArgumentException("Probability sum must not be greater than 1");
        }
        return cdfSelector(hexProbabilities, random.nextDouble());
    }

    static HexEntity cdfSelector(List<HexProbability> hexProbabilities, double randomNumber) {
        var cumulativeProbability = 0d;

        for (HexProbability hexProbability : hexProbabilities) {
            cumulativeProbability += hexProbability.probability();

            if (cumulativeProbability > randomNumber) {
                return hexProbability.hexEntity();
            }
        }

        return HexEntity.EMPTY;
    }
}

package simulation;

import hex.Hex;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FoxRabbitSimulationTest {

    private final FoxRabbitSimulation foxRabbitSimulation = new FoxRabbitSimulation();

    @Test
    public void shouldThrowExceptionIfProbabilitySumIsLargerThan1() {
        //given
        var hexProbabilities = List.of(new HexProbability(Hex.GRASS, 0.3), new HexProbability(Hex.RABBIT, 0.3), new HexProbability(Hex.FOX, 0.5));

        //when - then
        assertThrows(IllegalArgumentException.class, () -> foxRabbitSimulation.randomHexContent(hexProbabilities), "Probability sum must not be greater than 1");
    }

    @Test
    public void shouldSelect0thFromCDF() {
        //given
        var hexProbabilities = List.of(new HexProbability(Hex.GRASS, 0.3), new HexProbability(Hex.RABBIT, 0.3), new HexProbability(Hex.FOX, 0.4));

        //when
        var hex = foxRabbitSimulation.cdfSelector(hexProbabilities, 0.1);

        //then
        assertThat(hex, is(Hex.GRASS));
    }

    @Test
    public void shouldSelectLastFromCDF() {
        //given
        var hexProbabilities = List.of(new HexProbability(Hex.GRASS, 0.3), new HexProbability(Hex.RABBIT, 0.3), new HexProbability(Hex.FOX, 0.4));

        //when
        var hex = foxRabbitSimulation.cdfSelector(hexProbabilities, 0.6);

        //then
        assertThat(hex, is(Hex.FOX));
    }
    @Test
    public void shouldSelectLastFromCDFBy1RandomNumber() {
        //given
        var hexProbabilities = List.of(new HexProbability(Hex.GRASS, 0.3), new HexProbability(Hex.RABBIT, 0.3), new HexProbability(Hex.FOX, 0.4));

        //when
        var hex = foxRabbitSimulation.cdfSelector(hexProbabilities, 1);

        //then
        assertThat(hex, is(Hex.EMPTY));
    }
}
package hu.crs.hex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexContentFactoryTest {
    private final HexContentFactory<TestHex> underTest = new HexContentFactory<>();

    @Test
    public void shouldThrowExceptionIfProbabilitySumIsLargerThan1() {
        //given
        var hexProbabilities = List.of(new HexProbability<>(TestHex.A, 0.3), new HexProbability<>(TestHex.B, 0.3), new HexProbability<>(TestHex.C, 0.5));

        //when - then
        assertThrows(IllegalArgumentException.class, () -> underTest.randomHexEntity(hexProbabilities), "Probability sum must not be greater than 1");
    }

    @Test
    public void shouldSelect0thFromCDF() {
        //given
        var hexProbabilities = List.of(new HexProbability<>(TestHex.A, 0.3), new HexProbability<>(TestHex.B, 0.3), new HexProbability<>(TestHex.C, 0.4));

        //when
        var hex = underTest.cdfSelector(hexProbabilities, 0.1);

        //then
        assertThat(hex, is(TestHex.A));
    }

    @Test
    public void shouldSelectLastFromCDF() {
        //given
        var hexProbabilities = List.of(new HexProbability<>(TestHex.A, 0.3), new HexProbability<>(TestHex.B, 0.3), new HexProbability<>(TestHex.C, 0.4));

        //when
        var hex = underTest.cdfSelector(hexProbabilities, 0.6);

        //then
        assertThat(hex, is(TestHex.C));
    }

    @Test
    public void shouldSelectLastFromCDFIfCumulativeSumSmallerThanRandomNumber() {
        //given
        var hexProbabilities = List.of(new HexProbability<>(TestHex.A, 0.3), new HexProbability<>(TestHex.B, 0.3), new HexProbability<>(TestHex.C, 0.1));

        //when
        var hex = underTest.cdfSelector(hexProbabilities, 0.6);

        //then
        assertThat(hex, is(TestHex.C));
    }

    @Test
    public void shouldSelectLastFromCDFBy1RandomNumber() {
        //given
        var hexProbabilities = List.of(new HexProbability<>(TestHex.A, 0.3), new HexProbability<>(TestHex.B, 0.3), new HexProbability<>(TestHex.C, 0.4));

        //when
        Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.cdfSelector(hexProbabilities, 1), "Illegal generated random number!");
    }
}
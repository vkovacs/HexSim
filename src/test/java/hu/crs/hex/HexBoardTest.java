package hu.crs.hex;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexBoardTest {
    @Test
    void fillBoardWithEmpty() {
        //given
        var hexBoard = new HexBoard<Hex>(2, 3);

        //when
        hexBoard.fill(Hex.EMPTY);

        //then
        assertThat(hexBoard.stream()
                .filter(e -> e == Hex.EMPTY)
                .count(), is(5L));

        hexBoard.forEach(hex -> assertThat(hex, is(Hex.EMPTY)));
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,-1", "-1,0", "1,3", "2,2", "2,3"})
    void detectOutOfBoundIndexes(int x, int y) {
        //given
        var hexBoard = new HexBoard<Hex>(2, 3);
        hexBoard.fill(Hex.EMPTY);

        //when - then
        assertThrows(IndexOutOfBoundsException.class, () -> hexBoard.get(x, y), "Indexed out of HexBoard");

    }
}
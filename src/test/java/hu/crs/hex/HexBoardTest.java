package hu.crs.hex;


import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
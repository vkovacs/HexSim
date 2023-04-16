package hu.crs.hex;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class HexBoardTest {
    @Test
    void fillBoardWithEmpty() {
        //given
        var hexBoard = new HexBoard<Hex>(2, 3);

        //when
        hexBoard.fill(Hex.EMPTY);

        //then
        hexBoard.forEach( hex -> assertThat(hex, CoreMatchers.is(Hex.EMPTY)));
    }
}
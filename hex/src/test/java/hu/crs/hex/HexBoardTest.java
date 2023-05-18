package hu.crs.hex;


import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexBoardTest {
    @Test
    void fillBoardWithEmpty() {
        //given - when
        var hexBoard = new HexBoard<>(2, 3, TestHex.EMPTY);

        //then
        assertThat(hexBoard.stream()
                .filter(e -> e == TestHex.EMPTY)
                .count(), is(5L));

        hexBoard.forEach(hex -> MatcherAssert.assertThat(hex, is(TestHex.EMPTY)));
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,-1", "-1,0", "1,3", "2,2", "2,3"})
    void detectOutOfBoundIndexes(int x, int y) {
        //given
        var hexBoard = new HexBoard<>(2, 3, TestHex.EMPTY);

        //when - then
        assertThrows(IndexOutOfBoundsException.class, () -> hexBoard.getHex(x, y), "Indexed out of HexBoard");
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1,1", "3,4,11", "4,4,14"})
    void calculateSize(int rowCount, int colCount, int expectedSize) {
        //given
        var hexBoard = new HexBoard<>(rowCount, colCount, TestHex.EMPTY);

        //when - then
        assertThat(hexBoard.size(), is(expectedSize));
    }

    @Test
    void calculateNeighbours00() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(0, 0));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 1), new Coordinate(1, 0)));
    }

    @Test
    void calculateNeighbours01() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(0, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 2), new Coordinate(1, 1), new Coordinate(1, 0), new Coordinate(0, 0)));
    }

    @Test
    void calculateNeighbours02() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(0, 2));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 1), new Coordinate(0, 1)));
    }

    @Test
    void calculateNeighbours10() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(1, 0));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(2, 0), new Coordinate(0, 0)));
    }

    @Test
    void calculateNeighbours11() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(1, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(1, 0), new Coordinate(0, 1)));
    }

    @Test
    void calculateNeighbours22() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(2, 2));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 1), new Coordinate(2, 1)));
    }

    @Test
    void calculateNeighbours21() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(2, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 1), new Coordinate(2, 2), new Coordinate(2, 0), new Coordinate(1, 0)));
    }

    @Test
    void calculateNeighbours20() {
        //given
        var hexBoard = new HexBoard<>(3, 3, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(2, 0));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 0), new Coordinate(2, 1)));
    }

    @Test
    void calculateNeighbours11In4x4Board() {
        //given
        var hexBoard = new HexBoard<>(4, 4, TestHex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(1, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 2), new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(1, 0), new Coordinate(0, 1)));
    }

    @ParameterizedTest
    @CsvSource(value = {"0,0", "0,1", "1,0"})
    void getBoardElement(int x, int y) {
        //given
        var hexBoard = new HexBoard<>(2, 2, TestHex.EMPTY);

        //when
        var hex = hexBoard.getHex(x, y);

        //then
        assertThat(hex, is(TestHex.EMPTY));
    }

    @Test
    void setBoardElement() {
        //given
        var hexBoard = new HexBoard<>(2, 2, TestHex.EMPTY);

        //when
        hexBoard.setHex(1, 0, TestHex.A);

        //then
        assertThat(hexBoard.getHex(0, 0), is(TestHex.EMPTY));
        assertThat(hexBoard.getHex(0, 1), is(TestHex.EMPTY));
        assertThat(hexBoard.getHex(1, 0), is(TestHex.A));
    }
}
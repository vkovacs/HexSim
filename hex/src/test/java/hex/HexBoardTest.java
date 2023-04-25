package hex;


import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexBoardTest {
    @Test
    void fillBoardWithEmpty() {
        //given - when
        var hexBoard = new HexBoard<>(2, 3, Hex.EMPTY);

        //then
        assertThat(hexBoard.stream()
                .filter(e -> e == Hex.EMPTY)
                .count(), CoreMatchers.is(5L));

        hexBoard.forEach(hex -> MatcherAssert.assertThat(hex, CoreMatchers.is(Hex.EMPTY)));
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,-1", "-1,0", "1,3", "2,2", "2,3"})
    void detectOutOfBoundIndexes(int x, int y) {
        //given
        var hexBoard = new HexBoard<>(2, 3, Hex.EMPTY);

        //when - then
        assertThrows(IndexOutOfBoundsException.class, () -> hexBoard.get(x, y), "Indexed out of HexBoard");
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1,1", "3,4,11", "4,4,14"})
    void calculateSize(int rowCount, int colCount, int expectedSize) {
        //given
        var hexBoard = new HexBoard<>(rowCount, colCount, Hex.EMPTY);

        //when - then
        assertThat(hexBoard.size(), CoreMatchers.is(expectedSize));
    }

    @Test
    void calculateNeighbours00() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(0, 0));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 1), new Coordinate(1, 0)));
    }

    @Test
    void calculateNeighbours01() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(0, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 2), new Coordinate(1, 1), new Coordinate(1, 0), new Coordinate(0, 0)));
    }

    @Test
    void calculateNeighbours02() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(0, 2));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 1), new Coordinate(0, 1)));
    }

    @Test
    void calculateNeighbours10() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(1, 0));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(2, 0), new Coordinate(0, 0)));
    }

    @Test
    void calculateNeighbours11() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(1, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 2), new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(1, 0), new Coordinate(0, 1)));
    }

    @Test
    void calculateNeighbours22() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(2, 2));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 1), new Coordinate(2, 1)));
    }

    @Test
    void calculateNeighbours21() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(2, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 1), new Coordinate(2, 2), new Coordinate(2, 0), new Coordinate(1, 0)));
    }

    @Test
    void calculateNeighbours20() {
        //given
        var hexBoard = new HexBoard<>(3, 3, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(2, 0));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(1, 0), new Coordinate(2, 1)));
    }

    @Test
    void calculateNeighbours11In4x4Board() {
        //given
        var hexBoard = new HexBoard<>(4, 4, Hex.EMPTY);

        //when
        var neighbours = hexBoard.neighbours(new Coordinate(1, 1));

        //then
        assertThat(neighbours, containsInAnyOrder(new Coordinate(0, 2), new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(1, 0), new Coordinate(0, 1)));
    }
}
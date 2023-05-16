package hex;

import lombok.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Value
public class HexBoard<T> implements Iterable<T> {
    int rowCount;
    int colCount;
    List<List<T>> board = new ArrayList<>();

    public HexBoard(int rowCount, int colCount, T hex) {
        if (rowCount < 1 || colCount < 1)
            throw new IllegalArgumentException("RowCount and ColCount must be larger than 0!");
        this.rowCount = rowCount;
        this.colCount = colCount;
        fill(hex);
    }

    public T get(int x, int y) {
        if (x < 0 || y < 0 || x >= rowCount || y >= colCount)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard");
        if (x % 2 == 1 && y == colCount - 1)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard, that row does not contain that Hex");

        return board.get(x).get(y);
    }

    public void set(int x, int y, T t) {
        if (x < 0 || y < 0 || x >= rowCount || y >= colCount)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard");
        if (x % 2 == 1 && y == colCount - 1)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard, that row does not contain that Hex");

        board.get(x).set(y, t);
    }

    public boolean isLastColInRow(int row, int col) {
        return col == maxColInRow(row);
    }
    private int maxColInRow(int row) {
        if (row % 2 == 0) return colCount;
        return colCount - 1;
    }

    private int maxColIndexInRow(int row) {
        return maxColInRow(row) - 1;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public Iterator<T> iterator() {
        return new HexBoardIterator();
    }

    private void fill(T hex) {
        for (int i = 0; i < rowCount; i++) {
            List<T> row = new ArrayList<>();
            int maxJ = maxColInRow(i);

            for (int j = 0; j < maxJ; j++) {
                row.add(hex);
            }
            board.add(row);
        }
    }

    public int size() {
        int shortRowsCount = rowCount / 2;
        int longRowsCount = rowCount - shortRowsCount;
        return longRowsCount * colCount + shortRowsCount * (colCount - 1);
    }

    public Set<Coordinate> neighbours(Coordinate centerCoordinate) {
        var directions = List.of(Direction.NW, Direction.NE, Direction.E, Direction.SE, Direction.SW, Direction.W);

        Map<Parity, Map<Direction, Offset>> offsetMap = Map.of(
                Parity.ODD, Map.of(
                        Direction.NW, new Offset(-1, 0),
                        Direction.NE, new Offset(-1, 1),
                        Direction.E, new Offset(0, 1),
                        Direction.SE, new Offset(1, 1),
                        Direction.SW, new Offset(1, 0),
                        Direction.W, new Offset(0, -1)
                ),
                Parity.EVEN, Map.of(
                        Direction.NW, new Offset(-1, -1),
                        Direction.NE, new Offset(-1, 0),
                        Direction.E, new Offset(0, 1),
                        Direction.SE, new Offset(1, 0),
                        Direction.SW, new Offset(1, -1),
                        Direction.W, new Offset(0, -1)
                )
        );

        return directions.stream()
                .map(direction -> {
                            var parity = centerCoordinate.row() % 2 == 0 ? Parity.EVEN : Parity.ODD;
                            var offset = offsetMap.get(parity).get(direction);
                            return new Coordinate(centerCoordinate.row() + offset.row(), centerCoordinate.col() + offset.col());
                        }
                )
                .filter(possibleNeighbourCoordinate -> possibleNeighbourCoordinate.row() >= 0 && possibleNeighbourCoordinate.row() < rowCount && possibleNeighbourCoordinate.col() >= 0 && possibleNeighbourCoordinate.col() < maxColInRow(possibleNeighbourCoordinate.row()))
                .collect(Collectors.toSet());
    }

    public List<List<T>> getBoard() { //FIXME: use lombok
        return board;
    }

    private class HexBoardIterator implements Iterator<T> {

        int i = 0;
        int j = 0;

        @Override
        public boolean hasNext() {
            return i < rowCount;
        }

        @Override
        public T next() {
            T actual = board.get(i).get(j);

            if (j == maxColIndexInRow(i)) {
                j = 0;
                i++;
            } else {
                j++;
            }

            return actual;
        }
    }
}

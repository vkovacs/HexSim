package hex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public HexBoard(int rowCount, int colCount) {
        if (rowCount < 1 || colCount < 1)
            throw new IllegalArgumentException("RowCount and ColCount must be larger than 0!");
        this.rowCount = rowCount;
        this.colCount = colCount;

    }

    public T getHex(int row, int col) {
        if (row < 0 || col < 0 || row >= rowCount || col >= colCount)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard");
        if (row % 2 == 1 && col == colCount - 1)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard, that row does not contain that Hex");

        return board.get(row).get(col);
    }

    public void setHex(int row, int col, T t) {
        if (row < 0 || col < 0 || row >= rowCount || col >= colCount)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard");
        if (row % 2 == 1 && col == colCount - 1)
            throw new IndexOutOfBoundsException("Indexed out of HexBoard, that row does not contain that Hex");

        board.get(row).set(col, t);
    }

    public int maxColInRow(int row) {
        if (row % 2 == 0) return colCount;
        return colCount - 1;
    }

    public int maxColIndexInRow(int row) {
        return maxColInRow(row) - 1;
    }

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public Iterator<T> iterator() {
        return new HexBoardIterator();
    }

    protected void fill(T hex) {
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

    protected class HexBoardIterator implements Iterator<T> {

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

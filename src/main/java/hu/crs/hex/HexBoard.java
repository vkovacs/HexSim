package hu.crs.hex;

import lombok.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Value
public class HexBoard<T> implements Iterable<T> {
    int rowCount;
    int colCount;
    List<List<T>> board = new ArrayList<>();

    public HexBoard(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
    }

    public void fill(T hex) {
        for (int i = 0; i < rowCount; i++) {
            List<T> row = new ArrayList<>();
            int maxJ = maxColInRow(i);

            for (int j = 0; j < maxJ; j++) {
                row.add(hex);
            }
            board.add(row);
        }
    }

    public T get(int x, int y) {
        if (x >= rowCount || y >= colCount) throw new IllegalArgumentException("Indexed out of HexBoard");
        if (x % 2 == 0 && y == colCount - 1) throw new IllegalArgumentException("Indexed out of HexBoard, that row does not contain that Hex");

        return board.get(x).get(y);
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

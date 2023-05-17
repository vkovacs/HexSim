package hu.crs.hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ComplexHexBoard<T> extends HexBoard<HexField<T>> {

    public ComplexHexBoard(int rowCount, int colCount, T content) {
        super(rowCount, colCount);

        for (int i = 0; i < rowCount; i++) {
            List<HexField<T>> row = new ArrayList<>();
            int maxJ = maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                row.add(new HexField<>(new Coordinate(i, j), content));
            }
            board.add(row);
        }
    }

    public void set(Coordinate coordinate, T content) {
        set(coordinate.row(), coordinate.col(), content);
    }
    public void set(int row, int col, T content) {
        var actual = getHex(row, col);
        setHex(row, col, new HexField<>(actual.id(), content));
    }

    public HexField<T> randomHexField() {
        var random = new Random();
        var skipCount = random.nextInt(size());

        return board.stream()
                .flatMap(Collection::stream)
                .skip(skipCount)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Set<HexField<T>> neighbourHexFields(Coordinate coordinate) {
        var neighbours = neighbours(coordinate);

        return neighbours.stream()
                .map(this::getHex)
                .collect(Collectors.toSet());
    }
}

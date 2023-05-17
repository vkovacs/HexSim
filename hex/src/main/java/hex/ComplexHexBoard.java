package hex;

import java.util.ArrayList;
import java.util.List;

public class ComplexHexBoard<T> extends HexBoard<HexProperties<T>> {

    public ComplexHexBoard(int rowCount, int colCount, T content) {
        super(rowCount, colCount);

        for (int i = 0; i < rowCount; i++) {
            List<HexProperties<T>> row = new ArrayList<>();
            int maxJ = maxColInRow(i);
            for (int j = 0; j < maxJ; j++) {
                row.add(new HexProperties<>(new Coordinate(i, j), content));
            }
            board.add(row);
        }
    }

    public void set(int row, int col, T content) {
        var actual = getHex(row, col);
        setHex(row, col, new HexProperties<>(actual.id(), content));
    }
}

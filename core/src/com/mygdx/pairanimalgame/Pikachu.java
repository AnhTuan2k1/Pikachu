package com.mygdx.pairanimalgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Pikachu {

    // Kiểm tra hai ô có cùng giá trị không (không tính ô trống)
    private static boolean isSame(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        return grid[x1][y1].isVisible()
                && grid[x1][y1].getId() == grid[x2][y2].getId()
                && grid[x1][y1] != grid[x2][y2];
    }

    // Kiểm tra đường đi trực tiếp từ (x1, y1) đến (x2, y2)
    private static boolean directPath(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        if (x1 == x2) { // Cùng hàng
            int min = Math.min(y1, y2);
            int max = Math.max(y1, y2);
            for (int y = min + 1; y < max; y++) {
                if (grid[x1][y].isVisible()) return false;
            }
            return true;
        } else if (y1 == y2) { // Cùng cột
            int min = Math.min(x1, x2);
            int max = Math.max(x1, x2);
            for (int x = min + 1; x < max; x++) {
                if (grid[x][y1].isVisible()) return false;
            }
            return true;
        }
        return false;
    }

    // Kiểm tra đường đi với một góc vuông
    private static boolean oneBendPath(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        // Thử điểm giao tại (x1, y2)
        if (!grid[x1][y2].isVisible()) {
            if (directPath(grid, x1, y1, x1, y2) && directPath(grid, x1, y2, x2, y2)) {
                return true;
            }
        }
        // Thử điểm giao tại (x2, y1)
        if (!grid[x2][y1].isVisible()) {
            return directPath(grid, x1, y1, x2, y1) && directPath(grid, x2, y1, x2, y2);
        }
        return false;
    }

    // Kiểm tra đường đi với hai góc vuông
    static boolean twoBendPath(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        // Duyệt qua tất cả các ô trên hàng và cột của (x1, y1) và (x2, y2)
        for (int k = 0; k < grid.length; k++) {
            // Kiểm tra qua hàng
            if (!grid[k][y1].isVisible() && !grid[k][y2].isVisible()) {
                if (directPath(grid, x1, y1, k, y1) && directPath(grid, k, y1, k, y2) && directPath(grid, k, y2, x2, y2)) {
                    return true;
                }
            }
        }
        for (int k = 0; k < grid[0].length; k++) {
            // Kiểm tra qua cột
            if (!grid[x1][k].isVisible() && !grid[x2][k].isVisible()) {
                if (directPath(grid, x1, y1, x1, k) && directPath(grid, x1, k, x2, k) && directPath(grid, x2, k, x2, y2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Kiểm tra có thể nối 2 ô hay không
    static boolean canMatch(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        if (!isSame(grid, x1, y1, x2, y2)) {
            return false;
        }
        return directPath(grid, x1, y1, x2, y2)
                || oneBendPath(grid, x1, y1, x2, y2)
                || twoBendPath(grid, x1, y1, x2, y2);
    }

    public static void shuffleMatrixExceptInvisible(AnimalCard[][] matrix) {
        List<AnimalCard> elements = new ArrayList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Lưu trữ vị trí của các phần tử không phải là
        List<Integer> positions = new ArrayList<>();

        // Thu thập các phần tử khác 1 và vị trí của chúng
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j].isVisible()) {
                    elements.add(matrix[i][j]);
                    positions.add(i * cols + j); // Chuyển đổi vị trí hàng, cột thành chỉ số 1 chiều
                }
            }
        }

        // Trộn các phần tử không phải là
        Collections.shuffle(elements);

        // Đặt lại các phần tử đã trộn vào vị trí ban đầu của chúng trong ma trận
        for (int index = 0; index < elements.size(); index++) {
            int pos = positions.get(index);
            int row = pos / cols;
            int col = pos % cols;
            matrix[row][col] = elements.get(index).setIndex(row, col);
        }

        //trộn lại
        if(!anyMatchPossible(matrix)) shuffleMatrixExceptInvisible(matrix);
    }
    // Kiểm tra trong ma trận có bất kỳ hai ô nào có thể nối được với nhau không
    static boolean anyMatchPossible(AnimalCard[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                for (int x = 0; x < grid.length; x++) {
                    for (int y = 0; y < grid[0].length; y++) {
                        if(!grid[x][y].isVisible() || !grid[i][j].isVisible()) continue;
                        if (i != x || j != y) { // Không kiểm tra cùng một ô
                            if (canMatch(grid, i, j, x, y)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    static boolean isWin(AnimalCard[][] matrix) {
        for (AnimalCard[] animalCards : matrix) {
            for (AnimalCard animal : animalCards) {
                if (animal.isVisible()) return false;
            }
        }
        return true;
    }

}

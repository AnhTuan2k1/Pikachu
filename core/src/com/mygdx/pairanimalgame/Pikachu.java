package com.mygdx.pairanimalgame;

import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pikachu {
    static Path foundPath = null;
    static Path possiblePath = null;

    // Kiểm tra có thể nối 2 ô hay không
    static boolean canMatch(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        // Kiểm tra hai ô có khả năng nối hay không
        if (grid[x1][y1].getType() != grid[x2][y2].getType() // 2 ô phải có hình giống nhau
                || grid[x1][y1] == grid[x2][y2]  // 2 ô phải khác vị trí
                || !grid[x1][y1].isActive() || !grid[x2][y2].isActive())
            return false;

        foundPath = directPath(grid, x1, y1, x2, y2);
        if (foundPath != null) return true;

        foundPath = oneBendPath(grid, x1, y1, x2, y2);
        if (foundPath != null) return true;

        foundPath = twoBendPath(grid, x1, y1, x2, y2);
        return foundPath != null;
    }

    // Kiểm tra đường đi trực tiếp từ (x1, y1) đến (x2, y2)
    private static Path directPath(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        Path path = new Path();
        path.addPoint(x1, y1);

        if (x1 == x2) { // Cùng hàng
            int min = Math.min(y1, y2);
            int max = Math.max(y1, y2);
            for (int y = min + 1; y < max; y++) {
                if (grid[x1][y].isActive()) return null;
            }
            path.addPoint(x2, y2);
            return path;
        } else if (y1 == y2) { // Cùng cột
            int min = Math.min(x1, x2);
            int max = Math.max(x1, x2);
            for (int x = min + 1; x < max; x++) {
                if (grid[x][y1].isActive()) return null;
            }
            path.addPoint(x2, y2);
            return path;
        }
        return null;
    }

    // Kiểm tra đường đi với một góc vuông
    private static Path oneBendPath(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        Path path = new Path();
        path.addPoint(x1, y1);

        // Thử điểm giao tại (x1, y2)
        if (!grid[x1][y2].isActive()) {
            if (directPath(grid, x1, y1, x1, y2) != null && directPath(grid, x1, y2, x2, y2) != null) {
                path.addPoint(x1, y2);
                path.addPoint(x2, y2);
                return path;
            }
        }
        // Thử điểm giao tại (x2, y1)
        if (!grid[x2][y1].isActive()) {
            if(directPath(grid, x1, y1, x2, y1) != null && directPath(grid, x2, y1, x2, y2) != null){
                path.addPoint(x2, y1);
                path.addPoint(x2, y2);
                return path;
            }
        }
        return null;
    }

    // Kiểm tra đường đi với hai góc vuông
    static Path twoBendPath(AnimalCard[][] grid, int x1, int y1, int x2, int y2) {
        Path path = new Path();
        path.addPoint(x1, y1);

        // Duyệt qua tất cả các ô trên hàng và cột của (x1, y1) và (x2, y2)
        for (int k = 0; k < grid.length; k++) {
            // Kiểm tra qua hàng
            if (!grid[k][y1].isActive() && !grid[k][y2].isActive()) {
                if (directPath(grid, x1, y1, k, y1) != null
                        && directPath(grid, k, y1, k, y2) != null
                        && directPath(grid, k, y2, x2, y2) != null) {
                    path.addPoint(k, y1);
                    path.addPoint(k, y2);
                    path.addPoint(x2, y2);
                    return path;
                }
            }
        }
        for (int k = 0; k < grid[0].length; k++) {
            // Kiểm tra qua cột
            if (!grid[x1][k].isActive() && !grid[x2][k].isActive()) {
                if (directPath(grid, x1, y1, x1, k) != null
                        && directPath(grid, x1, k, x2, k) != null
                        && directPath(grid, x2, k, x2, y2) != null) {
                    path.addPoint(x1, k);
                    path.addPoint(x2, k);
                    path.addPoint(x2, y2);
                    return path;
                }
            }
        }
        return null;
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
                if (matrix[i][j].isActive()) {
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


            AnimalCard animal = elements.get(index);
            // check if animal is moving. if moving-> completed it before change position
            if(animal.isActionActive()) {
                for (Action action : animal.getActions()) {
                    action.act(Float.MAX_VALUE);
                }
            }
            animal.setIndex(row,col).toFront();
            matrix[row][col] = animal;
        }

        if(!anyMatchPossible(matrix)) shuffleMatrixExceptInvisible(matrix);
    }

    // Kiểm tra trong ma trận có bất kỳ hai ô nào có thể nối được với nhau không
    static boolean anyMatchPossible(AnimalCard[][] grid) {
        possiblePath = new Path();
        Map<Integer, List<int[]>> map = new HashMap<>();
        // Collect positions for each non-zero value
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isActive()) {
                    if (!map.containsKey(grid[i][j].getType())) {
                        map.put(grid[i][j].getType(), new ArrayList<>());
                    }
                    map.get(grid[i][j].getType()).add(new int[]{i, j});
                }
            }
        }

        if(map.isEmpty()) throw new IllegalArgumentException("anyMatchPossible: ma trận rỗng.");

        // Check connections for each group of identical values
        for (List<int[]> positions : map.values()) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int[] pos1 = positions.get(i);
                    int[] pos2 = positions.get(j);
                    if (canMatch(grid, pos1[0], pos1[1], pos2[0], pos2[1])) {
                        System.out.println("next possible: " + pos1[0]+","+ pos1[1]+
                                " : " + pos2[0]+","+ pos2[1]);
                        possiblePath.addPoint(pos1[0], pos1[1]);
                        possiblePath.addPoint(pos2[0], pos2[1]);
                        return true;
                    }
                }
            }
        }
        return false;
    }


/*    static boolean anyMatchPossible(AnimalCard[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                for (int x = 0; x < grid.length; x++) {
                    for (int y = 0; y < grid[0].length; y++) {
                        if (i != x || j != y) { // Không kiểm tra cùng một ô
                            if (canMatch(grid, i, j, x, y)) {
                                System.out.println("next possible: " + i+","+ j+
                                        " : " + x+","+ y);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }*/

    static boolean isWin(AnimalCard[][] matrix) {
        for (AnimalCard[] animalCards : matrix) {
            for (AnimalCard animal : animalCards) {
                if (animal.isActive()) return false;
            }
        }
        return true;
    }

    public static void reDrawIndexMatrix(AnimalCard[][] matrix) {
        for (AnimalCard[] row : matrix) {
            for (AnimalCard value : row) {
                value.toFront();
            }
        }
    }

}

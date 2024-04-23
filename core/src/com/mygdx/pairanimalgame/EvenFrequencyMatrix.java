package com.mygdx.pairanimalgame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class EvenFrequencyMatrix {
    public static int[][] generateMatrixWithEvenFrequency(int rows, int cols, int maxValue) {
        int[][] matrix = new int[rows][cols];
        List<Integer> values = new ArrayList<>();
        Random rnd = new Random();

        // Tính số lượng phần tử trong ma trận
        int totalElements = rows * cols;

        // Đảm bảo tổng số phần tử là số chẵn
        if (totalElements % 2 != 0) {
            throw new IllegalArgumentException("Tổng số phần tử trong ma trận phải là số chẵn.");
        }

        // Sinh ra các cặp số ngẫu nhiên
        while (values.size() < totalElements) {
            int value = rnd.nextInt(maxValue);
            // Thêm hai lần để đảm bảo tần suất là số chẵn
            values.add(value);
            values.add(value);
        }

        // Trộn danh sách để tăng tính ngẫu nhiên
        Collections.shuffle(values);

        // Điền giá trị vào ma trận
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = values.get(index++);
            }
        }

        return matrix;
    }

    public static void shuffleMatrixExceptOnes(int[][] matrix) {
        List<Integer> elements = new ArrayList<>();
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Lưu trữ vị trí của các phần tử không phải là 1
        List<Integer> positions = new ArrayList<>();

        // Thu thập các phần tử khác 1 và vị trí của chúng
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != 1) {
                    elements.add(matrix[i][j]);
                    positions.add(i * cols + j); // Chuyển đổi vị trí hàng, cột thành chỉ số 1 chiều
                }
            }
        }

        // Trộn các phần tử không phải là 1
        Collections.shuffle(elements);

        // Đặt lại các phần tử đã trộn vào vị trí ban đầu của chúng trong ma trận
        for (int index = 0; index < elements.size(); index++) {
            int pos = positions.get(index);
            int row = pos / cols;
            int col = pos % cols;
            matrix[row][col] = elements.get(index);
        }
    }
    public static void printMatrix(int[][] matrix) {
        System.out.println("-------------------------------");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

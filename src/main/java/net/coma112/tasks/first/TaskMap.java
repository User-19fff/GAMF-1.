package net.coma112.tasks.first;

import lombok.NonNull;
import net.coma112.data.MapData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TaskMap {
    private static final int SIZE = 30, WATER_HEIGHT = 21, DEFAULT_VALUE = 0;

    public static int findNumber() {
        int[][] map = getContent();
        int count = DEFAULT_VALUE;

        for (int[] row : map) {
            for (int num : row) {
                if (num == 21) count++;
            }
        }

        return count;
    }

    public static @NonNull MapData water() {
        int[][] map = getContent();
        boolean[][] visited = new boolean[SIZE][SIZE];
        int waterSpread = DEFAULT_VALUE;
        int low10 = DEFAULT_VALUE;

        for (int i = DEFAULT_VALUE; i < SIZE; i++) {
            for (int j = DEFAULT_VALUE; j < SIZE; j++) {
                if (map[i][j] == WATER_HEIGHT && !visited[i][j]) {
                    MapData result = floodFill(map, visited, i, j);
                    waterSpread += result.count();
                    low10 += result.low10();
                }
            }
        }

        return new MapData(waterSpread, low10);
    }

    private static @NonNull MapData floodFill(int[][] map, boolean[][] visited, int x, int y) {
        int count = DEFAULT_VALUE;
        int low10 = DEFAULT_VALUE;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        int[] dx = {-1, 1, DEFAULT_VALUE, DEFAULT_VALUE};
        int[] dy = {DEFAULT_VALUE, DEFAULT_VALUE, -1, 1};

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int cx = pos[DEFAULT_VALUE], cy = pos[1];
            count++;

            if (map[cx][cy] < 10) low10++;

            for (int i = DEFAULT_VALUE; i < 4; i++) {
                int nx = cx + dx[i], ny = cy + dy[i];

                if (nx > DEFAULT_VALUE && nx < SIZE && ny >= DEFAULT_VALUE && ny < SIZE) {
                    if (!visited[nx][ny] && map[nx][ny] <= map[cx][cy]) {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }

        return new MapData(count, low10);
    }

    private static int[][] getContent() {
        try {
            Path path = Path.of("src/main/resources/map.txt");
            String content = Files.readString(path).trim();

            int[] numbers = Arrays.stream(content.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int[][] map = new int[SIZE][SIZE];

            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(numbers, i * SIZE, map[i], DEFAULT_VALUE, SIZE);
            }

            return map;
        } catch (IOException | NumberFormatException exception) {
            System.out.println("Hiba történt: " + exception.getMessage());
            return new int[DEFAULT_VALUE][DEFAULT_VALUE];
        }
    }
}

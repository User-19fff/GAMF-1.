package net.coma112.tasks.third;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class VirusSpread {

    private static List<int[]> readMeetings(String fileName) {
        List<int[]> meetings = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(fileName));
            for (String line : lines) {
                String[] parts = line.split(" ");
                int person1 = Integer.parseInt(parts[0]);
                int person2 = Integer.parseInt(parts[1]);
                meetings.add(new int[]{person1, person2});
            }
        } catch (IOException e) {
            System.out.println("Hiba: " + e.getMessage());
        }
        return meetings;
    }

    private static int[] simulateVirusSpread(List<int[]> meetings, int maxSteps) {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        for (int[] meeting : meetings) {
            int person1 = meeting[0];
            int person2 = meeting[1];
            adjacencyList.computeIfAbsent(person1, k -> new HashSet<>()).add(person2);
            adjacencyList.computeIfAbsent(person2, k -> new HashSet<>()).add(person1);
        }


        Map<Integer, Integer> infectedSteps = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();


        infectedSteps.put(2, 1);
        queue.add(2);

        for (int step = 1; step <= maxSteps; step++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int currentPerson = queue.poll();
                for (int neighbor : adjacencyList.getOrDefault(currentPerson, Collections.emptySet())) {
                    if (!infectedSteps.containsKey(neighbor)) {
                        infectedSteps.put(neighbor, step + 1);
                        queue.add(neighbor);
                    }
                }
            }
        }


        int[] infectedCounts = new int[maxSteps + 1];
        for (int step = 1; step <= maxSteps; step++) {
            int count = 0;
            for (Map.Entry<Integer, Integer> entry : infectedSteps.entrySet()) {
                int infectedStep = entry.getValue();
                if (step - infectedStep < 8) {
                    count++;
                }
            }
            infectedCounts[step] = count;
        }

        return infectedCounts;
    }

    // a)
    public static int getInfectedCountAfter11Steps() {
        List<int[]> meetings = readMeetings("src/main/resources/elek.txt");

        int[] infectedCounts = simulateVirusSpread(meetings, 11);
        return infectedCounts[11];
    }

    // b)
    public static int getStepWhenNoInfected() {
        List<int[]> meetings = readMeetings("src/main/resources/elek.txt");

        int step = 1;
        while (true) {
            int[] infectedCounts = simulateVirusSpread(meetings, step);
            if (infectedCounts[step] == 0) {
                return step;
            }
            step++;
        }
    }

}
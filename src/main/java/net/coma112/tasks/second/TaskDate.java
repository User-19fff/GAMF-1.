package net.coma112.tasks.second;

import lombok.NonNull;
import net.coma112.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskDate {

    public static String findMaxAngleTime() {
        String content = getContent("date");
        String[] lines = content.split("\n");
        String maxAngleTime = "";
        double maxAngle = -1;

        for (String line : lines) {
            line = line.trim();

            String[] parts = line.split("\\s+");

            try {
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                double angle = calculateAngle(hours, minutes);

                if (angle > maxAngle) {
                    maxAngle = angle;
                    maxAngleTime = String.format("%02d:%02d", hours, minutes);
                }
            } catch (NumberFormatException ignored) {}
        }

        return maxAngleTime;
    }

    public static @NonNull String findClockTimesForAngles() {
        String content = getContent("angles");
        String[] angleStrings = content.trim().split("\\s+");

        int currentDay = 1;
        int hours = 0;
        int minutes = 0;
        String lastTime = "";
        boolean first = true;

        for (String angleString : angleStrings) {
            double targetAngle = Double.parseDouble(angleString);

            if (!first) {
                minutes++;
                if (minutes >= 60) {
                    minutes = 0;
                    hours++;

                    if (hours >= 24) {
                        hours = 0;
                        currentDay++;
                    }
                }
            } else first = false;

            while (true) {
                double currentAngle = calculateAngle(hours, minutes);

                if (Math.abs(currentAngle - targetAngle) < 0.001) {
                    lastTime = String.format("%02d:%02d", hours, minutes);
                    break;
                }

                minutes++;
                if (minutes >= 60) {
                    minutes = 0;
                    hours++;

                    if (hours >= 24) {
                        hours = 0;
                        currentDay++;
                    }
                }
            }
        }

        return currentDay + "|" + lastTime;
    }

    public static double findSmallestAngleChange() {
        String content = getContent("date");
        String[] lines = content.split("\n");
        double smallestChange = Double.MAX_VALUE;

        for (int i = 0; i < lines.length - 1; i++) {
            String[] parts1 = lines[i].trim().split("\\s+");
            String[] parts2 = lines[i + 1].trim().split("\\s+");

            try {
                int hours1 = Integer.parseInt(parts1[0]);
                int minutes1 = Integer.parseInt(parts1[1]);
                int hours2 = Integer.parseInt(parts2[0]);
                int minutes2 = Integer.parseInt(parts2[1]);

                double angle1 = calculateAngle(hours1, minutes1);
                double angle2 = calculateAngle(hours2, minutes2);

                double angleDifference = Math.abs(angle1 - angle2);
                smallestChange = Math.min(smallestChange, angleDifference);
            } catch (NumberFormatException ignored) {}
        }

        return smallestChange;
    }

    private static double calculateAngle(int hours, int minutes) {
        hours = hours % 12;

        double hourAngle = (hours + minutes / 60.0) * 30.0;
        double minuteAngle = minutes * 6.0;
        double angle = Math.abs(hourAngle - minuteAngle);

        return Math.min(angle, 360 - angle);
    }

    private static String getContent(@NonNull String fileName) {
        String content;

        try {
            Path path = FileUtils.getTextFile(fileName);
            content = Files.readString(path).trim();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Hiba történt: " + exception.getMessage());
            return "HIBA";
        }

        return content;
    }
}

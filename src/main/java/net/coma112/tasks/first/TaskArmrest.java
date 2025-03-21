package net.coma112.tasks.first;

import lombok.NonNull;
import net.coma112.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TaskArmrest {
    public static int calculateDistance() {
        String content = getContent();
        int x = 0;
        int y = 0;

        for (char character : content.toCharArray()) {
            switch (character) {
                case 'a' -> y++;
                case 'b' -> x++;
                case 'c' -> y--;
                case 'd' -> x--;

                default -> {}
            }
        }

        return (int) Math.round(Math.sqrt(x * x * y * y));
    }

    public static int countABCSequence() {
        String content = getContent();
        int count = 0;
        int length = content.length();

        for (int i = 0; i < length; i++) {
            if (content.charAt(i) == 'a') {
                for (int j = i + 1; j < length; j++) {
                    if (content.charAt(j) == 'b') {
                        for (int k = j + 1; k < length; k++) {
                            if (content.charAt(k) == 'c') count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public static @NonNull String findLongestCharSubstring() {
        int maxLength = 0;
        String pair = "";
        char[] chars = {'a', 'b', 'c', 'd'};

        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                int maxTemp = getMaxTemp(chars[i], chars[j], getContent());

                if (maxTemp > maxLength) {
                    maxLength = maxTemp;
                    pair = "" + chars[i] + chars[j];
                }
            }
        }

        return pair + maxLength;
    }

    private static String getContent() {
        String content;

        try {
            Path path = FileUtils.getTextFile("armrest");
            content = Files.readString(path).trim();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Hiba történt: " + exception.getMessage());
            return "HIBA";
        }

        return content;
    }

    private static int getMaxTemp(char character1, char character2, @NonNull String content) {
        int length = 0;
        int maxTemp = 0;
        Set<Character> characterSet = new HashSet<>();

        for (char character : content.toCharArray()) {
            if (character == character1 || character == character2) {
                characterSet.add(character);
                length++;
            } else {
                characterSet.clear();
                length = 0;
            }

            if (characterSet.size() == 2) maxTemp = Math.max(maxTemp, length);
        }

        return maxTemp;
    }
}

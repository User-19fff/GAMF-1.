package net.coma112.tasks.first;

import lombok.NonNull;
import net.coma112.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TaskText {
    public static int findDistanceBetweenArticles() {
        String content = getContent();
        content = content.replace("\n", " ");
        int maxDistance = 0;
        int lastArticlePos = -1;

        String[] words = content.split("\\s+");
        int pos = 0;

        for (String word : words) {
            if (word.equals("A") || word.equals("AZ")) {
                if (lastArticlePos != -1) {
                    int distance = pos - lastArticlePos - word.length();
                    maxDistance = Math.max(maxDistance, distance);
                }

                lastArticlePos = pos;
            }
            pos += word.length() + 1;
        }

        return maxDistance;
    }

    public static String findLongestUniqueWord() {
        String content = getContent();
        String[] words = content.split("\\s+");
        String longestWord = "";

        for (String word : words) {
            if (hasUniqueCharacters(word) && word.length() > longestWord.length()) longestWord = word;
        }

        return longestWord;
    }

    public static int countPalindromes() {
        String content = getContent();
        String[] words = content.split("\\s+");
        Set<String> palindromes = new HashSet<>();

        for (String word : words) {
            if (word.length() > 1 && isPalindrome(word)) palindromes.add(word);
        }

        return palindromes.size();
    }

    private static boolean hasUniqueCharacters(@NonNull String word) {
        Set<Character> characterSet = new HashSet<>();

        for (char character : word.toCharArray()) {
            if (!characterSet.add(character)) return false;
        }

        return true;
    }

    private static boolean isPalindrome(@NonNull String word) {
        int left = 0;
        int right = word.length() - 1;

        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) return false;

            left++;
            right--;
        }

        return true;
    }

    private static String getContent() {
        String content;

        try {
            Path path = FileUtils.getTextFile("text");
            content = Files.readString(path).trim();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Hiba történt: " + exception.getMessage());
            return "HIBA";
        }

        return content;
    }
}

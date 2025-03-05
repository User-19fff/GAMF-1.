package net.coma112.tasks.second;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskEncryption {
    //    Task A
    private static final char[][] TABLE = {
            {'A', 'B', 'C', 'D', 'E'},
            {'F', 'G', 'H', 'I', 'J'},
            {'K', 'L', 'M', 'N', 'O'},
            {'P', 'Q', 'R', 'S', 'T'},
            {'U', 'V', 'X', 'Y', 'Z'},
    };

    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};

    private static String getContent(String fileName) {
        String content = "";

        try {
            Path path = Path.of(fileName);
            content = Files.readString(path);
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Hiba történt: " + exception.getMessage());
            return "HIBA";
        }

        return content;
    }

    private static int indexOfVowel(char c) {
        for (int i = 0; i < VOWELS.length; i++) {
            if (VOWELS[i] == c) {
                return i;
            }
        }
        return -1;
    }

    private static String decode(String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        boolean swap = true;

        for (int i = 0; i < encodedText.length(); i++) {
            char currentChar = encodedText.charAt(i);

            if (currentChar == ' ' || currentChar == '\n') {
                decodedText.append(currentChar);
                continue;
            }

            if (i + 1 >= encodedText.length()) break;

            char c1 = encodedText.charAt(i);
            char c2 = encodedText.charAt(i + 1);

            int v1 = indexOfVowel(c1);
            int v2 = indexOfVowel(c2);

            if (v1 == -1 || v2 == -1) {
                i++;
                continue;
            }

            int row, col;

            if (swap) {
                row = v1;
                col = v2;
            } else {
                row = v2;
                col = v1;
            }

            if (row >= 0 && row < TABLE.length && col >= 0 && col < TABLE[row].length) {
                decodedText.append(TABLE[row][col]);
            }

            swap = !swap;
            i++;
        }
        return decodedText.toString();
    }

    private static int countWordsBetweenQ(String decodedText) {
        String[] words = decodedText.split("\\s+");
        int firstQIndex = -1, lastQIndex = -1;

        for (int i = 0; i < words.length; i++) {
            if (words[i].contains("Q")) {
                if (firstQIndex == -1) {
                    firstQIndex = i;
                }
                lastQIndex = i;
            }
        }

        if (firstQIndex == -1) {
            return 0;
        }

        return lastQIndex - firstQIndex + 1;
    }

//    Task B

    private static String extractVowels(String text) {
        StringBuilder vowels = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (indexOfVowel(c) != -1) {
                vowels.append(c);
            }
        }
        return vowels.toString();
    }

    private static String decodeB(String encodedText) {
        StringBuilder decodedText = new StringBuilder();

        for (int i = 0; i < encodedText.length(); i += 2) {
            if (i + 1 >= encodedText.length()) break;

            char c1 = encodedText.charAt(i);
            char c2 = encodedText.charAt(i + 1);

            int row = indexOfVowel(c1);
            int col = indexOfVowel(c2);

            if (row == -1 || col == -1) continue;

            if (row >= 0 && row < TABLE.length && col >= 0 && col < TABLE[row].length) {
                decodedText.append(TABLE[row][col]);
            }

        }
        return decodedText.toString();
    }

    //    Task C
    private static String[] loadWords(String fileName) {
        String content = getContent(fileName);
        if (content.equals("HIBA")) {
            return new String[0];
        }
        return content.replace("\r", "").split("\n");
    }

    private static String findWordForVowelsPair(String vowelPair, String[] words) {
        if (vowelPair.length() != 2) return "";

        for (String word : words) {
            if (extractVowels(word).equals(vowelPair)) return word;
        }
        return "";
    }

    private static String decodeC(String encodedText, String[] words) {
        StringBuilder decodedText = new StringBuilder();
        String vowelsOnly = extractVowels(encodedText);

        if (vowelsOnly.length() % 2 != 0) {
            vowelsOnly = vowelsOnly.substring(0, vowelsOnly.length() - 1);
        }
        for (int i = 0; i < vowelsOnly.length(); i += 2) {
            if (i + 1 >= vowelsOnly.length()) break;

            String vowelPair = vowelsOnly.substring(i, i + 2);

            String word = findWordForVowelsPair(vowelPair, words);
            if (!word.isEmpty()) decodedText.append(word).append(" ");
        }
        return decodedText.toString().trim();
    }

    private static String endcodeToVowelPairs(String content) {
        StringBuilder endcodedText = new StringBuilder();
        for (char c : content.toCharArray()) {
            boolean found = false;
            for (int row = 0; row < TABLE.length; row++) {
                for (int col = 0; col < TABLE[row].length; col++) {
                    if (c == TABLE[row][col]) {
                        endcodedText.append(VOWELS[row]);
                        endcodedText.append(VOWELS[col]);

                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
        }
        return endcodedText.toString();
    }

    //    Answer
    public static int TaskThreeA() {
        String content = getContent("src/main/resources/textEncryption.txt");
        if (content.equals("HIBA")) {
            return -1;
        }
        String decodedContent = decode(content);

        return countWordsBetweenQ(decodedContent);
    }

    public static String TaskThreeB() {
        String content = getContent("src/main/resources/text2Encryption.txt");
        if (content.equals("HIBA")) {
            return "HIBA";
        }
        String vowelsOnly = extractVowels(content);

        return decodeB(vowelsOnly);
    }

    public static String TaskThreeC() {
        String content = getContent("src/main/resources/text3Encryption.txt");
        if (content.equals("HIBA")) {
            return "HIBA";
        }
        String encodedVowels = endcodeToVowelPairs(content);
        String[] words = loadWords("src/main/resources/wordsEncryption.txt");
        if (words.length == 0) {
            return "HIBA";
        }

        return decodeC(encodedVowels, words);
    }

}
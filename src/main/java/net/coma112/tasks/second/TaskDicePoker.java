package net.coma112.tasks.second;

import lombok.NonNull;
import net.coma112.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TaskDicePoker {

    private static final Map<String, Set<String>> playerResults = new HashMap<>();

    static {
        playerResults.put("alfa", new HashSet<>());
        playerResults.put("beta", new HashSet<>());
        playerResults.put("gamma", new HashSet<>());
    }

    public static int countFullRounds() {
        String rolls = getContent("rolls");
        String decisions = getContent("decisions");

        int fullRounds = 0;
        int rollIndex = 0;
        int decisionIndex = 0;

        while (rollIndex < rolls.length()) {
            for (int i = 0; i < 3; i++) {
                if (rollIndex >= rolls.length() || decisionIndex >= decisions.length()) {
                    break;
                }

                int[] playerResult = processPlayerRolls(rolls, decisions, rollIndex, decisionIndex);
                rollIndex = playerResult[0];
                decisionIndex = playerResult[1];

                String playerName = getPlayerName(i);
                int[] dice = Arrays.copyOfRange(playerResult, 2, playerResult.length);
                String result = evaluateResult(dice);

                if (!playerResults.get(playerName).contains(result)) {
                    playerResults.get(playerName).add(result);
                    if (playerResults.get(playerName).size() == 7) return fullRounds;
                }
            }

            fullRounds++;
        }

        return fullRounds;
    }

    public static int getGammaFullRound() {
        String rolls = getContent("rolls");
        String decisions = getContent("decisions");

        int rollIndex = 0;
        int decisionIndex = 0;
        int round = 1;

        while (rollIndex < rolls.length()) {
            for (int i = 0; i < 3; i++) {
                if (rollIndex >= rolls.length() || decisionIndex >= decisions.length()) {
                    break;
                }

                int[] playerResult = processPlayerRolls(rolls, decisions, rollIndex, decisionIndex);
                rollIndex = playerResult[0];
                decisionIndex = playerResult[1];

                if (i == 2) {
                    int[] dice = Arrays.copyOfRange(playerResult, 2, playerResult.length);
                    String result = evaluateResult(dice);
                    if ("full".equals(result)) return round;
                }
            }

            round++;
        }
        return -1;
    }

    public static String checkWinner() {
        for (Map.Entry<String, Set<String>> entry : playerResults.entrySet()) {
            if (entry.getValue().size() == 7) return entry.getKey();
        }

        return "Nincs gyoztes";
    }

    public static int getLargestPoker() {
        String rolls = getContent("rolls");
        String decisions = getContent("decisions");

        int rollIndex = 0;
        int decisionIndex = 0;
        int largestPoker = 0;

        while (rollIndex < rolls.length()) {
            for (int i = 0; i < 3; i++) {
                if (rollIndex >= rolls.length() || decisionIndex >= decisions.length()) {
                    break;
                }

                int[] playerResult = processPlayerRolls(rolls, decisions, rollIndex, decisionIndex);
                rollIndex = playerResult[0];
                decisionIndex = playerResult[1];

                int[] dice = Arrays.copyOfRange(playerResult, 2, playerResult.length);
                String result = evaluateResult(dice);

                if ("póker".equals(result)) {
                    int maxPoker = Arrays.stream(dice).max().orElse(0);
                    largestPoker = Math.max(largestPoker, maxPoker);
                }
            }
        }

        return largestPoker;
    }

    public static int getTotalPairs() {
        String rolls = getContent("rolls");
        String decisions = getContent("decisions");

        int rollIndex = 0;
        int decisionIndex = 0;
        int totalPairs = 0;

        while (rollIndex < rolls.length()) {
            for (int i = 0; i < 3; i++) {
                if (rollIndex >= rolls.length() || decisionIndex >= decisions.length()) {
                    break;
                }

                int[] playerResult = processPlayerRolls(rolls, decisions, rollIndex, decisionIndex);
                rollIndex = playerResult[0];
                decisionIndex = playerResult[1];

                int[] dice = Arrays.copyOfRange(playerResult, 2, playerResult.length);
                String result = evaluateResult(dice);

                if ("1 pár".equals(result) || "2 pár".equals(result)) totalPairs++;
            }
        }

        return totalPairs;
    }

    private static int[] processPlayerRolls(@NonNull String rolls, @NonNull String decisions, int startRollIndex, int startDecisionIndex) {
        int remainingRolls = 3;
        int currentRolls = 5;
        int rollIndex = startRollIndex;
        int decisionIndex = startDecisionIndex;
        int[] finalDice = new int[5];
        Arrays.fill(finalDice, -1);
        int finalDiceIndex = 0;

        while (remainingRolls > 0 && currentRolls > 0) {
            if (rollIndex + currentRolls > rolls.length() || decisionIndex + currentRolls > decisions.length()) {
                break;
            }

            int keptDice = 0;
            for (int i = 0; i < currentRolls; i++) {
                if (decisions.charAt(decisionIndex + i) == '1') {
                    finalDice[finalDiceIndex++] = Character.getNumericValue(rolls.charAt(rollIndex + i));
                    keptDice++;
                }
            }

            rollIndex += currentRolls;
            decisionIndex += currentRolls;
            currentRolls = currentRolls - keptDice;
            remainingRolls--;
        }

        return new int[]{rollIndex, decisionIndex, finalDice[0], finalDice[1], finalDice[2], finalDice[3], finalDice[4]};
    }

    private static String evaluateResult(int[] dice) {
        int[] filteredDice = Arrays.stream(dice).filter(value -> value != -1).toArray();
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int die : filteredDice) {
            frequencyMap.put(die, frequencyMap.getOrDefault(die, 0) + 1);
        }

        List<Integer> frequencies = new ArrayList<>(frequencyMap.values());
        frequencies.sort(Collections.reverseOrder());
        if (frequencies.contains(4)) return "póker";
        else if (frequencies.contains(3) && frequencies.contains(2)) return "full";
        else if (frequencies.contains(3)) return "terc";
        else if (frequencies.contains(2)) {
            int pairCount = 0;
            for (int freq : frequencies) {
                if (freq == 2) pairCount++;
            }
            return pairCount == 2 ? "2 pár" : "1 pár";
        } else {
            Arrays.sort(filteredDice);
            if (Arrays.equals(filteredDice, new int[]{1, 2, 3, 4, 5})) return "kissor";
            else if (Arrays.equals(filteredDice, new int[]{2, 3, 4, 5, 6})) return "nagysor";
            else return null;
        }
    }

    private static @NonNull String getPlayerName(int index) {
        return switch (index) {
            case 0 -> "alfa";
            case 1 -> "beta";
            case 2 -> "gamma";
            default -> throw new IllegalArgumentException("rósz");
        };
    }

    private static @NonNull String getContent(@NonNull String fileName) {
        try {
            Path path = FileUtils.getTextFile(fileName);
            return Files.readString(path).trim();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Hiba történt: " + exception.getMessage());
            return "HIBA";
        }
    }
}
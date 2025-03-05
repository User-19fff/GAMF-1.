package net.coma112;

import net.coma112.tasks.first.TaskArmrest;
import net.coma112.tasks.first.TaskMap;
import net.coma112.tasks.first.TaskText;
import net.coma112.tasks.second.TaskDate;
import net.coma112.tasks.second.TaskDicePoker;
import net.coma112.tasks.second.TaskEncryption;

public class Rounds {
    public static void main(String[] args) {
        System.out.println("1, a) " + TaskArmrest.findLongestCharSubstring());
        System.out.println("1, b) " + TaskArmrest.countABCSequence());
        System.out.println("1, c) " + TaskArmrest.calculateDistance() + "\n");

        System.out.println("2, a) " + TaskText.findLongestUniqueWord());
        System.out.println("2, b) " + TaskText.findDistanceBetweenArticles());
        System.out.println("2, c) " + TaskText.countPalindromes() + "\n");

        System.out.println("3, a) " + TaskMap.findNumber());
        System.out.println("3, b) " + TaskMap.water().count());
        System.out.println("3, c) " + TaskMap.water().low10() + "\n");

        System.out.println("1, a) " + TaskDate.findMaxAngleTime());
        System.out.println("1, b) " + TaskDate.findSmallestAngleChange());
        System.out.println("1, c) " + TaskDate.findClockTimesForAngles() + "\n");

        System.out.println("2, a) " + TaskDicePoker.countFullRounds());
        System.out.println("2, b) " + TaskDicePoker.checkWinner());
        System.out.println("2, c) " + TaskDicePoker.getGammaFullRound());
        System.out.println("2, d) " + TaskDicePoker.getLargestPoker());
        System.out.println("2, e) " + TaskDicePoker.getTotalPairs() + "\n");

        System.out.println("3, a) " + TaskEncryption.TaskThreeA());
        System.out.println("3, b) " + TaskEncryption.TaskThreeB());
        System.out.println("3, c) " + TaskEncryption.TaskThreeC());
    }
}
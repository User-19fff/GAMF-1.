package net.coma112;

import net.coma112.tasks.TaskArmrest;
import net.coma112.tasks.TaskMap;
import net.coma112.tasks.TaskText;

public class FirstRound {
    public static void main(String[] args) {
        System.out.println("1, a) " + TaskArmrest.findLongestCharSubstring());
        System.out.println("1, b) " + TaskArmrest.countABCSequence());
        System.out.println("1, c) " + TaskArmrest.calculateDistance() + "\n");

        System.out.println("2, a) " + TaskText.findLongestUniqueWord());
        System.out.println("2, b) " + TaskText.findDistanceBetweenArticles());
        System.out.println("2, c) " + TaskText.countPalindromes() + "\n");

        System.out.println("3, a) " + TaskMap.findNumber());
        System.out.println("3, b) " + TaskMap.water().count());
        System.out.println("3, c) " + TaskMap.water().low10());
    }
}
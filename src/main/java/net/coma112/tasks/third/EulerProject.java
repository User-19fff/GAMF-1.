package net.coma112.tasks.third;

import java.util.*;

public class EulerProject {

    private static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    private static Set<Integer> generatePermutations(int[] digits) {
        Set<Integer> permutations = new HashSet<>();
        generatePermutationsHelper(digits, 0, permutations);
        return permutations;
    }


    private static void generatePermutationsHelper(int[] digits, int start, Set<Integer> permutations) {
        if (start == digits.length - 1) {
            if (digits[0] != 0) {
                int number = 0;
                for (int digit : digits) {
                    number = number * 10 + digit;
                }
                permutations.add(number);
            }
            return;
        }
        for (int i = start; i < digits.length; i++) {
            swap(digits, start, i);
            generatePermutationsHelper(digits, start + 1, permutations);
            swap(digits, start, i);
        }
    }


    private static void swap(int[] digits, int i, int j) {
        int temp = digits[i];
        digits[i] = digits[j];
        digits[j] = temp;
    }

    // a)
    public static int countNumberQuadruplesWithAtLeastSixPrimes() {
        int count = 0;
        for (int a = 0; a <= 9; a++) {
            for (int b = 0; b <= 9; b++) {
                for (int c = 0; c <= 9; c++) {
                    for (int d = 0; d <= 9; d++) {
                        int[] digits = {a, b, c, d};
                        Set<Integer> permutations = generatePermutations(digits);
                        int primeCount = 0;
                        for (int number : permutations) {
                            if (isPrime(number)) {
                                primeCount++;
                            }
                        }
                        if (primeCount >= 6) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    // b)
    public static String findArithmeticSequence() {
        for (int a = 0; a <= 9; a++) {
            for (int b = 0; b <= 9; b++) {
                for (int c = 0; c <= 9; c++) {
                    for (int d = 0; d <= 9; d++) {
                        int[] digits = {a, b, c, d};
                        Set<Integer> permutations = generatePermutations(digits);
                        List<Integer> primes = new ArrayList<>();
                        for (int number : permutations) {
                            if (isPrime(number)) {
                                primes.add(number);
                            }
                        }
                        Collections.sort(primes);
                        for (int i = 0; i < primes.size(); i++) {
                            for (int j = i + 1; j < primes.size(); j++) {
                                for (int k = j + 1; k < primes.size(); k++) {
                                    int num1 = primes.get(i);
                                    int num2 = primes.get(j);
                                    int num3 = primes.get(k);
                                    if (num2 - num1 == num3 - num2 && num2 - num1 != 0) {
                                        if (num1 != 1487 && num2 != 4817 && num3 != 8147) {
                                            return "" + num1 + num2 + num3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

}
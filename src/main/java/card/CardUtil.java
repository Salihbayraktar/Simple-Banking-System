package card;

import java.util.Random;
import java.util.stream.IntStream;

public class CardUtil {

    public static String createRandomPin() {
        StringBuilder pin = new StringBuilder();
        Random random = new Random();
        IntStream.range(0, 4).forEach(i -> pin.append(random.nextInt(10)));
        return pin.toString();
    }

    public static String createRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder("400000");
        Random random = new Random();
        IntStream.range(0, 9).forEach(i -> cardNumber.append(random.nextInt(10)));
        int luhn = calculateLuhnNumber(Long.valueOf(cardNumber.toString()));
        cardNumber.append(luhn);
        return cardNumber.toString();
    }

    private static int calculateLuhnNumber(Long number) {
        int luhnSum = 0;
        int luhnValue = 0;
        boolean multiplyTwo = true;

        while (number != 0) {
            luhnValue = (int) (number % 10);
            number /= 10;

            if (multiplyTwo) {
                luhnValue *= 2;
                if (luhnValue >= 10) {
                    luhnValue -= 9;
                }
                multiplyTwo = false;
            } else {
                multiplyTwo = true;
            }
            luhnSum += luhnValue;

        }
        return luhnSum % 10 == 0 ? 0 : (10 - luhnSum % 10);
    }

    public static boolean checkLuhnNumber(String cardNumber) {

        long number = Long.parseLong(cardNumber);
        int luhnSum = 0;
        int luhnValue = 0;
        boolean multiplyTwo = false;

        while (number != 0) {
            luhnValue = (int) (number % 10);
            number /= 10;

            if (multiplyTwo) {
                luhnValue *= 2;
                if (luhnValue >= 10) {
                    luhnValue -= 9;
                }
                multiplyTwo = false;
            } else {
                multiplyTwo = true;
            }
            luhnSum += luhnValue;

        }
        return luhnSum % 10 == 0;

    }
}

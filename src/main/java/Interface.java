import card.Card;
import card.CardUtil;

import java.util.Scanner;

public class Interface {

    public Interface() {

        DataBase dataBase = Main.dataBase;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("""
                    1. Create an account
                    2. Log into account
                    0. Exit
                    """);
            System.out.print(">");
            int selection = scanner.nextInt();
            System.out.println();
            scanner.nextLine();
            switch (selection) {
                case 1 -> {
                    Card card = new Card();
                    dataBase.insert(card.getId(), card.getCardNumber(), card.getPin());
                }
                case 2 -> {
                    System.out.println("Enter your card number:");
                    String inputCardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String inputPin = scanner.nextLine();
                    Card selectedCard = dataBase.getCard(inputCardNumber, inputPin);
                    if (selectedCard != null) {
                        System.out.println("You have successfully logged in!\n");
                        boolean logIn = true;
                        while (logIn) {
                            System.out.println("""
                                    1. Balance
                                    2. Add income
                                    3. Do transfer
                                    4. Close account
                                    5. Log out
                                    0. Exit""");
                            System.out.print(">");
                            selection = scanner.nextInt();
                            scanner.nextLine();
                            switch (selection) {
                                case 1 -> System.out.printf("Balance: %d\n\n", selectedCard.getBalance());
                                case 2 -> {
                                    System.out.println("Enter income:");
                                    int income = scanner.nextInt();
                                    selectedCard.addIncome(income);
                                    dataBase.addIncomeToCard(selectedCard, income);
                                }
                                case 3 -> {
                                    System.out.println("Transfer\n" +
                                            "Enter card number:");
                                    String cardNumber = scanner.nextLine();
                                    if (CardUtil.checkLuhnNumber(cardNumber)) {
                                        Card receiverCard = dataBase.getCard(cardNumber);
                                        if (receiverCard != null) {
                                            System.out.println("Enter how much money you want to transfer:");
                                            int moneyToTransfer = scanner.nextInt();
                                            scanner.nextLine();
                                            if (selectedCard.getBalance() >= moneyToTransfer) {
                                                dataBase.transferMoneyToAnotherAccount(selectedCard, receiverCard, moneyToTransfer);
                                                selectedCard.addIncome(-1 * moneyToTransfer);
                                            } else {
                                                System.out.println("Not enough money!");
                                            }
                                        } else {
                                            System.out.println("Such a card does not exist.");
                                        }
                                    } else {
                                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                                    }
                                }
                                case 4 -> {
                                    dataBase.deleteCard(selectedCard);

                                    logIn = false;
                                }
                                case 5 -> {
                                    System.out.println("You have successfully logged out!\n");
                                    logIn = false;
                                }
                                case 0 -> {
                                    System.out.println("Bye!\n");
                                    System.exit(0);
                                }
                            }
                        }
                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                }
                case 0 -> {
                    System.out.println("Bye!\n");
                    System.exit(0);
                }
            }
        }
    }
}

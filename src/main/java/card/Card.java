package card;

public class Card {
    private int id = 1;
    private final String cardNumber;
    private final String pin;
    private int balance = 0;

    public Card() {
        cardNumber = CardUtil.createRandomCardNumber();
        pin = CardUtil.createRandomPin();
    }

    public Card(int id, String cardNumber, String pin, int balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addIncome(int balance) {
        this.balance += balance;
    }

    @Override
    public String toString() {
        return "card.Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                '}';
    }
}

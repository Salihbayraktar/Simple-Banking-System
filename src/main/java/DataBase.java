import card.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class DataBase {

    private final String fileName;
    private final String url;

    public DataBase(String fileName) {
        this.fileName = fileName;
        this.url =  "jdbc:sqlite:" + fileName;
        createNewFile();
        createCardTable();
    }

    public void createNewFile() {
        File file = new File(this.fileName);
        if (!file.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createCardTable() {

        String sql = """
                        CREATE TABLE IF NOT EXISTS "card" (
                                "id"\tINTEGER,
                                "number"\tTEXT,
                                "pin"\tTEXT,
                                "balance"\tINTEGER DEFAULT 0
                )""";

        try (Connection conn = DriverManager.getConnection(this.url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insert(int id, String cardNumber, String pin) {

        String sql = "INSERT INTO card(id,number,pin) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, cardNumber);
            pstmt.setString(3, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    public Card getCard(String cardNumber, String pin) {

        String sql = String.format("SELECT * FROM card WHERE number = '%s' and pin = '%s'", cardNumber, pin);

        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            int id = rs.getInt("id");
            int balance = rs.getInt("balance");
            return new Card(id, cardNumber, pin, balance);

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        return null;
    }

    public Card getCard(String cardNumber) {

        String sql = String.format("SELECT * FROM card WHERE number = '%s'", cardNumber);

        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            int id = rs.getInt("id");
            String pin = rs.getString("pin");
            int balance = rs.getInt("balance");
            return new Card(id, cardNumber, pin, balance);

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        return null;
    }

    public void addIncomeToCard(Card card, int income) {

        String sql = String.format("UPDATE card SET balance = balance + %d WHERE number = '%s' AND pin = '%s'", income, card.getCardNumber(), card.getPin());

        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCard(Card card) {

        String sql = String.format("DELETE FROM card WHERE id = %d AND number = '%s' AND pin = '%s' ", card.getId(), card.getCardNumber(), card.getPin());

        try (Connection conn = DriverManager.getConnection(this.url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}

package ro.itschool.exception;

public class NotEnoughMoneyInAccount extends Exception {

    public NotEnoughMoneyInAccount(String message) {
        super(message);
    }
}

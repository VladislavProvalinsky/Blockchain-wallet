package by.it.academy.blockchain.handleException;

public class NoSuchWalletException extends RuntimeException{

    public NoSuchWalletException(String message) {
        super(message);
    }
}

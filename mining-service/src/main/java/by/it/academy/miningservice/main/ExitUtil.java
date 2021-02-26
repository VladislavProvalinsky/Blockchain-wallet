package by.it.academy.miningservice.main;

import java.util.Scanner;

public class ExitUtil {

    public static String exit(Scanner scanner, Thread verificationThread) {
        String exit = scanner.nextLine();
        if (exit.equals("STOP")) System.exit(0);
        if (exit.equals("STOP-V")) verificationThread.interrupt();
        return exit(scanner, verificationThread);
    }
}

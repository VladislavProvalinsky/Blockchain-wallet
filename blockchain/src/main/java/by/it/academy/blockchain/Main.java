package by.it.academy.blockchain;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println(FileSystems.getDefault().getRootDirectories().iterator().next());

    }
}

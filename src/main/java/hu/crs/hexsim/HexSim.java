package hu.crs.hexsim;

import hex.Hex;
import hex.HexBoard;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexSim implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(HexSim.class, args);
    }

    @Override
    public void run(String... args) {
        var hexBoard = new HexBoard<>(3, 4, Hex.EMPTY);
        System.out.println("Hello World " + hexBoard.size());
    }
}
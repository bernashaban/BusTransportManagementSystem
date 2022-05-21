package project.view.employee.destination;

import project.model.Destination;

import java.util.Scanner;

public class AddDestinationDialog {
    public static Scanner scanner = new Scanner(System.in);

    public Destination input() {
        var destination = new Destination();
        while (destination.getDestination() == null) {
            System.out.println("Име на дестинация:");
            var answer = scanner.nextLine();
            destination.setDestination(answer);
        }
        return destination;
    }
}

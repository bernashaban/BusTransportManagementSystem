package project.view.employee.destination;


import project.model.Destination;
import project.service.impl.DestinationService;

import java.util.Scanner;

public class UpdateDestinationDialog {
    public static Scanner scanner = new Scanner(System.in);
    private DestinationService destinationService;

    public UpdateDestinationDialog(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    public Destination input() {
        Destination destination = null;
        while (destination == null) {
            System.out.println("ID на дестинация:");
            var answer = scanner.nextLine();
            int id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Моля въведете число.");
            }
            destination = destinationService.getById(id);
            if (destination == null) {
                System.out.println("Дестинация с такова ID не съществува!");
            }
        }
        destination.setDestination(null);
        while (destination.getDestination() == null) {
            System.out.println("Име на дестинация:");
            var answer = scanner.nextLine();
            destination.setDestination(answer);
        }
        return destination;
    }
}

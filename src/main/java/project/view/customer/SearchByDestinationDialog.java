package project.view.customer;

import project.model.Destination;
import project.model.Timetable;
import project.service.impl.DestinationService;
import project.service.impl.TimetableService;

import java.util.List;
import java.util.Scanner;

public class SearchByDestinationDialog {
    public static Scanner scanner = new Scanner(System.in);
    public TimetableService timetableService;
    public DestinationService destinationService;

    public SearchByDestinationDialog(TimetableService timetableService, DestinationService destinationService) {
        this.timetableService = timetableService;
        this.destinationService = destinationService;
    }

    public List<Timetable> input() {
        Destination destination = null;
        while (destination == null) {
            System.out.println("Име на дестинация: ");
            var answer = scanner.nextLine();
            try {
                destination = destinationService.getByName(answer);
            } catch (NullPointerException e) {
                System.out.println("Грешка! Не съществува дестинация с такова име!");
            }
        }
        return timetableService.getByDestination(destination);
    }
}

package project.view.employee.timetable;

import project.model.Destination;
import project.model.Timetable;
import project.service.impl.DestinationService;
import project.service.impl.TimetableService;

import java.util.Scanner;

public class SearchTimetableByIdDialog {
    public static Scanner scanner = new Scanner(System.in);
    private TimetableService timetableService;


    public SearchTimetableByIdDialog(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public Timetable input() {
        Timetable timetable = null;
        while (timetable == null) {
            System.out.println("ID на разписание: ");
            var answer = scanner.nextLine();
            int id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Моля въведете число.");
            }
            timetable = timetableService.getById(id);
            if (timetable == null) {
                System.out.println("Разписание с такова ID не съществува!");
            }
        }
        return timetable;
    }
}

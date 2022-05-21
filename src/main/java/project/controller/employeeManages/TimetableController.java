package project.controller.employeeManages;

import project.service.impl.DestinationService;
import project.service.impl.TimetableService;
import project.view.Menu;
import project.view.employee.timetable.AddTimetableDialog;
import project.view.employee.timetable.SearchTimetableByIdDialog;
import project.view.employee.timetable.UpdateTimetableDialog;

import java.util.List;
import java.util.Scanner;

public class TimetableController {
    public static Scanner scanner = new Scanner(System.in);
    private TimetableService timetableService;
    private DestinationService destinationService;

    public TimetableController(TimetableService timetableService, DestinationService destinationService) {
        this.timetableService = timetableService;
        this.destinationService = destinationService;
    }

    public void init() {
        var menu = new Menu("Разписания", List.of(
                new Menu.Option("Виж всички позиции", () -> {
                    timetableService.getAll().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Добави разписание", () -> {
                    var created = new AddTimetableDialog(destinationService).input();
                    timetableService.add(created);
                    return "Разписанието е добавена успешно!";
                }),
                new Menu.Option("Актуализирай разписание", () -> {
                    var updating = new UpdateTimetableDialog(destinationService, timetableService).input();
                    timetableService.update(updating);
                    return "Разписанието е актуализирана успшено!";
                }),
                new Menu.Option("Търси разписание по ID", () -> {
                    var timetable = new SearchTimetableByIdDialog(timetableService).input();
                    System.out.println(timetable);
                    return "";
                }),
                new Menu.Option("Изтрий разписание", () -> {
                    var deleted = new SearchTimetableByIdDialog(timetableService).input();
                    timetableService.delete(deleted.getId());
                    return "Разписанието е изтрита успешно!";
                })
        ));
        menu.show();
    }
}

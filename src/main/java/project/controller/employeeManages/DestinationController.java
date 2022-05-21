package project.controller.employeeManages;

import project.service.impl.DestinationService;
import project.view.Menu;
import project.view.employee.destination.AddDestinationDialog;
import project.view.employee.destination.SearchDestinationByIdDialog;
import project.view.employee.destination.UpdateDestinationDialog;

import java.util.List;
import java.util.Scanner;

public class DestinationController {
    public static Scanner scanner = new Scanner(System.in);
    private DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }
    public void init() {
        var menu = new Menu("Дестинации", List.of(
                new Menu.Option("Виж всички дестинации", () -> {
                    destinationService.getAll().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Добави дестинация", () -> {
                    var created = new AddDestinationDialog().input();
                    destinationService.add(created);
                    return "Дестинацията е добавена успешно!";
                }),
                new Menu.Option("Актуализирай дестинация", () -> {
                    var updating = new UpdateDestinationDialog(destinationService).input();
                    destinationService.update(updating);
                    return "Дестинацията е актуализирана успшено!";
                }),
                new Menu.Option("Търси дестинация по ID", () -> {
                    var destination = new SearchDestinationByIdDialog(destinationService).input();
                    System.out.println(destination);
                    return "";
                }),
                new Menu.Option("Изтрий дестинация", () -> {
                    var deleted = new SearchDestinationByIdDialog(destinationService).input();
                    destinationService.delete(deleted.getId());
                    return "Дестинацията е изтрита успешно!";
                })
        ));
        menu.show();
    }
}

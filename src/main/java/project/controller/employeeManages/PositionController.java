package project.controller.employeeManages;

import project.service.impl.PositionService;
import project.view.Menu;
import project.view.employee.position.AddPositionDialog;
import project.view.employee.position.SearchPositionByIdDialog;
import project.view.employee.position.UpdatePositionDialog;

import java.util.List;
import java.util.Scanner;

public class PositionController {
    public static Scanner scanner = new Scanner(System.in);
    private PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    public void init() {
        var menu = new Menu("Позиции", List.of(
                new Menu.Option("Виж всички позиции", () -> {
                    positionService.getAll().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Добави позиция", () -> {
                    var created = new AddPositionDialog().input();
                    positionService.add(created);
                    return "Позицията е добавена успешно!";
                }),
                new Menu.Option("Актуализирай позиция", () -> {
                    var updating = new UpdatePositionDialog(positionService).input();
                    positionService.update(updating);
                    return "Позицията е актуализирана успшено!";
                }),
                new Menu.Option("Търси позиция по ID", () -> {
                    var destination = new SearchPositionByIdDialog(positionService).input();
                    System.out.println(destination);
                    return "";
                }),
                new Menu.Option("Изтрий позиция", () -> {
                    var deleted = new SearchPositionByIdDialog(positionService).input();
                    positionService.delete(deleted.getId());
                    return "Позицията е изтрита успешно!";
                })
        ));
        menu.show();
    }
}

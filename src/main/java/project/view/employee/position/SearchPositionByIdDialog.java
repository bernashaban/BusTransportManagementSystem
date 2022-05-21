package project.view.employee.position;

import project.model.Position;
import project.service.impl.PositionService;

import java.util.Scanner;

public class SearchPositionByIdDialog {
    public static Scanner scanner = new Scanner(System.in);
    private PositionService positionService;

    public SearchPositionByIdDialog(PositionService positionService) {
        this.positionService = positionService;
    }

    public Position input()   {
        Position position = null;
        while (position == null) {
            System.out.println("ID на позиция: ");
            var answer = scanner.nextLine();
            int id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Моля въведете число.");
            }
            position = positionService.getById(id);
            if (position == null) {
                System.out.println("Позиция с такова ID не съществува!");
            }
        }
        return position;
    }
}

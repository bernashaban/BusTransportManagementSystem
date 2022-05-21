package project.view.employee.position;

import project.model.Position;
import project.service.impl.PositionService;

import java.util.Scanner;

public class UpdatePositionDialog {
    public static Scanner scanner = new Scanner(System.in);
    private PositionService positionService;

    public UpdatePositionDialog(PositionService positionService) {
        this.positionService = positionService;
    }

    public Position input()  {
        Position position = null;
        while (position == null) {
            System.out.println("ID на позиция:");
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
        position.setPosition(null);
        while (position.getPosition() == null) {
            System.out.println("Име на позиция:");
            var answer = scanner.nextLine();
            position.setPosition(answer);
        }
        return position;
    }
}

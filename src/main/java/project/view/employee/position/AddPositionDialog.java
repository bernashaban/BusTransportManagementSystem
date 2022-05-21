package project.view.employee.position;

import project.model.Position;

import java.util.Scanner;

public class AddPositionDialog {
    public static Scanner scanner = new Scanner(System.in);

    public Position input()  {
        var position = new Position();
        while (position.getPosition() == null) {
            System.out.println("Име на позиция:");
            var answer = scanner.nextLine();
            position.setPosition(answer);
        }
        return position;
    }
}

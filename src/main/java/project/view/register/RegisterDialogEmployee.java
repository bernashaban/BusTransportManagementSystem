package project.view.register;


import project.model.Employee;

import project.service.impl.PositionService;

import java.util.Scanner;

public class RegisterDialogEmployee {
    public static Scanner scanner = new Scanner(System.in);
    private PositionService positionService;

    public RegisterDialogEmployee(PositionService positionService) {
        this.positionService = positionService;
    }

    public Employee input() {
        Employee employee = new Employee();
        while (employee.getUsername() == null) {
            System.out.println("Потребителско име: ");
            var answer = scanner.nextLine();
            if (answer.length() < 2 || answer.length() > 15) {
                System.out.println("Грешка: Потребителското име трябва да бъде между 2 и 15 символа.\"");
            } else {
                employee.setUsername(answer);
            }
        }
        while (employee.getPassword() == null) {
            System.out.println("Парола: ");
            var answer = scanner.nextLine();
            if (answer.length() < 2 || answer.length() > 15) {
                System.out.println("Грешка: Паролата трябва да бъде между 2 и 15 символа.\"");
            } else {
                employee.setPassword(answer);
            }
        }
        while (employee.getName() == null) {
            System.out.println("Име и фамилия: ");
            var answer = scanner.nextLine();
            if (answer.length() < 2 || answer.length() > 15) {
                System.out.println("Грешка: Името и фамилията трябва да е между 2 и 20 символа.\"");
            } else {
                employee.setName(answer);
            }
        }
        while (employee.getPhone() == null) {
            System.out.println("Телефон: ");
            var answer = scanner.nextLine();
            if (answer.length() != 10) {
                System.out.println("Грешка: Телефона трябва да бъде точно 10 символа\"");
            } else {
                employee.setPhone(answer);
            }
        }
        while (employee.getPosition() == null) {
            System.out.println("Моля изберете от наличните позиции:");
            positionService.getAll().forEach(System.out::println);
            System.out.println("Позиция ID: ");
            var answer = scanner.nextLine();
            int positionId = 0;
            try {
                positionId = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Моля, въведете валиден номер!");
            }
            var position = positionService.getById(positionId);
            if (position == null) {
                System.out.println("Моля, въведете съществуващо ID!");
            } else {
                employee.setPosition(position);
            }
        }
        return employee;
    }
}

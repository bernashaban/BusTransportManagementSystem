package project.view.employee.timetable;

import project.model.Destination;
import project.model.Timetable;
import project.service.impl.DestinationService;

import java.sql.Timestamp;
import java.util.Scanner;

public class AddTimetableDialog {
    public static Scanner scanner = new Scanner(System.in);
    private DestinationService destinationService;

    public AddTimetableDialog(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    public Timetable input() {
        var timetable = new Timetable();
        Destination destination = null;
        while (destination == null) {
            System.out.println("ID на дестинация: ");
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
            } else {
                timetable.setDestination(destination);
            }
        }
        while (timetable.getDateFrom() == null) {
            System.out.println("Начало на превозване");
            System.out.println("1 - 01.06.2022");
            System.out.println("2 - 01.07.2022");
            System.out.println("3 - 01.08.2022");
            System.out.println("4 - 01.09.2022");
            System.out.println("5 - 01.10.2022");
            System.out.println("6 - 01.11.2022");
            System.out.println("7 - 01.12.2022");
            System.out.println("Избор: ");
            var answer = scanner.nextLine();
            Timestamp timestamp = null;
            switch (answer) {
                case "1":
                    timestamp = new Timestamp(2022 - 1900, 5, 1, 7, 0, 0, 0);
                    break;
                case "2":
                    timestamp = new Timestamp(2022 - 1900, 6, 1, 7, 0, 0, 0);
                    break;
                case "3":
                    timestamp = new Timestamp(2022 - 1900, 7, 1, 7, 0, 0, 0);
                    break;
                case "4":
                    timestamp = new Timestamp(2022 - 1900, 8, 1, 7, 0, 0, 0);
                    break;
                case "5":
                    timestamp = new Timestamp(2022 - 1900, 9, 1, 7, 0, 0, 0);
                    break;
                case "6":
                    timestamp = new Timestamp(2022 - 1900, 10, 1, 7, 0, 0, 0);
                    break;
                case "7":
                    timestamp = new Timestamp(2022 - 1900, 11, 1, 7, 0, 0, 0);
                    break;
                default:
                    System.out.println("Невалиден избор!");
                    break;
            }
            if (timestamp!=null){
                timetable.setDateFrom(timestamp);
            }
        }
        while (timetable.getMembersCount() == 0) {
            System.out.println("Брой пътници:");
            var answer = scanner.nextLine();
            int number = 0;
            try {
                number = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на брой. Моля въведете число.");
            }
            if (number <=0 || number>50) {
                System.out.println("Въвели сте невалиден брой за пътници! (1-50)");
            } else {
                timetable.setMembersCount(number);
            }
        }
        while (timetable.getPrice() == 0) {
            System.out.println("Цена:");
            var answer = scanner.nextLine();
            double price = 0;
            try{
                price = Double.parseDouble(answer);
            }catch (NumberFormatException e){
                System.out.println("Невалиден формат на цена. Моля въведете число.");
            }
            if (price <=0 || price>100) {
                System.out.println("Грешка! Моля въведете валидна цена! (1.00-100.00)");
            } else {
                timetable.setPrice(price);
            }
        }
        return timetable;
    }
}

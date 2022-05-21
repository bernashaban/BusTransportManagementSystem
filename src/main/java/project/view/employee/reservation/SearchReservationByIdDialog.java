package project.view.employee.reservation;

import project.model.Reservation;
import project.service.impl.ReservationService;

import java.util.Scanner;

public class SearchReservationByIdDialog {
    public static Scanner scanner = new Scanner(System.in);
    private ReservationService reservationService;

    public SearchReservationByIdDialog(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Reservation input() {
        Reservation reservation = null;
        while (reservation == null) {
            System.out.println("ID на резервация: ");
            var answer = scanner.nextLine();
            int id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Моля въведете число.");
            }
            reservation = reservationService.getById(id);
            if (reservation == null) {
                System.out.println("Резервация с такова ID не съществува!");
            }
        }
        return reservation;
    }
}

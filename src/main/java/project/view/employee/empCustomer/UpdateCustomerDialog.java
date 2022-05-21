package project.view.employee.empCustomer;

import project.model.Customer;
import project.service.impl.CustomerService;

import java.util.Scanner;

public class UpdateCustomerDialog {
    public static Scanner scanner = new Scanner(System.in);
    private CustomerService customerService;

    public UpdateCustomerDialog(CustomerService customerService) {
        this.customerService = customerService;
    }
    public Customer input() {
        Customer customer = null;
        while (customer == null) {
            System.out.println("ID на потребител: ");
            var answer = scanner.nextLine();
            int id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Моля въведете число.");
            }
            customer = customerService.getById(id);
            if (customer == null) {
                System.out.println("Потребител с такова ID не съществува!");
            }
        }
        customer.setUsername(null);
        while (customer.getUsername() == null) {
            System.out.println("Потребителско име: ");
            var answer = scanner.nextLine();
            if (answer.length() < 2 || answer.length() > 15) {
                System.out.println("Грешка: Потребителското име трябва да бъде между 2 и 15 символа.");
            } else {
                customer.setUsername(answer);
            }
        }
        customer.setPassword(null);
        while (customer.getPassword() == null) {
            System.out.println("Парола: ");
            var answer = scanner.nextLine();
            if (answer.length() < 2 || answer.length() > 15) {
                System.out.println("Грешка: Паролата трябва да бъде между 2 и 15 символа.\"");
            } else {
                customer.setPassword(answer);
            }
        }
        customer.setName(null);
        while (customer.getName() == null) {
            System.out.println("Име и фамилия: ");
            var answer = scanner.nextLine();
            if (answer.length() < 2 || answer.length() > 20) {
                System.out.println("Грешка: Името и фамилията трябва да е между 2 и 20 символа.\"");
            } else {
                customer.setName(answer);
            }
        }
        customer.setPassword(null);
        while (customer.getPhone() == null) {
            System.out.println("Телефон: ");
            var answer = scanner.nextLine();
            if (answer.length() != 10) {
                System.out.println("Грешка: Телефона трябва да бъде точно 10 символа\"");
            } else {
                customer.setPhone(answer);
            }
        }
        return customer;
    }
}

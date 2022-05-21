package project.view.customer;

import project.model.Customer;
import project.service.impl.CustomerService;

import java.util.Scanner;

public class UpdateDataDialog {
    private CustomerService customerService;
    public static Scanner scanner = new Scanner(System.in);
    private Customer customer;

    public UpdateDataDialog(CustomerService customerService, Customer customer) {
        this.customerService = customerService;
        this.customer = customer;
    }

    public Customer input() {
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
            if (answer.length() < 2 || answer.length() > 15) {
                System.out.println("Грешка: Името и фамилията трябва да е между 2 и 20 символа.\"");
            } else {
                customer.setName(answer);
            }
        }
        customer.setPhone(null);
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

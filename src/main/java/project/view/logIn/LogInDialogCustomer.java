package project.view.logIn;


import project.model.Customer;
import project.service.impl.CustomerService;

import java.util.Scanner;

public class LogInDialogCustomer  {
    public static Scanner scanner = new Scanner(System.in);
    public CustomerService customerService;

    public LogInDialogCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer input() {
        Customer customer = null;
        while (customer == null) {
            System.out.println("Потребителско име: ");
            var answer = scanner.nextLine();
            try {
                customer = customerService.findCustomerByUsername(answer);
            } catch (NullPointerException e) {
                System.out.println("Потребителят не съществува.");
            }
            if (customer != null) {
                System.out.println("Парола:");
                var pass = scanner.nextLine();
                while (true) {
                    if (pass.equals(customer.getPassword())) {
                        return customer;
                    }
                    System.out.println("Грешна парола. Опитайте отново");
                    pass = scanner.nextLine();
                }
            } else {
                System.out.println("Грешка: Клиент с потребителско име '" + answer + "' не съществува.");
            }
        }
        System.out.println("Неуспешен вход.");
        return null;
    }

}

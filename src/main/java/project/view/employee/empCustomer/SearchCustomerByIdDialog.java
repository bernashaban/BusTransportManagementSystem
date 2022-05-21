package project.view.employee.empCustomer;

import project.model.Customer;
import project.service.impl.CustomerService;

import java.util.Scanner;

public class SearchCustomerByIdDialog {
    public static Scanner scanner = new Scanner(System.in);
    private CustomerService customerService;

    public SearchCustomerByIdDialog(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer input(){
        Customer customer =null;
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
        return customer;
    }
}

package project.controller.employeeManages;

import project.service.impl.CustomerService;
import project.view.Menu;
import project.view.employee.empCustomer.SearchCustomerByIdDialog;
import project.view.employee.empCustomer.UpdateCustomerDialog;
import project.view.register.RegisterDialogCustomer;

import java.util.List;
import java.util.Scanner;

public class EmpCustomerController {
    public static Scanner scanner = new Scanner(System.in);
    private CustomerService customerService;

    public EmpCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void init() {
        var menu = new Menu("Потребители", List.of(
                new Menu.Option("Виж всички потребители", () -> {
                    customerService.getAll().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Добави потребител", () -> {
                    var created = new RegisterDialogCustomer().input();
                    customerService.add(created);
                    return "Потребителят е добавена успешно!";
                }),
                new Menu.Option("Актуализирай потребител", () -> {
                    var updating = new UpdateCustomerDialog(customerService).input();
                    customerService.update(updating);
                    return "Потребителят е актуализирана успшено!";
                }),
                new Menu.Option("Търси потребител по ID", () -> {
                    var customer = new SearchCustomerByIdDialog(customerService).input();
                    System.out.println(customer);
                    return "";
                }),
                new Menu.Option("Изтрий потребител", () -> {
                    var deleted = new SearchCustomerByIdDialog(customerService).input();
                    customerService.delete(deleted.getId());
                    return "Потребителят е изтрита успешно!";
                })
        ));
        menu.show();
    }
}

package project.controller.employeeManages;

import project.service.impl.EmployeeService;
import project.service.impl.PositionService;
import project.view.Menu;
import project.view.employee.empEmployee.SearchEmployeeByIdDialog;
import project.view.employee.empEmployee.UpdateEmployeeDialog;
import project.view.register.RegisterDialogEmployee;

import java.util.List;
import java.util.Scanner;

public class EmpEmployeeController {
    public static Scanner scanner = new Scanner(System.in);
    private EmployeeService employeeService;
    private PositionService positionService;

    public EmpEmployeeController(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    public void init() {
        var menu = new Menu("Служители", List.of(
                new Menu.Option("Виж всички служители", () -> {
                    employeeService.getAll().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Добави служител", () -> {
                    var created = new RegisterDialogEmployee(positionService).input();
                    employeeService.add(created);
                    return "Служителят е добавена успешно!";
                }),
                new Menu.Option("Актуализирай служител", () -> {
                    var updating = new UpdateEmployeeDialog(employeeService,positionService).input();
                    employeeService.update(updating);
                    return "Служителят е актуализирана успшено!";
                }),
                new Menu.Option("Търси служител по ID", () -> {
                    var employee = new SearchEmployeeByIdDialog(employeeService).input();
                    System.out.println(employee);
                    return "";
                }),
                new Menu.Option("Изтрий служител", () -> {
                    var deleted = new SearchEmployeeByIdDialog(employeeService).input();
                    employeeService.delete(deleted.getId());
                    return "Служителят е изтрита успешно!";
                })
        ));
        menu.show();
    }
}

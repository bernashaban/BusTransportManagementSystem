package project.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static class Option {
        private String text;
        private Command command;

        public Option(String text, Command command) {
            this.text = text;
            this.command = command;
        }

        public String getText() {
            return text;
        }

        public Command getCommand() {
            return command;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Option{");
            sb.append("text='").append(text).append('\'');
            sb.append(", command=").append(command);
            sb.append('}');
            return sb.toString();
        }
    }

    private String title;
    private List<Option> options = List.of(new Option("Изход", new ExitCommand()));
    private Scanner scanner = new Scanner(System.in);

    public Menu() {
    }

    public Menu(String title, List<Option> options) {
        this.title = title;
        var oldOptions = this.options;
        this.options = new ArrayList<>();
        this.options.addAll(options);
        this.options.addAll(oldOptions);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu menu = (Menu) o;

        if (getTitle() != null ? !getTitle().equals(menu.getTitle()) : menu.getTitle() != null) return false;
        return getOptions() != null ? getOptions().equals(menu.getOptions()) : menu.getOptions() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getOptions() != null ? getOptions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("title='").append(title).append('\'');
        sb.append(", options=").append(options);
        sb.append('}');
        return sb.toString();
    }

    public void show() {
        while (true) {
            System.out.printf("\nMENU: %s%n", title);
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%2d. %s%n", i + 1, options.get(i).getText());
            }
            int choice = -1;
            do {
                System.out.printf("Въведете своя избор:(1-%s):", options.size());
                var choiceStr = scanner.nextLine();
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException ex) {
                    System.out.println("Грешка: Невалиден избор. Моля, изберете валиден номер между 1 и " + options.size());
                }
            } while (choice < 1 || choice > options.size());
            String result = null;
            try {
                result = options.get(choice - 1).getCommand().execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (choice == options.size()) {
                break;
            }
        }
    }
}

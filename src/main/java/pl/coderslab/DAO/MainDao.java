package pl.coderslab.DAO;

import pl.coderslab.ConsoleColors;
import pl.coderslab.entity.User;
import java.sql.SQLException;
import java.util.Scanner;

public class MainDao {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.PURPLE + "WELCOME TO USERS DATABASE!" + "\n");
        System.out.println();

        while (true) {
            System.out.println(ConsoleColors.BLUE + "Please select an option:");
            showmenu();
            String line = scanner.next();
            if ("exit".equals(line)) {
                System.out.println(ConsoleColors.RED + "Bye, bye");
                scanner.close();
                break;
            }
            switch (line) {
                case "add":
                addUser();
                    System.out.println();
                    break;

                case "read":
                readUser();
                    System.out.println();
                    break;

                case "update":
                updateUser();
                    System.out.println();
                    break;

                case "delete":
                deleteUser();
                    System.out.println();
                    break;

                case "findAll":
                findAll();
                    System.out.println();
                    break;

                default:
                    System.out.println("Please select a correct option.");
            }
        }

    }

    public static User addUser(){
        String email;
        String username;
        String password;
        User user = new User();
        UserDao userDao = new UserDao();
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        Scanner scanner3 = new Scanner(System.in);

        System.out.println("Please type email");

        while (!scanner1.hasNext()) {
            scanner1.next();
            System.out.println("Incorrect email!");
        }
        email = scanner1.nextLine();


        System.out.println("Please type username");
        while (!scanner2.hasNextLine()) {
            scanner2.nextLine();
            System.out.println("Incorrect username");
        }
        username = scanner2.next();


        System.out.println("Please type password");
        while (!scanner3.hasNext()) {
            scanner2.next();
            System.out.println("Incorrect password");
        }
        password = scanner3.next();

        user.setAllParameters(username, email, password);
        userDao.create(user);

        System.out.println(ConsoleColors.YELLOW + "User was successfully added!");

        return user;

    }

    public static User readUser(){

        int userID;
        User user = new User();
        UserDao userDao = new UserDao();
        Scanner scanner4 = new Scanner(System.in);
        System.out.println("Please type user id to read");
        while (!scanner4.hasNextInt()) {
            scanner4.next();
            System.out.println("Incorrect id!");
        }
        userID = scanner4.nextInt();
        try {
            if (userDao.read(userID) == null){
                System.out.println("Record doesn't exist in database!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;

    }

    public static User updateUser(){
        int id;
        String email;
        String username;
        String password;
        User user = new User();
        UserDao userDao = new UserDao();
        Scanner scanner5 = new Scanner(System.in);
        Scanner scanner6 = new Scanner(System.in);
        Scanner scanner7 = new Scanner(System.in);
        Scanner scanner8 = new Scanner(System.in);
        System.out.println("Please type user id to update");
        while (!scanner5.hasNextInt()) {
            scanner5.next();
            System.out.println("Incorrect id!");
        }
        id = scanner5.nextInt();

        System.out.println("Please type email");

        while (!scanner6.hasNext()) {
            scanner6.next();
            System.out.println("Incorrect email!");
        }
        email = scanner6.nextLine();


        System.out.println("Please type username");
        while (!scanner7.hasNext()) {
            scanner7.next();
            System.out.println("Incorrect username");
        }
        username = scanner7.next();


        System.out.println("Please type password");
        while (!scanner8.hasNext()) {
            scanner8.next();
            System.out.println("Incorrect password");
        }
        password = scanner8.next();

        user.setAllParameters(id,username, email, password);
        try {
            userDao.update(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(ConsoleColors.YELLOW + "User was successfully updated!");

        return user;
    }

    public static User deleteUser(){
        int userID;
        User user = new User();
        UserDao userDao = new UserDao();
        Scanner scanner9 = new Scanner(System.in);

        System.out.println("Please select id user to delete");

        while (!scanner9.hasNextInt()) {
            scanner9.next();
            System.out.println("Incorrect email!");
        }
        userID = scanner9.nextInt();
        try {
            userDao.delete(userID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(ConsoleColors.YELLOW + "User was successfully deleted!");

        return user;

    }

    public static void findAll(){
        UserDao userDao = new UserDao();
        try {
            User[] userList = new User[0];
            userList = userDao.findALL();
            for (User user:userList) {
                System.out.println(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    private static void showmenu() {
        String[] menuTab = {"add", "read", "update", "delete", "findAll", "exit"};
        for (int i = 0; i < menuTab.length; i++) {
            System.out.println(ConsoleColors.RESET + menuTab[i]);
        }

    }

}



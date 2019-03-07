package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Login {

    private static final UserBaseService USB = new UserBaseService();

    public static User login(Scanner sc) throws IOException {
        User user = null;
        while (user == null) {
            System.out.println("Input number: 1 - login; 2 - sigh in; 3 - end program");
            String opt = sc.nextLine();
            if (opt.equals("2")) {
                SighUp(sc);
                continue;
            }else if(opt.equals("1")){
                user = SighIn(sc);
                continue;
            }
            break;
        }
        return user;
    }

    private static User SighIn(Scanner sc) {
        System.out.println("Enter Username");
        String login = sc.nextLine();
        if(!UserBaseService.containsUser(login)){
            System.out.println("No such user");
            return null;
        }
        System.out.println("Enter Password");
        User user = comparePass(login, sc.nextLine());
        if(user == null){
            System.out.println("Wrong Password");
            return  null;
        }
        return user;
    }

    private static void SighUp(Scanner sc) {
        while (true){
            System.out.println("Enter Username:");
            String login = sc.nextLine();
            if (UserBaseService.containsUser(login)){
                System.out.println("Username already taken");
                continue;
            }else if(login.length() < 3){
                System.out.println("Username is to short");
                continue;
            }
            System.out.println("Enter Password:");
            String pass = sc.nextLine();
            if(pass.length() < 6){
                System.out.println("Password is to short");
                continue;
            }
            System.out.println("Enter Password again:");
            if(!pass.equals(sc.nextLine())){
                System.out.println("Passwords are not equals");
                continue;
            }
            try {
                USB.addUser(login, pass);
            } catch (IOException e) {
                System.err.println("Something goes wrong, try again later");
                break;
            }

            System.out.println("Registration complete");
            break;
        }
    }



    public static User comparePass(String login, String pass){
        User[] userBase = USB.getUsers();
        if(userBase == null){return null;}
        for (User u: userBase) {
            if(u != null & u.getLogin().equals(login)){
                if(u.getPassword().equals(pass)){
                   return u;
                }
            }
        }
        return null;
    }
}

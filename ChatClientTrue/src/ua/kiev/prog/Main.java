package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		User user = null;
		try {
			user = Login.login(scanner);
			if(user == null) return;

			user.setOnline(true);

			Thread th = new Thread(new GetThread(user));
			th.setDaemon(true);
			th.start();

			System.out.println("[@username - send private mesage][/userlist - to see all users]Enter your message:");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) {

					break;
				}
                String to = null;
                if(text.equals("/userlist")){
                	UserBaseService.printUsers();
                	continue;
				}
				if (text.indexOf("@") == 0){
                    to = getDestination(text);
                }
				Message m = new Message(user.getLogin(), text, to);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occured: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
			System.out.println(user.setOnline(false));
		}
	}
	public static String getDestination(String text){
		int end = text.indexOf(" ");
		if (end < 3){
			return null;
		}
		String username = text.substring(1, end);
		if(UserBaseService.containsUser(username)){
			return username;
		}
		return null;
	}

}

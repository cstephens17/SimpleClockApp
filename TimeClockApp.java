// Carson Stephens
// 2022
// Paychex coding exercise
// Time Clock Application

import java.util.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  
import java.io.*;


public class TimeClockApp {    
    private static Scanner in = new Scanner(System.in);
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static Employee currentUser;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	private static String header = " TYPE\t  STATUS\t         START TIME\t           END TIME";
	
	private void printCurrentTime() {
		LocalDateTime dt = LocalDateTime.now();
		String now = dt.format(formatter);
		System.out.println("\tThe current time is now " + now);
	}
    
    private void welcome() {
        currentUser = null;
        String option = "";
        while (true)
        {
			System.out.println("\nWELCOME");
			printCurrentTime();
            System.out.println("\tSelect an option by entering the appropriate number.");
            System.out.println("1\tLogin\n2\tRegister\n0\tQuit");
			option = in.nextLine();
			
			switch (option) {
				case "1": {
					login();
					break;
				}
				case "2": {
					register();
					break;
				}	
				case "0": {
					System.out.println("Quitting...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
        }
    }
    
    private void login() {
        String option = "";
        String uInput = "";
        String pInput = "";
        while (true)
		{
            System.out.println("\nLOGIN");
			System.out.println("\tFor demo purposes, sample employee login information is \"emp0001\" and \"pass1234\" (no quotation marks). For admin, it's \"admin\" and \"password\" (no quotation marks).");
			printCurrentTime();
            System.out.println("\tSelect an option by entering the appropriate number.");
            System.out.println("1\tAttempt Login\n0\tReturn to Welcome Screen");
			option = in.nextLine();
			
            switch (option) {
                case "1": {
                    System.out.print("Username: ");
                    uInput = in.nextLine();
                    System.out.print("Password: ");
                    pInput = in.nextLine();
                    for (Employee emp : employees) {
                        if (emp.getUsername().equals(uInput) && emp.getPassword().equals(pInput)) {
                            currentUser = emp;
							break;
                        }
                    }
					if (currentUser == null) {
						System.out.println("ERROR! Invalid username/password.");
					}
					else {
						System.out.println("Login successful! Welcome back, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
						mainMenu();
					}
					break;
                }
                case "0": {
					System.out.println("Returning to welcome screen...");
					return;
				}
                default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
            }
        }
    }
    
    private void register() {
        String option = "";
		String f = "";
		String l = "";
		String u = "";
		String p = "";
		boolean unique;
		while (true) {
			System.out.println("\nUSER REGISTRATION");
			printCurrentTime();
			System.out.println("\tSelect an option by entering the appropriate number.");
            System.out.println("1\tRegister User\n0\tReturn to Welcome Screen");
			option = in.nextLine();
			unique = true;
			switch (option) {
				case "1": {
					System.out.print("First Name: ");
					f = in.nextLine();
					if (f.length() == 0) {
						System.out.println("ERROR! Please enter a valid first name.");
						break;
					}
					System.out.print("Last Name: ");
					l = in.nextLine();
					if (l.length() == 0) {
						System.out.println("ERROR! Please enter a valid first name.");
						break;
					}
					System.out.print("Username: ");
					u = in.nextLine();
					if (u.length() == 0) {
						System.out.println("ERROR! Please enter a valid username.");
						break;
					}
					System.out.print("Password (must be 8-16 characters): ");
					p = in.nextLine();
					if (p.length() < 8 || p.length() > 16) {
						System.out.println("ERROR! Please enter a valid password.");
						break;
					}
					for (Employee e : employees) {
						if (e.getUsername().equals(u)) {
							unique = false;
							break;
						}
					}
					if (!unique) {
						System.out.println("ERROR! Username already exists.");
						break;
					}
					Employee newEmployee = new Employee(u, p, f, l, false);
					employees.add(newEmployee);
					System.out.println("User registration for " + newEmployee.getFirstName() + " " + newEmployee.getLastName() + " completed. Returning to welcome screen...");
					break;
				}
				case "0": {
					System.out.println("Returning to welcome screen...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
				}
			}
		}
    }
	
	private void mainMenu() {
		String option = "";
		while (true) {
			System.out.println("\nMAIN MENU");
			System.out.println("\tLogged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName());
			printCurrentTime();
			if (currentUser.onShift()) {
				System.out.println("\tYou are on an active shift");
			}
			if (currentUser.onBreak()) {
				System.out.println("\tYou are on break");
			}
			if (currentUser.onLunch()) {
				System.out.println("\tYou are on lunch");
			}
			System.out.println("\tSelect an option by entering the appropriate number.");
			if (!currentUser.getIsAdmin()) {
				System.out.println("1\tStart Shift\n2\tEnd Shift\n3\tView Shifts\n4\tTake/Finish Break\n5\tTake/Finish Lunch\n0\tLogout");
			}
			else {
				System.out.println("1\tStart Shift\n2\tEnd Shift\n3\tView Shifts\n4\tTake/Finish Break\n5\tTake/Finish Lunch\n6\tView All Shifts\n0\tLogout");
			}
			option = in.nextLine();
			switch (option) {
				case "1": {
					if (currentUser.onShift() && !currentUser.getIsAdmin()) {
						System.out.println("ERROR! You are already on an active shift.");
					}
					else {
						startShift();
					}
					break;
				}
				case "2": {
					if (!currentUser.onShift()) {
						System.out.println("ERROR! You are not on an active shift.");
						break;
					}
					if (currentUser.onBreak() && !currentUser.getIsAdmin()) {
						System.out.println("ERROR! You are on break.");
						break;
					}
					if (currentUser.onLunch() && !currentUser.getIsAdmin()) {
						System.out.println("ERROR! You are on lunch.");
						break;
					}
					else {
						endShift();
					}
					break;
				}
				case "3": {
					if (currentUser.getShifts().isEmpty()) {
						System.out.println("ERROR! You have no shifts history.");
					}
					else {
						viewShifts();
					}
					break;
				}
				case "4": {
					if (currentUser.getIsAdmin() || currentUser.onShift()) {
						takeFinishBreak();
					}
					else {
						System.out.println("ERROR! You are not on an active shift.");
					}
					break;
				}
				case "5": {
					if (currentUser.getIsAdmin() || currentUser.onShift()) {
						takeFinishLunch();
					}
					else {
						System.out.println("ERROR! You are not on an active shift.");
					}
					break;
				}
				case "6": {
					if (!currentUser.getIsAdmin()) {
						System.out.println("ERROR! Invalid input.");
					}
					else {
						viewAllShifts();
					}
					break;
				}
				case "0": {
					currentUser = null;
					System.out.println("Logging out...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
				}
			}
		}
	}
	
	private void startShift() {
		String option = "";
		while (true) {
			System.out.println("\nSTART SHIFT");
			printCurrentTime();
			System.out.println("\tSelect an option by entering the appropriate number.");
			System.out.println("1\tConfirm\n0\tCancel");
			option = in.nextLine();
			switch (option) {
				case "1": {
					currentUser.start("WORK");
					System.out.println("Shift started. Returning to main menu...");
					return;
				}
				case "0": {
					System.out.println("Returning to main menu...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
		}
	}
	
	private void endShift() {
		String option = "";
		while (true) {
			System.out.println("\nEND SHIFT");
			printCurrentTime();
			if (currentUser.getIsAdmin())
			{
				int x = 0;
				System.out.println("\tSelect a shift to end by entering the appropriate number, or enter 0 to cancel");
				for (Shift sh : currentUser.getShifts()) {
					if (sh.getShiftType().equals("WORK") && sh.getIsActive()) {
						x++;
						System.out.println(x + "\t" + sh.getStartDateTime().format(formatter));
					}
				}
				System.out.println("0\tCancel");
				option = in.nextLine();
				if (option.equals("0")) {
					System.out.println("Returning to main menu...");
				}
				else {
					try {
						int i = Integer.parseInt(option);
						if (i > x) {
							System.out.println("ERROR! Invalid input.");
						}
						else {
							currentUser.end("WORK", i);
							System.out.println("Shift ended. Returning to main menu...");
							return;
						}
					}
					catch (Exception e) {
						System.out.println("ERROR! Invalid input.");
					}
				}
			}
			else
			{
				System.out.print("\tYour active shift started on ");
				for (Shift sh : currentUser.getShifts())
				{
					if (sh.getIsActive() && sh.getShiftType().equals("WORK")) {
						System.out.println(sh.getStartString());
						break;
					}
				}
				System.out.println("\tSelect an option by entering the appropriate number.");
				System.out.println("1\tConfirm\n0\tCancel");
				option = in.nextLine();
				switch (option) {
					case "1": {
						currentUser.end("WORK");
						System.out.println("Shift ended. Returning to main menu...");
						return;
					}
					case "0": {
						System.out.println("Returning to main menu...");
						return;
					}
					default: {
						System.out.println("ERROR! Invalid input.");
						break;
					}
				}
			}
		}
	}
	
	private void viewShifts() {
		String option = "";
		String header = " TYPE\t  STATUS\t         START TIME\t           END TIME";
		while (true) {
			System.out.println("VIEW SHIFTS");
			printCurrentTime();
			System.out.println("\tSelect an option by entering the appropriate number.");
			System.out.println("1\tAll\n2\tWork\n3\tBreak\n4\tLunch\n5\tActive\n6\tInactive\n0\tCancel");
			option = in.nextLine();
			switch (option) {
				case "1": {
					System.out.println(header);
					empShifts(currentUser, "ALL");
					break;
				}
				case "2": {
					System.out.println(header);
					empShifts(currentUser, "WORK");
					break;
				}
				case "3": {
					System.out.println(header);
					empShifts(currentUser, "BREAK");
					break;
				}
				case "4": {
					System.out.println(header);
					empShifts(currentUser, "LUNCH");
					break;
				}
				case "5": {
					System.out.println(header);
					empShifts(currentUser, "ACTIVE");
					break;
				}
				case "6": {
					System.out.println(header);
					empShifts(currentUser, "INACTIVE");
					break;
				}
				case "0": {
					System.out.println("Returning to main menu...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
		}
	}
	
	private void takeFinishBreak() {
		String option = "";
		while (true) {
			System.out.println("\nBREAK TIME");
			printCurrentTime();
			if (currentUser.onBreak()) {
				System.out.println("\tWould you like to finish your break now?");
			}
			else {
				System.out.println("\tWould you like to take a break now?");
			}
			System.out.println("\tSelect an option by entering the appropriate number.");
			System.out.println("1\tConfirm\n0\tCancel");
			option = in.nextLine();
			switch (option) {
				case "1": {
					if (currentUser.onBreak()) {
						currentUser.end("BREAK");
						System.out.println("Your break is now over. Returning to main menu...");
					}
					else {
						currentUser.start("BREAK");
						System.out.println("You are now on break. Returning to main menu...");
					}
					return;
				}
				case "0": {
					System.out.println("Returning to main menu...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
		}
	}
	
	private void takeFinishLunch() {
		String option = "";
		while (true) {
			System.out.println("\nLUNCH TIME");
			printCurrentTime();
			if (currentUser.onLunch()) {
				System.out.println("\tWould you like to finish your lunch now?");
			}
			else {
				System.out.println("\tWould you like to take a lunch now?");
			}
			System.out.println("\tSelect an option by entering the appropriate number.");
			System.out.println("1\tConfirm\n0\tCancel");
			option = in.nextLine();
			switch (option) {
				case "1": {
					if (currentUser.onLunch()) {
						currentUser.end("LUNCH");
						System.out.println("Your lunch is now over. Returning to main menu...");
					}
					else {
						currentUser.start("LUNCH");
						System.out.println("You are now on lunch. Returning to main menu...");
					}
					return;
				}
				case "0": {
					System.out.println("Returning to main menu...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
		}
	}
	
	private void viewAllShifts() {
		String option = "";
		while (true) {
			System.out.println("VIEW ALL SHIFTS");
			printCurrentTime();
			System.out.println("\tSelect an option by entering the appropriate number.");
			System.out.println("1\tAll\n2\tWork\n3\tBreak\n4\tLunch\n5\tActive\n6\tInactive\n7\tEmployee Lookup\n0\tCancel");
			option = in.nextLine();
			switch (option) {
				case "1": {
					for (Employee e : employees) {
						System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
						System.out.println(header);
						empShifts(e, "ALL");
					}
					System.out.println();
					break;
				}
				case "2": {
					for (Employee e : employees) {
						System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
						System.out.println(header);
						empShifts(e, "WORK");
					}
					System.out.println();
					break;
				}
				case "3": {
					for (Employee e : employees) {
						System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
						System.out.println(header);
						empShifts(e, "LUNCH");
					}
					System.out.println();
					break;
				}
				case "4": {
					for (Employee e : employees) {
						System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
						System.out.println(header);
						empShifts(e, "BREAK");
					}
					System.out.println();
					break;
				}
				case "5": {
					for (Employee e : employees) {
						System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
						System.out.println(header);
						empShifts(e, "ACTIVE");
					}
					System.out.println();
					break;
				}
				case "6": {
					for (Employee e : employees) {
						System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
						System.out.println(header);
						empShifts(e, "INACTIVE");
					}
					System.out.println();
					break;
				}
				case "7": {
					empLookup();
					break;
				}
				case "0": {
					System.out.println("Returning to main menu...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
		}
	}
	
	private void shiftLog(Shift sh) {
		System.out.format("%5s\t%8s\t" + sh.getStartString() + "\t" + sh.getEndString(), sh.getShiftType(), sh.getStatus());
		System.out.println();
	}
	
	private void empShifts(Employee e, String param) {
		switch (param) {
			case "ALL": {
				for (Shift sh : e.getShifts()) {
					shiftLog(sh);
				}
				break;
			}
			case "WORK": {
				for (Shift sh : e.getShifts()) {
					if (sh.getShiftType().equals("WORK")) {
						shiftLog(sh);
					}
				}
				break;
			}
			case "BREAK": {
				for (Shift sh : e.getShifts()) {
					if (sh.getShiftType().equals("BREAK")) {
						shiftLog(sh);
					}
				}
				break;
			}
			case "LUNCH": {
				for (Shift sh : e.getShifts()) {
					if (sh.getShiftType().equals("LUNCH")) {
						shiftLog(sh);
					}
				}
				break;
			}
			case "ACTIVE": {
				for (Shift sh : e.getShifts()) {
					if (sh.getIsActive()) {
						shiftLog(sh);
					}
				}
				break;
			}
			case "INACTIVE": {
				for (Shift sh : e.getShifts()) {
					if (!sh.getIsActive()) {
						shiftLog(sh);
					}
				}
				break;
			}
		}
		System.out.println();
	}
	
	private void empLookup() {
		String option = "";
		Employee e = null;
		while (true) {
			System.out.println("\nEMPLOYEE SEARCH");
			printCurrentTime();
			System.out.println("\tSelect an option by entering the appropriate number.");
			System.out.println("1\tSearch\n0\tCancel");
			option = in.nextLine();
			switch (option) {
				case "1": {
					System.out.print("Employee Username: ");
					String u = in.nextLine();
					if (u.length() == 0) {
						System.out.println("ERROR! Invalid input.");
					}
					else {
						for (Employee emp : employees) {
							if (emp.getUsername().equals(u)) {
								e = emp;
								System.out.println(e.getLastName() + ", " + e.getFirstName() + ": " + e.getUsername());
								System.out.println(header);
								empShifts(e, "ALL");
								break;
							}
						}
						if (e == null) {
							System.out.println("ERROR! User not found.");
						}
					}
					break;
				}
				case "0": {
					System.out.println("Returning to previous screen...");
					return;
				}
				default: {
					System.out.println("ERROR! Invalid input.");
					break;
				}
			}
		}
	}
    
    public static void main(String args[]) {
		TimeClockApp tca = new TimeClockApp();
        System.out.println("Simple Time Clock Application Exercise");
        Employee emp = new Employee("emp0001", "pass1234", "Emm", "Ployee", false);
		Employee adm = new Employee("admin", "password", "Admin I.", "Strator", true);
        employees.add(emp);
		employees.add(adm);
        tca.welcome();
    }
}
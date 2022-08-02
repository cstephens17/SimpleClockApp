import java.util.*;
import java.time.*;

public class Employee {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private ArrayList<Shift> shifts;
    
    public Employee(String u, String p, String f, String l, boolean a) {
        username = u;
        password = p;
        firstName = f;
        lastName = l;
        isAdmin = a;
        shifts = new ArrayList<>();
    }
    
    public void setUsername(String s) {
       this.username = s;
    }
    
    public void setPassword(String s) {
        this.password = s;
    }
    
    public void setFirstName(String s) {
        this.firstName = s;
    }
    
    public void setLastName(String s) {
        this.lastName = s;
    }
    
    public void setAdmin(boolean b) {
        this.isAdmin = b;
    }
	
	public void setShifts(ArrayList<Shift> sh) {
		this.shifts = sh;
	}
	
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
	
	public ArrayList<Shift> getShifts() {
		return shifts;
	}
	
	public boolean onShift() {
		for (Shift sh : shifts) {
			if (sh.getIsActive() && sh.getShiftType().equals("WORK")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean onBreak() {
		for (Shift sh : shifts) {
			if (sh.getIsActive() && sh.getShiftType().equals("BREAK")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean onLunch() {
		for (Shift sh : shifts) {
			if (sh.getIsActive() && sh.getShiftType().equals("LUNCH")) {
				return true;
			}
		}
		return false;
	}
	
	public void start(String st) {
		Shift sh = new Shift(true, st, LocalDateTime.now());
		this.shifts.add(sh);
	}
	
	public void end(String st) {
		for (Shift sh : this.shifts) {
			if (sh.getIsActive() && sh.getShiftType().equals(st)) {
				sh.setIsActive(false);
				sh.setEndDateTime(LocalDateTime.now());
				return;
			}
		}
	}
	
	public void end(String st, int i) {
		int counter = 0;
		int x = 0;
		for (Shift sh : this.shifts) {
			if (sh.getIsActive() && sh.getShiftType().equals(st)) {
				counter++;
			}
			if (counter == i) {
				sh.setIsActive(false);
				sh.setEndDateTime(LocalDateTime.now());
				return;
			}
		}
	}
}
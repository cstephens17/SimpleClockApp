import java.util.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  

public class Shift {
    private boolean isActive;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
	private String shiftType;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    
    public Shift(boolean a, String st, LocalDateTime sdt, LocalDateTime edt) {
        isActive = a;
        shiftType = st;
        startDateTime = sdt;
        endDateTime = edt;
    }
    
    public Shift(boolean a, String st, LocalDateTime sdt) {
        isActive = a;
        shiftType = st;
        startDateTime = sdt;
        endDateTime = null;
    }
    
    public void setIsActive(boolean a) {
        this.isActive = a;
    }
    
    public void setShiftType(String st) {
        this.shiftType = st;
    }
    
    public void setStartDateTime(LocalDateTime sdt) {
        this.startDateTime = sdt;
    }
    
    public void setEndDateTime(LocalDateTime edt) {
        this.endDateTime = edt;
    }
	
	public boolean getIsActive() {
		return isActive;
	}
	
	public String getShiftType() {
		return shiftType;
	}
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public String getStatus() {
		if (this.isActive)
		{
			return "ACTIVE";
		}
		else
		{
			return "INACTIVE";
		}
	}
	
	public String getStartString() {
		if (this.startDateTime == null)
		{
			return "--/--/---- --:--:--";
		}
		return this.startDateTime.format(formatter);
	}
	
	public String getEndString() {
		if (this.endDateTime == null)
		{
			return "--/--/---- --:--:--";
		}
		return this.endDateTime.format(formatter);
	}
}
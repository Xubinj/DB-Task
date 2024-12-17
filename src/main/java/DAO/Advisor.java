package DAO;

public class Advisor {
    private int advisorId;
    private String name;
    private String title;
    private String email;
    private String phone;
    private String department;
    private boolean isActive;
    private int totalQuota;
    private int remainingQuota;
    private String id_number;


    public Advisor() {
    	
    }

    public Advisor(int int1) {
		this.advisorId = int1;
	}

	// Getters and Setters
    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

	public int getTotalQuota() {
		return totalQuota;
	}

	public void setTotalQuota(int totalQuota) {
		this.totalQuota = totalQuota;
	}

	public int getRemainingQuota() {
		return remainingQuota;
	}

	public void setRemainingQuota(int remainingQuota) {
		this.remainingQuota = remainingQuota;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}


}

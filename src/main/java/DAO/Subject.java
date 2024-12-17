package DAO;

public class Subject {
    private int subject_id;
    private String name;
    private String type;
    private int level;
    private Integer parentSubjectId;  // 使用 Integer 以允许 null 值
    private String description;
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Integer getParentSubjectId() {
		return parentSubjectId;
	}
	public void setParentSubjectId(Integer parentSubjectId) {
		this.parentSubjectId = parentSubjectId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    
}

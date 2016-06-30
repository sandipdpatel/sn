package selenium.common.datamodel;

public class Staff {

	public Integer id;
	public String name;
	public String branch;

	public Staff(Integer id, String name, String branch) {
		this.id = id;
		this.name = name;
		this.branch = branch;
	}

	public boolean equals(Staff staff) {
		if (this.branch != staff.branch || this.name != staff.branch) {
			return false;
		}
		return true;
	}
}

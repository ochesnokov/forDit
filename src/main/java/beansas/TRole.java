package beansas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dbo.tRole")
public class TRole {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name ="RoleID")
private long id;

@Column(name = "Brief")
private String brief;

@Column(name = "Name")
private String name;

@Column(name ="AddInfo")
private String addInfo;



public long getID() {
	return id;
}

public void setId(long id) {
	this.id = id;
}


public String getBrief() {
	return brief;
}
public void setBrief(String brief) {
	this.brief = brief;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddInfo() {
	return addInfo;
}
public void setAddInfo(String addInfo) {
	this.addInfo = addInfo;
}


}

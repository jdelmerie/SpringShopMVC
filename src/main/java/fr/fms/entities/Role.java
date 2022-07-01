package fr.fms.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleId;

	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<Users> users = new ArrayList<Users>();

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + ", users=" + users + "]";
	}
}

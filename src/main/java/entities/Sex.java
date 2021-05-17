package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sex")
public class Sex
{
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "sex")
	private List<Guest> guests = new ArrayList<>();

	public Sex() {}

	public Sex(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sex{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}

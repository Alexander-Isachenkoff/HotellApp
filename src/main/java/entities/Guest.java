package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Guest")
public class Guest
{
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private int id;

	@Column (name = "name")
	private String name;

	@Column (name = "second_name")
	private String secondName;

	@Column (name = "surname")
	private String surname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sex")
	private Sex sex;

	@Column (name = "birth_date")
	private String birthDate;

	@Column (name = "phone_number")
	private String phoneNumber;

	@Column (name = "passport_series")
	private int passportSeries;

	@Column (name = "passport_id")
	private int passportId;

	@OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Registration> registrations = new ArrayList<>();

	public Guest() {}

	public Guest(String name, String secondName, String surname, Sex sex, String birthDate, String phoneNumber, int passportSeries, int passportId) {
		this.name = name;
		this.secondName = secondName;
		this.surname = surname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.passportSeries = passportSeries;
		this.passportId = passportId;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Sex getSex() {
		return sex;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return getSurname() + " " + getName() + " " + getSecondName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getBirthDate() {
		if (birthDate == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return LocalDate.parse(birthDate, formatter);
	}

	public void setBirthDate(LocalDate birthDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		this.birthDate = birthDate.format(formatter);
	}

	public int getPassportSeries() {
		return passportSeries;
	}

	public void setPassportSeries(int passportSeries) {
		this.passportSeries = passportSeries;
	}

	public int getPassportId() {
		return passportId;
	}

	public void setPassportId(int passportId) {
		this.passportId = passportId;
	}

	@Override
	public String toString() {
		return "Guest{" +
				"id=" + id +
				", name='" + name + '\'' +
				", secondName='" + secondName + '\'' +
				", surname='" + surname + '\'' +
				", sex=" + sex +
				", birthDate='" + birthDate + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", passportSeries=" + passportSeries +
				", passportId=" + passportId +
				'}';
	}
}

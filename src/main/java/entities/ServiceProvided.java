package entities;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Service_provided")
public class ServiceProvided implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_registration")
	private Registration registration;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_service")
	private Service service;

	@Column(name = "date" )
	private String date;

	public ServiceProvided() {}

	public ServiceProvided(Registration registration, Service service, String date) {
		this.registration = registration;
		this.service = service;
		this.date = date;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public LocalDate getDate() {
		if (date == null) {
			return null;
		}
		return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	public void setDate(LocalDate date) {
		this.date = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	@Override
	public String toString() {
		return "ServiceProvided{" +
				"registration=" + registration +
				", service=" + service +
				", date='" + date + '\'' +
				'}';
	}
}

package control;

import entities.Registration;
import entities.Service;
import entities.ServiceProvided;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import service.ServiceProvidedService;
import service.ServiceService;

import java.time.LocalDate;

public class ServiceProvidedForm
{
	private final ServiceService serviceService = new ServiceService();
	private final ServiceProvidedService serviceProvidedService = new ServiceProvidedService();
	@FXML
	private Button saveButton;
	@FXML
	private DatePicker serviceDatePk;
	@FXML
	private ComboBox<Service> servicesCmb;
	private Registration registration;
	private ServiceProvided initialServiceProvided = new ServiceProvided();

	public void initialize() {
		servicesCmb.setConverter(new StringConverter<Service>()
		{
			@Override
			public String toString(Service object) {
				return object.getName();
			}

			@Override
			public Service fromString(String string) {
				return null;
			}
		});
	}

	public void update() {
		servicesCmb.getItems().setAll(serviceService.findAll());
		serviceDatePk.setValue(LocalDate.now());
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public void setInitialServiceProvided(ServiceProvided serviceProvided) {
		initialServiceProvided = serviceProvided;
		servicesCmb.setValue(serviceProvided.getService());
		serviceDatePk.setValue(serviceProvided.getDate());
	}

	private void initService() {
		initialServiceProvided.setDate(serviceDatePk.getValue());
		initialServiceProvided.setService(servicesCmb.getValue());
		initialServiceProvided.setRegistration(registration);
	}

	@FXML
	private void onSave() {
		initService();
		serviceProvidedService.saveOrUpdate(initialServiceProvided);
		getStage().close();
	}

	private Stage getStage() {
		return ((Stage) saveButton.getScene().getWindow());
	}
}

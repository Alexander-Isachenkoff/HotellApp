package control;

import entities.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceService;

public class ServiceForm
{
	@FXML
	private Button saveButton;
	@FXML
	private TextField nameField;
	@FXML
	private TextField costField;

	private final ServiceService serviceService = new ServiceService();

	private Service initialService = new Service();

	public void setInitialService(Service service) {
		initialService = service;
		nameField.setText(service.getName());
		costField.setText(String.valueOf(service.getCost()));
	}

	@FXML
	private void onSave() {
		initService();
		serviceService.saveOrUpdate(initialService);
		getStage().close();
	}

	private void initService() {
		initialService.setName(nameField.getText());
		initialService.setCost(Double.parseDouble(costField.getText()));
	}

	private Stage getStage() {
		return ((Stage) saveButton.getScene().getWindow());
	}
}

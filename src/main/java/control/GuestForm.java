package control;

import entities.Guest;
import entities.Sex;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.GuestService;
import service.SexService;

public class GuestForm
{
	private final GuestService guestService = new GuestService();
	private final SexService sexService = new SexService();
	@FXML
	private TextField surnameField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField secondNameField;
	@FXML
	private DatePicker birthDatePk;
	@FXML
	private CheckBox manCheckBox;
	@FXML
	private CheckBox womanCheckBox;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField seriesPassportField;
	@FXML
	private TextField idPassportField;
	@FXML
	private Button saveButton;
	private Guest initialGuest = new Guest();

	public void initialize() {
		manCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> womanCheckBox.setSelected(!newValue));
		womanCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> manCheckBox.setSelected(!newValue));
	}

	public void setInitialGuest(Guest guest) {
		initialGuest = guest;
		nameField.setText(guest.getName());
		surnameField.setText(guest.getSurname());
		secondNameField.setText(guest.getSecondName());
		birthDatePk.setValue(guest.getBirthDate());
		idPassportField.setText(String.valueOf(guest.getPassportId()));
		seriesPassportField.setText(String.valueOf(guest.getPassportSeries()));
		phoneField.setText(guest.getPhoneNumber());
		manCheckBox.setSelected(true);
		if (guest.getSex() != null && guest.getSex().getId() == 2) {
			womanCheckBox.setSelected(true);
		}
	}

	private void initGuest() {
		initialGuest.setName(nameField.getText());
		initialGuest.setSecondName(secondNameField.getText());
		initialGuest.setSurname(surnameField.getText());
		initialGuest.setBirthDate(birthDatePk.getValue());
		initialGuest.setPassportId(Integer.parseInt(idPassportField.getText()));
		initialGuest.setPassportSeries(Integer.parseInt(seriesPassportField.getText()));
		initialGuest.setPhoneNumber(phoneField.getText());
		initialGuest.setSex(getSex());
	}

	@FXML
	private void onSave() {
		initGuest();
		guestService.saveOrUpdate(initialGuest);
		getStage().close();
	}

	private Sex getSex() {
		if (manCheckBox.isSelected()) {
			return sexService.findById(1);
		}
		if (womanCheckBox.isSelected()) {
			return sexService.findById(2);
		} else return null;
	}

	private Stage getStage() {
		return ((Stage) saveButton.getScene().getWindow());
	}
}

package control;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class MainForm
{
	@FXML
	private Tab guestsTab;
	@FXML
	private GuestsTab guestsTabContentController;
	@FXML
	private Tab registrationsTab;
	@FXML
	private RegistrationsTab registrationsTabContentController;
	@FXML
	private Tab roomsTab;
	@FXML
	private RoomsTab roomsTabContentController;
	@FXML
	private Tab servicesTab;
	@FXML
	private ServicesTab servicesTabContentController;

	public void initialize() {
		guestsTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				guestsTabContentController.update();
			}
		});
		registrationsTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				registrationsTabContentController.update();
			}
		});
		roomsTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				roomsTabContentController.update();
			}
		});
		servicesTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				servicesTabContentController.update();
			}
		});
	}
}

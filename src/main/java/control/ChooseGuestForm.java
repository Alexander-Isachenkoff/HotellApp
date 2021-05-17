package control;

import control.util.DateColumnCallback;
import entities.Guest;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.GuestService;

import java.time.LocalDate;

public class ChooseGuestForm
{
	@FXML
	private TableView<Guest> guestsTable;
	@FXML
	private TableColumn<Guest, String> fullName;
	@FXML
	private TableColumn<Guest, String> sex;
	@FXML
	private TableColumn<Guest, LocalDate> birthDate;
	@FXML
	private TableColumn<Guest, Integer> passportSeries;
	@FXML
	private TableColumn<Guest, Integer> passportId;

	private final GuestService guestService = new GuestService();

	private Guest selectedGuest;

	public void initialize() {
		initTable();
	}

	private void initTable() {
		fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		sex.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSex().getName()));
		birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		birthDate.setCellFactory(new DateColumnCallback<>());
		passportSeries.setCellValueFactory(new PropertyValueFactory<>("passportSeries"));
		passportId.setCellValueFactory(new PropertyValueFactory<>("passportId"));
	}

	public void update() {
		guestsTable.getItems().setAll(guestService.findAll());
	}

	@FXML
	private void onSelect() {
		selectedGuest = guestsTable.getSelectionModel().getSelectedItem();
		getStage().close();
	}

	public Guest getSelectedGuest() {
		return selectedGuest;
	}

	private Stage getStage() {
		return ((Stage) guestsTable.getScene().getWindow());
	}
}

package control;

import control.util.DateColumnCallback;
import entities.Guest;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.GuestService;

import java.io.IOException;
import java.time.LocalDate;

public class GuestsTab
{
	private final GuestService guestService = new GuestService();
	@FXML
	private Button addButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private TableView<Guest> guestsTable;
	@FXML
	private TableColumn<Guest, String> fullNameColumn;
	@FXML
	private TableColumn<Guest, String> sexColumn;
	@FXML
	private TableColumn<Guest, LocalDate> birthDateColumn;
	@FXML
	private TableColumn<Guest, String> phoneColumn;
	@FXML
	private TableColumn<Guest, Integer> passportSeriesColumn;
	@FXML
	private TableColumn<Guest, Integer> passportIdColumn;
	@FXML
	private Label recordsCountLabel;
	private Stage guestForm;
	private GuestForm guestFormController;

	public void initialize() {
		initTable();
		addButton.setOnAction(event -> showCreateGuestForm());
		editButton.setOnAction(event -> {
			Guest selected = getSelectedGuest();
			if (selected != null) {
				showEditGuestForm(selected);
			}
		});
		deleteButton.setOnAction(event -> {
			Guest selected = getSelectedGuest();
			if (selected != null) {
				guestService.delete(selected);
				update();
			}
		});
	}

	private void initTable() {
		fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		sexColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSex().getName()));
		birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		birthDateColumn.setCellFactory(new DateColumnCallback<>());
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		passportSeriesColumn.setCellValueFactory(new PropertyValueFactory<>("passportSeries"));
		passportIdColumn.setCellValueFactory(new PropertyValueFactory<>("passportId"));
		guestsTable.getItems().addListener((ListChangeListener<Guest>) c -> updateRecordsCountLabel());
	}

	private void updateGuestsTable() {
		guestsTable.getItems().setAll(guestService.findAll());
	}

	private void updateRecordsCountLabel() {
		recordsCountLabel.setText("Всего записей: " + guestsTable.getItems().size());
	}

	private void lazyGuestFormInit() {
		if (guestForm == null || guestFormController == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/guest_form.fxml"));
			Parent root;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			guestForm = new Stage();
			guestForm.setScene(new Scene(root));
			guestForm.initModality(Modality.WINDOW_MODAL);
			guestForm.initOwner(getStage());
			guestForm.setResizable(false);
			guestFormController = loader.getController();
		}
	}

	private void showCreateGuestForm() {
		lazyGuestFormInit();
		guestFormController.setInitialGuest(new Guest());
		guestForm.showAndWait();
		update();
	}

	private void showEditGuestForm(Guest guest) {
		lazyGuestFormInit();
		guestFormController.setInitialGuest(guest);
		guestForm.showAndWait();
		update();
	}

	private Guest getSelectedGuest() {
		return guestsTable.getSelectionModel().getSelectedItem();
	}

	private Stage getStage() {
		return ((Stage) guestsTable.getScene().getWindow());
	}

	public void update() {
		updateGuestsTable();
	}
}

package control;

import entities.Guest;
import entities.Registration;
import entities.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.RegistrationService;
import service.RoomService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationForm
{
	@FXML
	private Button chooseGuestBtn;
	@FXML
	private DatePicker placementDatePicker;
	@FXML
	private DatePicker departureDatePicker;
	@FXML
	private TableView<Room> roomTable;
	@FXML
	private Button saveButton;
	@FXML
	private TableColumn<Room, Integer> numberColumn;
	@FXML
	private TableColumn<Room, String> categoryColumn;
	@FXML
	private TextField guestField;
	@FXML
	private TextField roomField;

	private RoomService roomService = new RoomService();
	private RegistrationService registrationService = new RegistrationService();
	private Registration registration;
	private Guest guest;
	private Room room;
	private Stage chooseGuestForm;
	private ChooseGuestForm chooseGuestFormController;

	public void initialize() {
		chooseGuestBtn.setOnAction(event -> showCreateRegistrationForm());
		initRoomTable();
	}

	private void initRoomTable() {
		numberColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory().getName()));
		roomTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setRoom(newValue));
	}

	private void updateRoomsTable() {
		List<Room> rooms = roomService.findAll();
		List<Room> emptyRooms = rooms.stream().filter(room1 -> !room1.getStatus()).collect(Collectors.toList());
		roomTable.getItems().setAll(emptyRooms);
	}

	@FXML
	private void onSave() {
		initRegistration();
		roomService.update(room);
		registrationService.saveOrUpdate(registration);
		getStage().close();
	}

	private void showCreateRegistrationForm() {
		lazyChooseGuestFormInit();
		chooseGuestFormController.update();
		chooseGuestForm.showAndWait();
		Guest guest = chooseGuestFormController.getSelectedGuest();
		if (guest != null) {
			setGuest(guest);
		}
	}

	private void lazyChooseGuestFormInit() {
		if (chooseGuestForm == null || chooseGuestFormController == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/choice_guest_form.fxml"));
			Parent root;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			chooseGuestForm = new Stage();
			chooseGuestForm.setScene(new Scene(root));
			chooseGuestForm.initModality(Modality.WINDOW_MODAL);
			chooseGuestForm.initOwner(getStage());
			chooseGuestForm.setResizable(false);
			chooseGuestFormController = loader.getController();
		}
	}

	private void setRoom(Room room) {
		this.room = room;
		if (room != null) {
			roomField.setText(room.getName() + " (" + room.getCategory().getName() + ")");
		} else {
			roomField.clear();
		}
	}

	private void setGuest(Guest guest) {
		this.guest = guest;
		if (guest == null) {
			guestField.clear();
		} else {
			guestField.setText(guest.getFullName());
		}
	}

	public void setInitialRegistration(Registration registration) {
		this.registration = registration;
		LocalDate placementDate = registration.getDateOfPlacement();
		if (placementDate != null) {
			placementDatePicker.setValue(registration.getDateOfPlacement());
		} else {
			placementDatePicker.setValue(LocalDate.now());
		}
		departureDatePicker.setValue(registration.getDateOfDeparture());
		setRoom(registration.getRoom());
		setGuest(registration.getGuest());
		roomTable.getSelectionModel().select(registration.getRoom());
	}

	private void initRegistration() {
		registration.setGuest(guest);
		registration.setRoom(room);
		registration.setDateOfPlacement(placementDatePicker.getValue());
		registration.setDateOfDeparture(departureDatePicker.getValue());
	}

	public void update() {
		updateRoomsTable();
	}

	private Stage getStage() {
		return ((Stage) saveButton.getScene().getWindow());
	}
}

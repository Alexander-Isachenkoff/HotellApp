package control;

import control.util.ColumnCallback;
import entities.Room;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.RoomService;

public class RoomsTab
{
	private final RoomService roomService = new RoomService();
	@FXML
	public TableColumn<Room, Number> bedsColumn;
	@FXML
	private TableView<Room> roomsTable;
	@FXML
	private TableColumn<Room, Integer> numberColumn;
	@FXML
	private TableColumn<Room, String> categoryColumn;
	@FXML
	private TableColumn<Room, Integer> floorColumn;
	@FXML
	private TableColumn<Room, Boolean> statusColumn;
	@FXML
	private Label recordsCountLabel;

	public void initialize() {
		initTable();
	}

	private void initTable() {
		numberColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory().getName()));
		bedsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCategory().getBedsNumber()));
		floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
		roomsTable.getItems().addListener((ListChangeListener<Room>) c -> updateRecordsCountLabel());
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		statusColumn.setCellFactory(new ColumnCallback<Room, Boolean>()
		{
			@Override
			public String makeText(Boolean item) {
				return item ? "Занят" : "Свободен";
			}
		});
	}

	public void update() {
		updateRoomsTable();
	}

	private void updateRoomsTable() {
		roomsTable.getItems().setAll(roomService.findAll());
	}

	private void updateRecordsCountLabel() {
		recordsCountLabel.setText("Всего записей: " + roomsTable.getItems().size());
	}
}

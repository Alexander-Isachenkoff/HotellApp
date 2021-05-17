package control;

import entities.Service;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ServiceService;

import java.io.IOException;

public class ServicesTab
{
	private final ServiceService serviceService = new ServiceService();

	// region FXML
	@FXML
	private Button addButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private TableView<Service> servicesTable;
	@FXML
	private TableColumn<Service, String> nameColumn;
	@FXML
	private TableColumn<Service, Double> costColumn;
	@FXML
	private Label recordsCountLabel;
	// endregion

	private Stage serviceForm;
	private ServiceForm serviceFormController;

	public void initialize() {
		initTable();
		addButton.setOnAction(event -> showCreateServiceForm());
		editButton.setOnAction(event -> {
			Service selected = getSelectedService();
			if (selected != null) {
				showEditServiceForm(selected);
			}
		});
		deleteButton.setOnAction(event -> {
			Service selected = getSelectedService();
			if (selected != null) {
				serviceService.delete(selected);
				update();
			}
		});
	}

	private void showEditServiceForm(Service service) {
		lazyServiceFormInit();
		serviceFormController.setInitialService(service);
		serviceForm.showAndWait();
		update();
	}

	private void showCreateServiceForm() {
		lazyServiceFormInit();
		serviceFormController.setInitialService(new Service());
		serviceForm.showAndWait();
		update();
	}

	private void lazyServiceFormInit() {
		if (serviceForm == null || serviceFormController == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/service_form.fxml"));
			Parent root;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			serviceForm = new Stage();
			serviceForm.setScene(new Scene(root));
			serviceForm.initModality(Modality.WINDOW_MODAL);
			serviceForm.initOwner(getStage());
			serviceForm.setResizable(false);
			serviceFormController = loader.getController();
		}
	}

	private void initTable() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
		servicesTable.getItems().addListener((ListChangeListener<Service>) c -> updateRecordsCountLabel());
	}

	private Service getSelectedService() {
		return servicesTable.getSelectionModel().getSelectedItem();
	}

	public void update() {
		updateTable();
	}

	private void updateTable() {
		servicesTable.getItems().setAll(serviceService.findAll());
	}

	private void updateRecordsCountLabel() {
		recordsCountLabel.setText("Всего записей: " + servicesTable.getItems().size());
	}

	private Stage getStage() {
		return ((Stage) servicesTable.getScene().getWindow());
	}
}

package control;

import control.util.DateColumnCallback;
import entities.Guest;
import entities.Registration;
import entities.Room;
import entities.ServiceProvided;
import javafx.beans.property.SimpleDoubleProperty;
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
import service.RegistrationService;
import service.ServiceProvidedService;

import java.io.IOException;
import java.time.LocalDate;

public class RegistrationsTab
{
	private final RegistrationService registrationService = new RegistrationService();
	private final ServiceProvidedService serviceProvidedService = new ServiceProvidedService();
	// region FXML
	@FXML
	private Button addButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private TableView<Registration> registrationTable;
	@FXML
	private TableColumn<Registration, Integer> id;
	@FXML
	private TableColumn<Registration, String> guest;
	@FXML
	private TableColumn<Registration, String> room;
	@FXML
	private TableColumn<Registration, LocalDate> placementDate;
	@FXML
	private TableColumn<Registration, LocalDate> departureDate;
	@FXML
	private TextField idRegistrationFld;
	@FXML
	private TextField fullNameGuestFld;
	@FXML
	private TextField livingCostFld;
	@FXML
	private TextField servicesCostFld;
	@FXML
	private TextField checkTotalSumField;
	@FXML
	private TableView<ServiceProvided> servicesTable;
	@FXML
	private TableColumn<ServiceProvided, String> serviceColumn;
	@FXML
	private TableColumn<ServiceProvided, LocalDate> serviceDateColumn;
	@FXML
	private TableColumn<ServiceProvided, Number> serviceCostColumn;
	@FXML
	private TextField serviceTotalSumField;
	@FXML
	private Button addServiceButton;
	@FXML
	private Button deleteServiceButton;
	@FXML
	private Button editServiceButton;
	@FXML
	private Label recordsCountLabel;
	// endregion
	private Stage serviceForm;
	private ServiceProvidedForm serviceFormController;
	private Stage registrationForm;
	private RegistrationForm registrationFormController;

	public void initialize() {
		initRegistrationTable();
		registrationTable.getItems().addListener((ListChangeListener<Registration>) c -> updateRecordsCountLabel());
		registrationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateCheck(newValue);
				updateServicesTable(newValue);
			}
		});
		initServicesTable();
		initServiceButtons();
		initRegistrationButtons();
		update();
	}

	private void updateServicesTable(Registration registration) {
		servicesTable.getItems().setAll(registration.getServiceProvidedList());
		serviceTotalSumField.setText(String.valueOf(registration.calcServicesCost()));
	}

	private void initServicesTable() {
		serviceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
		serviceCostColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getService().getCost()));
		serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		serviceDateColumn.setCellFactory(new DateColumnCallback<>());
	}

	private void initRegistrationButtons() {
		addButton.setOnAction(event -> {
			showCreateRegistrationForm();
			update();
		});

		editButton.setOnAction(event -> {
			Registration selectedRegistration = getSelectedRegistration();
			if (selectedRegistration != null) {
				showEditRegistrationForm(selectedRegistration);
				update();
			}
		});

		deleteButton.setOnAction(event -> {
			Registration selectedRegistration = getSelectedRegistration();
			if (selectedRegistration != null) {
				registrationService.delete(selectedRegistration);

				update();
			}

		});
	}

	private void initServiceButtons() {
		addServiceButton.setOnAction(event -> {
			Registration selectedRegistration = getSelectedRegistration();
			int index = registrationTable.getSelectionModel().getSelectedIndex();
			if (selectedRegistration != null) {
				showCreateServiceForm(selectedRegistration);
				registrationTable.getSelectionModel().select(index);
			}
		});
		editServiceButton.setOnAction(event -> {
			ServiceProvided serviceProvided = servicesTable.getSelectionModel().getSelectedItem();
			if (serviceProvided != null) {
				showEditServiceForm(serviceProvided);
				registrationTable.getSelectionModel().select(serviceProvided.getRegistration());
			}
		});
		deleteServiceButton.setOnAction(event -> {
			ServiceProvided serviceProvided = servicesTable.getSelectionModel().getSelectedItem();
			if (serviceProvided != null) {
				Registration registration = serviceProvided.getRegistration();
				serviceProvidedService.delete(serviceProvided);
				updateRegistrations();
				registrationTable.getSelectionModel().select(registration);
			}
		});
	}

	private Registration getSelectedRegistration() {
		return registrationTable.getSelectionModel().getSelectedItem();
	}

	private void initRegistrationTable() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		guest.setCellValueFactory(data -> {
			Guest guest = data.getValue().getGuest();
			return new SimpleStringProperty(guest.getFullName());
		});
		room.setCellValueFactory(data -> {
			Room room = data.getValue().getRoom();
			return new SimpleStringProperty(room.getName() + " (" + room.getCategory().getName() + ")");
		});
		placementDate.setCellValueFactory(new PropertyValueFactory<>("dateOfPlacement"));
		placementDate.setCellFactory(new DateColumnCallback<>());
		departureDate.setCellValueFactory(new PropertyValueFactory<>("dateOfDeparture"));
		departureDate.setCellFactory(new DateColumnCallback<>());
	}

	private void updateRecordsCountLabel() {
		recordsCountLabel.setText("Всего записей: " + registrationTable.getItems().size());
	}

	private void updateRegistrations() {
		registrationTable.getItems().setAll(registrationService.findAll());
	}

	private void updateCheck(Registration registration) {
		fullNameGuestFld.setText(registration.getGuest().getFullName());
		idRegistrationFld.setText(String.valueOf(registration.getId()));
		double livingCost = registration.calcLivingCost();
		livingCostFld.setText(String.valueOf(livingCost));
		double servicesCost = registration.calcServicesCost();
		servicesCostFld.setText(String.valueOf(servicesCost));
		checkTotalSumField.setText(String.valueOf(livingCost + servicesCost));
	}

	public void update() {
		updateRegistrations();
	}

	private void showEditServiceForm(ServiceProvided service) {
		lazyServiceFormInit();
		serviceFormController.setRegistration(service.getRegistration());
		serviceFormController.setInitialServiceProvided(service);
		serviceFormController.update();
		serviceForm.showAndWait();
		update();
	}

	private void showCreateServiceForm(Registration registration) {
		lazyServiceFormInit();
		serviceFormController.setRegistration(registration);
		serviceFormController.setInitialServiceProvided(new ServiceProvided());
		serviceFormController.update();
		serviceForm.showAndWait();
		update();
	}

	private void lazyServiceFormInit() {
		if (serviceForm == null || serviceFormController == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/add_provided_service_form.fxml"));
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

	private void showEditRegistrationForm(Registration registration) {
		lazyRegistrationFormInit();
		registrationFormController.update();
		registrationFormController.setInitialRegistration(registration);
		registrationForm.showAndWait();
		update();
	}

	private void showCreateRegistrationForm() {
		lazyRegistrationFormInit();
		registrationFormController.setInitialRegistration(new Registration());
		registrationFormController.update();
		registrationForm.showAndWait();
		update();
	}

	private void lazyRegistrationFormInit() {
		if (registrationForm == null || registrationFormController == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/add_registration_form.fxml"));
			Parent root;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			registrationForm = new Stage();
			registrationForm.setScene(new Scene(root));
			registrationForm.initModality(Modality.WINDOW_MODAL);
			registrationForm.initOwner(getStage());
			registrationForm.setResizable(false);
			registrationFormController = loader.getController();
		}
	}

	private Stage getStage() {
		return ((Stage) registrationTable.getScene().getWindow());
	}
}

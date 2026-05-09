package controllers;

import dao.EnrollmentDAO;
import dao.StudentDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Enrollment;

public class EnrollmentController implements Initializable {

    @FXML
    private ComboBox<Integer> studentsCombobox;

    @FXML
    private ComboBox<Integer> coursesCombobox;

    @FXML
    private DatePicker enrollDate;

    @FXML
    private TableView<Enrollment> table;

    @FXML
    private TableColumn<Enrollment, Integer> enrollIdTC;

    @FXML
    private TableColumn<Enrollment, Integer> studentIdTC;

    @FXML
    private TableColumn<Enrollment, Integer> courseIdTC;

    @FXML
    private TableColumn<Enrollment, String> enrollDateTC;

    EnrollmentDAO dao = new EnrollmentDAO();
    StudentDAO studentDAO = new StudentDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // تحميل الطلاب في ComboBox
        studentsCombobox.getItems().addAll(studentDAO.getAllStudentsids());

        // كورسات ثابتة
        coursesCombobox.getItems().addAll(1, 2, 3, 4);

        // ربط الأعمدة بالبيانات
        enrollIdTC.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleIntegerProperty(data.getValue().getEnrollId()).asObject());

        studentIdTC.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStudentId()).asObject());

        courseIdTC.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCourseId()).asObject());

        enrollDateTC.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getEnrollDate()));
    }

    // ================= VIEW =================
    @FXML
    public void viewHandle() {
        table.getItems().setAll(dao.findAll());
    }

    // ================= ADD =================
    @FXML
    public void addHandle() {

        if (studentsCombobox.getValue() == null 
                || coursesCombobox.getValue() == null 
                || enrollDate.getValue() == null) {

            showWarning("Missing Data");
            return;
        }

        Enrollment e = new Enrollment(
                0,
                studentsCombobox.getValue(),
                coursesCombobox.getValue(),
                enrollDate.getValue().toString()
        );

        if (dao.insert(e)) {
            showInfo("Added Successfully");
            viewHandle();
        }
    }

    // ================= UPDATE =================
    @FXML
    public void updateHandle() {

        Enrollment selected = table.getSelectionModel().getSelectedItem();

        if (selected == null || enrollDate.getValue() == null) {
            showWarning("Select record + date");
            return;
        }

        selected.setEnrollDate(enrollDate.getValue().toString());

        if (dao.update(selected)) {
            showInfo("Updated");
            viewHandle();
        }
    }

    // ================= DELETE =================
    @FXML
    public void deleteHandle() {

        Enrollment selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showWarning("Select record");
            return;
        }

        if (dao.delete(selected)) {
            showInfo("Deleted");
            viewHandle();
        }
    }

    // ================= ALERTS =================
    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}
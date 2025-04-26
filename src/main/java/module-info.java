module com.example.employeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;

    opens com.example.employeemanagementsystem to javafx.fxml;
    exports com.example.employeemanagementsystem;
}
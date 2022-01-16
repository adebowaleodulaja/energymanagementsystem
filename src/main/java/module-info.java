module com.ems.energymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.testng;


    opens com.ems.energymanagementsystem to javafx.fxml;
    exports com.ems.energymanagementsystem;
    exports com.ems.energymanagementsystem.model;
    exports com.ems.energymanagementsystem.test;
}
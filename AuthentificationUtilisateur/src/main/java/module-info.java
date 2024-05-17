module com.example.authentificationutilisateur {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.authentificationutilisateur to javafx.fxml;
    exports com.example.authentificationutilisateur;
}
module idatt1002.project.k2 {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;

    exports org.edu.ntnu.idatt1002.techn;
    exports org.edu.ntnu.idatt1002.techn.Teams;
    exports org.edu.ntnu.idatt1002.techn.App;
    exports org.edu.ntnu.idatt1002.techn.Tournament;
    exports org.edu.ntnu.idatt1002.techn.App.Controllers;
    exports org.edu.ntnu.idatt1002.techn.Tournament.Runners;

    opens org.edu.ntnu.idatt1002.techn;
    opens org.edu.ntnu.idatt1002.techn.Teams;
    opens org.edu.ntnu.idatt1002.techn.App;
    opens org.edu.ntnu.idatt1002.techn.Tournament;
    opens org.edu.ntnu.idatt1002.techn.App.Controllers;
}
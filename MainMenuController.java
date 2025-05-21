package poiupv.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import model.Navigation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Problem;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import model.Answer;
import model.NavDAOException;




public class MainMenuController {
    
    

    @FXML
    private void onStartProblems(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewProfile(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewResults(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onOpenMap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarProblemaDetallado(Problem problema) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setPrefWidth(600);

    Label lblEnunciado = new Label(problema.getText());
    lblEnunciado.setWrapText(true);
    lblEnunciado.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

    ScrollPane scrollEnunciado = new ScrollPane(lblEnunciado);
    scrollEnunciado.setPrefHeight(150);
    scrollEnunciado.setFitToWidth(true);

    String[] letras = {"A)", "B)", "C)", "D)"};
    List<Answer> respuestas = new ArrayList<>(problema.getAnswers());
    Collections.shuffle(respuestas);

    ToggleGroup grupo = new ToggleGroup();
    List<RadioButton> opciones = new ArrayList<>();

    for (int i = 0; i < respuestas.size(); i++) {
        RadioButton rb = new RadioButton(letras[i] + " " + respuestas.get(i).getText());
        rb.setToggleGroup(grupo);
        opciones.add(rb);
    }

    Button btnVerificar = new Button("Verificar respuesta");
    Label lblResultado = new Label();
    lblResultado.setStyle("-fx-font-size: 14px;");

    btnVerificar.setOnAction(e -> {
        Toggle selected = grupo.getSelectedToggle();
        if (selected != null) {
            int index = -1;
            for (int i = 0; i < opciones.size(); i++) {
                if (opciones.get(i).isSelected()) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                Answer respuesta = respuestas.get(index);
                if (respuesta.getValidity()) {
                    lblResultado.setText("✅ ¡Correcto!");
                    lblResultado.setStyle("-fx-text-fill: green;");
                } else {
                    lblResultado.setText("❌ Incorrecto");
                    lblResultado.setStyle("-fx-text-fill: red;");
                }
            }
        } else {
            lblResultado.setText("⚠️ Selecciona una respuesta");
            lblResultado.setStyle("-fx-text-fill: orange;");
        }
    });

    layout.getChildren().add(scrollEnunciado);
    layout.getChildren().addAll(opciones);
    layout.getChildren().addAll(btnVerificar, lblResultado);

    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Problema seleccionado");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    dialog.getDialogPane().setContent(new ScrollPane(layout));
    dialog.setResizable(true);
    dialog.getDialogPane().setPrefSize(650, 500);
    dialog.showAndWait();
}
        
    @FXML
    private void handleListaProblemas() throws NavDAOException {
    Navigation navigation = Navigation.getInstance();
    List<Problem> problemas = navigation.getProblems();

    ListView<Problem> listView = new ListView<>();
    listView.setPrefSize(650, 450);
    listView.getItems().addAll(problemas);

    // Mostrar texto acortado para cada problema
    listView.setCellFactory(param -> new ListCell<>() {
        @Override
        protected void updateItem(Problem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                String preview = item.getText();
                if (preview.length() > 100) {
                    preview = preview.substring(0, 100) + "...";
                }
                setText(preview);
            }
        }
    });

    // Acción al seleccionar un problema
    listView.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !listView.getSelectionModel().isEmpty()) {
            Problem seleccionado = listView.getSelectionModel().getSelectedItem();
            mostrarProblemaDetallado(seleccionado);
        }
    });

    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Lista de Problemas");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    dialog.getDialogPane().setContent(new ScrollPane(listView));
    dialog.getDialogPane().setPrefSize(650, 450);
    dialog.setResizable(true);
    dialog.showAndWait();
    }
   
}

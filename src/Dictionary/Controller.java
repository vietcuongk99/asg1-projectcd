package Dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable {
    @FXML
    private TableView<Dictionary> tableView;
    @FXML
    private TableColumn<Dictionary,String> english;
    @FXML
    private TableColumn<Dictionary, String> vietnamese;
    @FXML
    private TextField anh;
    @FXML
    private TextField viet;
    @FXML
    private TextField search;
    @FXML
    private TextArea textArea;

    private Dictionary dictionary;
    private List<Dictionary> list = new ArrayList<Dictionary>();
    private ObservableList<Dictionary> data = FXCollections.observableArrayList(list);

    int row = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String fileName = "C:\\Users\\ADMIN\\Downloads\\anhviet109K.txt";//bạn hãy thay đổi đường dẫn tới file của bạn
        String content = null;//đưa về chuẩn utf-8
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] word = content.split("\n\n");
        ArrayList<String> anh = new ArrayList<String>();
        for(String w:word){
            String[] b;
            b=w.split("/");
            anh.add(b[0]);
        }
        for (int i=0;i<word.length;i++){
            Dictionary a = new Dictionary();
            a.setVietnamese(word[i]) ;
            a.setEnglish(anh.get(i));
            data.add(a);
        }
        english.setCellValueFactory(new PropertyValueFactory<>("english"));
       // vietnamese.setCellValueFactory(new PropertyValueFactory<>("vietnamese"));
        tableView.setItems(data);
    }
    public void Search(){
        FilteredList<Dictionary> filteredData = new FilteredList<>(data, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dictionary -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (dictionary.getEnglish().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
//                else if (dictionary.getVietnamese().toLowerCase().contains(lowerCaseFilter)) {
//                    return true;
//                }
                return false;
            });
        });
        SortedList<Dictionary> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }


    private void Disable(boolean check) {
        anh.setDisable(check);
//        viet.setDisable(check);
//        textArea.setDisable(check);

    }


    private void Reset() {
          anh.setText("");
//        viet.setText("");
        textArea.setText("");
    }

    @FXML
    public void Save(ActionEvent event) {
        try {
            dictionary = new Dictionary();
            dictionary.setEnglish(anh.getText());
            dictionary.setVietnamese(textArea.getText());

            if (row == 0) {
                data.add(dictionary);
            } else {
                data.set(row, dictionary);
            }
            tableView.setItems(data);
            Disable(true);
            Reset();
        } catch (NumberFormatException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void Edit(ActionEvent event) {
        try {
            if (dictionary != null) {
                Disable(false);
            } else {
                Disable(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void New(ActionEvent event) {
        try {
            Disable(false);
            Reset();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void tableClick(MouseEvent e) throws IOException {
        if (MouseButton.PRIMARY == e.getButton() && e.getClickCount() == 1) {
            dictionary = tableView.getSelectionModel().getSelectedItem();
            anh.setText(dictionary.getEnglish());
        //    viet.setText(dictionary.getVietnamese());
            textArea.setText(dictionary.getVietnamese());
            row = tableView.getSelectionModel().getSelectedIndex();
            Disable(true);
        }
    }
    public void Delete (ActionEvent e){
        Dictionary selected = tableView.getSelectionModel().getSelectedItem();
        data.remove(selected);
    }

}


package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    MysqlConnection connection = new MysqlConnection();

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTitle;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private ListView<String> listView;

    public void displayRecord() throws SQLException {
        String name = listView.getSelectionModel().getSelectedItem();
        connection.connect();
        String sql = "Select * from emp where name=?";
        PreparedStatement pst = connection.con.prepareStatement(sql);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        if (rs.next()){
            txtID.setText(String.valueOf(rs.getInt("id")));
            txtName.setText(rs.getString("name"));
            txtTitle.setText(rs.getString("title"));
        }
    }

    public void deleteRecord() throws SQLException {
        connection.connect();
        int id = Integer.parseInt(txtID.getText());
        String sql = "delete from emp where id=?";
        PreparedStatement pst = connection.con.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        load();
    }
    public void updateRecord() throws SQLException {
        connection.connect();
        int id = Integer.parseInt(txtID.getText());
        String name = txtName.getText();
        String sql = "update emp set name=? where id=?";
        PreparedStatement pst = connection.con.prepareStatement(sql);
        pst.setString(1, name);
        pst.setInt(2, id);

        pst.executeUpdate();
        load();
    }


    public void insert() throws SQLException {
        connection.connect();
        System.out.println("Connected...");
        String sql = "Insert into emp(id, name, title) values(?,?,?)";

        int id = Integer.parseInt(txtID.getText());
        String name = txtName.getText();
        String title = txtTitle.getText();

        PreparedStatement pst = connection.con.prepareStatement(sql);

        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, title);
        int res = pst.executeUpdate();
        if(res > 0){
            System.out.println("Successfully saved...");
        }
        listView.getItems().clear();
        load();
    }

    public void load() throws SQLException{
        // Clear the listbox
        listView.getItems().clear();

        connection.connect();
        System.out.println("Connected...");

        String sql = "Select name from emp";
        PreparedStatement pst = connection.con.prepareStatement(sql);
        ResultSet res = pst.executeQuery();

        while (res.next()){
            listView.getItems().add(res.getString("name"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            load();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


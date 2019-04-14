package com.robby.controller;

import com.google.gson.Gson;
import com.robby.entity.Team;
import com.robby.entity.TeamResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * FXML Controller class
 *
 * @author Robby
 */
public class MainFormController implements Initializable {

    @FXML
    private TableColumn<Team, String> col01;
    @FXML
    private TableColumn<Team, String> col02;
    @FXML
    private TableColumn<Team, String> col03;
    @FXML
    private TableView<Team> tableClub;
    private ObservableList<Team> teams;

    private ObservableList<Team> getTeams() {
        if (teams == null) {
            teams = FXCollections.observableArrayList();
        }
        return teams;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        If you want to use pooling http client that can max your connection manager, use code below
//        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
//        cm.setMaxTotal(100);
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setConnectionManager(cm)
//                .build();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    URI uri = new URIBuilder()
                            .setScheme("https")
                            .setHost("www.thesportsdb.com")
                            .setPath("/api/v1/json/1/lookup_all_teams.php")
                            .setParameter("id", "4328")
                            .build();
                    HttpGet httpGet = new HttpGet(uri);
                    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                    String response = EntityUtils.toString(httpResponse.getEntity());
                    TeamResponse teamResponse = new Gson().fromJson(response, TeamResponse.class);
                    getTeams().addAll(teamResponse.getTeams());
                    System.out.println(getTeams().isEmpty());
                    tableClub.refresh();
                    httpResponse.close();
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(task);
        service.shutdown();

        tableClub.setItems(getTeams());
        col01.setCellValueFactory(data -> {
            Team t = data.getValue();
            return new SimpleStringProperty(t.getName());
        });
        col02.setCellValueFactory(data -> {
            Team t = data.getValue();
            return new SimpleStringProperty(t.getAlias());
        });
        col03.setCellValueFactory(data -> {
            Team t = data.getValue();
            return new SimpleStringProperty(t.getStadium());
        });
    }

}

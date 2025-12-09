package Presentation;

import Entity.FineHistory;
import Entity.User;
import Repository.FineHistoryRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class FineHistoryReportController
{

    @FXML
    private TableView<FineHistory> tblHistory;

    @FXML
    private TableColumn<FineHistory, String> colDate;

    @FXML
    private TableColumn<FineHistory, String> colType;

    @FXML
    private TableColumn<FineHistory, String> colAmount;

    private final FineHistoryRepository fineHistoryRepository;

    private User user;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    public FineHistoryReportController(FineHistoryRepository fineHistoryRepository)
    {
        this.fineHistoryRepository = fineHistoryRepository;
    }

    @FXML
    private void initialize()
    {

        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppliedDate() != null
                ? dateFormat.format(cellData.getValue().getAppliedDate())
                : ""));
        colAmount.setCellValueFactory(cellData -> {
            BigDecimal amount = cellData.getValue().getFineAmount();
            return new SimpleStringProperty(amount != null ? amount.toString() : "0.00");
        });
        colType.setCellValueFactory(cellData -> {
            BigDecimal amount = cellData.getValue().getFineAmount();
            String type;
            if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0)
            {
                type = "Neutral";
            } else if (amount.compareTo(BigDecimal.ZERO) > 0)
            {
                type = "Fine";
            } else
            {
                type = "Payment";
            }
            return new SimpleStringProperty(type);
        });
    }

    public void setUser(User user)
    {
        this.user = user;
        loadData();
    }

    private void loadData()
    {
        if (user == null || tblHistory == null)
            return;

        List<FineHistory> historyList = fineHistoryRepository.findByUserOrderByAppliedDateAsc(user);
        tblHistory.setItems(FXCollections.observableArrayList(historyList));
    }
}

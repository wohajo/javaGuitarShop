package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.StockModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class DashboardController {

    @FXML private PieChart mostPopularChart;
    @FXML private StackedAreaChart monthlyIncomeChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;

    private StockModel stockModel = new StockModel();

    @FXML
    private void initialize() {
        initCharts();
    }

    @FXML
    private void initCharts() {

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(31);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(true);

        initPieChart();
        initmonthlyIncomeChart();
    }

    private void initmonthlyIncomeChart() {
        if (monthlyIncomeChart.getData().size() != 0) {
            monthlyIncomeChart.getData().clear();
        }
        monthlyIncomeChart.getData().add(stockModel.getDataForIncomeChart());
    }

    private void initPieChart() {
        if (mostPopularChart.getData().size() != 0) {
            mostPopularChart.getData().clear();
        }
        ObservableList<PieChart.Data> pieChartData = stockModel.getInfoForPieChart();
        mostPopularChart.setData(pieChartData);
    }
}

package com.qml.usecases;

import org.flexdata.csv.DataReader;
import org.flexdata.csv.DataRecord;
import org.flexdata.csv.Resource;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.flexdata.csv.CSVReader.CSVReaderBuilder.createCSVReader;

public class QML003Test {
    @Test
    public void basicLinearRegression() {
        try (DataReader dataReader = createCSVReader()
                .withSkipLines(1)
                .withResource(Resource.fromClasspath("linear_regression.csv"))
                .build()) {
            List<DataRecord> dataRecords = dataReader.readAllRecords();
            System.out.println(dataRecords);
        }
    }

    @Test
    @Ignore
    public void lifeSatisfactionLinearRegression() {
        try (DataReader dataReader = createCSVReader()
                .withSkipLines(1)
                .withResource(Resource.fromClasspath("linear_regression.csv"))
                .build()) {
            List<DataRecord> dataRecords = dataReader.readAllRecords();
            System.out.println(dataRecords);
        }
    }
}

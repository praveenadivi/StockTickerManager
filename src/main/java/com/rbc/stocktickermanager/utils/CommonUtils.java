package com.rbc.stocktickermanager.utils;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rbc.stocktickermanager.entity.Trade;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


public class CommonUtils {
    private CommonUtils() {

    }

    public static Optional<String> getMd5Hex(String data) {
        if (!StringUtils.hasLength(data)) {
            return Optional.empty();
        }

        String md5Hex = DigestUtils
                .md5DigestAsHex(data.getBytes(StandardCharsets.UTF_8)).toUpperCase();
        return Optional.of(md5Hex);
    }

    public static Optional<List<Trade>> loadTradeDataList(String fileName) throws FileNotFoundException {
        if (!StringUtils.hasLength(fileName)) {
            return Optional.empty();
        }

        CSVReader reader = new CSVReader(new FileReader(fileName));

        CsvToBean<Trade> csvToBean = new CsvToBeanBuilder<Trade>(reader)
                .withType(Trade.class)
                .withIgnoreEmptyLine(true)
                .withIgnoreLeadingWhiteSpace(true)
                .build();


        List<Trade> tradeDataList = csvToBean.parse();

        return Optional.of(tradeDataList);
    }

}

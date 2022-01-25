package com.rbc.stocktickermanager.service;

import com.rbc.stocktickermanager.entity.Trade;
import com.rbc.stocktickermanager.repository.TradeRepository;
import com.rbc.stocktickermanager.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TradeService {

    @Autowired
    TradeRepository repository;

    @Autowired
    MetaDataService metaDataService;

    @Transactional
    public Optional<Trade> reportATrade(Trade trade) {
        if (trade == null) {
            return Optional.empty();
        }

        Trade returnedTrade = repository.save(trade);

        if (returnedTrade == null) {
            return Optional.empty();
        }

        return Optional.of(returnedTrade);
    }

    @Transactional
    public Optional<List<Trade>> findATradeByTickerSymbol(String ticker) {
        // StringUtils.isEmpty was deprecated and hence using hasLength
        if (!StringUtils.hasLength(ticker)) {
            return Optional.empty();
        }

        List<Trade> trades = repository.findByStock(ticker);

        if (trades == null) {
            return Optional.empty();
        }

        return Optional.of(trades);
    }

    @Transactional
    public Optional<List<Trade>> bulkUploadTickerInfo(String fileName) throws FileNotFoundException {
        Optional<Boolean> duplicate = metaDataService.isDuplicate(fileName);

        if (duplicate != null && duplicate.isPresent() && !duplicate.get()) {
            Optional<List<Trade>> trades = CommonUtils.loadTradeDataList(fileName);
            if (trades.isPresent()) {
                List<Trade> tradeList =
                        trades.get().stream()
                        .filter(trade -> !ObjectUtils.isEmpty(trade)).collect(Collectors.toList());

                if (tradeList == null || tradeList.isEmpty() || tradeList.size() == 0) {
                    return Optional.empty();
                } else {

                    List<Trade> savedTrades = (List<Trade>) repository.saveAll(tradeList);

                    if (savedTrades == null || savedTrades.isEmpty()) {
                        return Optional.empty();
                    }

                    metaDataService.recordMetaData(fileName);
                    return Optional.of(savedTrades);
                }
            }
        }

        return Optional.empty();
    }

    @Transactional
    public List<Trade> findAll(){
        return (List<Trade>) repository.findAll();
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}

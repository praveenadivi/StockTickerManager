package com.rbc.stocktickermanager.service;

import com.rbc.stocktickermanager.entity.MetaData;
import com.rbc.stocktickermanager.entity.Trade;
import com.rbc.stocktickermanager.repository.TradeRepository;
import com.rbc.stocktickermanager.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private MetaDataService metaDataService;

    @InjectMocks
    private TradeService tradeService;

    private static final String TEST_STOCK = "AA";
    private static final String FILE_NAME = "src/test/resources/dow_jones_index.data";

    @Test
    public void test_report_a_trade_happy_path() {
        when(tradeRepository.save(ArgumentMatchers.any(Trade.class))).thenReturn(new Trade());
        Optional<Trade> trade = tradeService.reportATrade(new Trade());
        assertThat(trade, is(notNullValue()));
        assertThat(trade.isPresent(), is(true));
        assertThat(trade.get(), is(notNullValue()));
    }

    @Test
    public void test_report_a_trade_null() {
        Optional<Trade> trade = tradeService.reportATrade(null);
        assertThat(trade, is(notNullValue()));
        assertThat(trade.isPresent(), is(false));
    }

    @Test
    public void testFindTradeByTickerSymbol_happy_path() throws FileNotFoundException {
        Optional<List<Trade>> tradeData
                = CommonUtils.loadTradeDataList(FILE_NAME);
        List<Trade> trades = tradeData.get().stream()
                .filter(trade -> trade != null)
                .filter(trade -> TEST_STOCK.equalsIgnoreCase(trade.getStock()))
                .collect(Collectors.toUnmodifiableList());

        when(tradeRepository.findByStock(ArgumentMatchers.any(String.class))).thenReturn(trades);

        Optional<List<Trade>> tradesFound= tradeService.findATradeByTickerSymbol(TEST_STOCK);
        assertThat(tradesFound, is(notNullValue()));
        assertThat(tradesFound.isPresent(), is(true));
        assertThat(tradesFound.get(), is(notNullValue()));
    }

    @Test
    public void testFindTradeByTickerSymbol_No_trades_found_empty_list() throws FileNotFoundException {
        List<Trade> trades = Collections.EMPTY_LIST;

        when(tradeRepository.findByStock(ArgumentMatchers.any(String.class))).thenReturn(trades);

        Optional<List<Trade>> tradesFound= tradeService.findATradeByTickerSymbol(TEST_STOCK);
        assertThat(tradesFound, is(notNullValue()));
        assertThat(tradesFound.isPresent(), is(true));
        assertThat(tradesFound.get(), is(notNullValue()));
        assertThat(tradesFound.get().isEmpty(), is(true));
    }

    @Test
    public void testFindTradeByTickerSymbol_No_trades_found_null_list() throws FileNotFoundException {
        List<Trade> trades = null;

        when(tradeRepository.findByStock(ArgumentMatchers.any(String.class))).thenReturn(trades);

        Optional<List<Trade>> tradesFound= tradeService.findATradeByTickerSymbol(TEST_STOCK);
        assertThat(tradesFound, is(notNullValue()));
        assertThat(tradesFound.isPresent(), is(false));
    }

    @Test
    public void test_bulk_upload_ticker_happy_path() throws IOException {

        Optional<List<Trade>> tradeData
                = CommonUtils.loadTradeDataList(FILE_NAME);

        when(metaDataService.isDuplicate(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(false));
        when(tradeRepository.saveAll(ArgumentMatchers.any(List.class)))
                .thenReturn(tradeData.get());

        when(metaDataService.recordMetaData(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(new MetaData()));

        Optional<List<Trade>> trades = tradeService.bulkUploadTickerInfo(FILE_NAME);

        assertThat(trades, is(notNullValue()));
        assertThat(trades.isPresent(), is(true));
        assertThat(trades.get(), is(notNullValue()));
    }

    @Test
    public void test_bulk_upload_ticker_wrong_file_name() throws IOException {

        Optional<List<Trade>> trades = tradeService.bulkUploadTickerInfo("src/test/resources/dow_jones_index.data1");

        assertThat(trades, is(notNullValue()));
        assertThat(trades.isPresent(), is(false));
    }

    @Test
    public void test_bulk_upload_ticker_no_data() throws IOException {

        Optional<List<Trade>> tradeData
                = Optional.of(Collections.emptyList());

        when(metaDataService.isDuplicate(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(false));
        when(tradeRepository.saveAll(ArgumentMatchers.any(List.class)))
                .thenReturn(tradeData.get());

        Optional<List<Trade>> trades = tradeService.bulkUploadTickerInfo(FILE_NAME);

        assertThat(trades.isPresent(), is(false));
    }

    @Test
    public void test_bulk_upload_ticker_duplicates_found() throws IOException {

        when(metaDataService.isDuplicate(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(true));

        Optional<List<Trade>> trades = tradeService.bulkUploadTickerInfo(FILE_NAME);

        assertThat(trades, is(notNullValue()));
        assertThat(trades.isPresent(), is(false));
    }

}

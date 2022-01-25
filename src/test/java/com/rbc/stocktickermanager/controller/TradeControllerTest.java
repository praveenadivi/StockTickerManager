package com.rbc.stocktickermanager.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbc.stocktickermanager.entity.Trade;
import com.rbc.stocktickermanager.service.MetaDataService;
import com.rbc.stocktickermanager.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Path;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TradeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private TradeService tradeService;

    @Test
    public void test_loading() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("successfully loaded the TradeController")));
    }

    @Test
    public void test_find_all_empty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/findAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_load_file_and_find_all()  throws Exception {
        metaDataService.deleteAll();
        tradeService.deleteAll();

        String absolutePath = Path.of("src/test/resources/dow_jones_index.data")
                .toAbsolutePath().toString();
        mvc.perform(MockMvcRequestBuilders.post("/loadTrades")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("fileName", absolutePath))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/findAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_add_a_trade_and_search_for_it() throws Exception {
        Trade trade = new Trade();

        trade.setLow(200);
        trade.setHigh(250);
        trade.setQuarter(1);
        trade.setPercentChangePrice(20);

        trade.setDate(new Date());
        trade.setClose(225);
        trade.setDaysToNextDividend(15);
        trade.setOpen(200);

        trade.setPercentChangeVolumeOverLastWk(25);
        trade.setVolume(2345646);
        trade.setNextWeeksOpen(230);
        trade.setPercentReturnNextDividend(23);

        trade.setPreviousWeeksVolume(2234678);
        trade.setStock("CRNT");

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(MockMvcRequestBuilders.post("/report")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(trade)))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/ticker/CRNT")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult result) throws Exception {
                        String actual = result.getResponse().getContentType();
                        assertThat(actual.toString(), is(MediaType.APPLICATION_JSON.toString()));
                        assertThat(result.getResponse().getContentAsString(), containsString("CRNT"));
                    }
                });
    }
}

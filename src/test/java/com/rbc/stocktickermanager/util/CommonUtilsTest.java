package com.rbc.stocktickermanager.util;

import com.rbc.stocktickermanager.entity.Trade;
import com.rbc.stocktickermanager.utils.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CommonUtilsTest {

    @Test
    public void testCommonUtils_md5Hex() {

        Optional<String> hex = CommonUtils.getMd5Hex("test");
        assertThat(hex, is(notNullValue()));
        assertThat(hex.isPresent(), is(true));
        assertThat(hex.get(), is(notNullValue()));
        assertThat(hex.get(), is("098F6BCD4621D373CADE4E832627B4F6"));
    }

    @Test
    public void testCommonUtils_loadTradeDataList_incorrect_file() throws FileNotFoundException {
        FileNotFoundException thrown =  Assertions.assertThrows(FileNotFoundException.class,
                () -> CommonUtils.loadTradeDataList("test"));
        assertThat(thrown.getMessage(), containsString("No such file"));
        assertThat(CommonUtils.loadTradeDataList(""), is(nullValue()));
    }

    @Test
    public void testCommonUtils_load_correctFile() throws FileNotFoundException {
        Optional<List<Trade>> tradeData = CommonUtils.loadTradeDataList("src/test/resources/dow_jones_index.data");
        assertThat(tradeData.isPresent(), is(true));
        assertThat(tradeData.get(), is(notNullValue()));
        assertThat(tradeData.get().size(), is(750));
        assertThat(tradeData.get().get(0).toString(),
                is("1,AA,01/07/2011,15.82,16.72,15.78,16.42,239655616," +
                        "3.79267,0.0,0.0,16.71,15.97,-4.42849,26,0.182704"));
    }
}

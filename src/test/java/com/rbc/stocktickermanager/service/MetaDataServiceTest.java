package com.rbc.stocktickermanager.service;

import com.rbc.stocktickermanager.entity.MetaData;
import com.rbc.stocktickermanager.repository.MetaDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class MetaDataServiceTest {
    @Mock
    private MetaDataRepository metaDataRepository;

    @InjectMocks
    private MetaDataService metaDataService;

    @Test
    public void testMd5Sum_noDuplicate() throws IOException {
        String data = Files.readString(Paths.get("src/test/resources/dow_jones_index.data"));
        when(metaDataRepository.findByMd5Hex(ArgumentMatchers.any(String.class))).thenReturn(null);

        Optional<Boolean> duplicate
                = metaDataService
                .isDuplicate("src/test/resources/dow_jones_index.data");
        assertThat(duplicate.isPresent(), is(true));
        assertThat(duplicate.get(), is(false));
    }

    @Test
    public void testMd5Sum_noDuplicate_1() throws IOException {
        String data = Files.readString(Paths.get("src/test/resources/dow_jones_index.data"));
        when(metaDataRepository.findByMd5Hex(ArgumentMatchers.any(String.class))).thenReturn(new ArrayList<>());

        Optional<Boolean> duplicate
                = metaDataService
                .isDuplicate("src/test/resources/dow_jones_index.data");
        assertThat(duplicate.isPresent(), is(true));
        assertThat(duplicate.get(), is(false));
    }

    @Test
    public void testMd5Sum_duplicate_exists() throws IOException {

        List<MetaData> list = new ArrayList<>();

        MetaData metaData = new MetaData();
        metaData.setMd5Hex("test");
        metaData.setInsertDate(new Date());
        metaData.setFilePath("src/test/resources/dow_jones_index.data");

        list.add(metaData);

        when(metaDataRepository.findByMd5Hex(ArgumentMatchers.any(String.class))).thenReturn(list);

        Optional<Boolean> duplicate
                = metaDataService
                .isDuplicate("src/test/resources/dow_jones_index.data");
        assertThat(duplicate.isPresent(), is(true));
        assertThat(duplicate.get(), is(true));
    }
}

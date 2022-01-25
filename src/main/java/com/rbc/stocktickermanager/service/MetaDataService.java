package com.rbc.stocktickermanager.service;

import com.rbc.stocktickermanager.entity.MetaData;
import com.rbc.stocktickermanager.repository.MetaDataRepository;
import com.rbc.stocktickermanager.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MetaDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MetaDataService.class);

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Transactional
    public Optional<MetaData> recordMetaData(String filePath) {
        try {
            String fileData = Files.readString(Path.of(filePath));

            if (fileData == null) {
                return Optional.empty();
            }

            Optional<String> md5Sum = CommonUtils.getMd5Hex(fileData);

            if (md5Sum.isPresent()) {

                MetaData metaData = new MetaData();

                metaData.setFilePath(filePath);
                metaData.setInsertDate(new Date());
                metaData.setMd5Hex(md5Sum.get());

                MetaData savedMetaData = (MetaData) metaDataRepository.save(metaData);
                return Optional.of(savedMetaData);
            } else {
                LOG.debug("The file data was invalid");
            }

        } catch (Exception e) {

            LOG.error("cannot read data from the file: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Boolean> isDuplicate(String filePath) {
        try {

            String fileData = Files.readString(Path.of(filePath));

            if (fileData == null) {

                return Optional.empty();
            }

            Optional<String> md5Sum = CommonUtils.getMd5Hex(fileData);

            if (md5Sum.isPresent()) {

                List<MetaData> metaDataObjs = metaDataRepository.findByMd5Hex(md5Sum.get());

                if (metaDataObjs == null || metaDataObjs.isEmpty()) {

                    return Optional.of(false);
                } else {

                    return Optional.of(true);
                }

            } else {

               return Optional.empty();
            }

        } catch (Exception e) {

            LOG.error("cannot read data from the file: " + e.getMessage(), e);
        }

        return Optional.empty();
    }


    @Transactional
    public void deleteAll() {
        metaDataRepository.deleteAll();
    }
}

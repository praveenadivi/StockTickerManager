package com.rbc.stocktickermanager.repository;

import com.rbc.stocktickermanager.entity.MetaData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDataRepository extends CrudRepository<MetaData,Integer> {
    List<MetaData> findByFilePath(String filePath);

    List<MetaData> findByMd5Hex(String md5Hex);
}

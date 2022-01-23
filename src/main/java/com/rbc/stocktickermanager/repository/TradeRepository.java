package com.rbc.stocktickermanager.repository;

import com.rbc.stocktickermanager.entity.Trade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends CrudRepository<Trade, Integer> {
    public List<Trade> findByStock(String stock);
}

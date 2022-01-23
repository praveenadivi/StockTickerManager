package com.rbc.stocktickermanager.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class MetaData {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "file_path")
    private String filePath;

    @Column
    private String md5Hex;

    @Column(name = "insert_date")
    private Date insertDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMd5Hex() {
        return md5Hex;
    }

    public void setMd5Hex(String md5Hex) {
        this.md5Hex = md5Hex;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}

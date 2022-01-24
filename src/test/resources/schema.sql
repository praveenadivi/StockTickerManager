DROP TABLE IF EXISTS TRADE_DATA;
CREATE TABLE TRADE_DATA (
id Integer primary key,
quarter Integer,
stock VARCHAR(10),
date Date,
open Numeric(15,2),
high Numeric(15,2),
low Numeric(15,2),
close Numeric(15,2),
volume BIGINT,
percent_change_price Numeric(7,2),
percent_change_volume_over_last_wk Numeric (7,2),
previous_weeks_volume DOUBLE PRECISION,
next_weeks_open Numeric(15,2),
next_weeks_close Numeric(15,2),
percent_change_next_weeks_price Numeric(7,2),
days_to_next_dividend Integer,
percent_return_next_dividend Numeric(7,2));

DROP TABLE IF EXISTS META_DATA;
CREATE TABLE META_DATA (
    id INTEGER primary key,
    file_path varchar(1000),
    md5_hex varchar(100),
    insert_date DATE
);

drop sequence IF EXISTS TRADE_DATA_SEQ;
create sequence TRADE_DATA_SEQ start with 1 increment by 1;

drop sequence IF EXISTS META_DATE_DATA_SEQ;
create sequence META_DATE_DATA_SEQ start with 1 increment by 1;

drop sequence IF EXISTS HIBERNATE_SEQUENCE;
create sequence HIBERNATE_SEQUENCE start with 1 increment by 1;
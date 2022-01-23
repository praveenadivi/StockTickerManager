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
previous_weeks_volume Numeric(7,2),
next_weeks_open Numeric(15,2),
next_weeks_close Numeric(15,2),
percent_change_next_weeks_price Numeric(7,2),
days_to_next_dividend Integer,
percent_return_next_dividend Numeric(7,2));

DROP TABLE IF EXISTS UPLOAD_META_DATA;
CREATE TABLE UPLOAD_META_DATA (
    id INTEGER primary key,
    file_path varchar(1000),
    md5Hex varchar(32),
    insert_date DATE
)
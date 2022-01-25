package com.rbc.stocktickermanager.constants;

import java.text.SimpleDateFormat;

public class Constants {
    public static final String EMPTY = "";

    private Constants() {

    }

    public static class SupportedFormats {
        private SupportedFormats() {
        }

        public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    }
}

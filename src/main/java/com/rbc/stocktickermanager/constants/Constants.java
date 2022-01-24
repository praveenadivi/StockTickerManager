package com.rbc.stocktickermanager.constants;

import com.google.common.collect.ImmutableMap;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

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

package org.mitumc.sdk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
    final public static String ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private String ISOtimestamp;
    private String UTCtimestamp;

    private String date;
    private String time;

    TimeStamp() {
        this(new Date());
    }

    public TimeStamp(Date timestamp) {
        DateFormat ISOformatter = new SimpleDateFormat(ISO_PATTERN);
        this.ISOtimestamp = ISOformatter.format(timestamp);
        formatTimestamp();
    }

    public TimeStamp(String timestamp) {
        this.ISOtimestamp = timestamp;
        int t = timestamp.indexOf("T");
        int z = timestamp.indexOf("Z");

        if(t < 0 || z < 0) {
            Util.raiseError("Invalid timestamp; TimeStamp.");
            return;
        }
        formatTimestamp();
    }

    private void formatTimestamp() {
        int t = this.ISOtimestamp.indexOf("T");
        int z = this.ISOtimestamp.indexOf("Z");

        this.date = this.ISOtimestamp.substring(0, t);
        this.time = this.ISOtimestamp.substring(11, z);

        if(this.time.indexOf(".") < 0) {
            this.UTCtimestamp = this.date + " " + this.time + " +0000 UTC";
        }
        else {
            int dot = this.time.indexOf(".");
            String decimal = this.time.substring(dot + 1);

            boolean isAllZero = true;
            int zero = decimal.length();
            for(int i = decimal.length() - 1; i > -1; i--) {
                if(decimal.charAt(i) != '0') {
                    isAllZero = false;
                    break;
                }
                zero = i;
            }

            if(isAllZero) {
                this.UTCtimestamp = this.date + " " + this.time.substring(0, dot) + " +0000 UTC";
                return;
            }

            String rdecimal = decimal.substring(0, zero);
            this.UTCtimestamp = this.date + " " + this.time.substring(0, dot) + "." + rdecimal + " +0000 UTC";
        }
    }

    public String getISO() {
        return this.ISOtimestamp;
    }

    public String getUTC() {
        return this.UTCtimestamp;
    }
}
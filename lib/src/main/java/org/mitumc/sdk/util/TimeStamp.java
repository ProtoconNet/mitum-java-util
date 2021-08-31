package org.mitumc.sdk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
    final public static String ISO_PATTERN = "yyyy-MM-ddTHH:mm:ssZ";
    final public static String UTC_PATTERN = "yyyy-MM-dd HH:mm:ss +0000 UTC";
    final public static DateFormat ISOformatter = new SimpleDateFormat(ISO_PATTERN);
    final public static DateFormat UTCformatter = new SimpleDateFormat(UTC_PATTERN);
    private Date timestamp;
    private String ISOtimestamp;
    private String UTCtimestamp;
    
    TimeStamp() {
        this(new Date());
    }

    TimeStamp(Date timestamp) {
        this.timestamp = timestamp;
        formatTimestamp();
    }

    TimeStamp(String timestamp) {

        try {
            if(timestamp.indexOf('T') > -1 && timestamp.indexOf('Z') > -1) {
                this.timestamp = ISOformatter.parse(timestamp);
            }
            else if(timestamp.indexOf("UTC") > -1) {
                this.timestamp = UTCformatter.parse(timestamp);
            }
            else {
            Util.raiseError("Invalid timestamp format for Timestamp.");
            }
        } catch(Exception e) {
            Util.raiseError("Parse error for Timestamp.");
        }

        formatTimestamp();
    }

    private void formatTimestamp() {
        this.ISOtimestamp = ISOformatter.format(this.timestamp);
        this.UTCtimestamp = UTCformatter.format(this.timestamp);
    }

    public String getISO() {
        return this.ISOtimestamp;
    }

    public String getUTC()  {
        return this.UTCtimestamp;
    }
}
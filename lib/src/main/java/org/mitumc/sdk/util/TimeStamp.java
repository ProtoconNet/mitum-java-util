package org.mitumc.sdk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
    final public static String ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    final public static String UTC_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS +0000 'UTC'";
    private DateFormat ISOformatter;
    private DateFormat UTCformatter;
    private Date timestamp;
    private String ISOtimestamp;
    private String UTCtimestamp;
    
    TimeStamp() {
        this(new Date());
    }

    public TimeStamp(Date timestamp) {
        this.ISOformatter = new SimpleDateFormat(ISO_PATTERN);
        this.UTCformatter = new SimpleDateFormat(UTC_PATTERN);

        this.timestamp = timestamp;
        formatTimestamp();
    }

    public TimeStamp(String timestamp) {
        this.ISOformatter = new SimpleDateFormat(ISO_PATTERN);
        this.UTCformatter = new SimpleDateFormat(UTC_PATTERN);

        try {
            if(timestamp.indexOf('T') > -1 && timestamp.indexOf('Z') > -1) {
                this.timestamp = this.ISOformatter.parse(timestamp);
            }
            else if(timestamp.indexOf("UTC") > -1) {
                this.timestamp = this.UTCformatter.parse(timestamp);
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
        this.ISOtimestamp = this.ISOformatter.format(this.timestamp);
        this.UTCtimestamp = this.UTCformatter.format(this.timestamp);
    }

    public String getISO() {
        return this.ISOtimestamp;
    }

    public String getUTC()  {
        return this.UTCtimestamp;
    }
}
package ramo.klevis;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class SwingLogHandler extends Handler {

    private ArrayList<LogSubscriber> logSubscribers;


    public SwingLogHandler(){
        setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });

        logSubscribers = new ArrayList<>();
    }

    public void addLogSubscriber(LogSubscriber logSubscriber){
        logSubscribers.add(logSubscriber);
    }

    @Override
    public void publish(LogRecord record) {
        String s = getFormatter().format(record);
        for(LogSubscriber logSubscriber: logSubscribers){
            logSubscriber.newLogPublished(s);
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}

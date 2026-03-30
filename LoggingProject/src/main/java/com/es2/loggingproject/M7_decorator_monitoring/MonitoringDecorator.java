package com.es2.loggingproject.M7_decorator_monitoring;
import com.es2.loggingproject.M1_config.LogLevel;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;
import com.es2.loggingproject.M4_composite_category.LogComponent;

public class MonitoringDecorator extends LogDecorator {
    private int debugLogCounter = 0;
    private int infoLogCounter = 0;
    private int warningLogCounter = 0;
    private int errorLogCounter = 0;
    private final int alertThreshold;

    public MonitoringDecorator(LogComponent wrapped) {
        this(wrapped, 50); // default threshold
    }

    public MonitoringDecorator(LogComponent wrapped, int alertThreshold){
        super(wrapped);
        this.alertThreshold = alertThreshold;
    }
    // para que e como funciona o primeiro construtor?
    @Override
    public void outputTo(LogDestinationInterface destination) {
        LogLevel level = getLevel();

        if (level != null) {
            switch (level) {
                case DEBUG -> debugLogCounter++;
                case INFO -> infoLogCounter++;
                case WARNING -> warningLogCounter++;
                case ERROR -> errorLogCounter++;
            }
            int total = debugLogCounter + infoLogCounter + warningLogCounter + errorLogCounter;
            if (total > alertThreshold) {
                String category = getCategoryName() != null ? getCategoryName() : "UNKNOWN";
                System.out.println("[MONITORING ALERT] Threshold exceeded in category '"
                        + category + "'! Total logs: " + total
                        + " (DEBUG=" + debugLogCounter + ", INFO=" + infoLogCounter
                        + ", WARNING=" + warningLogCounter + ", ERROR=" + errorLogCounter + ")");
            }
        }
        super.outputTo(destination);
    }

    public String getSummary() {
        String category = getCategoryName() != null ? getCategoryName() : "N/A";
        int total = debugLogCounter + infoLogCounter + warningLogCounter + errorLogCounter;
        return String.format(
                "[MONITORING SUMMARY] Category='%s' | DEBUG=%d | INFO=%d | WARNING=%d | ERROR=%d | TOTAL=%d",
                category, debugLogCounter, infoLogCounter, warningLogCounter, errorLogCounter, total
        );
    }

    public int getDebugCount()  { return debugLogCounter; }
    public int getInfoCount()   { return infoLogCounter; }
    public int getWarnCount()   { return warningLogCounter; }
    public int getErrorCount()  { return errorLogCounter; }
    public int getTotalCount()  {
        int total = debugLogCounter + infoLogCounter + warningLogCounter + errorLogCounter;
        return total;
    }  // total já calculado acima

}

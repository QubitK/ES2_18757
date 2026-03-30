package com.es2.loggingproject.M7_decorator_monitoring;
import com.es2.loggingproject.M1_config.LogLevel;
import com.es2.loggingproject.M3_bridge_destination.LogDestinationInterface;
import com.es2.loggingproject.M4_composite_category.LogCategory;
import com.es2.loggingproject.M4_composite_category.LogComponent;

// M7: DECORATOR PATTERN (classe abstrata base)

public abstract class LogDecorator extends LogComponent {

    protected final LogComponent wrapped;

    public LogDecorator(LogComponent wrapped){
        this.wrapped = wrapped;
    }

    @Override
    public void outputTo(LogDestinationInterface destination){
        wrapped.outputTo(destination);
    }

    @Override
    public LogLevel getLevel() {
        return wrapped.getLevel();
    }

    // Retorna o nome da categoria percorrendo a cadeia de decorators
    public String getCategoryName() {
        LogComponent current = wrapped;
        while(current != null) {
            if(current instanceof CategoryDecorator cd) {
                return cd.getCategoryName();
            }
            if(current instanceof LogDecorator ld) {
                current = ld.wrapped;
                continue;
            }
            if(current instanceof LogCategory cat) {
                return cat.getName();
            }
            break;
        }
        return null;
    }


}

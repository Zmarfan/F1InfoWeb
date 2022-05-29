package common.logger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerFactory {
    public Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger("f1Logger");
    }
}

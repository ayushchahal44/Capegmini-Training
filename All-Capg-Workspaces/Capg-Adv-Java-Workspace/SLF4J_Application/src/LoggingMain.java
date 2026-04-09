

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMain {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingMain.class);

    public static void main(String[] args) {

        logger.info("Application started");

        int a = 10;
        int b = 0;

        try {
            int result = a / b;
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
        }

        logger.info("Application finished");
    }
}
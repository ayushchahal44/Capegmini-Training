

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jAdvancedExample {

    // 1. Logger creation (static + final best practice)
    private static final Logger logger =
            LoggerFactory.getLogger(Slf4jAdvancedExample.class);

    public static void main(String[] args) {

        // 2. Basic log levels
        logger.trace("TRACE: Very detailed information");
        logger.debug("DEBUG: Debugging information");
        logger.info("INFO: Application started");
        logger.warn("WARN: Warning message");
        logger.error("ERROR: Error message");

        // 3. Placeholder (parameterized logging) - BEST PRACTICE
        String name = "Ram";
        int age = 25;

        logger.info("User name is {} and age is {}", name, age);

        // 4. Multiple placeholders
        int a = 10, b = 20;
        logger.debug("Values: a = {}, b = {}, sum = {}", a, b, (a + b));

        // 5. Avoid string concatenation (performance)
        logger.info("Correct way: {}", "No string concatenation");

        // 6. Exception logging
        try {
            int result = 10 / 0;
        } catch (Exception e) {

            // Wrong way
            logger.error("Exception message: {}", e.getMessage());

            //  Correct way (prints full stack trace)
            logger.error("Exception occurred while dividing", e);
        }

        // 7. Conditional logging (improves performance)
        if (logger.isDebugEnabled()) {
            logger.debug("Debug is enabled, executing extra logic...");
        }

        // 8. Logging with method flow
        logger.info("Entering method: calculate()");
        calculate(5, 0);
        logger.info("Exiting method: calculate()");

        // 9. Marker-like usage (concept)
        logger.info("Application finished successfully");
    }

    // Additional method
    public static void calculate(int x, int y) {

        logger.debug("Inside calculate method: x={}, y={}", x, y);

        try {
            int result = x / y;
            logger.info("Result is {}", result);
        } catch (Exception e) {
            logger.error("Error in calculate()", e);
        }
    }
}
package raf.dsw.classycraft.app.gui.swing.controller.logger;

public class LoggerFactory {

    public Logger createLogger(String string){
        if (string.equalsIgnoreCase("console"))
            return new ConsoleLogger();

        else if (string.equalsIgnoreCase("file"))
            return new FileLogger();

        return null;
    }
}

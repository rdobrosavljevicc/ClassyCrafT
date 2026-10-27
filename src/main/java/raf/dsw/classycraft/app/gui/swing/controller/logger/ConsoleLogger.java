package raf.dsw.classycraft.app.gui.swing.controller.logger;

import java.io.IOException;

public class ConsoleLogger implements Logger{
    @Override
    public void log(String string) throws IOException {
        System.out.println(string);
    }

    @Override
    public void update(Object notification) {

    }
}

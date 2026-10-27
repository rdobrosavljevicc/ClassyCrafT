package raf.dsw.classycraft.app.gui.swing.controller.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger implements Logger{
    @Override
    public void log(String string) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("src/main/resources/maven/log.txt"));
        pw.println(string);

        pw.close();
    }

    @Override
    public void update(Object notification) {

    }
}

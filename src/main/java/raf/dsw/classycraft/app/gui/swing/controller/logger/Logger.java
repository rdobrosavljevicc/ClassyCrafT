package raf.dsw.classycraft.app.gui.swing.controller.logger;

import raf.dsw.classycraft.app.observer.ISubscriber;

import java.io.IOException;

public interface Logger extends ISubscriber {
    void log(String string) throws IOException;
}

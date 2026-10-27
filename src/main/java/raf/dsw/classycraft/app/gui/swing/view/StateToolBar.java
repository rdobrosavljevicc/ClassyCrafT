package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;

public class StateToolBar extends JToolBar{
    protected StateToolBar() {
        super(VERTICAL);
        setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getAddInterclassAction());
        add(MainFrame.getInstance().getActionManager().getAddConnectionAction());
        add(MainFrame.getInstance().getActionManager().getChangeInterclassAction());
        add(MainFrame.getInstance().getActionManager().getDeleteElementAction());
        add(MainFrame.getInstance().getActionManager().getSelectElementAction());
        add(MainFrame.getInstance().getActionManager().getMoveElementAction());
        add(MainFrame.getInstance().getActionManager().getMoveEnvironmentAction());
        add(MainFrame.getInstance().getActionManager().getSaveImageAction());
    }
}

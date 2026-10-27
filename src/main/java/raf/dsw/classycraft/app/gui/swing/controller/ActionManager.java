package raf.dsw.classycraft.app.gui.swing.controller;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.gui.swing.controller.myActions.*;
import raf.dsw.classycraft.app.gui.swing.controller.stateActions.*;

@Getter
@Setter
public class ActionManager {
    private ExitAction exitAction;
    private NewNodeAction newNodeAction;
    private DeleteNodeAction deleteNodeAction;
    private AboutUsAction aboutUsAction;
    private EditAuthorAction editAuthorAction;
    private AddInterclassAction addInterclassAction;
    private AddConnectionAction addConnectionAction;
    private ChangeInterclassAction changeInterclassAction;
    private DeleteElementAction deleteElementAction;
    private SelectElementAction selectElementAction;
    private MoveElementAction moveElementAction;
    private MoveEnvironmentAction moveEnvironmentAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private SaveProjectAction saveProjectAction;
    private LoadProjectAction loadProjectAction;
    private SaveImageAction saveImageAction;
    private ProjectToCodeAction projectToCodeAction;

    public ActionManager() {
        initialiseActions();
    }

    public void initialiseActions(){
        exitAction = new ExitAction();
        newNodeAction = new NewNodeAction();
        deleteNodeAction = new DeleteNodeAction();
        aboutUsAction = new AboutUsAction();
        editAuthorAction = new EditAuthorAction();
        addInterclassAction = new AddInterclassAction();
        addConnectionAction = new AddConnectionAction();
        changeInterclassAction = new ChangeInterclassAction();
        deleteElementAction = new DeleteElementAction();
        selectElementAction = new SelectElementAction();
        moveElementAction = new MoveElementAction();
        moveEnvironmentAction = new MoveEnvironmentAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        saveProjectAction = new SaveProjectAction();
        loadProjectAction = new LoadProjectAction();
        saveImageAction = new SaveImageAction();
        projectToCodeAction = new ProjectToCodeAction();
    }
}

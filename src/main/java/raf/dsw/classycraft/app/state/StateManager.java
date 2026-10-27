package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.state.concrete.*;

public class StateManager {
    private State currState;
    private AddInterclassState addInterclassState;
    private AddConnectionState addConnectionState;
    private ChangeInterclassState changeInterclassState;
    private DeleteDiagramElementState deleteDiagramElement;
    private SelectDiagramElementState selectDiagramElementState;
    private MoveElementState moveElementState;
    private MoveEnvironmentState moveEnvironmentState;

    public StateManager() {
        initStates();
    }

    private void initStates() {
        addInterclassState = new AddInterclassState();
        addConnectionState = new AddConnectionState();
        changeInterclassState = new ChangeInterclassState();
        deleteDiagramElement = new DeleteDiagramElementState();
        selectDiagramElementState = new SelectDiagramElementState();
        moveElementState = new MoveElementState();
        moveEnvironmentState = new MoveEnvironmentState();
        currState = addInterclassState;
    }

    public State getCurrent(){
        return currState;
    }

    public void setAddInterclass() {
        this.currState = addInterclassState;
    }

    public void setAddConnection() {
        this.currState = addConnectionState;
    }

    public void setAddClassContent() {
        this.currState = changeInterclassState;
    }

    public void setDeleteDiagramElement() {
        this.currState = deleteDiagramElement;
    }

    public void setSelectDiagramElement() {
        this.currState = selectDiagramElementState;
    }

    public void setMoveElementState() {
        this.currState = moveElementState;
    }
    public void setMoveEnvironmentState() {
        this.currState = moveEnvironmentState;
    }
}

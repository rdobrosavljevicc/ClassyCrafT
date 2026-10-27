package raf.dsw.classycraft.app.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFramework {
    protected Gui gui;
    protected ClassyRepository classyRepository;
    protected Serializer serializer;

    public void run(){
        this.gui.start();
    }

    public void initialize(Gui gui, ClassyRepository classyRepository, Serializer serializer){
        this.gui = gui;
        this.classyRepository = classyRepository;
        this.serializer = serializer;
    }

    //Singleton
    private static ApplicationFramework instance;

    private ApplicationFramework(){

    }

    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }
}

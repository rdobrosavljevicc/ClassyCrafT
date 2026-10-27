package raf.dsw.classycraft.app.classyRepository;

import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.ClassyRepository;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;


public class ClassyRepositoryImplementation implements ClassyRepository, IPublisher {
    private ProjectExplorer projectExplorer;
    private List<ISubscriber> subscribers;

    public ClassyRepositoryImplementation() {
        this.projectExplorer = new ProjectExplorer("My Project Explorer");
    }

    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    @Override
    public void addChild(ClassyNodeComposite parent, ClassyNode child) {
        if (parent.getChildren().contains(child)){
            return;
        }
        ClassyNode node = parent;
        while (!(node instanceof ProjectExplorer) && node != null){
            if (node instanceof Project)
                ((Project) node).setChanged(true);
            node = node.getParent();
        }
        child.setParent(parent);
        parent.getChildren().add(child);
        notifySubscribers(child);
    }

    @Override
    public void deleteNode(ClassyNode child) {
        ClassyNode node = child.getParent();
        while (!(node instanceof ProjectExplorer) && node != null){
            if (node instanceof Project)
                ((Project) node).setChanged(true);
            node = node.getParent();
        }
        ((ClassyNodeComposite)child.getParent()).getChildren().remove(child);
        notifySubscribers(child);
    }

    @Override
    public void editAuthor(ClassyNode project, String newAuthor) {
        ((Project) project).setChanged(true);
        ((Project)project).setAuthor(newAuthor);
        notifySubscribers(this);
    }

    @Override
    public void editPosition(Interclass interclass, int xDiff, int yDiff){
        ClassyNode node = interclass.getParent();
        while (!(node instanceof ProjectExplorer)){
            if (node instanceof Project)
                ((Project) node).setChanged(true);
            node = node.getParent();
        }
        interclass.getPosition().x += xDiff;
        interclass.getPosition().y += yDiff;
        notifySubscribers(this);
    }

    @Override
    public void addSubscriber(ISubscriber sub) {
        if (sub == null)
            return;
        if (this.subscribers == null)
            this.subscribers = new ArrayList<>();
        if (this.subscribers.contains(sub))
            return;
        this.subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        if (sub == null || this.subscribers == null || !this.subscribers.contains(sub))
            return;
        this.subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification) {
        if (notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;
        for (ISubscriber subscriber : this.subscribers)
            subscriber.update(notification);
    }
}

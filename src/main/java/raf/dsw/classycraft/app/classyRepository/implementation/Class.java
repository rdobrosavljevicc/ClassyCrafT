package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassContent;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class Class extends Interclass {
    ArrayList<ClassContent> content;

    public Class(String name, ClassyNode parent, Paint paint, Stroke stroke, String visibility, Point position, Dimension size) {
        super(name, parent, paint, stroke, visibility, position, size);
        content = new ArrayList<>();
    }

    @Override
    public void updateSize(FontMetrics metrics) {
        int width = 99;
        int height = 50;
        int currHeight = 0;
        int currWidth = 0;

        int name = metrics.stringWidth(getVisibility() + " " + getName());

        if (name > width)
            currWidth = name;

        for (ClassContent content : content){
            currHeight += metrics.getHeight();
            if (metrics.stringWidth(content.toString()) > currWidth)
                currWidth = metrics.stringWidth(content.toString());
        }

        if (currWidth > width)
            width = currWidth;
        if (currHeight > height)
            height = currHeight;

        this.getSize().width = width + 1;
        this.getSize().height = height;
    }

    public void rearrangeContent(){
        ArrayList<ClassContent> newContent = new ArrayList<>();
        for (ClassContent content : content){
            if (content instanceof Attribute)
                newContent.add(content);
        }
        for (ClassContent content : content){
            if (content instanceof Method)
                newContent.add(content);
        }
        content = newContent;
    }
}

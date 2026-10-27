package raf.dsw.classycraft.app.gui.swing.view.painter;

import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class EnumPainter extends InterclassPainter{
    public EnumPainter(DiagramElement element) {
        super(element);
        element.setStroke(new BasicStroke(2));
        Enum interclass = (Enum) element;
        this.getClassContent().clear();
        for (String content: interclass.getContent()){
            this.getClassContent().add(content);
        }
        this.setShape(new GeneralPath());
        ((GeneralPath)this.getShape()).moveTo((float)interclass.getPosition().x, (float)interclass.getPosition().y);
        ((GeneralPath)this.getShape()).lineTo((float)(interclass.getPosition().x + interclass.getSize().width), (float)interclass.getPosition().y);
        ((GeneralPath)this.getShape()).lineTo((float)(interclass.getPosition().x + interclass.getSize().width), (float)(interclass.getPosition().y + interclass.getSize().height));
        ((GeneralPath)this.getShape()).lineTo((float)interclass.getPosition().x, (float)(interclass.getPosition().y + interclass.getSize().height));
        ((GeneralPath)this.getShape()).closePath();
    }
}

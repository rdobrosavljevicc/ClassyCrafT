package raf.dsw.classycraft.app.classyRepository.composite;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Connection extends DiagramElement{
    private Interclass fromElement;
    private Interclass toElement;

    public Connection(String name, ClassyNode parent, Paint paint, Stroke stroke) {
        super(name, parent, paint, stroke);
    }

    public Connection(String name, ClassyNode parent, Paint paint, Stroke stroke, Interclass fromElement, Interclass toElement) {
        super(name, parent, paint, stroke);
        this.fromElement = fromElement;
        this.toElement = toElement;
    }

    @Override
    public boolean equals(Object o) {
        return this.getClass() == o.getClass() && ((this.getFromElement() == ((Connection) o).getFromElement() && this.getToElement() == ((Connection) o).getToElement()) ||
                (this.getFromElement() == ((Connection) o).getToElement() && this.getToElement() == ((Connection) o).getFromElement()));
    }
}

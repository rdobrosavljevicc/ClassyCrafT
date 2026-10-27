package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.state.State;
import raf.dsw.classycraft.app.state.concrete.AddConnectionState;
import raf.dsw.classycraft.app.state.concrete.AddInterclassState;

public class ElementFactoryUtils {
    static ClassFactory classFactory;
    static EnumFactory enumFactory;
    static InterfaceFactory interfaceFactory;
    static GeneralizationFactory generalizationFactory;
    static DependencyFactory dependencyFactory;
    static CompositionFactory compositionFactory;
    static AggregationFactory aggregationFactory;
    public static DiagramElementFactory getNodeFactory(int childType, State state){
        if (state instanceof AddInterclassState) {
            if (childType == 0)
                return classFactory;
            else if(childType == 1)
                return interfaceFactory;
            else
                return enumFactory;
        }
        else if (state instanceof AddConnectionState) {
            if (childType == 0)
                return generalizationFactory;
            else if (childType == 1)
                return dependencyFactory;
            else if (childType == 2)
                return compositionFactory;
            else
                return aggregationFactory;
        }
        return null;
    }

    static{
        classFactory = new ClassFactory();
        enumFactory = new EnumFactory();
        interfaceFactory = new InterfaceFactory();
        generalizationFactory = new GeneralizationFactory();
        dependencyFactory = new DependencyFactory();
        compositionFactory = new CompositionFactory();
        aggregationFactory = new AggregationFactory();
    }
}

package com.mibesoft.appicon;

import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;
import java.awt.Color;
import java.util.List;
import lombok.*;

@ToString
@Getter
@Setter
@XStreamAlias("template")
public class Template {
    
    String folderPath;
    String filename;
    String extension;
 
    @XStreamAlias("window")
    Window window;
    
    @ToString
    @Getter
    @Setter
    public class Window {
        int width;
        int height;
        int borderRadius;
        @XStreamConverter(AwtColorConverter.class)
        Color gradient1;
        @XStreamConverter(AwtColorConverter.class)
        Color gradient2;
    }
    
    @XStreamImplicit(itemFieldName = "command")
    List<Command> commands;
    
    @ToString
    @Getter
    @Setter
    public class Command {
        String name;
        @XStreamImplicit(itemFieldName = "param")
        List<String> params;
    }
    

    
    public static class AwtColorConverter implements Converter {
        @Override
        public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
            Color color = (Color) o;
            writer.setValue(color.toString());
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
            String rgb = reader.getValue();
            Color color = new Color(Integer.parseInt(rgb, 16));
            return color;
        }

        @Override
        public boolean canConvert(Class type) {
            return type.equals(Color.class);
        }
    }
            
            
}

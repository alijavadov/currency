package com.digirella.task.dto;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValCurs {

    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "Description")
    private String description;

    @XmlElement(name = "ValType")
    private List<ValType> valTypes;
}


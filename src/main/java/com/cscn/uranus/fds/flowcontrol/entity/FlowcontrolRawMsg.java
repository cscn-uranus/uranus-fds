package com.cscn.uranus.fds.flowcontrol.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class FlowcontrolRawMsg {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String xmlContent;

    public String getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }
}

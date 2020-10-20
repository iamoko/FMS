package org.pahappa.systems.models;


import org.sers.webutils.model.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "template")
@Inheritance(strategy = InheritanceType.JOINED)
public class Template extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private TemplateType template;
    private String value;

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "template", unique = true)
    public TemplateType getTemplate() {
        return template;
    }

    public void setTemplate(TemplateType template) {
        this.template = template;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

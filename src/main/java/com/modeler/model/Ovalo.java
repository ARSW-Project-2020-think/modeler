package com.modeler.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Ovalo")
public class Ovalo extends Componente {
    public Ovalo() {
    }
    
    @Override
    public int hashCode() {
    	return 8;
    }
}

/*
 * Copyright 2014 ecabrerar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ecabrerar.examples.airalliance.formbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a Flight Backing Bean. Instance of this class can store flight
 * information.
 *
 * @author ecabrerar
 */
@XmlRootElement(name = "flight")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight {

    private int id;
    private String name;
    @XmlElement
    private Sector sourceSector = new Sector();
    @XmlElement
    private Sector destSector = new Sector();

    public Flight(int id, String name, Sector source, Sector dest) {
        this.id = id;
        this.name = name;
        this.sourceSector = source;
        this.destSector = dest;
    }

    public Flight() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the sourceSector
     */
    public Sector getSourceSector() {
        return sourceSector;
    }

    /**
     * @param sourceSector the sourceSector to set
     */
    public void setSourceSector(Sector sourceSector) {
        this.sourceSector = sourceSector;
    }

    /**
     * @return the destSector
     */
    public Sector getDestSector() {
        return destSector;
    }

    /**
     * @param destSector the destSector to set
     */
    public void setDestSector(Sector destSector) {
        this.destSector = destSector;
    }

    
}

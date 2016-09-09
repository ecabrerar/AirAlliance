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
package org.ecabrerar.examples.airalliance.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ecabrerar
 */
@Entity
@Table(name = "flight")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 10)
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @ManyToOne( optional = false)
    @JoinColumn(name = "source_sector_id")
    private Sector sourceSector;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dest_sector_id")
    private Sector destSector;

    public Flight() {
    }

    public Flight(Integer id) {
        super.setId(id);
    }

    public Flight(Integer id, String name, Sector sourceSectorId, Sector destSectorId) {
    	super.setId(id);
        this.name = name;
        this.sourceSector = sourceSectorId;
        this.destSector = destSectorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sector getSourceSector() {
        return sourceSector;
    }

    public void setSourceSector(Sector sourceSector) {
        this.sourceSector = sourceSector;
    }

    public Sector getDestSectorId() {
        return destSector;
    }

    public void setDestSectorId(Sector destSectorId) {
        this.destSector = destSectorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Flight other = (Flight) object;
        return this.getId().equals(other.getId());
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}

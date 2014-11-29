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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ecabrerar
 */
@Entity
@Table(name = "flight")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "name")
    private String name;
    
    @JoinColumn(name = "source_sector_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sector sourceSector;
    
    @JoinColumn(name = "dest_sector_id", referencedColumnName = "id")
    @ManyToOne (optional = false)
    private Sector destSector;
    
    @OneToMany(mappedBy = "flight")
    @XmlTransient
    private List<Schedule> schedules;
    
    @OneToMany(mappedBy = "flight")
    @XmlTransient
    private List<Itinerary> itineraries;

    public Flight() {
        this.itineraries = new ArrayList<>();
        this.schedules = new ArrayList<>();
    }

    public Flight(Integer id) {
        this.id = id;
    }

    public Flight(Integer id, String name, Sector sourceSectorId, Sector destSectorId) {
        this.id = id;
        this.name = name;
        this.sourceSector = sourceSectorId;
        this.destSector = destSectorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Sector getDestSector() {
        return destSector;
    }

    public void setDestSector(Sector destSector) {
        this.destSector = destSector;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Collection<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

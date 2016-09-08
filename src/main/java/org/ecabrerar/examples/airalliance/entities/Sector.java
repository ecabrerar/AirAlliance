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
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@Table(name = "sector")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Sector extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "sector")
    private String sector;

    @OneToMany(mappedBy="sourceSector", fetch = FetchType.LAZY)
    private List<Flight> sources;

    @OneToMany(mappedBy="destSector", fetch = FetchType.LAZY)
    private List<Flight> dests;

    public Sector() {
    }

    public Sector(Integer id) {
        super.setId(id);
    }

    public Sector(Integer id, String sector) {
        super.setId(id);
        this.sector = sector;
    }


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<Flight> getSources() {
		return sources;
	}

	public void setSources(List<Flight> sources) {
		this.sources = sources;
	}

	public List<Flight> getDests() {
		return dests;
	}

	public void setDests(List<Flight> dests) {
		this.dests = dests;
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

        Sector other = (Sector) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}

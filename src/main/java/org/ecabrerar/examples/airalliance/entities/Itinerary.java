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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ecabrerar.examples.airalliance.converters.LocalDateAdapter;

/**
 *
 * @author ecabrerar
 */
@Entity
@Table(name = "itinerary")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Itinerary extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne(optional = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Itinerary() {
    }

    public Itinerary(Integer id) {
        super.setId(id);
    }

    public Itinerary(Integer id, Guest guest, Flight flight, Schedule schedule) {
       super.setId(id);
        this.guest = guest;
        this.flight = flight;
        this.schedule = schedule;
    }


    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Schedule getScheduleId() {
        return schedule;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.schedule = scheduleId;
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

        Itinerary other = (Itinerary) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}

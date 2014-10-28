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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * This is a Schedule Backing Bean. 
 * Instance of this class can store Schedule information.
 * @author ecabrerar
 */

public class Schedule {
    private String id;
    private Date scheduleDate;
    private Guest guest;
    private Flight flight;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the scheduleDate
     */
    public Date getScheduleDate() {
        return scheduleDate;
    }

    /**
     * @param scheduleDate the scheduleDate to set
     */
   
    public void setScheduleDate(String scheduleDate) {
        try {
            this.scheduleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(scheduleDate);
        } catch (ParseException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * @param guest the guest to set
     */
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    /**
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }       

    
}

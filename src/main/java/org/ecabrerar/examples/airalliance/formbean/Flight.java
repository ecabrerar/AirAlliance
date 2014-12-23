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

/**
 * This is a Flight Backing Bean. Instance of this class can store flight information.
 * @author ecabrerar
 */

public class Flight {
    private String id;
    private String name;
    private Sector source=new Sector();
    private Sector dest=new Sector();

    public Flight(String id, String name, Sector source, Sector dest) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.dest = dest;
    }

    public Flight() {
    }
       

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
     * @return the source
     */
    public Sector getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Sector source) {
        this.source = source;
    }

    /**
     * @return the dest
     */
    public Sector getDest() {
        return dest;
    }

    /**
     * @param dest the dest to set
     */
    public void setDest(Sector dest) {
        this.dest = dest;
    }
}

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

package org.ecabrerar.examples.airalliance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ecabrerar.examples.airalliance.formbean.Schedule;

/**
 *
 * @author ecabrerar
 */
public class Util {
    
    public static Date toDate(String date){
        
        Date fDate = null;
        
        try {
            fDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);            
        }
        
        return fDate;
    }
}

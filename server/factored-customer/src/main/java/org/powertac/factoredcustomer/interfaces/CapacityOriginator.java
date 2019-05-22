/*
* Copyright 2011 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an
* "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
* either express or implied. See the License for the specific language
* governing permissions and limitations under the License.
*/

package org.powertac.factoredcustomer.interfaces;

import org.powertac.common.Tariff;
import org.powertac.common.TariffSubscription;
import org.powertac.factoredcustomer.CapacityAccumulator;
import org.powertac.factoredcustomer.CapacityProfile;


/**
 * @author Prashant Reddy
 */
public interface CapacityOriginator
{
  CapacityProfile getCurrentForecast ();

  CapacityProfile getForecastForNextTimeslot ();

  CapacityProfile getCurrentForecastPerSub (TariffSubscription sub);

  CapacityAccumulator useCapacity (TariffSubscription subscription);

  double adjustCapacityForSubscription (int timeslot, double totalCapacity,
                                        TariffSubscription subscription);

  String getCapacityName ();

  CapacityBundle getParentBundle ();

  CapacityProfile getForecastPerSubStartingAt (int startingTimeslot,
                                               TariffSubscription subscription);

  double getShiftingInconvenienceFactor (Tariff tariff);

  /**
   * True just in case the underlying CapacityStructure has a baseCapacityType of
   * INDIVIDUAL.
   */
  boolean isIndividual();
}

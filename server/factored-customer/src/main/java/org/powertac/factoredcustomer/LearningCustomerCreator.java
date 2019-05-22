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

package org.powertac.factoredcustomer;

import org.powertac.factoredcustomer.interfaces.*;
import org.powertac.factoredcustomer.CustomerFactory.CustomerCreator;


/**
 * Creates instances of @code{LearningFactoredCustomer}
 * through the @code{CustomerFactory}.
 *
 * @author Prashant Reddy
 */
public class LearningCustomerCreator implements CustomerCreator
{
  @Override
  public String getKey ()
  {
    return "LEARNING";
  }

  @Override
  public FactoredCustomer createModel (CustomerStructure customerStructure)
  {
    return new LearningFactoredCustomer(customerStructure);
  }
}



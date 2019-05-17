/*
 * Copyright (c) 2012 by the original author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.powertac.samplebroker;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.powertac.common.BankTransaction;
import org.powertac.common.CashPosition;
import org.powertac.common.Competition;
import org.powertac.common.msg.DistributionReport;
import org.powertac.samplebroker.interfaces.BrokerContext;
import org.powertac.samplebroker.interfaces.Initializable;
import org.springframework.stereotype.Service;

/**
 * Handles incoming context and bank messages with example behaviors. 
 * @author John Collins
 */
@Service
public class ContextManagerService
implements Initializable
{
  static private Logger log = LogManager.getLogger(ContextManagerService.class);

  BrokerContext master;

  // current cash balance
  private double cash = 0;

  private ArrayList<Double> cashArray = new ArrayList<>();
  private ArrayList<Double> totalConsumption = new ArrayList<>();
  private ArrayList<Double> totalProduction = new ArrayList<>();
  private int numberofCustomers;
  private int numberofBrokers;

//  @SuppressWarnings("unchecked")
  @Override
  public void initialize (BrokerContext broker)
  {
    if(!PrintService.getInstance().isInitialized()) {
      PrintService.getInstance().startCSV();
      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
      {

          @Override
          public void run()
          {
              System.out.println("Printing");
              PrintService.getInstance().printData();
              System.out.println("Printed");
          }
      }));
    }

    master = broker;
// --- no longer needed ---
//    for (Class<?> clazz: Arrays.asList(BankTransaction.class,
//                                       CashPosition.class,
//                                       DistributionReport.class,
//                                       Competition.class,
//                                       java.util.Properties.class)) {
//      broker.registerMessageHandler(this, clazz);
//    }    
  }

  // -------------------- message handlers ---------------------
  //
  // Note that these arrive in JMS threads; If they share data with the
  // agent processing thread, they need to be synchronized.
  
  /**
   * BankTransaction represents an interest payment. Value is positive for 
   * credit, negative for debit. 
   */
  public void handleMessage (BankTransaction btx)
  {
    // TODO - handle this
    log.info("Bank transaction: " + btx.toString());
  }

  /**
   * CashPosition updates our current bank balance.
   */
  public void handleMessage (CashPosition cp)
  {
    cashArray.add(cp.getBalance());
    cash = cp.getBalance();
    log.info("Cash position: " + cash);
  }
  
  /**
   * DistributionReport gives total consumption and production for the timeslot,
   * summed across all brokers.
   */
  public void handleMessage (DistributionReport dr)
  {
    PrintService.getInstance().addDistributionReport(dr.getTimeslot(), dr.getTotalProduction(), dr.getTotalConsumption());
    System.out.println("For timeslot "+ dr.getTimeslot() +" \n Consumption: "+ dr.getTotalConsumption() + "\n Production: "+dr.getTotalProduction());
    // TODO - use this data
    log.info("Distribution Report: " + dr.toString());
  }
  
  /**
   * Handles the Competition instance that arrives at beginning of game.
   * Here we capture all the customer records so we can keep track of their
   * subscriptions and usage profiles.
   */
  public void handleMessage (Competition comp)
  {
    // TODO - process competition properties
    log.info("Competition Properties: " + comp.toString());
  }

  /**
   * Receives the server configuration properties.
   */
  public void handleMessage (java.util.Properties serverProps)
  {
    // TODO - adapt to the server setup.
    log.info("Server props: " + serverProps.toString());
  }
}

package es.unileon.ulebank.assets.handler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FinancialProductHandlerTest.class,
		LoanIdentificationNumberCodeTest.class,
		ScheduledPaymentHandlerTest.class })
public class SuiteTestsHandler {
}

package es.unileon.ulebank.assets.strategy.loan;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AmericanMethodTest.class, FrenchMethodTest.class,
		GermanMethodTest.class, ItalianMethodTest.class,
		ProgressiveMethodTest.class })
public class SuiteTestsStrategy {
}
package es.unileon.ulebank.assets.support;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaymentPeriodTest {
	@Test
	public void testPaymentPeridoFromString() {
		assertEquals(PaymentPeriod.ANNUAL, PaymentPeriod.getPeriodFromString("Annual"));
		assertEquals(PaymentPeriod.ANNUAL, PaymentPeriod.getPeriodFromString("aNNual"));
	}
	
	
	@Test
	public void testPaymentPeridoFromStringAll() {
		assertEquals(PaymentPeriod.ANNUAL, PaymentPeriod.getPeriodFromString("Annual"));
		assertEquals(PaymentPeriod.BIANNUAL, PaymentPeriod.getPeriodFromString("Biannual"));
		assertEquals(PaymentPeriod.MONTHLY, PaymentPeriod.getPeriodFromString("Monthly"));
		assertEquals(PaymentPeriod.QUARTERLY, PaymentPeriod.getPeriodFromString("Quarterly"));
		assertEquals(PaymentPeriod.TWICEMONTHLY, PaymentPeriod.getPeriodFromString("Twicemonthly"));
	}
}

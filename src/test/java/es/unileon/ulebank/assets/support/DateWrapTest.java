package es.unileon.ulebank.assets.support;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.assets.financialproducts.PeriodTime;

public class DateWrapTest {
	private DateWrap dateWrap;
	
	
	private Calendar calendar;
	@Before
	public void setUp() {
		this.calendar = Calendar.getInstance();
		//1 de Enero de 2014
		this.calendar.set(2014, 1, 1);
		
		this.dateWrap = new DateWrap(this.calendar.getTime(), PaymentPeriod.MONTHLY);
	}
	
	@Test
	public void testUpdateMonthly() {
		this.calendar.add(Calendar.MONTH, 1); //Adelantamos el calendario un mes
		this.dateWrap.updateDate();
		assertEquals(this.calendar.getTime().getTime(), this.dateWrap.getDate().getTime(), 0);
		
		long time =  this.dateWrap.getDate().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(2, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		
	}
	
	@Test
	public void testUpdateAnnual() {
		this.dateWrap.setPeriodOfTime(PaymentPeriod.ANNUAL);
		this.calendar.add(Calendar.MONTH, 12); //Adelantamos el calendario un anho
		this.dateWrap.updateDate();
		assertEquals(this.calendar.getTime().getTime(), this.dateWrap.getDate().getTime(), 0);
		
		long time =  this.dateWrap.getDate().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		assertEquals(2015, cal.get(Calendar.YEAR));
		assertEquals(1, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));

	}
	
	@Test
	public void testUpdateBiannual() {
		this.dateWrap.setPeriodOfTime(PaymentPeriod.BIANNUAL);
		this.calendar.add(Calendar.MONTH, 6); //Adelantamos el calendario medio anho
		this.dateWrap.updateDate();
		assertEquals(this.calendar.getTime().getTime(), this.dateWrap.getDate().getTime(), 0);
		
		long time =  this.dateWrap.getDate().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(7, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void testUpdateQuarterly() {
		this.dateWrap.setPeriodOfTime(PaymentPeriod.QUARTERLY);
		this.calendar.add(Calendar.MONTH, 3); //Adelantamos el calendario tres meses
		this.dateWrap.updateDate();
		assertEquals(this.calendar.getTime().getTime(), this.dateWrap.getDate().getTime(), 0);
		
		long time =  this.dateWrap.getDate().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(4, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testUpdateTwicemonthly() {
		this.dateWrap.setPeriodOfTime(PaymentPeriod.TWICEMONTHLY);
		this.calendar.add(Calendar.MONTH, 2); //Adelantamos el calendario dos meses
		this.dateWrap.updateDate();
		assertEquals(this.calendar.getTime().getTime(), this.dateWrap.getDate().getTime(), 0);
		
		long time =  this.dateWrap.getDate().getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(3, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
	}
	
}

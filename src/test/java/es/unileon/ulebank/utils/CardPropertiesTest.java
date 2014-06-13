package es.unileon.ulebank.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CardPropertiesTest {
	CardProperties properties;
	
	@Before
	public void setUp() throws Exception {
		properties = new CardProperties();
		
		properties.setMinimumLimit(200.0);
		properties.setExpirationYear(3);
		properties.setCvvSize(3);
		properties.setPinSize(4);
		properties.setBuyLimitDiary(200.00);
		properties.setBuyLimitMonthly(2000.00);
		properties.setCashLimitDiary(200.00);
		properties.setCashLimitMonthly(2000.00);
		properties.setDimensionRow(20);
		properties.setDimensionColumns(20);
		properties.setDefaultFee(20.00);
		properties.setMaximumAge(35);
	}

	@Test
	public void testGetMinimumLimit() {
		assertEquals(200.00, CardProperties.getMinimumLimit(), 0.0001);
	}

	@Test
	public void testSetMinimumLimit() {
		assertEquals(200.00, CardProperties.getMinimumLimit(), 0.0001);
		properties.setMinimumLimit(300.0);
		assertEquals(300.00, CardProperties.getMinimumLimit(), 0.0001);
	}

	@Test
	public void testGetExpirationYear() {
		assertEquals(3, CardProperties.getExpirationYear());
	}

	@Test
	public void testSetExpirationYear() {
		assertEquals(3, CardProperties.getExpirationYear());
		properties.setExpirationYear(4);
		assertEquals(4, CardProperties.getExpirationYear());
	}

	@Test
	public void testGetCvvSize() {
		assertEquals(3, CardProperties.getCvvSize());
	}

	@Test
	public void testSetCvvSize() {
		assertEquals(3, CardProperties.getCvvSize());
		properties.setCvvSize(4);
		assertEquals(4, CardProperties.getCvvSize());
	}

	@Test
	public void testGetPinSize() {
		assertEquals(4, CardProperties.getPinSize());
	}

	@Test
	public void testSetPinSize() {
		assertEquals(4, CardProperties.getPinSize());
		properties.setPinSize(5);
		assertEquals(5, CardProperties.getPinSize());
	}

	@Test
	public void testGetBuyLimitDiary() {
		assertEquals(200.00, CardProperties.getBuyLimitDiary(), 0.0001);
	}

	@Test
	public void testSetBuyLimitDiary() {
		assertEquals(200.00, CardProperties.getBuyLimitDiary(), 0.0001);
		properties.setBuyLimitDiary(250.00);
		assertEquals(250.00, CardProperties.getBuyLimitDiary(), 0.0001);
	}

	@Test
	public void testGetBuyLimitMonthly() {
		assertEquals(2000.00, CardProperties.getBuyLimitMonthly(), 0.0001);
	}

	@Test
	public void testSetBuyLimitMonthly() {
		assertEquals(2000.00, CardProperties.getBuyLimitMonthly(), 0.0001);
		properties.setBuyLimitMonthly(2500.00);
		assertEquals(2500.00, CardProperties.getBuyLimitMonthly(), 0.0001);
	}

	@Test
	public void testGetCashLimitDiary() {
		assertEquals(200.00, CardProperties.getCashLimitDiary(), 0.0001);
	}

	@Test
	public void testSetCashLimitDiary() {
		assertEquals(200.00, CardProperties.getCashLimitDiary(), 0.0001);
		properties.setCashLimitDiary(250.00);
		assertEquals(250.00, CardProperties.getCashLimitDiary(), 0.0001);
	}

	@Test
	public void testGetCashLimitMonthly() {
		assertEquals(2000.00, CardProperties.getCashLimitMonthly(), 0.0001);
	}

	@Test
	public void testSetCashLimitMonthly() {
		assertEquals(2000.00, CardProperties.getCashLimitMonthly(), 0.0001);
		properties.setCashLimitMonthly(2500.00);
		assertEquals(2500.00, CardProperties.getCashLimitMonthly(), 0.0001);
	}

	@Test
	public void testGetDimensionRow() {
		assertEquals(20, CardProperties.getDimensionRow());
	}

	@Test
	public void testSetDimensionRow() {
		assertEquals(20, CardProperties.getDimensionRow());
		properties.setDimensionRow(30);
		assertEquals(30, CardProperties.getDimensionRow());
	}

	@Test
	public void testGetDimensionColumns() {
		assertEquals(20, CardProperties.getDimensionColumns());
	}

	@Test
	public void testSetDimensionColumns() {
		assertEquals(20, CardProperties.getDimensionColumns());
		properties.setDimensionColumns(30);
		assertEquals(30, CardProperties.getDimensionColumns());
	}

	@Test
	public void testGetDefaultFee() {
		assertEquals(20.00, CardProperties.getDefaultFee(), 0.0001);
	}

	@Test
	public void testSetDefaultFee() {
		assertEquals(20.00, CardProperties.getDefaultFee(), 0.0001);
		properties.setDefaultFee(30.00);
		assertEquals(30.00, CardProperties.getDefaultFee(), 0.0001);
	}

	@Test
	public void testGetMaximumAge() {
		assertEquals(35, CardProperties.getMaximumAge());
	}

	@Test
	public void testSetMaximumAge() {
		assertEquals(35, CardProperties.getMaximumAge());
		properties.setMaximumAge(30);
		assertEquals(30, CardProperties.getMaximumAge());
	}

}

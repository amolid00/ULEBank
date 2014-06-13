package es.unileon.ulebank.payments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.fees.LinearFee;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.exceptions.IncorrectLengthException;
import es.unileon.ulebank.payments.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.exceptions.PaymentException;
import es.unileon.ulebank.payments.handler.CardHandler;
import es.unileon.ulebank.utils.CardProperties;

public class CreditCardTest {

	CreditCard testCard;
	CreditCard testCard2;
	Handler handler;
	private Office office;
	private Bank bank;
	private String accountNumber = "0000000000";

	@Before
	public void setUp() throws Exception {
		CardProperties properties = new CardProperties();
		properties.setCvvSize(3);
		properties.setPinSize(4);
		properties.setMinimumLimit(200.0);
		properties.setExpirationYear(3);
		this.bank = new Bank(new BankHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), this.bank);
		handler = new CardHandler(new BankHandler("1234"), "01", "123456789");
		Client client = new Person(71451559, 'N');
		Account account = new Account(office, bank, accountNumber, client);
		FeeStrategy commissionEmission = new LinearFee(0, 25);
		FeeStrategy commissionMaintenance = new LinearFee(0, 0);
		FeeStrategy commissionRenovate = new LinearFee(0, 0);
		testCard = new CreditCard(handler, client, account);
		testCard.setBuyLimitMonthly(1000.0);
		testCard.setBuyLimitDiary(400.0);
		testCard.setCashLimitMonthly(1000.0);
		testCard.setCashLimitDiary(400.0);
		testCard.setCommissionEmission(commissionEmission);
		testCard.setCommissionMaintenance(commissionMaintenance);
		testCard.setCommissionRenovate(commissionRenovate);
		testCard2 = new CreditCard(handler, client, account);
		testCard2.setBuyLimitMonthly(1000.0);
		testCard2.setBuyLimitDiary(400.0);
		testCard2.setCashLimitMonthly(1000.0);
		testCard2.setCashLimitDiary(400.0);
		testCard2.setCommissionEmission(commissionEmission);
		testCard2.setCommissionMaintenance(commissionMaintenance);
		testCard2.setCommissionRenovate(commissionRenovate);
	}

	@Test
	public void cardNotNull() {
		assertNotNull(testCard);
	}

	@Test
	public void cardNull() {
		testCard = null;
		
		assertNull(testCard);
	}

	@Test
	public void cardOk() {
		assertNotNull(testCard);
	}

	@Test
	public void testDefaultConstructor() {
		CreditCard card = null;
		assertNull(card);
		card = new CreditCard();
		assertNotNull(card);
	}
	
	@Test
	public void testGeneratePinCode() {
		assertEquals(4, testCard.generatePinCode().length());
	}

	@Test
	public void testGenerateEmissionDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String today = dateFormat.format(new Date());
		assertEquals(today, testCard.generateEmissionDate());
	}

	@Test
	public void testGenerateExpirationDate() {
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
		String currentMonth = monthFormat.format(new Date());
		String currentYear = String.valueOf(Integer.parseInt(yearFormat.format(new Date()))+3);
		assertEquals(currentMonth+"/"+currentYear, testCard.generateExpirationDate());
	}

	@Test
	public void testGenerateCVV() {
		assertEquals(3, testCard.generateCVV().length());
	}

	@Test
	public void testGetCardId() {
		assertTrue(((CardHandler)testCard.getId()).toString().length() == 16 + 3); //add +3 because the cardId have 3 white spaces
	}

	@Test
	public void testGetPin() throws PaymentException {
		assertEquals(4, testCard.getPin().length());

		testCard.setPin("9182");
		assertEquals("9182", testCard.getPin());
	}

	@Test
	public void testSetPinOK() throws PaymentException {
		testCard.setPin("1357");
		assertEquals("1357", testCard.getPin());
	}
	
	@Test (expected = IncorrectLengthException.class)
	public void testSetPinFailLength() throws PaymentException {
		testCard.setPin("135");
		assertEquals("1357", testCard.getPin());
	}
	
	@Test (expected = NumberFormatException.class)
	public void testSetPinFailFormat() throws PaymentException {
		testCard.setPin("13f7");
		assertEquals("1357", testCard.getPin());
	}

	@Test
	public void testCheckPin() throws PaymentException {
		testCard.setPin("1357");
		assertTrue(testCard.checkPin("1357"));
	}

	@Test
	public void testGetBuyLimitDiary() {
		assertEquals(400.0, testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test
	public void testSetBuyLimitDiaryOKUpLimit() throws IncorrectLimitException {
		assertEquals(testCard.getBuyLimitMonthly(), 1000.0, 0.0001);

		testCard.setBuyLimitDiary(800.0);
		assertEquals(800.0, testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test
	public void testSetBuyLimitDiaryOKDownLimit() throws IncorrectLimitException{
		testCard.setBuyLimitDiary(200); //Ok because buy limit diary is 200
		assertEquals(200.0, testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test (expected = IncorrectLimitException.class)
	public void testSetBuyLimitDiaryFAILUp() throws IncorrectLimitException{
		testCard.setBuyLimitDiary(2000); //fail because buy limit diary is greater than buy limit monthly
	}

	@Test (expected = IncorrectLimitException.class)
	public void testSetBuyLimitDiaryFAILDownMinimumLimit() throws IncorrectLimitException{
		testCard.setBuyLimitMonthly(190);
		testCard.setBuyLimitDiary(199.0);
	}

	@Test
	public void testCheckBuyLimitDiaryTRUE() throws IncorrectLimitException {
		testCard.setBuyLimitDiary(500.0);
		assertTrue(testCard.checkBuyLimitDiary(500.0));
	}

	@Test
	public void testCheckBuyLimitDiaryFALSE() throws IncorrectLimitException {
		testCard.setBuyLimitDiary(500.0);
		assertFalse(testCard.checkBuyLimitDiary(600.0));
	}

	@Test
	public void testGetBuyLimitMonthly() {
		assertEquals(1000.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test
	public void testSetBuyLimitMonthly() throws IncorrectLimitException {
		assertEquals(1000.0, testCard.getBuyLimitMonthly(), 0.0001);

		testCard.setBuyLimitMonthly(1500.0);
		assertEquals(1500.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test
	public void testSetBuyLimitMonthlyOKDownLimit() throws IncorrectLimitException{
		testCard.setBuyLimitMonthly(400.0); //Ok because buy limit diary is 400
		assertEquals(400.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test  (expected = IncorrectLimitException.class)
	public void testSetBuyLimitMonthlyFAILDownMinimumLimit() throws IncorrectLimitException{
		testCard.setBuyLimitMonthly(399); //fail because buy limit diary is 400
		assertEquals(399.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test
	public void testGetCashLimitDiary() {
		assertEquals(400.0, testCard.getCashLimitDiary(), 0.0001);
	}

	@Test
	public void testSetCashLimitDiaryOKUpLimit() throws IncorrectLimitException {
		assertEquals(testCard.getCashLimitMonthly(), 1000.0, 0.0001);

		testCard.setCashLimitDiary(800.0);
		assertEquals(800.0, testCard.getCashLimitDiary(), 0.0001);
	}

	@Test
	public void testSetCashLimitDiaryOKDownLimit() throws IncorrectLimitException{
		testCard.setCashLimitDiary(200); //Ok because cash limit diary is 200
		assertEquals(200.0, testCard.getCashLimitDiary(), 0.0001);
	}

	@Test (expected = IncorrectLimitException.class)
	public void testSetCashLimitDiaryFAILUp() throws IncorrectLimitException{
		testCard.setCashLimitDiary(2000); //fail because cash limit diary is greater than cash limit monthly
	}

	@Test (expected = IncorrectLimitException.class)
	public void testSetCashLimitDiaryFAILDownMinimumLimit() throws IncorrectLimitException{
		testCard.setCashLimitMonthly(190);
		testCard.setCashLimitDiary(199); //fail because cash limit diary is over monthly
	}

	@Test
	public void testCheckCashLimitDiaryTRUE() throws IncorrectLimitException {
		testCard.setCashLimitDiary(500.0);
		assertTrue(testCard.checkCashLimitDiary(500.0));
	}

	@Test
	public void testCheckCashLimitDiaryFALSE() throws IncorrectLimitException {
		testCard.setCashLimitDiary(500.0);
		assertFalse(testCard.checkCashLimitDiary(600.0));
	}

	@Test
	public void testGetCashLimitMonthly() {
		assertEquals(1000.0, testCard.getCashLimitMonthly(), 0.0001);
	}

	@Test
	public void testSetCashLimitMonthlyOKDownLimit() throws IncorrectLimitException{
		testCard.setCashLimitMonthly(500.0); //Ok because cash limit diary is 500
		assertEquals(500.0, testCard.getCashLimitMonthly(), 0.0001);
	}

	@Test  (expected = IncorrectLimitException.class)
	public void testSetCashLimitMonthlyFAILDownMinimumLimit() throws IncorrectLimitException{
		testCard.setCashLimitMonthly(399); //fail because cash limit diary is 400
	}

	@Test
	public void testGetEmissionDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String today = dateFormat.format(new Date());
		assertEquals(today, testCard.getEmissionDate());
	}

	@Test
	public void testGetExpirationDate() {
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
		String currentMonth = monthFormat.format(new Date());
		String currentYear = String.valueOf(Integer.parseInt(yearFormat.format(new Date()))+3);

		assertEquals(currentMonth+"/"+currentYear, testCard.getExpirationDate());
	}

	@Test
	public void testSetExpirationDate() {
		testCard.setExpirationDate("05/17");
		assertEquals("05/17", testCard.getExpirationDate());
	}

	@Test
	public void testGetCardType() {
		assertEquals(CardType.CREDIT.toString(), testCard.getCardType().toString());
	}

	@Test
	public void testGetCvv() {
		assertEquals(3, testCard.generateCVV().length());
	}

	@Test (expected = IncorrectLengthException.class)
	public void testSetCvvFAILLenght() throws PaymentException {
		testCard.setCvv("1954");
	}

	@Test (expected = NumberFormatException.class)
	public void testSetCvvFAILLetter() throws PaymentException {
		testCard.setCvv("19f");
	}

	@Test
	public void testSetCvvOK() throws PaymentException{
		testCard.setCvv("146");
		assertEquals("146", testCard.getCvv());
	}

	@Test
	public void testGetCardNumber(){
		assertEquals("1234 0112 3456 7892", testCard.getId().toString());
	}
	
	@Test
	public void testGetAmount() throws PaymentException{
		testCard.makeTransaction(100.00, "Test getAmount");
		Date effectiveDate = testCard.getTransactionList().get(0).getEffectiveDate();
		assertEquals(100.00, testCard.getAmount(effectiveDate), 0.0001);
	}
	
	@Test
	public void testGetTransactionList() throws PaymentException{
		testCard.makeTransaction(200.00, "Test getTransaction");
		assertEquals(200.00, testCard.getTransactionList().get(0).getAmount(), 0.0001);
		assertEquals("Test getTransaction", testCard.getTransactionList().get(0).getSubject());
	}
	
	@Test
	public void testSetTransactionList() throws PaymentException{
		testCard.makeTransaction(200.00, "Test getTransaction Card1");
		assertEquals(200.00, testCard.getTransactionList().get(0).getAmount(), 0.0001);
		
		testCard2.makeTransaction(500.00, "Test getTransaction Card2");
		assertEquals(500.00, testCard2.getTransactionList().get(0).getAmount(), 0.0001);
		
		testCard.setTransactionList(testCard2.getTransactionList());
		assertEquals(500.00, testCard.getTransactionList().get(0).getAmount(), 0.0001);
	}
	
	@Test
	public void testMakeTransaction() throws PaymentException{
		testCard.makeTransaction(500.00, "Test makeTransaction");
		assertEquals(1, testCard.getTransactionList().size());
		testCard.makeTransaction(350.00, "Test2 makeTransaction");
		assertEquals(2, testCard.getTransactionList().size());
	}
	
	@Test
	public void setMonthDay(){
		testCard.setMonthDay(Calendar.DAY_OF_MONTH);
		assertEquals(Calendar.DAY_OF_MONTH, testCard.getMonthDay());
	}
	
	@Test
	public void getMonthDay(){
		testCard.setMonthDay(Calendar.DAY_OF_MONTH-1);
		assertEquals(Calendar.DAY_OF_MONTH-1, testCard.getMonthDay());
	}
	
	
}

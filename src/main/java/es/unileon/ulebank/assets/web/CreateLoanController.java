package es.unileon.ulebank.assets.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.command.CreateLoanCommand;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.service.LoanMapping;
import es.unileon.ulebank.assets.service.Manager;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;

@Controller
@RequestMapping(value = "/create-loan.htm")
public class CreateLoanController {

	@Autowired
	private Manager manager;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmit(@Valid LoanMapping loanMapping,
			BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return new ModelAndView("the page has been falled");
		}

		// TODO como obtenemos account y client de liabilities
		Account account = null;
		Client client = null;

		Handler idLoan = new FinancialProductHandler("LN", "ES");

		/**
		 * ¿El command no deberia tener una instancia del mannager para crear y
		 * guardar el loan? o hacemos un get del command y despues llamamos al
		 * saveLoan del mannager???
		 */

		Loan loan = new Loan(idLoan, loanMapping.getInitialCapital(),
				loanMapping.getInterest(),
				PaymentPeriod.getPeriodFromString(loanMapping
						.getPaymentPeriod()),
				loanMapping.getAmortizationTime(), account, client,
				loanMapping.getDescription());

		this.manager.saveLoan(loan);

		return new ModelAndView("client", "model", null);
	}
}
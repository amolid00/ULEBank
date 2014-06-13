package es.unileon.ulebank.assets.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.unileon.ulebank.assets.service.Manager;
import es.unileon.ulebank.assets.service.Quantity;

@Controller
@RequestMapping(value = "/amortize-loan.htm")
public class AmortizeLoanController {

	@Autowired
	private Manager manager;

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(@Valid Quantity quantity, BindingResult result) {

		double quantityAmortize = quantity.getQuantity();

		this.manager.setAmortization(quantityAmortize);

		return "redirect:/payments.htm";
	}

	@RequestMapping(method = RequestMethod.GET)
	protected Quantity formBackingObject(HttpServletRequest request)
			throws ServletException {
		Quantity quantity = new Quantity();
		quantity.setQuantity(0);
		return quantity;
	}

}
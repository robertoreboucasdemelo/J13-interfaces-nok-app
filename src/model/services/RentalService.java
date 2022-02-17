package model.services;

import model.entities.Invoice;
import model.entities.RentalCar;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;
	
	public RentalService() {
	}
	

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}


	public void processInvoice(RentalCar rentalCar) {
		
		// Transformando a Data em Milisegundos
		
		long t1 = rentalCar.getStart().getTime();
		long t2 = rentalCar.getFinish().getTime();
		
		// Verificando a Duração da Locacao
		
		double hours = (double)(t2-t1) / 1000 / 60 /60;
		
		double basicPayment;
		
		// Math.ceil arredonda as horas pra cima
		
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		
		rentalCar.setInvoice(new Invoice(basicPayment, tax));
		
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public BrazilTaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(BrazilTaxService taxService) {
		this.taxService = taxService;
	}
	
	
}

package bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class ConversorBean implements Serializable {

	/**
	 * Valor em Celsius
	 */
	private Double tempCelsius;
	
	/**
	 * Valor em Fahrenheit
	 */
	private Double tempFahrenheit;
	
	/**
	 * Método para conversão de temperatura
	 */
	public void convert() {
		// Cria uma mensagem informativa
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		
		// Define qual conversão será utilizada
		if (tempCelsius == null) {
			convertToCelsius();
			msg.setSummary("Conversão para Celsius realizada");
		} else {
			convertToFahrenheit();
			msg.setSummary("Conversão para Fahrenheit realizada");
		}
		
		// Adiciona a mensagem no contexto do JSF, para ela ser exibida na tela
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	/**
	 * Converte de Fahrenheit para Celsius
	 */
	private void convertToCelsius() {
		tempCelsius = ((tempFahrenheit - 32) * 5) / 9;
	}
	
	/**
	 * Converte de Celsius para Fahrenheit
	 */
	private void convertToFahrenheit() {
		tempFahrenheit = (tempCelsius * 9) / 5 + 32;
	}

	public Double getTempCelsius() {
		return tempCelsius;
	}

	public void setTempCelsius(Double tempCelsius) {
		this.tempCelsius = tempCelsius;
	}

	public Double getTempFahrenheit() {
		return tempFahrenheit;
	}

	public void setTempFahrenheit(Double tempFahrenheit) {
		this.tempFahrenheit = tempFahrenheit;
	}
}

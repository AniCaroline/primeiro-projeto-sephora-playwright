package br.com.treinamento.scenarios;

import br.com.treinamento.components.LoginProton_Selenium;

public class ID00_RealizarLoginProton_Selenium
{
	//Instância dos componentes que serão utilizados no cenário
	LoginProton_Selenium loginProton = new LoginProton_Selenium();
	
	public void run() throws Exception
	{
		loginProton.runComponent();
	}
}

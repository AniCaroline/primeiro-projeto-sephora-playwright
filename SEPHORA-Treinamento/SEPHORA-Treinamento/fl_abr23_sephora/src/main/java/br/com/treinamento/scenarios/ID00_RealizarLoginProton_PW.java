package br.com.treinamento.scenarios;

import br.com.treinamento.components.LoginProton_Playwright;

public class ID00_RealizarLoginProton_PW
{
	//Instância dos componentes que serão utilizados no cenário
	LoginProton_Playwright loginProton = new LoginProton_Playwright();
	
	public void run() throws Exception
	{
		loginProton.runComponent();
	}
}

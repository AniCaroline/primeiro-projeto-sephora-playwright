package br.com.treinamento.components;

import static br.com.atomic.framework.helpers.PropertyHelper.getProperty;

import br.com.atomic.framework.controllers.PlaywrightController;
import br.com.atomic.framework.proton.ProtonHelper;
import br.com.treinamento.pages.HomePage_Playwright;

public class LoginProton_Playwright 
{
	//Necessário usar o GetPage somente para a instância das Pages devido a particularidade do framework.
	HomePage_Playwright exemplo = new HomePage_Playwright(PlaywrightController.getPage());
	
	
	public void runComponent() throws Exception
	{
		
		//Recebendo valores para execução Local
		String usuario = "treinamento.qa";
		String senha = "proton";
		
		if(ProtonHelper.isProtonExecution())
		{
			//Recebendo valores do Proton
			usuario = ProtonHelper.getProtonParameter("in_usuario");
			senha = ProtonHelper.getProtonParameter("in_senha");
		}
		
		//Ações do componente
		exemplo.goToHome(getProperty("env.web.driver.url"));
		exemplo.realizarLoginExemplo(usuario, senha);
	}
}

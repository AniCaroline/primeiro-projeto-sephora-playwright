package br.com.treinamento.components;

import static br.com.atomic.framework.base.DefaultBaseController.getPage_;
import static br.com.atomic.framework.helpers.PropertyHelper.getProperty;

import br.com.atomic.framework.proton.ProtonHelper;
import br.com.treinamento.pages.HomePage_Selenium;

public class LoginProton_Selenium 
{
	//Necessário usar o GetPage somente para a instância das Pages devido a particularidade do framework.
	HomePage_Selenium exemplo = getPage_(HomePage_Selenium.class);
	
	
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

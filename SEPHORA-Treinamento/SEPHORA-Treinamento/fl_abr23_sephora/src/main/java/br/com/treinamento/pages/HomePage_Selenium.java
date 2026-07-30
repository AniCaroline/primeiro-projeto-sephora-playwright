package br.com.treinamento.pages;

import static br.com.atomic.framework.helpers.base.QueryHelper.getElementByXPath;
import static br.com.atomic.framework.helpers.base.QueryHelper.getElementById;
import static br.com.atomic.framework.helpers.base.QueryHelper.getElementByCss;
import br.com.atomic.framework.base.PageBase;
import br.com.atomic.framework.base.VirtualElement;
import br.com.atomic.framework.helpers.LoggerHelper;
import br.com.treinamento.validations.ValidationsHelper;

public class HomePage_Selenium extends PageBase
{
	
	@SuppressWarnings("rawtypes")
	VirtualElement
		txtUsuario = getElementById("username"), //Escolha de elemento via ID
		txtSenha = getElementByCss("#password"), //Escolha de elemento via CSS Selector
		btnVamosLa = getElementByXPath("//button[contains(text(), 'Vamos l')]"); //Escolha de elemento via XPath
	
	LoggerHelper logger = new LoggerHelper(HomePage_Selenium.class);
	
	public void goToHome(String url)
	{
		//Informa uma URL para o navegador
		getURL(url);
	}
	
	public void realizarLoginExemplo(String usuario, String senha) throws Exception
	{
		//Espera até o elemento ficar visível
		waitUntilVisible(txtUsuario);
		
		//Preenche um valor
		txtUsuario.sendKeys(usuario);
		
		//Log técnico - para desenvolvimento
		logger.debug("Usuário preenchido: "+usuario);		
		
		txtSenha.sendKeys(senha);
		logger.debug("Senha preenchida: "+senha);
		
		btnVamosLa.click();
		//clickJavaScript(btnLogar);
		
		//Verifica se o elemento existe ou não -> Existe = true | Não existe = false
		if(elementExists(getElementByXPath("//div[contains(@class, 'MuiBackdrop-root')]")))
		{
			//waitUntilVisible(getElementByXPath("//div[contains(@class, 'MuiBackdrop-root')]"));
			
			//Log visível para o usuário
			logger.info("Login Realizado com sucesso.");
			
			//Tira print para mandar no Proton
			logger.takeScreenShot("Login Realizado", this);
			
			ValidationsHelper.getCv_().setUsuario(usuario);
		}
		else
		{
			//Log de erro visível para o usuário
			logger.error("Não foi possível realizado login");
			throw new Exception("Falha ao realizar login");
		}
	}
	
}

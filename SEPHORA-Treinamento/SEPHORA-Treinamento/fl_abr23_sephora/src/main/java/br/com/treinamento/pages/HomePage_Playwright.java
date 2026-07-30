package br.com.treinamento.pages;

import static br.com.atomic.framework.helpers.base.QueryHelper.getElementByXPath;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static br.com.atomic.framework.helpers.base.QueryHelper.getElementById;
import static br.com.atomic.framework.helpers.base.QueryHelper.getElementByCss;
import br.com.atomic.framework.base.PageBase;
import br.com.atomic.framework.base.VirtualElement;
import br.com.atomic.framework.controllers.PlaywrightController;
import br.com.atomic.framework.helpers.AssertHelper;
import br.com.atomic.framework.helpers.LoggerHelper;
import br.com.atomic.framework.playwright.PlaywrightElementHelper;
import br.com.treinamento.validations.ValidationsHelper;

public class HomePage_Playwright
{
	
	private Page page;
	private Locator txtUsuario;
	private Locator txtSenha;
	private Locator btnVamosLa;
	
	public HomePage_Playwright(Page page) {
		this.page = page;
		this.txtUsuario = page.locator("[id='username']");
		this.txtSenha = page.locator("#password");
		this.btnVamosLa = page.locator("//button[contains(text(), 'Vamos l')]");
	}
	LoggerHelper logger = new LoggerHelper(HomePage_Playwright.class);
	
	public void goToHome(String url)
	{
		//Informa uma URL para o navegador
		page.navigate(url);
	}
	
	public void realizarLoginExemplo(String usuario, String senha) throws Exception
	{
		//Espera até o elemento ficar visível
		
		//Preenche um valor
		txtUsuario.fill(usuario);
		
		//Log técnico - para desenvolvimento
		logger.debug("Usuário preenchido: "+usuario);		
		
		txtSenha.fill(senha);
		logger.debug("Senha preenchida: "+senha);
		
		btnVamosLa.click();
//		btnVamosLa.dispatchEvent("click");
		
		AssertHelper.assertTrue("Login deve ser realizado com sucesso", 
				PlaywrightElementHelper.elementExist(page, page.locator("//div[contains(@class, 'MuiBackdrop-root')]"), 5));
		
		//Log visível para o usuário
		logger.info("Login Realizado com sucesso.");
			
		//Tira print para mandar no Proton
		logger.takeScreenShot("Login Realizado", page);
		ValidationsHelper.getCv_().setUsuario(usuario);
	}
	
}

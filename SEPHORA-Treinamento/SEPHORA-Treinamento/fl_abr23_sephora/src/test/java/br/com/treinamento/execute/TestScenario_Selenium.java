package br.com.treinamento.execute;

import static br.com.atomic.framework.proton.ProtonHelper.isProtonExecution;
import static br.com.atomic.framework.proton.ProtonHelper.updateProtonRunStatus;
import static br.com.atomic.framework.proton.ProtonHelper.getProtonCurrentComponentName;
import static br.com.atomic.framework.proton.ProtonHelper.getProtonCurrentComponentType;
import static br.com.atomic.framework.helpers.BrowserHelper.setBrowser;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.google.common.base.Strings;

import br.com.atomicsolutions.proton.Proton;
import br.com.atomicsolutions.proton.RunStatus;
import br.com.atomic.framework.base.DefaultBaseController;
import br.com.atomic.framework.base.PageBase;
import br.com.atomic.framework.controllers.WebController;
import br.com.atomic.framework.helpers.LoggerHelper;
import br.com.atomic.framework.proton.ProtonHelper;
import br.com.treinamento.scenarios.ID00_RealizarLoginProton_Selenium;
import br.com.treinamento.validations.ValidationsHelper;


public class TestScenario_Selenium extends PageBase {
	
	@Test 
	public void main() throws Exception	
	{
		
		boolean isProtonStarted = false;
		boolean isProtonFinished = false;
		
		LoggerHelper logger = new LoggerHelper(TestScenario_Selenium.class);

		try 
		{
			
			//Muda Status de execução do Proton
			updateProtonRunStatus(RunStatus.RUNNING);

			ValidationsHelper.initializeCv_();
			
			//Prepara o ambiente para o navegador escolhido (olhar config.properties)
			setBrowser();
			
			
			//Determina uma controller para o projeto. Pode ser WebController, AndroidController, IOSController ou EmptyController
			DefaultBaseController.initializeController(WebController.class);

			if(isProtonExecution()) 
			{
				
				logger.info("----------------------- Início da execução do teste - Squad Treinamento-----------------------");
				logger.info("Execução via Proton. ID do Dataset Run: "+ProtonHelper.getDatasetRunID());
				
				//Execução Proton -> Busca no cenário os tipos de componente 'Selenium'
				while(Proton.getCurrentComponentSystem().toLowerCase().equals("selenium")) 
				{
					//Monta o package + nome do componente para fazer a instância da classe correta. 
					//Nome do componente DEVE ser IGUAL no Eclipse e Proton
					String className = getProtonCurrentComponentName();
					Class<?> component = Class.forName("br.com.treinamento.components." + className);
					Object componentInst  = component.newInstance();
					isProtonStarted = true;
					Method runComponent = componentInst.getClass().getMethod("runComponent", null);
					runComponent.invoke(componentInst, null);
				}
				
				if(Strings.isNullOrEmpty(Proton.getCurrentComponentSystem()))
					isProtonFinished = true;

			}
			else
			{
				//Execução de teste local (sem Proton)
				logger.info("Início da execução do teste");
				ID00_RealizarLoginProton_Selenium scenario = new ID00_RealizarLoginProton_Selenium();
				scenario.run();
			}
			
			if(isProtonFinished)
			{
				updateProtonRunStatus(RunStatus.PASSED);
				
				logger.info("Cenário executado com sucesso.");
				logger.info("----------------------- Fim da execução do teste - Squad Treinamento -----------------------");
			}
			else
			{
				logger.info("Parte web do cenário finalizada com sucesso. Preparado para iniciar steps do próximo sistema");
			}

			
			DefaultBaseController.tearDown_();

		}
		catch(Exception e)
		{

			//Local onde o script deve ir em caso de falha
			
			ProtonHelper.setProtonExceptionLog(e);
			
			if(isProtonExecution() && !isProtonStarted)
				ProtonHelper.startProtonScript();
			
			updateProtonRunStatus(RunStatus.FAILED);
			
			if(getDriver() != null)
				logger.takeScreenShot("FALHA - Tela de Erro", this);

			logger.info("----------------------- Fim da execução do teste - Squad Treinamento -----------------------");
			
			DefaultBaseController.tearDown_();

			assertTrue("Scenario Execution", false);
		}
	}
	
}

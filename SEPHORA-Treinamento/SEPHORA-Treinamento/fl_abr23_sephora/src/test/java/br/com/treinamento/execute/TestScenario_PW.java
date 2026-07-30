package br.com.treinamento.execute;

import static br.com.atomic.framework.proton.ProtonHelper.isProtonExecution;
import static br.com.atomic.framework.proton.ProtonHelper.updateProtonRunStatus;
import static br.com.atomic.framework.proton.ProtonHelper.getProtonCurrentComponentName;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.google.common.base.Strings;

import br.com.atomicsolutions.proton.Proton;
import br.com.atomicsolutions.proton.RunStatus;
import br.com.atomic.framework.base.PageBase;
import br.com.atomic.framework.controllers.PlaywrightController;
import br.com.atomic.framework.helpers.LoggerHelper;
import br.com.atomic.framework.proton.ProtonHelper;
import br.com.treinamento.scenarios.ID00_RealizarLoginProton_PW;
import br.com.treinamento.validations.ValidationsHelper;


public class TestScenario_PW {
	
	@Test 
	public void main() throws Exception	
	{
		
		boolean isProtonStarted = false;
		boolean isProtonFinished = false;
		
		LoggerHelper logger = new LoggerHelper(TestScenario_PW.class);

		try 
		{
			
			//Muda Status de execução do Proton
			updateProtonRunStatus(RunStatus.RUNNING);

			ValidationsHelper.initializeCv_();
			
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
				ID00_RealizarLoginProton_PW scenario = new ID00_RealizarLoginProton_PW();
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

			
			PlaywrightController.closePage();

		}
		catch(Exception e)
		{

			//Local onde o script deve ir em caso de falha
			
			ProtonHelper.setProtonExceptionLog(e);
			
			if(isProtonExecution() && !isProtonStarted)
				ProtonHelper.startProtonScript();
			
			updateProtonRunStatus(RunStatus.FAILED);
			
			if(PlaywrightController.getPage() != null)
				logger.takeScreenShot("FALHA - Tela de Erro", PlaywrightController.getPage());

			logger.info("----------------------- Fim da execução do teste - Squad Treinamento -----------------------");
			
			PlaywrightController.closePage();

			assertTrue("Scenario Execution", false);
		}
	}
	
}

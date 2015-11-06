package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void sessaoNaoPodeTerDataInicioMaiorQueDataFim(){
		Sessao sessao = new Sessao();
		LocalDate inicio = new LocalDate();
		inicio = inicio.plusDays(1);
		LocalDate fim = new LocalDate();
		
		LocalTime horario = null;
		Periodicidade periodicidade = null;
		Espetaculo espetaculo = new Espetaculo();
		sessao.setEspetaculo(espetaculo);
		
		List<Sessao> lista = sessao.getEspetaculo().criaSessoesTDD(inicio, fim, horario, periodicidade);
		
		Assert.assertEquals(0, lista.size());
	}
	
	@Test
	public void sessaoNaoPodeTerDataInicioMenorQueDataAtual(){
		Sessao sessao = new Sessao();
		LocalDate inicio = new LocalDate();
		inicio = inicio.minusDays(1);
		LocalDate fim = new LocalDate();
		
		LocalTime horario = null;
		Periodicidade periodicidade = null;
		Espetaculo espetaculo = new Espetaculo();
		sessao.setEspetaculo(espetaculo);
		
		List<Sessao> lista = sessao.getEspetaculo().criaSessoesTDD(inicio, fim, horario, periodicidade);
		
		Assert.assertEquals(0, lista.size());
	}
	
	@Test
	public void criarUmaSessaoDiaria(){
		Sessao sessao = new Sessao();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate();
		
		LocalTime horario = null;
		Periodicidade periodicidade = Periodicidade.DIARIA;
		Espetaculo espetaculo = new Espetaculo();
		sessao.setEspetaculo(espetaculo);
		
		List<Sessao> lista = sessao.getEspetaculo().criaSessoesTDD(inicio, fim, horario, periodicidade);
		
		Assert.assertEquals(1, lista.size());
	}
	
	@Test
	public void criarDuasSessoesSemanais(){
		Sessao sessao = new Sessao();
		LocalDate inicio = new LocalDate();
		LocalDate fim = inicio.plusDays(7);
		
		LocalTime horario = null;
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		Espetaculo espetaculo = new Espetaculo();
		sessao.setEspetaculo(espetaculo);
		
		List<Sessao> lista = sessao.getEspetaculo().criaSessoesTDD(inicio, fim, horario, periodicidade);
		
		Assert.assertEquals(2, lista.size());
	}
	
	@Test
	public void deveriaCriarApenasUmaSessaoParaPeriodicidadeDiariaComDataInicioIgualDataFim() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalTime agora = new LocalTime();
	  	Periodicidade diaria = Periodicidade.DIARIA;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, hoje, agora, diaria);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(1, sessoes.size());
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
	  	Sessao unica = sessoes.get(5);
	  	Assert.assertEquals(show, unica.getEspetaculo());
	    Assert.assertEquals(hoje.toDateTime(agora), unica.getInicio());
	}
	
	@Test
	public void deveriaCriarCincoSessoesParaPeriodicidadeDiariaComIntervaloDeCincoDias() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalDate daquiQuatroDias = hoje.plusDays(4);
	  	LocalTime agora = new LocalTime();
	  	Periodicidade diaria = Periodicidade.DIARIA;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, daquiQuatroDias, agora, diaria);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(5, sessoes.size());
		
		Sessao criada;
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
		for(int i = 0; i < sessoes.size(); i++) {
			criada = sessoes.get(i);
			Assert.assertEquals(show, criada.getEspetaculo());
			Assert.assertEquals(hoje.plusDays(i).toDateTime(agora), criada.getInicio());
	    }
	}

	@Test
	public void deveriaCriarCincoSessoesParaPeriodicidadeSemanalComIntervaloDeCincoSemanas() {
		//DADAS ESTAS ENTRADAS:
		LocalDate hoje = new LocalDate();
	  	LocalDate daquiQuatroSemanas = hoje.plusWeeks(4);
	  	LocalTime agora = new LocalTime();
	  	Periodicidade semanal = Periodicidade.SEMANAL;
	  
	  	//QUANDO EU DISPARAR O PROCESSAMENTO:
	  	Espetaculo show = new Espetaculo();
	  	List<Sessao> sessoes = show.criaSessoes(hoje, daquiQuatroSemanas, agora, semanal);
	  
	  	//ESTAS SAO AS SAIDAS ESPERADAS:
		Assert.assertEquals(5, sessoes.size());
		
		Sessao criada;
	  
	  	//Nao basta apenas verificar o size da lista, precisa garantir que criou as sessoes corretamente:
		for(int i = 0; i < sessoes.size(); i++) {
			criada = sessoes.get(i);
			Assert.assertEquals(show, criada.getEspetaculo());
			Assert.assertEquals(hoje.plusWeeks(i).toDateTime(agora), criada.getInicio());
	    }
	}	
}

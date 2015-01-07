package banco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import filtro.Filtragem;

@SuppressWarnings("unused")
public class BancoDados {
	public static final BancoDados bdados = new BancoDados();
	private String banco = "config" + Filtragem.filtro.extension;
	private FileReader lei; 
	private BufferedWriter esc;
	private String endereco = "banco" + Filtragem.filtro.extension;
	
	public String getEndereco() {
		return endereco;
	}
	
	public String getBanco () {
		return this.banco;
	}
	
	public String getEnder (){
		return pegaObjeto(getBanco(), 1);
	}

	//pega todas as linhas do arquivo num ArrayList
	public List<String> pegaObjetoList(String caminho) {
		/*
		 * Responsável por ler o arquivo contendo a lista de disciplinas
		 */
		if (!existe(caminho)){
			return null;
		}
		
		List<String> grupo = new ArrayList<String>();
		String linha;
		
		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader(new File(caminho)));
			while((linha = buf.readLine())!= null && linha != null){
				grupo.add(linha);
			}			
			buf.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "erro na leitura do arquivo:\n"
					+ e.getMessage());

		}
		
		return grupo;
	}
	//retorna um array de strings
	public String[] pegaObjeto(String caminho) {
		List<String> linha = pegaObjetoList(caminho);
		
		if (linha == null){
			return null;
		}
		
		String[] pega = new String [linha.size()];
		
		for (String ob: linha){
			pega[linha.indexOf(ob)] = ob;
		}
		
		linha.clear();
		
		return pega;
	}

	//Pega uma linha específica
	public String pegaObjeto(String caminho, int pular) {
		String[] pega = pegaObjeto(caminho);
		if (pega == null){
			return null;
		}
		return pega[pular -1];
	}
	
	public boolean existe (String caminho) {
		if (caminho.equals(null)){
			return false;
		}
		
		try {
			this.lei = new FileReader(new File(caminho));
		} catch (FileNotFoundException e1) {
			return false;
		}
		
		try {
			this.lei.close();
		} catch (IOException e1) {
		}
		return true;
	}
	
/**
 * Guardar linhas de objetos
 * */
	
	public void guardaLinha (String linha, boolean anexar, String caminho){
		/*
		 * Guarda as edições/mdificações/
		 * anexar == false, reescreve o arquivo
		 * true, escreve no final
		 */
		
		try {
			//BufferedWriter esc = new BufferedWriter(new FileWriter(new File(caminho), anexar));
			this.esc = new BufferedWriter(new FileWriter(new File(caminho), anexar));
			this.esc.write(linha);
			this.esc.newLine();
			this.esc.flush();
			this.esc.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					("erro: \n" + e.getMessage() + "\n O programa deve ser fechado."));
			System.exit(0);
		}
	}
	
	public void copiaArquivo (String copiar, String destino) {
		String[] pega = pegaObjeto(copiar);
		
		for (int i = 0; i < pega.length; i++){
			guardaLinha(pega[i], i > 0, destino);
		}
	}
	
	public void copiaLinhas (String copiar, String destino, int primeiro, int ultimo) {
		String[] pega = pegaObjeto(copiar);
		
		for (int i = 0; i < pega.length; i++){
			if (ultimo == i){
				break;
			} else if (primeiro == i){
				continue;
			}
			guardaLinha(pega[i], i > primeiro, destino);
		}
	}
	
	public void mudaLinha (String destino, int substituir, String novo) {
		String[] pega = pegaObjeto(destino);
		
		for (int i = 0; i < pega.length; i++){
			if (i == substituir){
				guardaLinha(novo, i > 0, destino);
				continue;
			}
			guardaLinha(pega[i], i > 0, destino);
		}
	}
	
	public boolean removeLinha (String destino, String remover) {
		String[] pega = pegaObjeto(destino);

		if (pega.length == 1) {
			JOptionPane.showMessageDialog(null,
					"Se quiser refazer esta lista do zero apenas apague um dos arquivos:\n"
							+ BancoDados.bdados.getEnder() + "\nou "
							+ BancoDados.bdados.getBanco());
			return false;
		}

		for (int i = 0; i < pega.length; i++){
			if (pega[i].equals(remover)){
				continue;
			}
			guardaLinha(pega[i], i > 0, destino);
		}
		return true;
	}
	
	public void organizaArquivo (String destino) {
		List<String> pega = pegaObjetoList(destino);
		
		Collections.sort(pega);
		
		for (String um : pega){
			guardaLinha(um, pega.indexOf(um) > 0, destino);
		}
	}
	
	//copia o conteudo de 2 arquivos num arquivo destino
	public void junta (String origem1, String origem2, String destino){
		copiaArquivo(origem1, destino);
		copiaLinhas(origem2, destino, -1, -1);
	}
	
	public void limpaDuplos(String caminho) {
		List<String> tudo = pegaObjetoList(caminho);
		Set<String> todo = new HashSet<String>();
		
		if (tudo == null){
			return;
		}
		for (String um : tudo){
			todo.add(um);
		}
		
		boolean anexar = false;
		for (String um : todo){
			guardaLinha(um, anexar, caminho);
			anexar = true;
		}

		organizaArquivo(caminho);		
	}

	public int achaLinha(String caminho, String linha){
		int num = -1;
		List<String> arq = pegaObjetoList(caminho);
		
		for (String lin : arq) {
			if (lin.equals(linha)){
				num = arq.indexOf(lin);
				return num + 1;
			}
		}
		
		return num;
	}
	
	private void removeArq(String nome) {
		File file = new File(nome);
		file.delete();
	}
	
	public void limpaArquivos (){
		if (existe(Filtragem.filtro.getAberto() + Filtragem.filtro.extension)){
			new File(Filtragem.filtro.getAberto() + Filtragem.filtro.extension).delete();
		}

		if (existe(Filtragem.filtro.getConcluido() + Filtragem.filtro.extension)){
			new File(Filtragem.filtro.getConcluido() + Filtragem.filtro.extension).delete();
		}

		if (existe(Filtragem.filtro.getIndefinido() + Filtragem.filtro.extension)){
			new File(Filtragem.filtro.getIndefinido() + Filtragem.filtro.extension).delete();
		}

		if (existe("buffer.txt")){
			new File("buffer.txt").delete();	
		}
	}
	
	public boolean ArqVazio(String caminho) {
		if (new File (caminho).length() < 5){
			return true;
		}
		return false;
	}
}

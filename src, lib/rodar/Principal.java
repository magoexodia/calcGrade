package rodar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import filtro.Filtragem;
import interfGrafica.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import banco.BancoDados;

public class Principal {
	public final static Principal prin = new Principal();
	
	public void criaBanco (){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				FazBanco janela = new FazBanco();
				janela.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent arg0) {
						prin.roda(BancoDados.bdados.getEnder());
					}
				});
				janela.setVisible(true);
			}
		});
	}
	
	public void adition (){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Adicao janela = new Adicao();
				janela.setVisible(true);
			}
		});
	}
	
	public void edition (String linha){
		final String lin = linha;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Edicao janela = new Edicao(lin);
				janela.setVisible(true);
			}
		});
	}
	
	public void roda (String caminho){
		if (new File (caminho).length() > 2 && BancoDados.bdados.existe(caminho)){
			Filtragem.filtro.verReqs(caminho);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//BaseListadora janela = new BaseListadora();
				PrimeiraJanela jana = new PrimeiraJanela();
				jana.setVisible(true);
			}
		});
	}
	
	public void lista (String disc, String[] pre){
		final String a = disc;
		final String[] b = pre;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RostoListaPercurso janela = new RostoListaPercurso(a, b);
				janela.setVisible(true);
			}
		});
	}
	
	public String abreCaminho() {
		JFileChooser arquivo = new JFileChooser();

		// configurando para que somente arquivos seja abertos.
		arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

		SwingUtilities.updateComponentTreeUI(arquivo);

		// definindo filtro de Extensao para abrir somente txt
		arquivo.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith(Filtragem.filtro.extension.toLowerCase())
				|| file.isDirectory();
			}

			@Override
			public String getDescription() {
				return Filtragem.filtro.extension;
			}
		});

		// impedir que o usuario possa escolher qualquer outro tipo de arquivo
		arquivo.setAcceptAllFileFilterUsed(false);

		// armazena a escolha do usuario.
		int resposta = arquivo.showOpenDialog(null);
		if (resposta == JFileChooser.APPROVE_OPTION) {
			return arquivo.getSelectedFile().getPath();
		} else if (resposta == JFileChooser.CANCEL_OPTION) {
		}
		return null;
	}
	
	public void executa() {
		
		/*
		 * Se Existe arquivo de configuração de endereço 
		 * Se contem uma linha não nula 
		 * E se o endereço existe Então roda() o programa principal.
		 */
		if (BancoDados.bdados.existe(BancoDados.bdados.getBanco())
				&& !BancoDados.bdados.getEnder().equals(null)
				&& BancoDados.bdados.existe(BancoDados.bdados.getEnder())) {
			prin.roda(BancoDados.bdados.getEnder());
		} else {
			// No outro caso, ou seja, positivo pra qualquer um dos problemas
			// acima
			int aceita = JOptionPane.showConfirmDialog(null, "H"
					+ Acentos.acentuar.aAgudo + " algum arquivo "
					+ Filtragem.filtro.extension
					+ " com sua lista de disciplinas?");

			switch (aceita) {
			case JOptionPane.YES_OPTION:
				// abre o pesquisador de arquivos e salva o caminho
				String caminho = abreCaminho();
				if (caminho == null) {
					JOptionPane.showMessageDialog(null,
							"Fechando o programa...");
					System.exit(0);
				}
				BancoDados.bdados.guardaLinha(caminho, false,
						BancoDados.bdados.getBanco());
				prin.roda(caminho);
				break;

			case JOptionPane.NO_OPTION:
				// Abre o criador de bancos e salva num caminho padrão
				JOptionPane.showMessageDialog(null, "OK, vamos cri"
						+ Acentos.acentuar.aAgudo + "...");
				BancoDados.bdados.guardaLinha(BancoDados.bdados.getEndereco(),
						false, BancoDados.bdados.getBanco());
				prin.criaBanco();
				break;

			default:
				//fecha o programa
				System.exit(0);
				break;
			}
		}
	}
	
	public void sobre (){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Sobre janela = new Sobre();
				janela.setVisible(true);
			}
		});
	}
	
	public static void main(String[] args) {
		prin.executa();		
		//Acentos.acentuar.mAchaLetra(513);
		prin.sobre();
		//prin.roda(BancoDados.bdados.getEnder());
	}
}

package rodar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import filtro.Filtragem;
import interfGrafica.Adicao;
import interfGrafica.Edicao;
import interfGrafica.FazBanco;
import interfGrafica.ListaPercurso;
import interfGrafica.PrimeiraJanela;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import banco.BancoDados;

@SuppressWarnings("unused")
public class Principal {
	
	public void criaBanco (){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				FazBanco janela = new FazBanco();
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
	
	public void roda (){
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
				ListaPercurso janela = new ListaPercurso(a, b);
				janela.setVisible(true);
				janela.setAlwaysOnTop(true);
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
				return file.getName().toLowerCase().endsWith(new Filtragem().extension)
				|| file.isDirectory();
			}

			@Override
			public String getDescription() {
				return "Planilhas " + new Filtragem().extension;
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
		BancoDados banco = new BancoDados();
		String caminho;
		
		if (!banco.existe(banco.getBanco()) || !banco.existe(banco.getEnder())){
			int aceita = JOptionPane.showConfirmDialog(null, "H\u00e1 algum arquivo " + new Filtragem().extension + " com sua lista de disciplinas?");
			if (aceita == JOptionPane.YES_OPTION){
				caminho = abreCaminho();
				if (caminho == null){
					JOptionPane.showMessageDialog(null, "Fechando o programa...");
					System.exit(0);
				}
				banco.guardaLinha(caminho, false, banco.getBanco());
				new Principal().roda();
			} else if (aceita == JOptionPane.NO_OPTION){
				JOptionPane.showMessageDialog(null, "OK, vamos cri\u00e1-lo...");
				banco.guardaLinha("saida" + (new Filtragem().extension), false, banco.getBanco());
				new Principal().criaBanco();
			} else if (aceita == JOptionPane.CANCEL_OPTION){
				System.exit(0);
			}
		} else {
			caminho = banco.getEnder();
			new Principal().roda();
		}
	}
	
	public void filEVer (String caminho){
		new Filtragem().verReqs(caminho);
		new Filtragem().filtraTudo(caminho);
	}
	
	public static void main(String[] args) {
		//String copiar = "ListaDisc.txt";
		//String destino = "buffer.txt";
		String saida = "saida.txt";
		//new Filtragem().testaPres(saida);
		//new Filtragem().listaReqs(saida);
		//new BancoDados().copiaArquivo(saida, destino);
	//	System.out.println(" quero foder o diabo que me fez apaixonar ".trim());
	new Principal().executa();
		//new Principal().lista(";;free", new String[] {";;nothing"});
		//new Filtragem().percurso(saida, new BancoDados().pegaObjeto(saida, 29));//29));
		//System.out.println(new Filtragem().paraNum(120368));
		
	}
}

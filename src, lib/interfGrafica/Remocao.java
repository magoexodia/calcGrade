package interfGrafica;

import banco.BancoDados;


@SuppressWarnings({ "unused", "serial" })
public class Remocao extends RostoEdicao{
	private String linha;
	private String caminho = new BancoDados().getEnder();
	
	public Remocao(String linha) {
		this.linha = linha;
		novo.setText("editar".toUpperCase());
		limpar.setVisible(false);
		concluir.setVisible(false);
		novo.setVisible(false);
		cancel.setVisible(false);
		seta(linha);
			
		setVisible(true);
	}
}

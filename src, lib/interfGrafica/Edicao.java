package interfGrafica;

import banco.BancoDados;


@SuppressWarnings("serial")
public class Edicao extends RostoEdicao {
	private String linha;
	private String caminho = new BancoDados().getEnder();
	
	@Override
	public void novo(){
		BancoDados banco = new BancoDados();
		String mudar = this.linha;
		String mudado = this.junta();
		int linha = banco.achaLinha(caminho, mudar) -1;
		
		banco.mudaLinha(banco.getEnder(), linha, mudado);
		limpar();
		setVisible(false);
		super.concluir();
	}
	
	public Edicao(String linha) {
		this.linha = linha;
		novo.setText("editar".toUpperCase());
		limpar.setVisible(false);
		concluir.setVisible(false);
		seta(linha);
			
		setVisible(true);		
	}
}

package interfGrafica;

import banco.BancoDados;


@SuppressWarnings("serial")
public class Edicao extends RostoEdicao {
	private String linha;
	private String caminho = BancoDados.bdados.getEnder();
	
	@Override
	public void novo(){
		String mudar = this.linha;
		String mudado = this.junta();
		int linha = BancoDados.bdados.achaLinha(caminho, mudar) -1;
		
		if (mudado.equals(mudar) || tudoVazio()){
			cancelar();
		} else {
			BancoDados.bdados.mudaLinha(BancoDados.bdados.getEnder(), linha,
					mudado);
			limpar();
			setVisible(false);
			super.concluir();
		}
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

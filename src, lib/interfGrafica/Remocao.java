package interfGrafica;

@SuppressWarnings("serial")
public class Remocao extends RostoEdicao{
	
	public Remocao(String linha) {
		novo.setText("editar".toUpperCase());
		limpar.setVisible(false);
		concluir.setVisible(false);
		novo.setVisible(false);
		cancel.setVisible(false);
		seta(linha);
			
		setVisible(true);
	}
}

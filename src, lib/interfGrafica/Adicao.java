package interfGrafica;

import java.awt.Dialog.ModalExclusionType;

import banco.BancoDados;

@SuppressWarnings("serial")
public class Adicao extends RostoEdicao{		
	@Override
	public void novo() {
		new BancoDados().guardaLinha(junta(), true, new BancoDados().getEnder());
		limpar();
		setVisible(false);
		concluir();
	}
	
	@Override
	public void concluir() {
		if (junta().contains(";;;")) {			
		} else{
			novo();
		}
		super.concluir();
	}
	
	public Adicao() {
		this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setVisible(true);
		cancel.setText("Cancelar");
		concluir.setVisible(false);
		limpar.setVisible(false);
		novo.setText("Salvar");
	}
}

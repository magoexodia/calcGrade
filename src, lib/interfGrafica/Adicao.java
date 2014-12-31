package interfGrafica;

import java.awt.Dialog.ModalExclusionType;

@SuppressWarnings("serial")
public class Adicao extends RostoEdicao{		
	@Override
	public void novo() {
		setVisible(false);
		if (salva(true)){
			concluir();
		} else {
			cancelar();
		}
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

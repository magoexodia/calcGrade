package interfGrafica;

import javax.swing.JOptionPane;

import banco.BancoDados;
import rodar.Principal;

@SuppressWarnings("serial")
public class FazBanco extends RostoEdicao{
	private boolean anexar = false;
	public void setAnexar(boolean anexar) {
		this.anexar = anexar;
	}
	
	@Override
	public void novo() {
		if (tudoVazio()) {
			JOptionPane
					.showMessageDialog(
							FazBanco.this,
							"Somente os pre-requisitos, ou o semestre, podem estar em branco"
									+ "\nPor favor corrija os dados desta disciplina.");
			return;
		}
		if (salva(this.anexar)) {
			setAnexar(true);
			limpar();
			JOptionPane.showMessageDialog(FazBanco.this, "Adicionado com sucesso!!!");
		}
	}
	
	@Override
	public void concluir() {
		if (!tudoVazio()){
			novo();
		}
		Principal.prin.roda(BancoDados.bdados.getEnder());
		super.concluir();
	}
	
	public FazBanco() {
		setSize(500, 500);
		cancel.setText("Adiar");
	}
}

package interfGrafica;

import javax.swing.JOptionPane;

import rodar.Principal;
import filtro.Filtragem;
import banco.BancoDados;

@SuppressWarnings("serial")
public class FazBanco extends RostoEdicao{		
	@Override
	public void novo() {
		if (junta().contains(";;;")) {
			JOptionPane
					.showMessageDialog(
							FazBanco.this,
							"Somente os pre-requisitos podem estar em branco"
									+ "\nPor favor corrija os dados desta disciplina.");
			return;
		}
		new BancoDados().guardaLinha(junta(), true, new BancoDados().getEnder());
		limpar();
		JOptionPane.showMessageDialog(FazBanco.this, "Adicionado com sucesso!!!");
	}
	
	@Override
	public void concluir() {
		if (junta().contains(";;;")) {			
		} else{
			novo();
		}
		new Filtragem().verReqs(new BancoDados().getEnder());
		new Principal().roda();
		super.concluir();
	}
	
	public FazBanco() {
		setSize(500, 500);
		cancel.setText("Adiar");
	}
}

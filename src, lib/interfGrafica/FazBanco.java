package interfGrafica;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JOptionPane;

import rodar.Acentos;
import banco.BancoDados;

@SuppressWarnings("serial")
public class FazBanco extends RostoEdicao{
	private boolean anexar = false;
	public void setAnexar(boolean anexar) {
		this.anexar = anexar;
	}
	
	@Override
	public void novo() {
		if (invalido()) {
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
		if (!invalido()){
			novo();
		} else {
			if (new File (BancoDados.bdados.getEndereco()).length() < 2){
				JOptionPane.showMessageDialog(FazBanco.this,
						"Por favor, ao menos inclua um c"
								+ Acentos.acentuar.oAgudo
								+ "digo e um nome de disciplina.");
				return;
			}
		}
		super.concluir();
	}
	
	public FazBanco() {
		String[] ini = {"Codigo", "nome da disciplina", "requisito1 requisito2 requisito3", "horas","semestre"};
		cancel.setVisible(false);
		cod.setText(ini[0]);
		nome.setText(ini[1]);
		req.setText(ini[2]);
		hora.setText(ini[3]);
		sem.setText(ini[4]);
		cod.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cod.setText(null);
			}
		});
		nome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				nome.setText(null);
			}
		});
		req.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				req.setText(null);
			}
		});
		hora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				hora.setText(null);
			}
		});
		sem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sem.setText(null);
			}
		});
		setSize(500, 500);
		cancel.setText("Adiar");
	}
}

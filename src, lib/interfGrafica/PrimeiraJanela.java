package interfGrafica;


@SuppressWarnings("serial")
public class PrimeiraJanela extends BaseListadora{
	public PrimeiraJanela(){
		setSize(600,600);
		setVisible(true);
		setLocationRelativeTo(null);
		btnAdd.setText("Adicionar".toUpperCase());
		btnRem.setText("Remover".toUpperCase());
		btnEdi.setText("Editar".toUpperCase());
		btnSem.setText("Tudo".toUpperCase());
		btnAbe.setText("Abertas".toUpperCase());
		btnCum.setText("terminadas".toUpperCase());
		btnTra.setText("Bloqueadas".toUpperCase());
		btnPer.setText("Percurso at\u00e9...".toUpperCase());
		tglbtnFil.setText("Filtros".toUpperCase());
		
		btnAdd.setToolTipText("Adicionar uma nova disciplina a lista.");
		btnRem.setToolTipText("Remover a disciplina selecionada.");
		btnEdi.setToolTipText("Editar a disciplina selecionada.");
		btnSem.setToolTipText("Mostrar todas as disciplinas da lista.");
		btnAbe.setToolTipText("Mostrar somente as disciplinas que se pode fazer no pr\u00f3ximo per\u00edodo letivo.");
		btnCum.setToolTipText("Mostrar somente o que j\u00e0 foi feito e cumprido com sucesso.");
		btnTra.setToolTipText("Mostra somente o que ainda falta cumprir.");
		btnPer.setToolTipText("Mostra o caminho: todas as disciplinas que devem ser feitas para chegar na disciplina selecionada.");
		tglbtnFil.setToolTipText("Algumas op\u00e7\u00f5es de filtro/listagem para voc\u00ea ver melhor a sua lista de disciplinas.");
		
	}
}

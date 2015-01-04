package interfGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import rodar.Acentos;
import rodar.Principal;
import banco.BancoDados;
import filtro.Filtragem;

@SuppressWarnings("serial")
public class RostoPrimeiraJanela extends JFrame {
	private JSplitPane splitPane_1;
	private JSplitPane splitPane_2;
	private JScrollPane scrollPane;
	private JList<String> list; // A lista selecionavel com as disciplinas
	private JPanel panel;
	protected JButton btnAdd, btnRem, btnEdi, btnSem, btnAbe, btnCum, btnTra, btnPer, btnResetar, btnSair; // op��es
	protected JToggleButton tglbtnFil; // tglButton que esconde as op��es de filtragem
	private String caminho = BancoDados.bdados.getEnder(); // endere�o do banco com a lista
	private String filtrado = caminho; // onde vai o caminho filtrado

	/*********************************************************************/
	public String getFiltrado() {
		return filtrado;
	}
	/*********************************************************************/
	public String getCaminho() {
		return caminho;
	}
	/*********************************************************************/
	private void habilita (boolean tf){
		btnPer.setEnabled(tf);
		btnRem.setEnabled(tf);
		btnEdi.setEnabled(tf);
	}
	/*********************************************************************/
	private void visivel (boolean tf){
		btnSem.setVisible(tf);
		btnTra.setVisible(tf);
		btnAbe.setVisible(tf);
		btnCum.setVisible(tf);
	}
	/*********************************************************************/
	private void muda (){
		btnSem.setEnabled(!filtrado.contains(caminho));
		btnTra.setEnabled(!filtrado.contains(Filtragem.filtro.getIndefinido()));
		btnAbe.setEnabled(!filtrado.contains(Filtragem.filtro.getAberto()));
		btnCum.setEnabled(!filtrado.contains(Filtragem.filtro.getConcluido()));
	}
	/*********************************************************************/
	private void remover() {
		int indFiltrado = list.getSelectedIndex() +1;
		String termoFiltrado = BancoDados.bdados.pegaObjeto(filtrado, indFiltrado);
		
		
		Remocao rem = new Remocao(termoFiltrado);
		
		int ace = JOptionPane.showConfirmDialog(rem, "Voc"+ Acentos.acentuar.eCirc +" tem certeza que deseja\nexcluir esta disciplina??");
		if (ace == JOptionPane.NO_OPTION || ace == JOptionPane.CANCEL_OPTION) {
			rem.setVisible(false);
		} else if (ace == JOptionPane.YES_OPTION){
			BancoDados.bdados.removeLinha(this.caminho, termoFiltrado);
			JOptionPane.showMessageDialog(rem, termoFiltrado + "\n"
					+ "removido com sucesso!");
			rem.setVisible(false);
			reseta();
		}
	}
	/*********************************************************************/
	private void Editar() {		
		int indFiltrado = list.getSelectedIndex() +1;
		String termoFiltrado = BancoDados.bdados.pegaObjeto(filtrado, indFiltrado);
		
		
		Edicao edit = new Edicao(termoFiltrado);
		edit.setVisible(true);
		edit.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				Filtragem.filtro.verReqs(caminho);
				reseta();
			}
		});
	}
	/*********************************************************************/
	private String[] objeta() {
		String[] objetos = Filtragem.filtro.objeta(this.filtrado);
		
		if (objetos == null){
			JOptionPane.showMessageDialog(null, "Lista Vazia...\nNada nesta lista.");
			filtrado = getCaminho();
			objetos = Filtragem.filtro.objeta(this.filtrado);
		}
		
		return objetos;
	}
	/*********************************************************************/
	public void reseta(){
		
		list.setModel(new AbstractListModel<String>() {
			String[] values = objeta();
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		habilita(false);
	}
	/*********************************************************************/
	public void busca (String digit){
		List<String> obj = BancoDados.bdados.pegaObjetoList(this.filtrado);
		
		for (String um : obj) {
			if (um.contains(digit)){
				list.setSelectedIndex(obj.indexOf(um));
			}
		}
	}
	/*********************************************************************/
	public void doMouse() {
		tglbtnFil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				visivel(tglbtnFil.isSelected());
			}
		});
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Filtragem().verReqs(caminho);
				Adicao add = new Adicao();
				add.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent arg0) {
						Filtragem.filtro.verReqs(caminho);
						reseta();
					}
				});
			}
		});
		btnRem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				remover();
			}
		});
		btnEdi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Editar();
			}
		});
		btnSem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				filtrado = caminho;
				reseta();
				muda();
			}
		});
		btnAbe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				filtrado = (Filtragem.filtro.getAberto()) + (Filtragem.filtro.extension);
				reseta();
				muda();
			}
		});
		btnCum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				filtrado = (Filtragem.filtro.getConcluido()) + (Filtragem.filtro.extension);
				reseta();
				muda();
			}
		});
		btnTra.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				filtrado = (Filtragem.filtro.getIndefinido()) + (Filtragem.filtro.extension);
				reseta();
				muda();
			}
		});
		btnPer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				procuraFiltro();
				muda();
			}
		});
		btnResetar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				reseta();
			}
		});
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				BancoDados.bdados.limpaArquivos();
				System.exit(RostoPrimeiraJanela.this.getDefaultCloseOperation());
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				habilita(!list.isSelectionEmpty());
			}
		});
	}
	/*********************************************************************/
	private void procuraFiltro() {
		String disc;
		int indFiltrado;
		
		indFiltrado = list.getSelectedIndex() + 1;
		disc = BancoDados.bdados.pegaObjeto(this.filtrado, indFiltrado);
		
		Principal.prin.lista(disc, Filtragem.filtro.percurso(caminho, disc));
	}
	/*********************************************************************/
	public RostoPrimeiraJanela (){
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(10);
		getContentPane().setFont(new Font("Liberation Mono", Font.PLAIN, 14));
		
		splitPane_1 = new JSplitPane();
		splitPane_1.setOneTouchExpandable(true);
		splitPane_1.setResizeWeight(0.5);
		getContentPane().add(splitPane_1, BorderLayout.SOUTH);
		
		btnSair = new JButton("Sair");
		splitPane_1.setRightComponent(btnSair);
		
		btnResetar = new JButton("Resetar");
		splitPane_1.setLeftComponent(btnResetar);
		
		splitPane_2 = new JSplitPane();
		splitPane_2.setOneTouchExpandable(true);
		splitPane_2.setResizeWeight(0.5);
		getContentPane().add(splitPane_2, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		splitPane_2.setRightComponent(scrollPane);
		
		list = new JList<String>();
		list.setFont(new Font("Monospaced", Font.PLAIN, 14));
		list.setModel(new AbstractListModel<String>() {
			String[] values = objeta();
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JLabel label = new JLabel("     sem     codigo     nome da disciplina => pre-requisitos".toUpperCase());
		label.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane.setColumnHeaderView(label);
		
		panel = new JPanel();
		splitPane_2.setLeftComponent(panel);
		panel.setLayout(null);
		
		btnAdd = new JButton("+");
		btnAdd.setBounds(20, 11, 161, 23);
		panel.add(btnAdd);
		
		btnRem = new JButton("-");
		btnRem.setBounds(30, 45, 138, 23);
		panel.add(btnRem);
		
		btnEdi = new JButton("Edi");
		btnEdi.setBounds(40, 79, 121, 23);
		panel.add(btnEdi);
		
		btnSem = new JButton("Sem");
		btnSem.setBounds(20, 240, 121, 23);
		panel.add(btnSem);
		
		tglbtnFil = new JToggleButton("Fil");
		tglbtnFil.setBounds(20, 206, 161, 23);
		panel.add(tglbtnFil);
		
		btnAbe = new JButton("Abe");
		btnAbe.setBounds(60, 274, 121, 23);
		panel.add(btnAbe);
		
		btnCum = new JButton("Cum");
		btnCum.setBounds(20, 308, 121, 23);
		panel.add(btnCum);
		
		btnTra = new JButton("Tra");
		btnTra.setBounds(60, 342, 121, 23);
		panel.add(btnTra);
		
		btnPer = new JButton("Per");
		btnPer.setBounds(20, 135, 161, 32);
		panel.add(btnPer);
		
		JButton btnBusca = new JButton("busca".toUpperCase());
		btnBusca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RostoBusca jan = new RostoBusca();
				jan.setVisible(true);
				jan.setLocationRelativeTo(getContentPane());
			}
		});
		btnBusca.setBounds(20, 398, 89, 23);
		panel.add(btnBusca);
		
		doMouse();
		habilita(false);
		visivel(false);
		muda();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFont(new Font("Times New Roman", Font.PLAIN, 13));
		setTitle("Lista de Disciplinas");
	}
}

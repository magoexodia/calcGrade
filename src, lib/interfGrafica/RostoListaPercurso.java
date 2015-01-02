package interfGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import banco.BancoDados;

import rodar.Acentos;
import rodar.Principal;

import filtro.Filtragem;

@SuppressWarnings("serial")
public class RostoListaPercurso extends JFrame {
	private JList<String> lista2;
	
	private String[] lista;
	
	public String[] getLista() {
		return lista;
	}

	public void setLista(String[] lista) {
		this.lista = lista;
	}

	private String[] organiza(String[] lista) {
		List<String> reor = new ArrayList<String>();
		
		for (int i = 0; i < lista.length; i++) {
			reor.add(lista[i]);
		}
		
		Collections.sort(reor);
		
		for (String um : reor) {
			lista[reor.indexOf(um)] = um;
		}
		
		return lista;
	}
	
	private String[] transforma (String[] objetos){
		String[] lista = new String [objetos.length];
		for (int i = 0; i < lista.length; i++) {
			if(objetos[i].contains(Filtragem.filtro.semPre)){
				lista[i] = "Sem pre-requisitos";
			} else if (objetos[i].contains(Filtragem.filtro.sep1)){
			lista[i] = (objetos[i].split(Filtragem.filtro.sep1)[5].equals(Filtragem.filtro.getConcluido())?"OK":"falta") + " => "
					+ objetos[i].split(Filtragem.filtro.sep1)[0] + Acentos.acentuar.oOrdin + "sem "
					+ objetos[i].split(Filtragem.filtro.sep1)[1] + ": "
					+ objetos[i].split(Filtragem.filtro.sep1)[2];	
			} else {
				lista[i] = objetos[i];
			}
		}
		return lista;
	}
	
	private String[] lista() {
		return transforma(organiza(getLista()));
	}
	
	private void percorre() {
		int indFiltrado = lista2.getSelectedIndex();
		String disc = this.getLista()[indFiltrado];
		Principal.prin.lista(disc, Filtragem.filtro.percurso(BancoDados.bdados.getEnder(), disc));
	}
	
	public RostoListaPercurso(String disc, String[] lista) {
		this.setLista(lista); 
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		setSize(750, 500);
		setTitle(disc.split(new Filtragem().sep1)[2]);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JLabel lblLista = new JLabel("lista");
		lblLista.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblLista.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblLista);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.9);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		scrollPane.setViewportView(splitPane);
		
		lista2 = new JList<String>();
		lista2.setModel(new AbstractListModel<String>() {
			String[] values = lista();
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		lista2.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		splitPane.setLeftComponent(lista2);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JButton btnPercurso = new JButton("Percurso");
		btnPercurso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				percorre();
			}
		});
		btnPercurso.setBounds(254, 11, 135, 23);
		panel.add(btnPercurso);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(399, 11, 89, 23);
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		panel.add(btnSair);
	}
}

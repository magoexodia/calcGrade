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

import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class RostoListaPercurso extends JFrame {
	private JList<String> lista2;
	private String a1 = "Aberto\t=> ", a2 = "\t=> ", a3 = "OK\t=> ";
		
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
			lista[i] = (objetos[i].split(Filtragem.filtro.sep1)[5].equals(Filtragem.filtro.getConcluido())?"__OK_ =>":"falta <=") + " ("
					+ objetos[i].split(Filtragem.filtro.sep1)[0] + Acentos.acentuar.oOrdin + "sem) ["
					+ objetos[i].split(Filtragem.filtro.sep1)[1] + "]: "
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
	
	private String layOut (String um){
		String quem = um.split(Filtragem.filtro.sep1)[5];
		
		return ((quem.equals(Filtragem.filtro.getAberto())?a1:quem.equals(Filtragem.filtro.getConcluido())?a3:a2)
		+ " ("
		+ um.split(Filtragem.filtro.sep1)[0] 
		+ Acentos.acentuar.oOrdin+"sem) ["
		+ um.split(Filtragem.filtro.sep1)[1] 
		+ "] "
		+ um.split(Filtragem.filtro.sep1)[2] 
		+ " => {"
		+ um.split(Filtragem.filtro.sep1)[3].replaceAll(Filtragem.filtro.sep2, ", ")
		+ "}");
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
		
		JLabel lblLista = new JLabel(layOut(disc));
		lblLista.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		lblLista.setHorizontalAlignment(SwingConstants.LEFT);
		scrollPane.setColumnHeaderView(lblLista);
		
		JButton btnPercurso = new JButton("Percurso");
		btnPercurso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				percorre();
			}
		});
		btnPercurso.setBounds(254, 11, 135, 23);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		scrollPane.setViewportView(splitPane);
		if (lista[0].equals(Filtragem.filtro.semPre)){			
			JTextPane txtpnSpre = new JTextPane();
			txtpnSpre.setBackground(Color.GRAY);
			txtpnSpre.setForeground(Color.GREEN);
			txtpnSpre.setFont(new Font("Monospaced", Font.PLAIN, 20));
			txtpnSpre.setText("\n\n\n\t\t\tSem\n\n\t\tpre-requisitos");
			txtpnSpre.setEditable(false);
			txtpnSpre.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent key) {
					if (key.getKeyCode() == 27 || key.getKeyCode() == 10){
						dispose();
					}
				}
			});
			splitPane.setLeftComponent(txtpnSpre);
			btnPercurso.setVisible(false);
		} else {
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
			lista2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					percorre();
				}
			});
			lista2.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent key) {
					if (key.getKeyCode() == 27){
						dispose();
					}
				}
			});
			lista2.setBackground(new Color(125, 255, 123));
			splitPane.setLeftComponent(lista2);
		}
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(399, 11, 89, 23);
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		panel.add(btnSair);
		panel.add(btnPercurso);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(140, 0, 41, 43);
		panel.add(verticalStrut);
	}
}

package interfGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import rodar.Principal;
import banco.BancoDados;
import filtro.Filtragem;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class RostoBusca extends JFrame {
	private JTextField txtfBusca;
	private JList<String> list;
	private String[] lista = BancoDados.bdados.pegaObjeto(BancoDados.bdados.getEnder());
	
	private void busca() {
		list.setModel(new AbstractListModel<String>() {
			String[] values = Filtragem.filtro.filtra(txtfBusca.getText().toUpperCase(), lista);
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
	}
	
	private void buscaReqs(String valor) {
		String sub = valor.substring(valor.indexOf("]")+2, valor.indexOf("=")-1);		
		String[] ob = BancoDados.bdados.pegaObjeto(BancoDados.bdados.getEnder());
		int i;
		for ( i = 0; i < ob.length; i++) {
			if (ob[i].contains(sub)){
				Principal.prin.lista(ob[i], Filtragem.filtro.percurso(BancoDados.bdados.getEnder(), ob[i]));
				return;
			}
		}
	}
	
	public RostoBusca() {
		setVisible(true);
		setSize(500,500);
		setTitle("Sistema de busca de disciplina individual");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		txtfBusca = new JTextField();
		txtfBusca.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				txtfBusca.setText("Clique aqui para fazer sua busca ou nos itens abaixo para ver seu percurso");
			}
		});
		scrollPane.setColumnHeaderView(txtfBusca);
		txtfBusca.setColumns(10);
		txtfBusca.setText("Clique aqui para fazer sua busca ou nos itens abaixo para ver seu percurso");
		txtfBusca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtfBusca.setText(null);
			}
		});
		txtfBusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent key) {
					busca();
			}
		});
		txtfBusca.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfBusca.setColumns(100);
		
		list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				buscaReqs(list.getSelectedValue());
			}
		});
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent key) {
				if (key.getKeyCode() == 10){
					buscaReqs(list.getSelectedValue());
				}
			}
		});
		scrollPane.setViewportView(list);
		list.setFont(new Font("Monospaced", Font.PLAIN, 16));
		list.setModel(new AbstractListModel<String>() {
			String[] values = Filtragem.filtro.objeta(lista);
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBackground(new Color(125, 255, 123));
	}
}

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

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import filtro.Filtragem;

@SuppressWarnings("serial")
public class ListaPercurso extends JFrame {
	private JTextPane txtpnNada;
	private String t1 = new Filtragem().sep1;
	public void setTxtpnNada(String txtpnNada) {
		this.txtpnNada.setText(txtpnNada);
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
	
	
	public ListaPercurso(String disc, String[] lista) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setTxtpnNada(null);
				dispose();
			}
		});
		setSize(500, 500);
		setTitle(disc.split(new Filtragem().sep1)[2]);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		lista = organiza(lista);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		txtpnNada = new JTextPane();
		txtpnNada.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtpnNada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		txtpnNada.setEditable(false);
		txtpnNada.setText("Disciplinas que devem ser cursadas antes:\n"
				+ "=======================================================\n"
				+ "sem\tCodigo\tNome\n"
				+ "===\t======\t====\n");
		for (int i = 0; i < lista.length; i++) {
			txtpnNada.setText(txtpnNada.getText()
					+ lista[i].split(t1)[0] + "\t"
					+ lista[i].split(t1)[1] + "\t"
					+ lista[i].split(t1)[2] + "\n");			
		}
		txtpnNada.setText(txtpnNada.getText() 
				+ "=======================================================\n");
		scrollPane.setViewportView(txtpnNada);
	}
}

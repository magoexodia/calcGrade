package interfGrafica;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import rodar.Acentos;

/**
 * @author marinaldo {@linkplain}www.facebook.com/marimagh
 * @since 2014-12
 * @version 1.0
 * @category Utilidades para estudantes
 * 
 */
public class Sobre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sobre() {
		setAlwaysOnTop(true);
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
			}
			public void windowLostFocus(WindowEvent e) {
				dispose();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				dispose();
			}
		});
		setVisible(true);
		setSize(400, 300);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTextPane sobre = new JTextPane();
		sobre.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 15));
		sobre.setText("Sobre o autor: \n" +
				"\tMarinaldo, estudante de Engenharia da computa"+Acentos.acentuar.cedilMin+Acentos.acentuar.aTil+"o pela UFBA.\n\n" +
				"Sobre este projeto:\n\n\tEste " +
				Acentos.acentuar.eAgudo+" um programa para ajudar os estudantes universit" +
						Acentos.acentuar.aAgudo+"rios a organizar\n" +
						"suas grades curriculares do(s) pr"+Acentos.acentuar.oAgudo+"ximo(s) semestre(s) ou per"
				+Acentos.acentuar.iAgudo+"odo(s) letivo(s).\n\n\n");
		scrollPane.setViewportView(sobre);
	}
}
